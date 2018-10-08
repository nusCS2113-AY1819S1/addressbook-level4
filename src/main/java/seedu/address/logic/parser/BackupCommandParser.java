package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.parser.exceptions.ParseException;


//@@author QzSG
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class BackupCommandParser implements Parser<BackupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BackupCommand
     * and returns a BackupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BackupCommand parse(String args) throws ParseException {
        try {
            String backupPathString = args.trim();
            if (backupPathString.isEmpty()) {
                return new BackupCommand(Optional.empty());
            } else {
                return new BackupCommand(ParserUtil.parsePath(backupPathString));
            }
        } catch (Exception pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BackupCommand.MESSAGE_USAGE), pe);
        }
    }

}
