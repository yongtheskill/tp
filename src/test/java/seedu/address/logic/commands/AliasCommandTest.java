package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class AliasCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        AliasCommand.getAliasRegistry().clear();
    }

    @Test
    public void execute_addAlias_success() throws Exception {
        AliasCommand cmd = new AliasCommand("add", "lc", "list");
        CommandResult result = cmd.execute(model);
        assertEquals("Alias 'lc' added for command 'list'.", result.getFeedbackToUser());
    }

    @Test
    public void execute_addAlias_conflict() throws Exception {
        AliasCommand cmd1 = new AliasCommand("add", "list", "find");
        assertThrows(CommandException.class, () -> cmd1.execute(model));
    }

    @Test
    public void execute_addAlias_invalidTarget() {
        AliasCommand command = new AliasCommand("add", "foo", "nonexistent");
        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(String.format(AliasCommand.MESSAGE_INVALID_TARGET, "nonexistent"), exception.getMessage());
    }

    @Test
    public void execute_removeAlias_success() throws Exception {
        AliasCommand cmd = new AliasCommand("add", "lc", "list");
        cmd.execute(model);
        AliasCommand removeCmd = new AliasCommand("remove", "lc", null);
        CommandResult result = removeCmd.execute(model);
        assertEquals("Alias 'lc' removed.", result.getFeedbackToUser());
    }

    @Test
    public void execute_removeAlias_fail() {
        AliasCommand removeCmd = new AliasCommand("remove", "notfound", null);
        assertThrows(CommandException.class, () -> removeCmd.execute(model));
    }

    @Test
    public void execute_listAlias() throws Exception {
        AliasCommand cmd = new AliasCommand("add", "lc", "list");
        cmd.execute(model);
        AliasCommand listCmd = new AliasCommand("list", null, null);
        CommandResult result = listCmd.execute(model);
        assertTrue(result.getFeedbackToUser().contains("lc -> list"), "expected alias listing to contain 'lc -> list'");
    }

    @Test
    public void execute_listAlias_empty() throws Exception {
        AliasCommand listCmd = new AliasCommand("list", null, null);
        CommandResult result = listCmd.execute(model);
        assertTrue(result.getFeedbackToUser().contains("(none)"));
    }

    @Test
    public void execute_addAliasDuplicate_throwsCommandException() throws Exception {
        AliasCommand cmd = new AliasCommand("add", "lc", "list");
        cmd.execute(model);
        AliasCommand duplicate = new AliasCommand("add", "lc", "find");
        CommandException exception = assertThrows(CommandException.class, () -> duplicate.execute(model));
        assertEquals(String.format(AliasCommand.MESSAGE_ADD_CONFLICT, "lc"), exception.getMessage());
    }

    @Test
    public void execute_invalidAction_throwsCommandException() {
        AliasCommand cmd = new AliasCommand("unknown", null, null);
        assertThrows(CommandException.class, () -> cmd.execute(model));
    }

    @Test
    public void execute_nullAction_throwsCommandException() {
        AliasCommand cmd = new AliasCommand(null, null, null);
        CommandException exception = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals(AliasCommand.MESSAGE_USAGE, exception.getMessage());
    }

    @Test
    public void execute_removeAliasFail_correctMessage() {
        AliasCommand removeCmd = new AliasCommand("remove", "notfound", null);
        CommandException exception = assertThrows(CommandException.class, () -> removeCmd.execute(model));
        assertEquals(String.format(AliasCommand.MESSAGE_REMOVE_FAIL, "notfound"), exception.getMessage());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        AliasCommand cmd = new AliasCommand("add", "lc", "list");
        assertTrue(cmd.equals(cmd));
    }

    @Test
    public void equals_equalObjects_returnsTrue() {
        AliasCommand cmd1 = new AliasCommand("add", "lc", "list");
        AliasCommand cmd2 = new AliasCommand("add", "lc", "list");
        assertTrue(cmd1.equals(cmd2));
    }

    @Test
    public void equals_differentFields_returnsFalse() {
        AliasCommand cmd1 = new AliasCommand("add", "lc", "list");
        AliasCommand cmd2 = new AliasCommand("add", "lc", "find");
        assertFalse(cmd1.equals(cmd2));
    }

    @Test
    public void equals_differentAction_returnsFalse() {
        AliasCommand cmd1 = new AliasCommand("add", "lc", "list");
        AliasCommand cmd2 = new AliasCommand("remove", "lc", "list");
        assertFalse(cmd1.equals(cmd2));
    }

    @Test
    public void equals_differentAlias_returnsFalse() {
        AliasCommand cmd1 = new AliasCommand("add", "lc", "list");
        AliasCommand cmd2 = new AliasCommand("add", "ls", "list");
        assertFalse(cmd1.equals(cmd2));
    }

    @Test
    public void equals_notAliasCommand_returnsFalse() {
        AliasCommand cmd = new AliasCommand("add", "lc", "list");
        assertFalse(cmd.equals("not an AliasCommand"));
    }

    @Test
    public void hashCode_equalObjects_sameHash() {
        AliasCommand cmd1 = new AliasCommand("add", "lc", "list");
        AliasCommand cmd2 = new AliasCommand("add", "lc", "list");
        assertEquals(cmd1.hashCode(), cmd2.hashCode());
    }
}
