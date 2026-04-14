package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code RemarkCommand}.
 */
public class RemarkCommandTest {

    private Model model;

    /**
     * Initializes a model containing a typical address book before each test.
     */
    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    /**
     * Verifies that adding a non-empty remark to a person succeeds.
     */
    @Test
    public void execute_addRemarkUnfilteredList_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Remark newRemark = new Remark("Likes to swim.");
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, newRemark);

        Person editedPerson = personToEdit.withRemark(newRemark);
        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Verifies that providing an empty remark clears an existing remark
     * successfully.
     */
    @Test
    public void execute_deleteRemarkUnfilteredList_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithRemark = personToEdit.withRemark(new Remark("some text"));
        model.setPerson(personToEdit, personWithRemark);

        Remark emptyRemark = new Remark("");
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, emptyRemark);

        Person editedPerson = personWithRemark.withRemark(emptyRemark);
        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personWithRemark, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Verifies that execution fails when the target index is out of bounds.
     */
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, new Remark("Any remark"));

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_archivedPerson_throwsCommandException() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person archivedPerson = personToEdit.withArchived(true);
        model.setPerson(personToEdit, archivedPerson);
        model.setViewPredicate(PREDICATE_SHOW_ARCHIVED_PERSONS);
        model.updateFilteredPersonList(PREDICATE_SHOW_ARCHIVED_PERSONS);

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark("Archived note"));

        assertCommandFailure(remarkCommand, model, RemarkCommand.MESSAGE_PERSON_ARCHIVED);
    }

    /**
     * Verifies equality semantics for {@code RemarkCommand}.
     */
    @Test
    public void equals() {
        RemarkCommand remarkFirstCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark("First"));
        RemarkCommand remarkSecondCommand = new RemarkCommand(INDEX_SECOND_PERSON, new Remark("Second"));

        assertEquals(remarkFirstCommand, remarkFirstCommand);

        RemarkCommand remarkFirstCommandCopy = new RemarkCommand(INDEX_FIRST_PERSON, new Remark("First"));
        assertEquals(remarkFirstCommand, remarkFirstCommandCopy);

        assertNotEquals(remarkFirstCommand, 1);
        assertNotEquals(remarkFirstCommand, null);
        assertNotEquals(remarkFirstCommand, remarkSecondCommand);
    }
}
