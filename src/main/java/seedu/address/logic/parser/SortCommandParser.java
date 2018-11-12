package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//Reused from https://github.com/CS2103JAN2018-F14-B1/main/pull/57/files with minor modifications
//@@author Woonhian
/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    public static final List<String> ACCEPTED_FIELDS = new ArrayList<>(Arrays.asList("name", "department"));
    public static final List<String> ACCEPTED_ORDERS = new ArrayList<>(Arrays.asList("asc", "desc"));

    @Override
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String[] argKeywords = trimmedArgs.split("\\s+");

        for (int i = 0; i < argKeywords.length; i++) {
            argKeywords[i] = argKeywords[i].toLowerCase();
        }

        if (argKeywords.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        if (!ACCEPTED_FIELDS.contains(argKeywords[0])) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        if (!ACCEPTED_ORDERS.contains(argKeywords[1])) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand(argKeywords[0], argKeywords[1]);
    }
}
