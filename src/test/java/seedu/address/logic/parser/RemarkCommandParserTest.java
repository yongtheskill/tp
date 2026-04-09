package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;

/**
 * Contains unit tests for {@link RemarkCommandParser}.
 */
public class RemarkCommandParserTest {

    private final RemarkCommandParser parser = new RemarkCommandParser();

    /**
     * Verifies parsing succeeds for a valid index and non-empty remark.
     */
    @Test
    public void parse_validArgsWithRemark_success() {
        assertParseSuccess(parser, " 1 r/Likes to swim.",
                new RemarkCommand(INDEX_FIRST_PERSON, new Remark("Likes to swim.")));
    }

    /**
     * Verifies parsing succeeds for a valid index and empty remark.
     */
    @Test
    public void parse_validArgsWithEmptyRemark_success() {
        assertParseSuccess(parser, " 1 r/", new RemarkCommand(INDEX_FIRST_PERSON, new Remark("")));
    }

    /**
     * Verifies parsing fails when the index is missing.
     */
    @Test
    public void parse_missingIndex_failure() {
        assertParseFailure(parser, " r/Anything",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
    }

    /**
     * Verifies parsing fails when the index is invalid.
     */
    @Test
    public void parse_invalidIndex_failure() {
        assertParseFailure(parser, " x r/Anything",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
    }

    /**
     * Verifies parsing fails when the remark prefix is omitted.
     */
    @Test
    public void parse_missingRemarkPrefix_failure() {
        assertParseFailure(parser, " 1 Anything",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
    }

    /**
     * Verifies parsing fails when only an index is provided with no remark prefix.
     */
    @Test
    public void parse_indexOnlyNoRemarkPrefix_failure() {
        assertParseFailure(parser, " 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
    }
}
