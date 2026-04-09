package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
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

/**
 * Parses user input.
 */
public class AddressBookParser {

    private static final String EMPTY_ARGUMENTS = "";

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    private final AliasRegistry aliasRegistry;

    public AddressBookParser() {
        this(AliasCommand.getAliasRegistry());
    }

    public AddressBookParser(AliasRegistry aliasRegistry) {
        this.aliasRegistry = Objects.requireNonNull(aliasRegistry);
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String normalizedCommandWord = commandWord.toLowerCase(Locale.ROOT);
        final String aliasTarget = aliasRegistry.getCommandWord(normalizedCommandWord);
        final String resolvedCommandWord = aliasTarget != null ? aliasTarget : normalizedCommandWord;
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Resolved command word: " + resolvedCommandWord
            + "; Arguments: " + arguments);

        switch (resolvedCommandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case AliasCommand.COMMAND_WORD:
            return new AliasCommandParser().parse(arguments);

        case ArchiveCommand.COMMAND_WORD:
            return new ArchiveCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            String clearArgs = arguments.trim();
            if (clearArgs.isEmpty()) {
                return new ClearCommand();
            }
            if ("confirm".equalsIgnoreCase(clearArgs)) {
                return new ClearCommand(true);
            }
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            requireNoArguments(arguments, ListCommand.MESSAGE_USAGE);
            return new ListCommand();

        case ListCommand.ARCHIVED_COMMAND_WORD:
            requireNoArguments(arguments, ListCommand.MESSAGE_ARCHIVED_USAGE);
            return new ListCommand(true);

        case ExitCommand.COMMAND_WORD:
            requireNoArguments(arguments, ExitCommand.MESSAGE_USAGE);
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            requireNoArguments(arguments, HelpCommand.MESSAGE_USAGE);
            return new HelpCommand();

        case RemarkCommand.COMMAND_WORD:
            return new RemarkCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            requireNoArguments(arguments, SortCommand.MESSAGE_USAGE);
            return new SortCommand();

        case StarCommand.COMMAND_WORD:
            return new StarCommandParser().parse(arguments);

        case UnarchiveCommand.COMMAND_WORD:
            return new UnarchiveCommandParser().parse(arguments);

        case UnstarCommand.COMMAND_WORD:
            return new UnstarCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private void requireNoArguments(String arguments, String usageMessage) throws ParseException {
        if (!arguments.trim().equals(EMPTY_ARGUMENTS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, usageMessage));
        }
    }

}
