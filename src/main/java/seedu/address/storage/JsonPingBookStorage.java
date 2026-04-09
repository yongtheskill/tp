package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Stores both the address book (persons) and aliases in a single JSON file.
 * Implements both {@link AddressBookStorage} and {@link AliasStorage}.
 */
public class JsonPingBookStorage implements AddressBookStorage, AliasStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonPingBookStorage.class);

    private final Path filePath;

    /**
     * Creates a {@code JsonPingBookStorage} backed by the given file path.
     */
    public JsonPingBookStorage(Path filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    // ==================== AddressBookStorage ====================

    @Override
    public Path getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(filePath);
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);
        try {
            return readAddressBookUnchecked(filePath);
        } catch (DataLoadingException primaryException) {
            return readAddressBookFromBackup(filePath, primaryException);
        }
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);
        Map<String, String> existingAliases;
        try {
            existingAliases = loadExistingAliases(filePath);
        } catch (DataLoadingException e) {
            throw new IOException("Malformed data file at " + filePath
                    + "; aborting save to prevent alias loss: " + e.getMessage(), e);
        }
        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePingBook(addressBook, existingAliases), filePath);
    }

    // ==================== AliasStorage ====================

    @Override
    public Path getAliasesFilePath() {
        return filePath;
    }

    @Override
    public Optional<Map<String, String>> readAliases() throws DataLoadingException {
        return readAliases(filePath);
    }

    private Optional<Map<String, String>> readAliases(Path filePath) throws DataLoadingException {
        try {
            return readAliasesUnchecked(filePath);
        } catch (DataLoadingException primaryException) {
            return readAliasesFromBackup(filePath, primaryException);
        }
    }

    @Override
    public void saveAliases(Map<String, String> aliases) throws IOException {
        requireNonNull(aliases);
        Optional<ReadOnlyAddressBook> existingAb;
        if (Files.exists(filePath)) {
            try {
                existingAb = readAddressBook(filePath);
            } catch (DataLoadingException e) {
                throw new IOException("Malformed data file at " + filePath
                        + "; aborting save to prevent data loss: " + e.getMessage(), e);
            }
        } else {
            existingAb = Optional.empty();
        }
        ReadOnlyAddressBook ab = existingAb.orElseGet(seedu.address.model.AddressBook::new);
        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePingBook(ab, aliases), filePath);
    }

    // ==================== Combined save ====================

    /**
     * Saves both address book and aliases atomically in a single write.
     */
    public void saveAll(ReadOnlyAddressBook addressBook, Map<String, String> aliases) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(aliases);
        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePingBook(addressBook, aliases), filePath);
    }

    private Map<String, String> loadExistingAliases(Path filePath) throws DataLoadingException {
        Optional<JsonSerializablePingBook> existing =
                JsonUtil.readJsonFile(filePath, JsonSerializablePingBook.class);
        return existing.map(JsonSerializablePingBook::getAliases).orElseGet(HashMap::new);
    }

    private Optional<ReadOnlyAddressBook> readAddressBookUnchecked(Path filePath) throws DataLoadingException {
        Optional<JsonSerializablePingBook> jsonPingBook =
                JsonUtil.readJsonFile(filePath, JsonSerializablePingBook.class);
        if (!jsonPingBook.isPresent()) {
            return Optional.empty();
        }
        try {
            return Optional.of(jsonPingBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    private Optional<Map<String, String>> readAliasesUnchecked(Path filePath) throws DataLoadingException {
        Optional<JsonSerializablePingBook> jsonPingBook =
                JsonUtil.readJsonFile(filePath, JsonSerializablePingBook.class);
        return jsonPingBook.map(JsonSerializablePingBook::getAliases);
    }

    private Optional<ReadOnlyAddressBook> readAddressBookFromBackup(Path primaryPath,
            DataLoadingException primaryException) throws DataLoadingException {
        Path backupPath = getBackupPath(primaryPath);
        if (backupPath == null) {
            logger.warning("Cannot derive backupPath for primaryPath " + primaryPath
                    + " while recovering PingBook contacts; rethrowing the original load failure.");
            throw primaryException;
        }
        if (isBackupPath(primaryPath) || !Files.exists(backupPath)) {
            throw primaryException;
        }

        logger.warning("Primary PingBook file could not be loaded from " + primaryPath
                + "; attempting recovery from backup " + backupPath);
        try {
            Optional<ReadOnlyAddressBook> recoveredAddressBook = readAddressBookUnchecked(backupPath);
            if (recoveredAddressBook.isPresent()) {
                logger.warning("Recovered PingBook contacts from backup file " + backupPath);
                return recoveredAddressBook;
            }
            logger.warning("Backup PingBook file " + backupPath
                    + " was readable but contained no contacts recoverable for " + primaryPath);
        } catch (DataLoadingException backupException) {
            primaryException.addSuppressed(backupException);
            logger.warning("Backup PingBook file could not be loaded from " + backupPath);
        }

        throw primaryException;
    }

    private Optional<Map<String, String>> readAliasesFromBackup(Path primaryPath,
            DataLoadingException primaryException) throws DataLoadingException {
        Path backupPath = getBackupPath(primaryPath);
        if (backupPath == null) {
            logger.warning("Cannot derive backupPath for primaryPath " + primaryPath
                    + " while recovering PingBook aliases; rethrowing the original load failure.");
            throw primaryException;
        }
        if (isBackupPath(primaryPath) || !Files.exists(backupPath)) {
            throw primaryException;
        }

        logger.warning("Primary alias data could not be loaded from " + primaryPath
                + "; attempting recovery from backup " + backupPath);
        try {
            Optional<Map<String, String>> recoveredAliases = readAliasesUnchecked(backupPath);
            if (recoveredAliases.isPresent()) {
                logger.warning("Recovered PingBook aliases from backup file " + backupPath);
                return recoveredAliases;
            }
            logger.warning("Backup alias data at " + backupPath
                    + " was readable but contained no recoverable aliases for " + primaryPath);
        } catch (DataLoadingException backupException) {
            primaryException.addSuppressed(backupException);
            logger.warning("Backup alias data could not be loaded from " + backupPath);
        }

        throw primaryException;
    }

    private Path getBackupPath(Path path) {
        Path fileName = path.getFileName();
        if (fileName == null) {
            return null;
        }
        return path.resolveSibling(fileName.toString() + ".bak");
    }

    private boolean isBackupPath(Path path) {
        Path fileName = path.getFileName();
        return fileName != null && fileName.toString().endsWith(".bak");
    }

}
