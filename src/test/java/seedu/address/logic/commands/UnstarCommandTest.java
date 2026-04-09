package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UnstarCommand}.
 */
public class UnstarCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToUnstar = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person starredPerson = personToUnstar.withStarred(true);
        model.setPerson(personToUnstar, starredPerson);

        UnstarCommand unstarCommand = new UnstarCommand(INDEX_FIRST_PERSON);
        Person unstarredPerson = starredPerson.withStarred(false);
        String expectedMessage = String.format(UnstarCommand.MESSAGE_UNSTAR_PERSON_SUCCESS,
                Messages.format(unstarredPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(starredPerson, unstarredPerson);

        assertCommandSuccess(unstarCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        Person personToUnstar = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person starredPerson = personToUnstar.withStarred(true);
        model.setPerson(personToUnstar, starredPerson);
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        UnstarCommand unstarCommand = new UnstarCommand(INDEX_FIRST_PERSON);
        Person unstarredPerson = starredPerson.withStarred(false);
        String expectedMessage = String.format(UnstarCommand.MESSAGE_UNSTAR_PERSON_SUCCESS,
                Messages.format(unstarredPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        expectedModel.setPerson(starredPerson, unstarredPerson);

        assertCommandSuccess(unstarCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnstarCommand unstarCommand = new UnstarCommand(outOfBoundIndex);

        assertCommandFailure(unstarCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnstarCommand unstarCommand = new UnstarCommand(outOfBoundIndex);

        assertCommandFailure(unstarCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_alreadyUnstarred_returnsInformativeNoOp() {
        Person personToUnstar = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnstarCommand unstarCommand = new UnstarCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnstarCommand.MESSAGE_PERSON_ALREADY_UNSTARRED,
                Messages.format(personToUnstar));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(unstarCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        UnstarCommand unstarFirstCommand = new UnstarCommand(INDEX_FIRST_PERSON);
        UnstarCommand unstarSecondCommand = new UnstarCommand(INDEX_SECOND_PERSON);

        assertTrue(unstarFirstCommand.equals(unstarFirstCommand));

        UnstarCommand unstarFirstCommandCopy = new UnstarCommand(INDEX_FIRST_PERSON);
        assertTrue(unstarFirstCommand.equals(unstarFirstCommandCopy));

        assertFalse(unstarFirstCommand.equals(1));
        assertFalse(unstarFirstCommand.equals(null));
        assertFalse(unstarFirstCommand.equals(unstarSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnstarCommand unstarCommand = new UnstarCommand(targetIndex);
        String expected = UnstarCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, unstarCommand.toString());
    }
}
