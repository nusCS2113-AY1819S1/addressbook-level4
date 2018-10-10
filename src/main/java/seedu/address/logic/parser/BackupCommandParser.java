package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.OnlineStorage;


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
            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()) {
                return new BackupCommand(Optional.empty(), true, Optional.empty(), Optional.empty());
            } else {
                return parseArguments(trimmedArgs);
            }
        } catch (Exception pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BackupCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Parses extra arguments given by the user
     * @param args
     * @return BackupCommand for execution
     * @throws ParseException
     */
    private BackupCommand parseArguments(String args) throws ParseException {
        List<String> argumentList = Arrays.asList(args.split(" ", 0));
        if (argumentList.size() == 1) {
            return new BackupCommand(ParserUtil.parsePath(argumentList.get(0)), true,
                    Optional.empty(), Optional.empty());
        }
        if (argumentList.size() == 2 && argumentList.get(0).toLowerCase().equals("github")) {
            return new BackupCommand(Optional.empty(), false,
                    Optional.ofNullable(OnlineStorage.OnlineStorageType.GITHUB),
                    Optional.ofNullable(argumentList.get(1)));
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BackupCommand.MESSAGE_USAGE));
    }
}
