package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(true), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(true), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noConfirmation_showsPrompt() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_CONFIRMATION_REQUIRED, expectedModel);
    }

    @Test
    public void execute_confirmedWhileViewingArchived_resetsToActiveView() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.setViewPredicate(Person::isArchived);
        model.updateFilteredPersonList(Person::isArchived);

        CommandResult result = new ClearCommand(true).execute(model);

        assertEquals(ClearCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());

        assertTrue(model.getFilteredPersonList().isEmpty());

        model.addPerson(new PersonBuilder().build());
        assertEquals(1, model.getFilteredPersonList().size());
    }

    @Test
    public void equalsAndHashCode() {
        ClearCommand confirmedCommand = new ClearCommand(true);
        ClearCommand sameConfirmedCommand = new ClearCommand(true);
        ClearCommand unconfirmedCommand = new ClearCommand(false);

        assertTrue(confirmedCommand.equals(confirmedCommand));
        assertTrue(confirmedCommand.equals(sameConfirmedCommand));
        assertFalse(confirmedCommand.equals(unconfirmedCommand));
        assertFalse(confirmedCommand.equals(new ClearCommand()));
        assertFalse(confirmedCommand.equals("not a command"));
        assertEquals(confirmedCommand.hashCode(), sameConfirmedCommand.hashCode());
    }

}
