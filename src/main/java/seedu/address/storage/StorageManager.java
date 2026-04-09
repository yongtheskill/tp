package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of PingBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private final AddressBookStorage addressBookStorage;
    private final UserPrefsStorage userPrefsStorage;
    private final AliasStorage aliasStorage;

    /**
     * Creates a {@code StorageManager} with the given storages.
     */
    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage,
            AliasStorage aliasStorage) {
        Objects.requireNonNull(addressBookStorage);
        Objects.requireNonNull(userPrefsStorage);
        Objects.requireNonNull(aliasStorage);
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.aliasStorage = aliasStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ PingBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    // ================ AliasStorage methods ==============================

    @Override
    public Path getAliasesFilePath() {
        return aliasStorage.getAliasesFilePath();
    }

    @Override
    public Optional<Map<String, String>> readAliases() throws DataLoadingException {
        logger.fine("Attempting to read aliases from file");
        return aliasStorage.readAliases();
    }

    @Override
    public void saveAliases(Map<String, String> aliases) throws IOException {
        logger.fine("Attempting to save aliases to file");
        aliasStorage.saveAliases(aliases);
    }

    // ================ Combined save ==============================

    /**
     * Saves both the address book and alias map, backing up both files beforehand and
      * restoring them on failure when an {@link IOException} is detected so the two files
      * remain in a mutually consistent state on a best-effort basis.
     */
    @Override
    public void saveAll(ReadOnlyAddressBook addressBook, Map<String, String> aliases) throws IOException {
        Objects.requireNonNull(addressBook, "addressBook");
        Objects.requireNonNull(aliases, "aliases");

        Path abPath = addressBookStorage.getAddressBookFilePath();
        Path aliasPath = aliasStorage.getAliasesFilePath();

        // Guard: shared file path requires both storages to be JsonPingBookStorage.
        if (abPath.equals(aliasPath)
                && !(addressBookStorage instanceof JsonPingBookStorage
                        && aliasStorage instanceof JsonPingBookStorage)) {
            throw new IllegalStateException(
                    "Shared-path storage configuration requires both addressBookStorage and aliasStorage"
                    + " to be JsonPingBookStorage for a safe combined write to " + abPath);
        }

        // When both storages share the same file (e.g. JsonPingBookStorage), do a single atomic write.
        if (abPath.equals(aliasPath) && addressBookStorage instanceof JsonPingBookStorage
                && aliasStorage instanceof JsonPingBookStorage) {
            JsonPingBookStorage combined = (JsonPingBookStorage) addressBookStorage;
            Path backupPath = abPath.resolveSibling(abPath.getFileName().toString() + ".bak");
            boolean hadExisting = Files.exists(abPath);
            if (hadExisting) {
                Files.copy(abPath, backupPath, StandardCopyOption.REPLACE_EXISTING);
            }
            try {
                combined.saveAll(addressBook, aliases);
            } catch (IOException e) {
                if (hadExisting) {
                    try {
                        Files.move(backupPath, abPath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException restoreEx) {
                        e.addSuppressed(restoreEx);
                        logger.warning("Could not restore pingbook backup after partial save: "
                                + restoreEx.getMessage());
                    }
                } else {
                    try {
                        Files.deleteIfExists(abPath);
                    } catch (IOException cleanupEx) {
                        e.addSuppressed(cleanupEx);
                    }
                }
                throw e;
            }
            try {
                Files.deleteIfExists(backupPath);
            } catch (IOException ignore) {
                logger.fine("Could not delete pingbook backup after successful save.");
            }
            return;
        }

        Path abBackupPath = abPath.resolveSibling(abPath.getFileName().toString() + ".bak");
        boolean hadExistingAbFile = Files.exists(abPath);
        if (hadExistingAbFile) {
            Files.copy(abPath, abBackupPath, StandardCopyOption.REPLACE_EXISTING);
        }

        Path aliasBackupPath = aliasPath.resolveSibling(aliasPath.getFileName().toString() + ".bak");
        boolean hadExistingAliasFile = Files.exists(aliasPath);
        if (hadExistingAliasFile) {
            Files.copy(aliasPath, aliasBackupPath, StandardCopyOption.REPLACE_EXISTING);
        }

        try {
            saveAddressBook(addressBook);
            saveAliases(aliases);
        } catch (IOException e) {
            if (hadExistingAbFile) {
                try {
                    Files.move(abBackupPath, abPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException restoreEx) {
                    e.addSuppressed(restoreEx);
                    logger.warning("Could not restore address book backup after partial save: "
                            + restoreEx.getMessage());
                }
            } else {
                try {
                    Files.deleteIfExists(abPath);
                } catch (IOException cleanupEx) {
                    e.addSuppressed(cleanupEx);
                    logger.warning("Could not clean up partial address book write: "
                            + cleanupEx.getMessage());
                }
            }
            if (hadExistingAliasFile) {
                try {
                    Files.move(aliasBackupPath, aliasPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException restoreEx) {
                    e.addSuppressed(restoreEx);
                    logger.warning("Could not restore aliases backup after partial save: "
                            + restoreEx.getMessage());
                }
            } else {
                try {
                    Files.deleteIfExists(aliasPath);
                } catch (IOException cleanupEx) {
                    e.addSuppressed(cleanupEx);
                    logger.warning("Could not clean up partial aliases write: "
                            + cleanupEx.getMessage());
                }
            }
            throw e;
        }
        try {
            Files.deleteIfExists(abBackupPath);
        } catch (IOException ignore) {
            logger.fine("Could not delete address book backup after successful save.");
        }
        try {
            Files.deleteIfExists(aliasBackupPath);
        } catch (IOException ignore) {
            logger.fine("Could not delete aliases backup after successful save.");
        }
    }

}
