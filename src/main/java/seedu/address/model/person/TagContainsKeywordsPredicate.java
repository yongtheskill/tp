package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Tag} set matches any of the given keywords.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Creates a {@code TagContainsKeywordsPredicate} with the given list of tag keywords.
     *
     * @param keywords list of tag keywords used to test whether a person's tags match
     */
    public TagContainsKeywordsPredicate(List<String> keywords) {
        requireNonNull(keywords);
        this.keywords = new ArrayList<>(keywords);
    }

    /**
     * Tests whether the given {@code Person}'s tags contain any of the stored keywords,
     * using case-insensitive comparison on tag names.
     *
     * @param person person whose tags are to be tested
     * @return {@code true} if at least one tag name matches any keyword, {@code false} otherwise
     * @throws NullPointerException if {@code person} is {@code null}
     */
    @Override
    public boolean test(Person person) {
        requireNonNull(person);
        return keywords.stream()
                .anyMatch(keyword -> person.getTags().stream()
                        .map(tag -> tag.tagName)
                        .anyMatch(tagName -> tagName.equalsIgnoreCase(keyword)));
    }

    /**
     * Returns true if both predicates use equal keyword lists.
     *
     * @param other other object to compare to
     * @return {@code true} if {@code other} is a {@code TagContainsKeywordsPredicate}
     *         with an equal list of keywords
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagContainsKeywordsPredicate)) {
            return false;
        }

        TagContainsKeywordsPredicate otherPredicate = (TagContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    /**
     * Returns a hash code value for this predicate based on its keywords.
     *
     * @return hash code representing this predicate
     */
    @Override
    public int hashCode() {
        return Objects.hash(keywords);
    }

    /**
     * Returns a string representation of this predicate for debugging.
     *
     * @return string representation containing the keywords
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
