package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

public class JsonPingBookStorageTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonPingBookStorageTest");

    @TempDir
    public Path testFolder;

    // ===================== helpers =====================

    private Path tempPath(String name) {
        return testFolder.resolve(name);
    }

    private Path testDataPath(String name) {
        return TEST_DATA_FOLDER.resolve(name);
    }

    private JsonPingBookStorage storageAt(Path path) {
        return new JsonPingBookStorage(path);
    }

    // ===================== constructor =====================

    @Test
    public void constructor_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JsonPingBookStorage(null));
    }

    // ===================== getAddressBookFilePath / getAliasesFilePath =====================

    @Test
    public void getAddressBookFilePath_returnsConfiguredPath() {
        Path path = tempPath("pingbook.json");
        assertEquals(path, storageAt(path).getAddressBookFilePath());
    }

    @Test
    public void getAliasesFilePath_sameAsAddressBookFilePath() {
        Path path = tempPath("pingbook.json");
        JsonPingBookStorage storage = storageAt(path);
        assertEquals(storage.getAddressBookFilePath(), storage.getAliasesFilePath());
    }

    // ===================== readAddressBook =====================

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        JsonPingBookStorage storage = storageAt(tempPath("any.json"));
        assertThrows(NullPointerException.class, () -> storage.readAddressBook(null));
    }

    @Test
    public void readAddressBook_missingFile_returnsEmptyOptional() throws Exception {
        assertFalse(storageAt(tempPath("nonExistent.json")).readAddressBook().isPresent());
    }

    @Test
    public void readAddressBook_notJsonFormat_throwsDataLoadingException() {
        assertThrows(DataLoadingException.class, () ->
                storageAt(testDataPath("notJsonFormatPingBook.json")).readAddressBook());
    }

    @Test
    public void readAddressBook_invalidPerson_throwsDataLoadingException() {
        assertThrows(DataLoadingException.class, () ->
                storageAt(testDataPath("invalidPersonPingBook.json")).readAddressBook());
    }

    @Test
    public void readAddressBook_malformedPrimaryWithValidBackup_recoversFromBackup() throws Exception {
        Path filePath = tempPath("pingbook.json");
        Path backupPath = tempPath("pingbook.json.bak");
        AddressBook backupAddressBook = getTypicalAddressBook();

        java.nio.file.Files.writeString(filePath, "not valid json {{{{{");
        storageAt(backupPath).saveAddressBook(backupAddressBook);

        ReadOnlyAddressBook recoveredAddressBook = storageAt(filePath).readAddressBook().get();
        assertEquals(backupAddressBook, new AddressBook(recoveredAddressBook));
    }

    @Test
    public void readAddressBook_rootPathWithoutFileName_throwsDataLoadingExceptionInsteadOfNpe() {
        assertThrows(DataLoadingException.class, () -> storageAt(Path.of("/")).readAddressBook());
    }

    // ===================== saveAddressBook =====================

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        Path path = tempPath("pingbook.json");
        assertThrows(NullPointerException.class, () -> storageAt(path).saveAddressBook(null));
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                storageAt(tempPath("pingbook.json")).saveAddressBook(new AddressBook(), null));
    }

    @Test
    public void saveAndReadAddressBook_roundTrip_success() throws Exception {
        Path filePath = tempPath("pingbook.json");
        JsonPingBookStorage storage = storageAt(filePath);
        AddressBook original = getTypicalAddressBook();

        // save and read back
        storage.saveAddressBook(original);
        ReadOnlyAddressBook readBack = storage.readAddressBook().get();
        assertEquals(original, new AddressBook(readBack));

        // modify, overwrite, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        storage.saveAddressBook(original);
        readBack = storage.readAddressBook().get();
        assertEquals(original, new AddressBook(readBack));

        // save and read using default (no explicit) path
        original.addPerson(IDA);
        storage.saveAddressBook(original);
        readBack = storage.readAddressBook().get();
        assertEquals(original, new AddressBook(readBack));
    }

    @Test
    public void saveAddressBook_preservesExistingAliases() throws Exception {
        Path filePath = tempPath("pingbook.json");
        JsonPingBookStorage storage = storageAt(filePath);

        // Write aliases first
        Map<String, String> aliases = new HashMap<>();
        aliases.put("ls", "list");
        storage.saveAliases(aliases);

        // Save address book without touching aliases
        storage.saveAddressBook(getTypicalAddressBook());

        // Aliases should still be present
        Optional<Map<String, String>> loaded = storage.readAliases();
        assertTrue(loaded.isPresent());
        assertEquals(aliases, loaded.get());
    }

    // ===================== readAliases =====================

    @Test
    public void readAliases_missingFile_returnsEmptyOptional() throws Exception {
        assertFalse(storageAt(tempPath("nonExistent.json")).readAliases().isPresent());
    }

    @Test
    public void readAliases_notJsonFormat_throwsDataLoadingException() {
        assertThrows(DataLoadingException.class, () ->
                storageAt(testDataPath("notJsonFormatPingBook.json")).readAliases());
    }

    @Test
    public void readAliases_fileWithNoAliasesField_returnsEmptyMap() throws Exception {
        // A pingbook file that has persons but no aliases key should deserialise aliases to {}
        Path filePath = tempPath("pingbook.json");
        JsonPingBookStorage storage = storageAt(filePath);
        storage.saveAddressBook(new AddressBook());
        // Now strip aliases from the file manually by saving a raw persons-only file
        java.nio.file.Files.writeString(filePath, "{ \"persons\": [] }");

        Optional<Map<String, String>> loaded = storage.readAliases();
        assertTrue(loaded.isPresent());
        assertEquals(new HashMap<>(), loaded.get());
    }

    @Test
    public void readAliases_malformedPrimaryWithValidBackup_recoversFromBackup() throws Exception {
        Path filePath = tempPath("pingbook.json");
        Path backupPath = tempPath("pingbook.json.bak");
        Map<String, String> aliases = new HashMap<>();
        aliases.put("ls", "list");
        aliases.put("la", "listarchived");

        java.nio.file.Files.writeString(filePath, "not valid json {{{{{");
        storageAt(backupPath).saveAliases(aliases);

        Optional<Map<String, String>> recoveredAliases = storageAt(filePath).readAliases();
        assertTrue(recoveredAliases.isPresent());
        assertEquals(aliases, recoveredAliases.get());
    }

    @Test
    public void readAliases_invalidPrimaryPersonWithValidPrimaryAliases_recoversFromBackup() throws Exception {
        Path filePath = tempPath("pingbook.json");
        Path backupPath = tempPath("pingbook.json.bak");

        AddressBook backupAddressBook = getTypicalAddressBook();
        Map<String, String> backupAliases = new LinkedHashMap<>();
        backupAliases.put("ls", "list");
        backupAliases.put("la", "listarchived");

        storageAt(backupPath).saveAll(backupAddressBook, backupAliases);

        String corruptedPrimary = Files.readString(backupPath)
                .replace("\"name\" : \"Alice Pauline\"",
                        "\"name\" : \"Person with invalid name field: Ha!ns Mu@ster\"")
                .replace("\"la\" : \"listarchived\"", "\"li\" : \"list\"");
        Files.writeString(filePath, corruptedPrimary);

        Optional<Map<String, String>> recoveredAliases = storageAt(filePath).readAliases();
        assertTrue(recoveredAliases.isPresent());
        assertEquals(backupAliases, recoveredAliases.get());
    }

    // ===================== saveAliases =====================

    @Test
    public void saveAliases_nullMap_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> storageAt(tempPath("pingbook.json")).saveAliases(null));
    }

    @Test
    public void saveAndReadAliases_roundTrip_success() throws Exception {
        Path filePath = tempPath("pingbook.json");
        JsonPingBookStorage storage = storageAt(filePath);

        Map<String, String> aliases = new HashMap<>();
        aliases.put("ls", "list");
        aliases.put("a", "add");

        storage.saveAliases(aliases);
        Optional<Map<String, String>> loaded = storage.readAliases();

        assertTrue(loaded.isPresent());
        assertEquals(aliases, loaded.get());
    }

    @Test
    public void saveAliases_preservesExistingPersons() throws Exception {
        Path filePath = tempPath("pingbook.json");
        JsonPingBookStorage storage = storageAt(filePath);
        AddressBook original = getTypicalAddressBook();

        storage.saveAddressBook(original);

        Map<String, String> aliases = new HashMap<>();
        aliases.put("d", "delete");
        storage.saveAliases(aliases);

        ReadOnlyAddressBook readBack = storage.readAddressBook().get();
        assertEquals(original, new AddressBook(readBack));
    }

    // ===================== saveAll =====================

    @Test
    public void saveAll_nullAddressBook_throwsNullPointerException() {
        Path path = tempPath("pingbook.json");
        assertThrows(NullPointerException.class, () ->
                storageAt(path).saveAll(null, new HashMap<>()));
    }

    @Test
    public void saveAll_nullAliases_throwsNullPointerException() {
        Path path = tempPath("pingbook.json");
        assertThrows(NullPointerException.class, () ->
                storageAt(path).saveAll(new AddressBook(), null));
    }

    @Test
    public void saveAll_roundTrip_bothPersistedTogether() throws Exception {
        Path filePath = tempPath("pingbook.json");
        JsonPingBookStorage storage = storageAt(filePath);
        AddressBook original = getTypicalAddressBook();

        Map<String, String> aliases = new HashMap<>();
        aliases.put("ls", "list");
        aliases.put("a", "add");

        storage.saveAll(original, aliases);

        ReadOnlyAddressBook personsBack = storage.readAddressBook().get();
        assertEquals(original, new AddressBook(personsBack));

        Map<String, String> aliasesBack = storage.readAliases().get();
        assertEquals(aliases, aliasesBack);
    }

    @Test
    public void saveAll_emptyInputs_succeeds() throws Exception {
        Path filePath = tempPath("pingbook.json");
        JsonPingBookStorage storage = storageAt(filePath);

        storage.saveAll(new AddressBook(), new HashMap<>());

        assertTrue(storage.readAddressBook().isPresent());
        assertTrue(storage.readAliases().isPresent());
        assertEquals(new AddressBook(), new AddressBook(storage.readAddressBook().get()));
        assertEquals(new HashMap<>(), storage.readAliases().get());
    }

    // ===================== shared-file behaviour =====================

    @Test
    public void singleFile_aliasesAndPersonsCoexist() throws IOException, DataLoadingException {
        Path filePath = tempPath("shared.json");
        JsonPingBookStorage storage = storageAt(filePath);
        AddressBook book = getTypicalAddressBook();
        Map<String, String> aliases = new HashMap<>();
        aliases.put("x", "exit");

        storage.saveAll(book, aliases);

        assertEquals(book, new AddressBook(storage.readAddressBook().get()));
        assertEquals(aliases, storage.readAliases().get());
    }

    // ===================== DataLoadingException propagation =====================

    @Test
    public void saveAddressBook_malformedFile_throwsIoException() throws Exception {
        Path filePath = tempPath("malformed_ab.json");
        java.nio.file.Files.writeString(filePath, "not valid json {{{{{");
        assertThrows(IOException.class, () ->
                storageAt(filePath).saveAddressBook(new AddressBook(), filePath));
    }

    @Test
    public void saveAliases_malformedFile_throwsIoException() throws Exception {
        Path filePath = tempPath("malformed_alias.json");
        java.nio.file.Files.writeString(filePath, "not valid json {{{{{");
        assertThrows(IOException.class, () ->
                storageAt(filePath).saveAliases(new HashMap<>()));
    }

    @Test
    public void readAddressBook_malformedPrimaryAndMalformedBackup_rethrowsWithSuppressedException() throws Exception {
        Path filePath = tempPath("pingbook.json");
        Path backupPath = tempPath("pingbook.json.bak");

        java.nio.file.Files.writeString(filePath, "not valid json {{{{{");
        java.nio.file.Files.writeString(backupPath, "also not valid json {{{{{");

        DataLoadingException exception = assertThrows(DataLoadingException.class, () -> storageAt(filePath)
            .readAddressBook());

        assertEquals(1, exception.getSuppressed().length);
    }

    @Test
    public void readAddressBook_backupFilePath_doesNotAttemptRecursiveRecovery() throws Exception {
        Path backupPath = tempPath("pingbook.json.bak");
        java.nio.file.Files.writeString(backupPath, "not valid json {{{{{");

        assertThrows(DataLoadingException.class, () -> storageAt(backupPath).readAddressBook());
    }

    @Test
    public void readAliases_rootPathWithoutFileName_throwsDataLoadingExceptionInsteadOfNpe() {
        assertThrows(DataLoadingException.class, () -> storageAt(Path.of("/")).readAliases());
    }

    @Test
    public void readAliases_malformedPrimaryAndMalformedBackup_rethrowsWithSuppressedException() throws Exception {
        Path filePath = tempPath("pingbook.json");
        Path backupPath = tempPath("pingbook.json.bak");

        java.nio.file.Files.writeString(filePath, "not valid json {{{{{");
        java.nio.file.Files.writeString(backupPath, "also not valid json {{{{{");

        DataLoadingException exception = assertThrows(DataLoadingException.class, () -> storageAt(filePath)
            .readAliases());

        assertEquals(1, exception.getSuppressed().length);
    }

    @Test
    public void readAliases_backupFilePath_doesNotAttemptRecursiveRecovery() throws Exception {
        Path backupPath = tempPath("pingbook.json.bak");
        java.nio.file.Files.writeString(backupPath, "not valid json {{{{{");

        assertThrows(DataLoadingException.class, () -> storageAt(backupPath).readAliases());
    }

}
