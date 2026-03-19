package seedu.address.logic.parser;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AliasCommand object.
 */
public class AliasCommandParser implements Parser<AliasCommand> {
    @Override
    public AliasCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(AliasCommand.MESSAGE_USAGE);
        }
        String[] tokens = trimmedArgs.split("\\s+");
        String action = tokens[0];
        if (action.equals("add")) {
            if (tokens.length != 3) {
                throw new ParseException(AliasCommand.MESSAGE_USAGE);
            }
            return new AliasCommand("add", tokens[1], tokens[2]);
        } else if (action.equals("remove")) {
            if (tokens.length != 2) {
                throw new ParseException(AliasCommand.MESSAGE_USAGE);
            }
            return new AliasCommand("remove", tokens[1], null);
        } else if (action.equals("list")) {
            return new AliasCommand("list", null, null);
        } else {
            throw new ParseException(AliasCommand.MESSAGE_USAGE);
        }
    }
}
