package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.OnlineStorage;

//@@author QzSG
/**
 * Parses input arguments and creates a new RestoreCommand object
 */
public class RestoreCommandParser implements Parser<RestoreCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RestoreCommand
     * and returns a RestoreCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RestoreCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()) {
                return new RestoreCommand(Optional.empty(), true, Optional.empty(), Optional.empty());
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
     * @return RestoreCommand for execution
     * @throws ParseException
     */
    private RestoreCommand parseArguments(String args) throws ParseException {
        List<String> argumentList = Arrays.asList(args.split(" ", 0));
        if (argumentList.size() == 1) {
            return new RestoreCommand(ParserUtil.parsePath(argumentList.get(0)), true,
                    Optional.empty(), Optional.empty());
        }
        if (argumentList.size() == 2 && argumentList.get(0).toLowerCase().equals("github")) {
            return new RestoreCommand(Optional.empty(), false,
                    Optional.ofNullable(OnlineStorage.OnlineStorageType.GITHUB),
                    Optional.ofNullable(argumentList.get(1)));
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BackupCommand.MESSAGE_USAGE));
    }
}
