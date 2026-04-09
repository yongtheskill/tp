package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class FieldsContainKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FieldsContainKeywordsPredicate firstPredicate = new FieldsContainKeywordsPredicate(firstPredicateKeywordList);
        FieldsContainKeywordsPredicate secondPredicate = new FieldsContainKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FieldsContainKeywordsPredicate firstPredicateCopy =
            new FieldsContainKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameFieldMatches_returnsTrue() {
        // One keyword
        FieldsContainKeywordsPredicate predicate =
            new FieldsContainKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new FieldsContainKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new FieldsContainKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new FieldsContainKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_noFieldsMatch_returnsFalse() {
        // Zero keywords
        FieldsContainKeywordsPredicate predicate = new FieldsContainKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new FieldsContainKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keyword does not match any field
        predicate = new FieldsContainKeywordsPredicate(Arrays.asList("Nonexistent"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_keywordsMatchOtherFields_returnsTrue() {
        // Keywords match phone
        FieldsContainKeywordsPredicate predicate = new FieldsContainKeywordsPredicate(Arrays.asList("12345"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345").build()));

        // Keywords match email
        predicate = new FieldsContainKeywordsPredicate(Arrays.asList("alice@email.com"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withEmail("alice@email.com").build()));

        // Keywords match address
        predicate = new FieldsContainKeywordsPredicate(Arrays.asList("Main"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withAddress("Main Street").build()));

        // Keywords match tags
        predicate = new FieldsContainKeywordsPredicate(Arrays.asList("friend"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTags("friend").build()));

        // Keywords match remark
        predicate = new FieldsContainKeywordsPredicate(Arrays.asList("likes"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withRemark("likes coffee").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        FieldsContainKeywordsPredicate predicate = new FieldsContainKeywordsPredicate(keywords);

        String expected = FieldsContainKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }

    @Test
    public void test_personWithoutAddressAndEmptyRemark_returnsFalse() {
        FieldsContainKeywordsPredicate predicate = new FieldsContainKeywordsPredicate(Arrays.asList("nonexistent"));

        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withNoAddress().build()));
    }

    @Test
    public void test_nonMatchingNonEmptyRemark_returnsFalse() {
        FieldsContainKeywordsPredicate predicate = new FieldsContainKeywordsPredicate(Arrays.asList("nonexistent"));

        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withNoAddress()
                .withRemark("likes coffee").build()));
    }

    @Test
    public void hashCodeMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        FieldsContainKeywordsPredicate predicate = new FieldsContainKeywordsPredicate(keywords);

        assertEquals(keywords.hashCode(), predicate.hashCode());
    }
}
