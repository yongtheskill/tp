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
 * {@code StarCommand}.
 */
public class StarCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToStar = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        StarCommand starCommand = new StarCommand(INDEX_FIRST_PERSON);

        Person starredPerson = personToStar.withStarred(true);

        String expectedMessage = String.format(StarCommand.MESSAGE_STAR_PERSON_SUCCESS, Messages.format(starredPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToStar, starredPerson);

        assertCommandSuccess(starCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToStar = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        StarCommand starCommand = new StarCommand(INDEX_FIRST_PERSON);

        Person starredPerson = personToStar.withStarred(true);
        String expectedMessage = String.format(StarCommand.MESSAGE_STAR_PERSON_SUCCESS, Messages.format(starredPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        expectedModel.setPerson(personToStar, starredPerson);

        assertCommandSuccess(starCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        StarCommand starCommand = new StarCommand(outOfBoundIndex);

        assertCommandFailure(starCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        StarCommand starCommand = new StarCommand(outOfBoundIndex);

        assertCommandFailure(starCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_alreadyStarred_returnsInformativeNoOp() {
        Person personToStar = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person starredPerson = personToStar.withStarred(true);
        model.setPerson(personToStar, starredPerson);

        StarCommand starCommand = new StarCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(
            StarCommand.MESSAGE_PERSON_ALREADY_STARRED, Messages.format(starredPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(starCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_starArchivedPerson_throwsCommandException() {
        Person personToStar = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person archivedPerson = personToStar.withArchived(true);
        model.setPerson(personToStar, archivedPerson);
        model.updateFilteredPersonList(Person::isArchived);

        StarCommand starCommand = new StarCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(starCommand, model, StarCommand.MESSAGE_PERSON_ARCHIVED);
    }

    @Test
    public void equals() {
        StarCommand starFirstCommand = new StarCommand(INDEX_FIRST_PERSON);
        StarCommand starSecondCommand = new StarCommand(INDEX_SECOND_PERSON);

        assertTrue(starFirstCommand.equals(starFirstCommand));

        StarCommand starFirstCommandCopy = new StarCommand(INDEX_FIRST_PERSON);
        assertTrue(starFirstCommand.equals(starFirstCommandCopy));

        assertFalse(starFirstCommand.equals(1));
        assertFalse(starFirstCommand.equals(null));
        assertFalse(starFirstCommand.equals(starSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        StarCommand starCommand = new StarCommand(targetIndex);
        String expected = StarCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, starCommand.toString());
    }
}
