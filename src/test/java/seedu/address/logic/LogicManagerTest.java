package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonAliasStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy IO exception");
    private static final IOException DUMMY_AD_EXCEPTION = new AccessDeniedException("dummy access denied exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        AliasCommand.getAliasRegistry().clear();
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonAliasStorage aliasStorage = new JsonAliasStorage(temporaryFolder.resolve("aliases.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage, aliasStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_mixedCaseBuiltInCommand_success() throws Exception {
        assertCommandSuccess("LiSt", ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_aliasThenUseAlias_success() throws Exception {
        CommandResult addAliasResult = logic.execute("ALIAS ADD LS LIST");
        assertEquals("Alias 'ls' added for command 'list'.", addAliasResult.getFeedbackToUser());

        CommandResult result = logic.execute("lS");
        assertEquals(ListCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
    }

    @Test
    public void execute_addWhileViewingArchived_switchesToActiveView() throws Exception {
        AddressBookBuilder addressBookBuilder = new AddressBookBuilder().withPerson(ALICE.withArchived(true));
        model = new ModelManager(addressBookBuilder.build(), new UserPrefs());

        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("archivedViewAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("archivedViewUserPrefs.json"));
        JsonAliasStorage aliasStorage = new JsonAliasStorage(temporaryFolder.resolve("archivedViewAliases.json"));
        logic = new LogicManager(model, new StorageManager(addressBookStorage, userPrefsStorage, aliasStorage));

        logic.execute(ListCommand.ARCHIVED_COMMAND_WORD);

        Person expectedAddedPerson = new PersonBuilder(AMY).withTags().build();
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        CommandResult result = logic.execute(addCommand);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(expectedAddedPerson), 2),
                result.getFeedbackToUser());
        // After adding, view should switch to active contacts so the new contact is visible
        assertEquals(1, logic.getFilteredPersonList().size());
        assertEquals(expectedAddedPerson, logic.getFilteredPersonList().get(0));
    }

    @Test
    public void execute_aliasStorageThrowsIoException_throwsCommandException() {
        Path prefPath = temporaryFolder.resolve("AliasExceptionAddressBook.json");
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(prefPath);
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("AliasExceptionUserPrefs.json"));

        // Inject aliasStorage that throws on saveAliases
        JsonAliasStorage aliasStorage = new JsonAliasStorage(
                temporaryFolder.resolve("AliasException.json")) {
            @Override
            public void saveAliases(java.util.Map<String, String> aliases) throws IOException {
                throw DUMMY_IO_EXCEPTION;
            }
        };
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage, aliasStorage);
        logic = new LogicManager(model, storage);

        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        ModelManager expectedModel = new ModelManager();
        assertCommandFailure(addCommand, CommandException.class,
                String.format(LogicManager.FILE_OPS_ERROR_FORMAT, DUMMY_IO_EXCEPTION.getMessage()),
                expectedModel);
    }

    @Test
    public void execute_deleteWhenSaveFails_rollsBackInMemoryState() {
        Person person = new PersonBuilder()
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY)
                .build();
        model = new ModelManager(new AddressBookBuilder().withPerson(person).build(), new UserPrefs());

        Path prefPath = temporaryFolder.resolve("DeleteSaveFailureAddressBook.json");
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(prefPath) {
            @Override
            public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath)
                    throws IOException {
                throw DUMMY_AD_EXCEPTION;
            }
        };

        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("DeleteSaveFailureUserPrefs.json"));
        JsonAliasStorage aliasStorage = new JsonAliasStorage(temporaryFolder.resolve("DeleteSaveFailureAliases.json"));
        logic = new LogicManager(model, new StorageManager(addressBookStorage, userPrefsStorage, aliasStorage));

        Model expectedModel = new ModelManager(new AddressBookBuilder().withPerson(person).build(), new UserPrefs());
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " 1", CommandException.class,
                String.format(LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT, model.getAddressBookFilePath()),
                expectedModel);
        assertEquals(1, logic.getFilteredPersonList().size());
        assertEquals(person, logic.getFilteredPersonList().get(0));
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_IO_EXCEPTION, String.format(
                LogicManager.FILE_OPS_ERROR_FORMAT, DUMMY_IO_EXCEPTION.getMessage()));
    }

    @Test
    public void execute_storageThrowsAdException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_AD_EXCEPTION, String.format(
                LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT, model.getAddressBookFilePath()));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
    }

    @Test
    public void getAddressBook_returnsModelAddressBook() {
        assertEquals(model.getAddressBook(), logic.getAddressBook());
    }

    @Test
    public void getAddressBookFilePath_returnsModelPath() {
        assertEquals(model.getAddressBookFilePath(), logic.getAddressBookFilePath());
    }

    @Test
    public void getGuiSettings_returnsModelGuiSettings() {
        assertEquals(model.getGuiSettings(), logic.getGuiSettings());
    }

    @Test
    public void setGuiSettings_updatesModel() {
        GuiSettings newSettings = new GuiSettings(800, 600, 10, 20);
        logic.setGuiSettings(newSettings);
        assertEquals(newSettings, model.getGuiSettings());
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * Tests the Logic component's handling of an {@code IOException} thrown by the Storage component.
     *
     * @param e the exception to be thrown by the Storage component
     * @param expectedMessage the message expected inside exception thrown by the Logic component
     */
    private void assertCommandFailureForExceptionFromStorage(IOException e, String expectedMessage) {
        Path prefPath = temporaryFolder.resolve("ExceptionUserPrefs.json");

        // Inject LogicManager with an AddressBookStorage that throws the IOException e when saving
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(prefPath) {
            @Override
            public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath)
                    throws IOException {
                throw e;
            }
        };

        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ExceptionUserPrefs.json"));
        JsonAliasStorage aliasStorage = new JsonAliasStorage(temporaryFolder.resolve("ExceptionAliases.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage, aliasStorage);

        logic = new LogicManager(model, storage);

        // Triggers the saveAddressBook method by executing an add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        ModelManager expectedModel = new ModelManager();
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }
}
