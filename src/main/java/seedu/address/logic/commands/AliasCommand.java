package seedu.address.logic.commands;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AliasRegistry;
import seedu.address.model.Model;

/**
 * Command for managing command aliases.
 */
public class AliasCommand extends Command {
    public static final String COMMAND_WORD = "alias";
    public static final String MESSAGE_USAGE = "alias add <alias> <command> | alias remove <alias> | alias list";
    public static final String MESSAGE_ADD_SUCCESS = "Alias '%s' added for command '%s'.";
    public static final String MESSAGE_ADD_CONFLICT = "Alias '%s' conflicts with existing command or alias.";
    public static final String MESSAGE_INVALID_TARGET = "Invalid target command '%s'.";
    public static final String MESSAGE_REMOVE_SUCCESS = "Alias '%s' removed.";
    public static final String MESSAGE_REMOVE_FAIL = "Alias '%s' not found.";
    public static final String MESSAGE_LIST = "Aliases:\n%s";

    public static final Set<String> RESERVED_COMMAND_WORDS = buildReservedCommandWords();

    private static final AliasRegistry aliasRegistry = new AliasRegistry();

    private final String action;
    private final String alias;
    private final String command;

    /**
     * Creates an {@code AliasCommand} for the given action.
     */
    public AliasCommand(String action, String alias, String command) {
        this.action = StringUtil.normalize(action);
        this.alias = StringUtil.normalize(alias);
        this.command = StringUtil.normalize(command);
    }

    private static Set<String> buildReservedCommandWords() {
        return Set.of(
                AddCommand.COMMAND_WORD,
                ArchiveCommand.COMMAND_WORD,
                EditCommand.COMMAND_WORD,
                DeleteCommand.COMMAND_WORD,
                StarCommand.COMMAND_WORD,
                UnstarCommand.COMMAND_WORD,
                ClearCommand.COMMAND_WORD,
                ListCommand.COMMAND_WORD,
                ListCommand.ARCHIVED_COMMAND_WORD,
                ExitCommand.COMMAND_WORD,
                HelpCommand.COMMAND_WORD,
                RemarkCommand.COMMAND_WORD,
                SortCommand.COMMAND_WORD,
                FilterCommand.COMMAND_WORD,
                FindCommand.COMMAND_WORD,
                UnarchiveCommand.COMMAND_WORD,
                AliasCommand.COMMAND_WORD);
    }

    /** Returns the shared alias registry used by the application. */
    public static AliasRegistry getAliasRegistry() {
        return aliasRegistry;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (action == null) {
            throw new CommandException(MESSAGE_USAGE);
        }

        switch (action) {
        case "add":
            if (!RESERVED_COMMAND_WORDS.contains(command)) {
                throw new CommandException(String.format(MESSAGE_INVALID_TARGET, command));
            }
            if (aliasRegistry.addAlias(alias, command, RESERVED_COMMAND_WORDS)) {
                return new CommandResult(String.format(MESSAGE_ADD_SUCCESS, alias, command));
            }
            throw new CommandException(String.format(MESSAGE_ADD_CONFLICT, alias));

        case "remove":
            if (aliasRegistry.removeAlias(alias)) {
                return new CommandResult(String.format(MESSAGE_REMOVE_SUCCESS, alias));
            }
            throw new CommandException(String.format(MESSAGE_REMOVE_FAIL, alias));

        case "list":
            String list = aliasRegistry.getAllAliases().entrySet().stream()
                    .map(e -> e.getKey() + " -> " + e.getValue())
                    .collect(Collectors.joining("\n"));
            return new CommandResult(String.format(MESSAGE_LIST, list.isEmpty() ? "(none)" : list));

        default:
            throw new CommandException(MESSAGE_USAGE);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AliasCommand)) {
            return false;
        }
        AliasCommand otherCmd = (AliasCommand) other;
        return java.util.Objects.equals(action, otherCmd.action)
                && java.util.Objects.equals(alias, otherCmd.alias)
                && java.util.Objects.equals(command, otherCmd.command);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(action, alias, command);
    }
}
