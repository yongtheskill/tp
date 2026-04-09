package seedu.address.model.person;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s fields contain any of the keywords given.
 * Uses case-insensitive substring matching across name, phone, email, address, remark, and tags.
 */
public class FieldsContainKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public FieldsContainKeywordsPredicate(List<String> keywords) {
        this.keywords = List.copyOf(keywords);
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> containsIgnoreCase(person, keyword));
    }

    private boolean containsIgnoreCase(Person person, String keyword) {
        String lowerKeyword = keyword.toLowerCase(Locale.ROOT);

        if (person.getName().fullName.toLowerCase(Locale.ROOT).contains(lowerKeyword)) {
            return true;
        }
        if (person.getPhone().value.toLowerCase(Locale.ROOT).contains(lowerKeyword)) {
            return true;
        }
        if (person.getEmail().value.toLowerCase(Locale.ROOT).contains(lowerKeyword)) {
            return true;
        }
        if (person.hasAddress() && person.getAddress().value.toLowerCase(Locale.ROOT).contains(lowerKeyword)) {
            return true;
        }
        if (person.getTags().stream()
                .anyMatch(tag -> tag.tagName.toLowerCase(Locale.ROOT).contains(lowerKeyword))) {
            return true;
        }
        if (!person.getRemark().value.isEmpty()
                && person.getRemark().value.toLowerCase(Locale.ROOT).contains(lowerKeyword)) {
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
        if (!(other instanceof FieldsContainKeywordsPredicate)) {
            return false;
        }

        FieldsContainKeywordsPredicate otherPredicate = (FieldsContainKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public int hashCode() {
        return keywords.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
