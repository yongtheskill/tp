package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
