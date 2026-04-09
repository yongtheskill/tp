package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.AliasRegistry;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;
    private final AliasRegistry aliasRegistry;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this(model, storage, AliasCommand.getAliasRegistry());
    }

    /**
     * Constructs a {@code LogicManager} with the given {@code Model}, {@code Storage}, and
     * {@code AliasRegistry}.
     */
    public LogicManager(Model model, Storage storage, AliasRegistry aliasRegistry) {
        this.model = model;
        this.storage = storage;
        this.aliasRegistry = aliasRegistry;
        addressBookParser = new AddressBookParser(aliasRegistry);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAll(model.getAddressBook(), aliasRegistry.getAllAliases());
        } catch (AccessDeniedException e) {
            logger.log(Level.WARNING,
                    "Address book save failed due to insufficient permissions for file: "
                            + model.getAddressBookFilePath(),
                    e);
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT,
                    model.getAddressBookFilePath()), e);
        } catch (IOException ioe) {
            logger.log(Level.WARNING, "Address book save failed due to I/O error", ioe);
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
