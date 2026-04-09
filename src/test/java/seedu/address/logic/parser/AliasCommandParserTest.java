package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AliasCommand;

public class AliasCommandParserTest {

    private final AliasCommandParser parser = new AliasCommandParser();

    @Test
    public void parse_validActions_success() {
        assertParseSuccess(parser, " add ls list ", new AliasCommand("add", "ls", "list"));
        assertParseSuccess(parser, " REMOVE ls ", new AliasCommand("remove", "ls", null));
        assertParseSuccess(parser, " LiSt ", new AliasCommand("list", null, null));
    }

    @Test
    public void parse_invalidInputs_failure() {
        assertParseFailure(parser, "   ", AliasCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "rename ls list", AliasCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "add ls", AliasCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "add ls list extra", AliasCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "remove", AliasCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "remove ls extra", AliasCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "list extra", AliasCommand.MESSAGE_USAGE);
    }
}
