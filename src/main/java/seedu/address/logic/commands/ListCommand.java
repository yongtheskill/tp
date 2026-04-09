package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ACTIVE_PERSONS;

import java.util.Objects;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String ARCHIVED_COMMAND_WORD = "listarchived";

    public static final String MESSAGE_SUCCESS = "Listed active contacts";
    public static final String MESSAGE_ARCHIVED_SUCCESS = "Listed archived contacts";

    private final boolean showArchived;

    /**
     * Creates a command that lists active contacts.
     */
    public ListCommand() {
        this(false);
    }

    /**
     * Creates a list command for either archived or active contacts.
     *
     * @param showArchived when true, list archived contacts; otherwise list active contacts
     */
    public ListCommand(boolean showArchived) {
        this.showArchived = showArchived;
    }

    /** Returns whether this command lists archived contacts. */
    public boolean isShowArchived() {
        return showArchived;
    }


    /**
     * Updates the filtered list according to the selected contact state.
     *
     * @param model model that owns the filtered person list
     * @return result message indicating which list is shown
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (showArchived) {
            model.setViewPredicate(person -> person.isArchived());
            model.updateFilteredPersonList(person -> person.isArchived());
            return new CommandResult(MESSAGE_ARCHIVED_SUCCESS);
        }

        model.setViewPredicate(PREDICATE_SHOW_ACTIVE_PERSONS);
        model.updateFilteredPersonList(PREDICATE_SHOW_ACTIVE_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Returns whether this command and another command have the same target mode.
     *
     * @param other object to compare against
     * @return true if both commands list the same category of contacts
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ListCommand)) {
            return false;
        }

        ListCommand otherCommand = (ListCommand) other;
        return showArchived == otherCommand.showArchived;
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return hash code for this command
     */
    @Override
    public int hashCode() {
        return Objects.hash(showArchived);
    }
}
