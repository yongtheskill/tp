package seedu.address;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.storage.AliasStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.UserPrefsStorage;

public class MainAppTest {

    @TempDir
    public Path tempDir;

    private final MainApp mainApp = new MainApp();

    @AfterEach
    public void tearDown() {
        AliasCommand.getAliasRegistry().clear();
    }

    @Test
    public void initModelManager_validData_returnsModelWithData() throws DataLoadingException {
        AddressBook testBook = getTypicalAddressBook();
        Storage storage = new StubStorage(() -> Optional.of(testBook));
        Model model = mainApp.initModelManager(storage, new UserPrefs());
        assertEquals(testBook, new AddressBook(model.getAddressBook()));
    }

    @Test
    public void initModelManager_noDataFile_returnsSampleData() {
        Storage storage = new StubStorage(() -> Optional.empty());
        Model model = mainApp.initModelManager(storage, new UserPrefs());
        assertFalse(model.getAddressBook().getPersonList().isEmpty());
    }

    @Test
    public void initModelManager_dataLoadingException_returnsEmptyModel() {
        Storage storage = new StubStorage(() -> {
            throw new DataLoadingException(new Exception("load fail"));
        });
        Model model = mainApp.initModelManager(storage, new UserPrefs());
        assertTrue(model.getAddressBook().getPersonList().isEmpty());
    }

    @Test
    public void initAliases_validAliases_loadsIntoRegistry() {
        Map<String, String> aliases = new HashMap<>();
        aliases.put("ls", "list");

        AliasStorage storage = new AliasStorage() {
            @Override
            public Path getAliasesFilePath() {
                return null;
            }

            @Override
            public Optional<Map<String, String>> readAliases() {
                return Optional.of(aliases);
            }

            @Override
            public void saveAliases(Map<String, String> a) throws IOException {}
        };

        mainApp.initAliases(storage);

        assertEquals("list", AliasCommand.getAliasRegistry().getCommandWord("ls"));
    }

    @Test
    public void initAliases_invalidPersistedAliases_loadsOnlyValidEntries() {
        Map<String, String> aliases = new HashMap<>();
        aliases.put("ls", "list");
        aliases.put("list", "list");
        aliases.put("", "add");

        AliasStorage storage = new AliasStorage() {
            @Override
            public Path getAliasesFilePath() {
                return null;
            }

            @Override
            public Optional<Map<String, String>> readAliases() {
                return Optional.of(aliases);
            }

            @Override
            public void saveAliases(Map<String, String> a) throws IOException {}
        };

        assertDoesNotThrow(() -> mainApp.initAliases(storage));
        assertEquals("list", AliasCommand.getAliasRegistry().getCommandWord("ls"));
        assertNull(AliasCommand.getAliasRegistry().getCommandWord("list"));
        assertNull(AliasCommand.getAliasRegistry().getCommandWord(""));
    }

    @Test
    public void initAliases_emptyOptional_clearsRegistry() {
        AliasCommand.getAliasRegistry().addAlias("ls", "list", AliasCommand.RESERVED_COMMAND_WORDS);

        AliasStorage storage = new AliasStorage() {
            @Override
            public Path getAliasesFilePath() {
                return null;
            }

            @Override
            public Optional<Map<String, String>> readAliases() {
                return Optional.empty();
            }

            @Override
            public void saveAliases(Map<String, String> a) throws IOException {}
        };

        assertDoesNotThrow(() -> mainApp.initAliases(storage));
        assertTrue(AliasCommand.getAliasRegistry().getAllAliases().isEmpty());
    }

    @Test
    public void initAliases_emptyMap_clearsRegistry() {
        AliasCommand.getAliasRegistry().addAlias("ls", "list", AliasCommand.RESERVED_COMMAND_WORDS);

        AliasStorage storage = new AliasStorage() {
            @Override
            public Path getAliasesFilePath() {
                return null;
            }

            @Override
            public Optional<Map<String, String>> readAliases() {
                return Optional.of(new HashMap<>());
            }

            @Override
            public void saveAliases(Map<String, String> a) throws IOException {}
        };

        assertDoesNotThrow(() -> mainApp.initAliases(storage));
        assertTrue(AliasCommand.getAliasRegistry().getAllAliases().isEmpty());
    }

    @Test
    public void initAliases_dataLoadingException_clearsRegistryAndDoesNotThrow() {
        AliasCommand.getAliasRegistry().addAlias("ls", "list", AliasCommand.RESERVED_COMMAND_WORDS);

        AliasStorage storage = new AliasStorage() {
            @Override
            public Path getAliasesFilePath() {
                return null;
            }

            @Override
            public Optional<Map<String, String>> readAliases() throws DataLoadingException {
                throw new DataLoadingException(new Exception("test error"));
            }

            @Override
            public void saveAliases(Map<String, String> a) throws IOException {}
        };

        assertDoesNotThrow(() -> mainApp.initAliases(storage));
        assertTrue(AliasCommand.getAliasRegistry().getAllAliases().isEmpty());
    }

    @Test
    public void initConfig_missingFile_returnsDefaultConfigAndCreatesFile() {
        Path configFile = tempDir.resolve("missingConfig.json");

        Config initializedConfig = mainApp.initConfig(configFile);

        assertEquals(new Config(), initializedConfig);
        assertTrue(Files.exists(configFile));
    }

    @Test
    public void initConfig_existingFile_returnsStoredConfig() throws IOException {
        Path configFile = tempDir.resolve("config.json");
        Config expectedConfig = new Config();
        expectedConfig.setLogLevel(Level.FINE);
        expectedConfig.setUserPrefsFilePath(tempDir.resolve("prefs.json"));
        ConfigUtil.saveConfig(expectedConfig, configFile);

        Config initializedConfig = mainApp.initConfig(configFile);

        assertEquals(expectedConfig, initializedConfig);
    }

    @Test
    public void initConfig_invalidFile_returnsDefaultConfig() throws IOException {
        Path configFile = tempDir.resolve("invalidConfig.json");
        Files.writeString(configFile, "not-json");

        Config initializedConfig = mainApp.initConfig(configFile);

        assertEquals(new Config(), initializedConfig);
    }

    @Test
    public void initConfig_directoryPath_returnsDefaultConfigWhenSaveFails() throws IOException {
        Path configDirectory = Files.createDirectory(tempDir.resolve("config-dir"));

        Config initializedConfig = mainApp.initConfig(configDirectory);

        assertEquals(new Config(), initializedConfig);
    }

    @Test
    public void initLogging_validConfig_doesNotThrow() throws Exception {
        Method initLoggingMethod = MainApp.class.getDeclaredMethod("initLogging", Config.class);
        initLoggingMethod.setAccessible(true);

        assertDoesNotThrow(() -> initLoggingMethod.invoke(mainApp, new Config()));
    }

    @Test
    public void initPrefs_validPrefs_returnsLoadedPrefs() {
        UserPrefs expectedPrefs = new UserPrefs();
        expectedPrefs.setAddressBookFilePath(tempDir.resolve("loaded.json"));

        StubUserPrefsStorage storage = new StubUserPrefsStorage(
            tempDir.resolve("prefs.json"), () -> Optional.of(expectedPrefs), false);

        UserPrefs initializedPrefs = mainApp.initPrefs(storage);

        assertEquals(expectedPrefs, initializedPrefs);
        assertEquals(expectedPrefs, storage.getSavedUserPrefs());
    }

    @Test
    public void initPrefs_missingPrefs_returnsDefaultPrefs() {
        StubUserPrefsStorage storage = new StubUserPrefsStorage(tempDir.resolve("prefs.json"),
                Optional::empty, false);

        UserPrefs initializedPrefs = mainApp.initPrefs(storage);

        assertEquals(new UserPrefs(), initializedPrefs);
        assertEquals(initializedPrefs, storage.getSavedUserPrefs());
    }

    @Test
    public void initPrefs_dataLoadingException_returnsDefaultPrefs() {
        StubUserPrefsStorage storage = new StubUserPrefsStorage(
                tempDir.resolve("prefs.json"), () -> {
                    throw new DataLoadingException(new Exception("load fail"));
                }, false);

        UserPrefs initializedPrefs = mainApp.initPrefs(storage);

        assertEquals(new UserPrefs(), initializedPrefs);
        assertEquals(initializedPrefs, storage.getSavedUserPrefs());
    }

    @Test
    public void initPrefs_saveFailure_returnsPrefs() {
        UserPrefs expectedPrefs = new UserPrefs();
        expectedPrefs.setAddressBookFilePath(tempDir.resolve("save-failure.json"));
        StubUserPrefsStorage storage = new StubUserPrefsStorage(
            tempDir.resolve("prefs.json"), () -> Optional.of(expectedPrefs), true);

        UserPrefs initializedPrefs = mainApp.initPrefs(storage);

        assertEquals(expectedPrefs, initializedPrefs);
        assertEquals(expectedPrefs, storage.getSavedUserPrefs());
    }

    @Test
    public void start_uiInitialized_delegatesToUi() {
        boolean[] started = new boolean[1];
        mainApp.ui = stage -> started[0] = true;

        mainApp.start(null);

        assertTrue(started[0]);
    }

    @Test
    public void stop_validState_savesUserPrefs() {
        StubStorage storage = new StubStorage(Optional::empty);
        mainApp.storage = storage;
        mainApp.model = new seedu.address.model.ModelManager(getTypicalAddressBook(), new UserPrefs());

        assertDoesNotThrow(mainApp::stop);
        assertEquals(mainApp.model.getUserPrefs(), storage.getSavedUserPrefs());
    }

    @Test
    public void stop_saveFailure_doesNotThrow() {
        StubStorage storage = new StubStorage(Optional::empty, true);
        mainApp.storage = storage;
        mainApp.model = new seedu.address.model.ModelManager(getTypicalAddressBook(), new UserPrefs());

        assertDoesNotThrow(mainApp::stop);
    }

    private static class StubUserPrefsStorage implements UserPrefsStorage {

        interface PrefReader {
            Optional<UserPrefs> read() throws DataLoadingException;
        }

        private final Path prefsFilePath;
        private final PrefReader prefReader;
        private final boolean throwOnSave;
        private ReadOnlyUserPrefs savedUserPrefs;

        StubUserPrefsStorage(Path prefsFilePath, PrefReader prefReader, boolean throwOnSave) {
            this.prefsFilePath = prefsFilePath;
            this.prefReader = prefReader;
            this.throwOnSave = throwOnSave;
        }

        @Override
        public Path getUserPrefsFilePath() {
            return prefsFilePath;
        }

        @Override
        public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
            return prefReader.read();
        }

        @Override
        public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
            savedUserPrefs = userPrefs;
            if (throwOnSave) {
                throw new IOException("save fail");
            }
        }

        ReadOnlyUserPrefs getSavedUserPrefs() {
            return savedUserPrefs;
        }
    }

    // ================ StubStorage inner class ==============================

    /**
     * Minimal Storage stub used to test initModelManager in isolation.
     * Only getAddressBookFilePath() and readAddressBook() have functional behaviour;
     * all other methods are no-ops or return empty values.
     */
    private static class StubStorage implements Storage {

        interface AbReader {
            Optional<ReadOnlyAddressBook> read() throws DataLoadingException;
        }

        private final AbReader abReader;
        private final boolean throwOnSaveUserPrefs;
        private ReadOnlyUserPrefs savedUserPrefs;

        StubStorage(AbReader abReader) {
            this(abReader, false);
        }

        StubStorage(AbReader abReader, boolean throwOnSaveUserPrefs) {
            this.abReader = abReader;
            this.throwOnSaveUserPrefs = throwOnSaveUserPrefs;
        }

        @Override
        public Path getAddressBookFilePath() {
            return Path.of("stub/ab.json");
        }

        @Override
        public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
            return abReader.read();
        }

        @Override
        public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
            return Optional.empty();
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        }

        @Override
        public Path getUserPrefsFilePath() {
            return null;
        }

        @Override
        public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
            return Optional.empty();
        }

        @Override
        public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
            savedUserPrefs = userPrefs;
            if (throwOnSaveUserPrefs) {
                throw new IOException("save fail");
            }
        }

        @Override
        public Path getAliasesFilePath() {
            return null;
        }

        @Override
        public Optional<Map<String, String>> readAliases() throws DataLoadingException {
            return Optional.empty();
        }

        @Override
        public void saveAliases(Map<String, String> aliases) throws IOException {
        }

        @Override
        public void saveAll(ReadOnlyAddressBook addressBook, Map<String, String> aliases) throws IOException {
        }

        ReadOnlyUserPrefs getSavedUserPrefs() {
            return savedUserPrefs;
        }
    }

}
