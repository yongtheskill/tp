package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listArchived_showsOnlyArchivedContacts() {
        Person personToArchive = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person archivedPerson = new PersonBuilder(personToArchive).withArchived(true).build();
        model.setPerson(personToArchive, archivedPerson);

        expectedModel.setPerson(personToArchive, archivedPerson);
        expectedModel.setViewPredicate(seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_PERSONS);
        expectedModel.updateFilteredPersonList(seedu.address.model.person.Person::isArchived);

        assertCommandSuccess(new ListCommand(true), model, ListCommand.MESSAGE_ARCHIVED_SUCCESS, expectedModel);
    }

    @Test
    public void constructorsAndAccessor_trackArchivedMode() {
        assertFalse(new ListCommand().isShowArchived());
        assertTrue(new ListCommand(true).isShowArchived());
    }

    @Test
    public void equalsAndHashCode() {
        ListCommand activeCommand = new ListCommand();
        ListCommand activeCommandCopy = new ListCommand(false);
        ListCommand archivedCommand = new ListCommand(true);

        assertTrue(activeCommand.equals(activeCommand));
        assertTrue(activeCommand.equals(activeCommandCopy));
        assertFalse(activeCommand.equals(archivedCommand));
        assertFalse(activeCommand.equals(1));
        assertFalse(activeCommand.equals(null));
        assertEquals(activeCommand.hashCode(), activeCommandCopy.hashCode());
    }
}
