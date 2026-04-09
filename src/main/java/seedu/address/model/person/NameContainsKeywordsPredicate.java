package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s fields contain any of the keywords given.
 * Uses case-insensitive substring matching across name, phone, email, address, and tags.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> containsIgnoreCase(person, keyword));
    }

    private boolean containsIgnoreCase(Person person, String keyword) {
        String lowerKeyword = keyword.toLowerCase();

        if (person.getName().fullName.toLowerCase().contains(lowerKeyword)) {
            return true;
        }
        if (person.getPhone().value.toLowerCase().contains(lowerKeyword)) {
            return true;
        }
        if (person.getEmail().value.toLowerCase().contains(lowerKeyword)) {
            return true;
        }
        if (person.hasAddress() && person.getAddress().value.toLowerCase().contains(lowerKeyword)) {
            return true;
        }
        if (person.getTags().stream()
                .anyMatch(tag -> tag.tagName.toLowerCase().contains(lowerKeyword))) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
