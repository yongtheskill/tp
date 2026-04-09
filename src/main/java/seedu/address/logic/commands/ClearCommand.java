package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "PingBook has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all contacts from PingBook. Requires confirmation.\n"
            + "Parameters: confirm\n"
            + "Example: " + COMMAND_WORD + " confirm";
    public static final String MESSAGE_CONFIRMATION_REQUIRED =
            "Are you sure you want to clear all contacts? Type 'clear confirm' to proceed.";

    private final boolean confirmed;

    public ClearCommand() {
        this.confirmed = false;
    }

    public ClearCommand(boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (!confirmed) {
            return new CommandResult(MESSAGE_CONFIRMATION_REQUIRED);
        }
        model.setAddressBook(new AddressBook());
        model.setViewPredicate(Model.PREDICATE_SHOW_ACTIVE_PERSONS);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ACTIVE_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ClearCommand)) {
            return false;
        }
        ClearCommand otherCommand = (ClearCommand) other;
        return confirmed == otherCommand.confirmed;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(confirmed);
    }
}
