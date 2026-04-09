package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/** Archives a person from the displayed list by index. */
public class ArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Archives the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ARCHIVE_PERSON_SUCCESS = "Archived Person: %1$s";
    public static final String MESSAGE_PERSON_ALREADY_ARCHIVED = "This person is already archived.";

    private final Index targetIndex;

    public ArchiveCommand(Index targetIndex) {
        this.targetIndex = requireNonNull(targetIndex, "targetIndex cannot be null");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToArchive = lastShownList.get(targetIndex.getZeroBased());
        if (personToArchive.isArchived()) {
            throw new CommandException(MESSAGE_PERSON_ALREADY_ARCHIVED);
        }

        Person archivedPerson = personToArchive.withArchived(true);

        model.setPerson(personToArchive, archivedPerson);
        return new CommandResult(String.format(MESSAGE_ARCHIVE_PERSON_SUCCESS, Messages.format(archivedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ArchiveCommand)) {
            return false;
        }

        ArchiveCommand otherCommand = (ArchiveCommand) other;
        return targetIndex.equals(otherCommand.targetIndex);
    }
}
