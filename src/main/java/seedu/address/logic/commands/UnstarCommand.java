package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Unstars a person identified using its displayed index from the address book.
 */
public class UnstarCommand extends Command {

    public static final String COMMAND_WORD = "unstar";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unstars the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNSTAR_PERSON_SUCCESS = "Unstarred Person: %1$s";
    public static final String MESSAGE_PERSON_ALREADY_UNSTARRED = "Person is already unstarred: %1$s";
    public static final String MESSAGE_PERSON_ARCHIVED =
            "Cannot unstar an archived person. Unarchive the person first.";

    private final Index targetIndex;

    public UnstarCommand(Index targetIndex) {
        this.targetIndex = requireNonNull(targetIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUnstar = lastShownList.get(targetIndex.getZeroBased());
        if (personToUnstar.isArchived()) {
            throw new CommandException(MESSAGE_PERSON_ARCHIVED);
        }
        if (!personToUnstar.isStarred()) {
            return new CommandResult(String.format(MESSAGE_PERSON_ALREADY_UNSTARRED,
                    Messages.format(personToUnstar)));
        }

        Person unstarredPerson = personToUnstar.withStarred(false);
        model.setPerson(personToUnstar, unstarredPerson);
        return new CommandResult(String.format(MESSAGE_UNSTAR_PERSON_SUCCESS, Messages.format(unstarredPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnstarCommand)) {
            return false;
        }

        UnstarCommand otherUnstarCommand = (UnstarCommand) other;
        return targetIndex.equals(otherUnstarCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
