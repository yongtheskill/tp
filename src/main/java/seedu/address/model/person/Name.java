package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain letters, digits, spaces, hyphens, apostrophes, and periods,"
                    + " must start with a letter or digit, and punctuation cannot appear consecutively."
                    + " Periods may appear at the end of a token, such as in Jr.";

    /*
     * Names must start with a letter or digit (\p{L} or \p{N}).
     * The regex then permits internal hyphens, apostrophes, and dots, while preventing
     * consecutive punctuation. Periods may appear at the end of a token.
     * Trailing spaces are permitted here because ParserUtil.parseName trims inputs before validation.
     */
    public static final String VALIDATION_REGEX = "[\\p{L}\\p{N}]+(?:[\\-'][\\p{L}\\p{N}]+|\\.[\\p{L}\\p{N}]+)*\\.?"
            + "(?: [\\p{L}\\p{N}]+(?:[\\-'][\\p{L}\\p{N}]+|\\.[\\p{L}\\p{N}]+)*\\.?)* *";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
