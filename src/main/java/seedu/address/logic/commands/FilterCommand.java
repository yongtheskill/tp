package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Filters and lists all persons in address book whose tags contain any of the
 * argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all persons whose tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: t/TAG [t/MORE_TAGS]...\n"
            + "Example: " + COMMAND_WORD + " t/friend t/colleague";

    private final TagContainsKeywordsPredicate predicate;

    /**
     * Creates a {@code FilterCommand} that filters the person list using the given
     * {@link TagContainsKeywordsPredicate}.
     *
     * @param predicate predicate to apply when filtering persons by tag
     * @throws NullPointerException if {@code predicate} is {@code null}
     */
    public FilterCommand(TagContainsKeywordsPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    /**
     * Executes the filter operation on the given {@code Model}, updating the filtered
     * person list to show only persons whose tags match this command's predicate.
     *
     * @param model model to apply the filter to
     * @return the {@code CommandResult} describing the outcome of the command
     * @throws NullPointerException if {@code model} is {@code null}
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    /**
     * Returns true if both {@code FilterCommand} instances use equal predicates.
     *
     * @param other other object to compare to
     * @return {@code true} if {@code other} is a {@code FilterCommand} with an equal predicate
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return predicate.equals(otherFilterCommand.predicate);
    }

    /**
     * Returns a hash code value for this {@code FilterCommand}, based on its predicate.
     *
     * @return hash code representing this command
     */
    @Override
    public int hashCode() {
        return Objects.hash(predicate);
    }

    /**
     * Returns a string representation of this {@code FilterCommand} for debugging.
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

