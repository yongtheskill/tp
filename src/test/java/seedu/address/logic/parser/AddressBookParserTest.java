package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.StarCommand;
import seedu.address.logic.commands.UnarchiveCommand;
import seedu.address.logic.commands.UnstarCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FieldsContainKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

/**
 * Contains unit tests for {@link AddressBookParser}.
 */
public class AddressBookParserTest {

    private AliasRegistry aliasRegistry;
    private AddressBookParser parser;

    @BeforeEach
    public void setUp() {
        AliasCommand.getAliasRegistry().clear();
        aliasRegistry = new AliasRegistry();
        parser = new AddressBookParser(aliasRegistry);
    }

    /**
     * Verifies parsing of {@code add} commands.
     */
    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    /**
     * Verifies parsing of {@code clear} commands.
     */
    @Test
    public void parseCommand_clear() throws Exception {
        assertEquals(new ClearCommand(), parser.parseCommand(ClearCommand.COMMAND_WORD));
        assertEquals(new ClearCommand(true), parser.parseCommand(ClearCommand.COMMAND_WORD + " confirm"));
        assertEquals(new ClearCommand(true), parser.parseCommand(ClearCommand.COMMAND_WORD + " CONFIRM"));
    }

    @Test
    public void parseCommand_clearInvalidArguments_throwsParseException() {
        String invalidClearCommand = ClearCommand.COMMAND_WORD + " 3";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE);

        assertThrows(ParseException.class, expectedMessage, () -> parser.parseCommand(invalidClearCommand));
    }

    /**
     * Verifies parsing of {@code delete} commands.
     */
    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    /**
     * Verifies parsing of {@code alias} commands.
     */
    @Test
    public void parseCommand_alias() throws Exception {
        AliasCommand listCommand = (AliasCommand) parser.parseCommand(
                AliasCommand.COMMAND_WORD + " list");
        assertEquals(new AliasCommand("list", null, null), listCommand);

        AliasCommand addCommand = (AliasCommand) parser.parseCommand(
                AliasCommand.COMMAND_WORD + " add ls list");
        assertEquals(new AliasCommand("add", "ls", "list"), addCommand);

        AliasCommand removeCommand = (AliasCommand) parser.parseCommand(
                AliasCommand.COMMAND_WORD + " remove ls");
        assertEquals(new AliasCommand("remove", "ls", null), removeCommand);
    }

    @Test
    public void parseCommand_alias_caseInsensitiveSuccess() throws Exception {
        AliasCommand addCommand = (AliasCommand) parser.parseCommand("ALIAS ADD LS LIST");
        assertEquals(new AliasCommand("add", "ls", "list"), addCommand);
    }

    /**
     * Verifies parsing of {@code archive} commands.
     */
    @Test
    public void parseCommand_archive() throws Exception {
        ArchiveCommand command = (ArchiveCommand) parser.parseCommand(
                ArchiveCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new ArchiveCommand(INDEX_FIRST_PERSON), command);
    }

    /**
     * Verifies parsing of {@code edit} commands.
     */
    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    /**
     * Verifies parsing of {@code exit} commands.
     */
    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
    }

    /**
     * Verifies parsing of {@code find} commands.
     */
    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new FieldsContainKeywordsPredicate(keywords)), command);
    }

    /**
     * Verifies parsing of {@code filter} commands.
     */
    @Test
    public void parseCommand_filter() throws Exception {
        List<String> tags = Arrays.asList("friends", "owesMoney");
        FilterCommand command = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD + " t/" + tags.stream().collect(Collectors.joining(" t/")));
        assertEquals(new FilterCommand(new TagContainsKeywordsPredicate(tags)), command);
    }

    /**
     * Verifies parsing of {@code help} commands.
     */
    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
    }

    /**
     * Verifies parsing of {@code list} commands.
     */
    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(((ListCommand) parser.parseCommand(ListCommand.ARCHIVED_COMMAND_WORD)).isShowArchived());
    }

    @Test
    public void parseCommand_noArgumentCommandWithExtraArguments_throwsParseException() {
        assertInvalidNoArgumentCommand(ExitCommand.COMMAND_WORD + " now", ExitCommand.MESSAGE_USAGE);
        assertInvalidNoArgumentCommand(HelpCommand.COMMAND_WORD + " me", HelpCommand.MESSAGE_USAGE);
        assertInvalidNoArgumentCommand(SortCommand.COMMAND_WORD + " byname", SortCommand.MESSAGE_USAGE);
        assertInvalidNoArgumentCommand(ListCommand.COMMAND_WORD + " archived", ListCommand.MESSAGE_USAGE);
        assertInvalidNoArgumentCommand(ListCommand.ARCHIVED_COMMAND_WORD + " now",
            ListCommand.MESSAGE_ARCHIVED_USAGE);
    }

    @Test
    public void parseCommand_mixedCaseBuiltInCommand_success() throws Exception {
        ListCommand activeListCommand = (ListCommand) parser.parseCommand("LiSt");
        ListCommand archivedListCommand = (ListCommand) parser.parseCommand("LiStArChIvEd");

        assertFalse(activeListCommand.isShowArchived());
        assertTrue(archivedListCommand.isShowArchived());
    }

    @Test
    public void parseCommand_mixedCaseAlias_success() throws Exception {
        aliasRegistry.addAlias("ls", "list", AliasCommand.RESERVED_COMMAND_WORDS);

        assertTrue(parser.parseCommand("LS") instanceof ListCommand);
    }

    @Test
    public void parseCommand_defaultConstructorUsesSharedAliasRegistry_success() throws Exception {
        AliasCommand.getAliasRegistry().addAlias("ls", "list", AliasCommand.RESERVED_COMMAND_WORDS);
        AddressBookParser defaultParser = new AddressBookParser();

        assertTrue(defaultParser.parseCommand("LS") instanceof ListCommand);
    }

    /**
     * Verifies parsing of {@code remark} commands.
     */
    @Test
    public void parseCommand_remark() throws Exception {
        RemarkCommand command = (RemarkCommand) parser.parseCommand(
                RemarkCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " r/Likes to swim.");
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark("Likes to swim.")), command);
    }

    /**
     * Verifies parsing of {@code sort} commands.
     */
    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD) instanceof SortCommand);
    }

    /**
     * Verifies parsing of {@code star} commands.
     */
    @Test
    public void parseCommand_star() throws Exception {
        StarCommand command = (StarCommand) parser.parseCommand(
                StarCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new StarCommand(INDEX_FIRST_PERSON), command);
    }

    /**
     * Verifies parsing of {@code unarchive} commands.
     */
    @Test
    public void parseCommand_unarchive() throws Exception {
        UnarchiveCommand command = (UnarchiveCommand) parser.parseCommand(
                UnarchiveCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new UnarchiveCommand(INDEX_FIRST_PERSON), command);
    }

    /**
     * Verifies parsing of {@code unstar} commands.
     */
    @Test
    public void parseCommand_unstar() throws Exception {
        UnstarCommand command = (UnstarCommand) parser.parseCommand(
                UnstarCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new UnstarCommand(INDEX_FIRST_PERSON), command);
    }

    /**
     * Verifies that empty input produces a parse exception.
     */
    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    /**
     * Verifies that unknown command words produce a parse exception.
     */
    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    private void assertInvalidNoArgumentCommand(String userInput, String usageMessage) {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, usageMessage);
        assertThrows(ParseException.class, expectedMessage, () -> parser.parseCommand(userInput));
    }
}
