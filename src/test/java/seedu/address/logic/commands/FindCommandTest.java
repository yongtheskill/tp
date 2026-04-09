package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.FieldsContainKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        FieldsContainKeywordsPredicate firstPredicate =
                new FieldsContainKeywordsPredicate(Collections.singletonList("first"));
        FieldsContainKeywordsPredicate secondPredicate =
                new FieldsContainKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FieldsContainKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FieldsContainKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_archivedViewOnly_returnsMatchesWithinCurrentView() {
        Person personToArchive = model.getFilteredPersonList().stream()
                .filter(CARL::equals)
                .findFirst()
                .orElseThrow();
        Person archivedPerson = personToArchive.withArchived(true);
        model.setPerson(personToArchive, archivedPerson);
        expectedModel.setPerson(personToArchive, archivedPerson);

        model.setViewPredicate(Model.PREDICATE_SHOW_ARCHIVED_PERSONS);
        model.updateFilteredPersonList(model.getViewPredicate());
        expectedModel.setViewPredicate(Model.PREDICATE_SHOW_ARCHIVED_PERSONS);

        FieldsContainKeywordsPredicate predicate = preparePredicate("street");
        FindCommand command = new FindCommand(predicate);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);

        expectedModel.updateFilteredPersonList(predicate.and(expectedModel.getViewPredicate()));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(archivedPerson), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        FieldsContainKeywordsPredicate predicate = new FieldsContainKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code FieldsContainKeywordsPredicate}.
     */
    private FieldsContainKeywordsPredicate preparePredicate(String userInput) {
        return new FieldsContainKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
