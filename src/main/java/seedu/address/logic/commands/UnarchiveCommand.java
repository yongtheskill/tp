package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/** Unarchives a person from the displayed list by index. */
public class UnarchiveCommand extends Command {

    public static final String COMMAND_WORD = "unarchive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unarchives the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNARCHIVE_PERSON_SUCCESS = "Unarchived Person: %1$s";
    public static final String MESSAGE_NO_ARCHIVED_CONTACTS_SHOWN =
            "No archived contacts are shown. Run listarchived first, then use unarchive INDEX.";
    public static final String MESSAGE_PERSON_ALREADY_ACTIVE = "This person is already active.";

    private final Index targetIndex;

    public UnarchiveCommand(Index targetIndex) {
        this.targetIndex = requireNonNull(targetIndex, "targetIndex cannot be null");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getViewPredicate() != PREDICATE_SHOW_ARCHIVED_PERSONS) {
            throw new CommandException(MESSAGE_NO_ARCHIVED_CONTACTS_SHOWN);
        }

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUnarchive = lastShownList.get(targetIndex.getZeroBased());
        if (!personToUnarchive.isArchived()) {
            throw new CommandException(MESSAGE_PERSON_ALREADY_ACTIVE);
        }

        Person unarchivedPerson = personToUnarchive.withArchived(false);

        model.setPerson(personToUnarchive, unarchivedPerson);
        return new CommandResult(String.format(MESSAGE_UNARCHIVE_PERSON_SUCCESS, Messages.format(unarchivedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnarchiveCommand)) {
            return false;
        }

        UnarchiveCommand otherCommand = (UnarchiveCommand) other;
        return targetIndex.equals(otherCommand.targetIndex);
    }
}
