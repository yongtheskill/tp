package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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

/**
 * Contains integration tests and unit tests for ArchiveCommand.
 */
public class ArchiveCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToArchive = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON);

        Person archivedPerson = new Person(
                personToArchive.getName(), personToArchive.getPhone(), personToArchive.getEmail(),
                personToArchive.getAddress(), personToArchive.getRemark(), personToArchive.getTags(), true);

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_PERSON_SUCCESS,
                Messages.format(archivedPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToArchive, archivedPerson);

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex);

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_archivedPerson_throwsCommandException() {
        Person personToArchive = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person archivedPerson = new Person(
                personToArchive.getName(), personToArchive.getPhone(), personToArchive.getEmail(),
                personToArchive.getAddress(), personToArchive.getRemark(), personToArchive.getTags(), true);
        model.setPerson(personToArchive, archivedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_PERSON);
        assertCommandFailure(archiveCommand, model, ArchiveCommand.MESSAGE_PERSON_ALREADY_ARCHIVED);
    }

    @Test
    public void equals() {
        ArchiveCommand archiveFirstCommand = new ArchiveCommand(INDEX_FIRST_PERSON);
        ArchiveCommand archiveSecondCommand = new ArchiveCommand(INDEX_SECOND_PERSON);

        org.junit.jupiter.api.Assertions.assertEquals(archiveFirstCommand, archiveFirstCommand);
        org.junit.jupiter.api.Assertions.assertEquals(archiveFirstCommand, new ArchiveCommand(INDEX_FIRST_PERSON));

        assertNotEquals(archiveFirstCommand, 1);
        assertNotEquals(archiveFirstCommand, null);
        assertNotEquals(archiveFirstCommand, archiveSecondCommand);
    }
}
