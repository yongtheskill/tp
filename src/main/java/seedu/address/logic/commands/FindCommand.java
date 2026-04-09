package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.FieldsContainKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose fields contain any of the
 * argument keywords.
 * Keyword matching is case insensitive and uses substring matching.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose fields contain any of "
            + "the specified keywords (case-insensitive substring match) and displays them as a list.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final FieldsContainKeywordsPredicate predicate;

    /**
     * Creates a find command with the provided matching predicate.
     *
     * @param predicate predicate used to filter matching persons
     */
    public FindCommand(FieldsContainKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Applies the predicate to show matching persons in the current view (active or archived).
     *
     * @param model model used to update the filtered person list
     * @return result containing the number of matching persons
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate.and(model.getViewPredicate()));
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    /**
     * Indicates whether another object is equal to this {@code FindCommand}.
     * This follows the usual {@code equals} contract (reflexive, symmetric,
     * transitive, consistent, and false for null).
     *
     * @param other object to compare against
     * @return true if {@code other} is a {@code FindCommand} with an equivalent predicate
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    /**
     * Returns a string representation of this command for debugging.
     *
     * @return string representation containing the predicate
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
