package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.CheckCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.QuantityContainsNumberPredicate;

/**
 * Parses input arguments and creates a new CheckCommand object
 */
public class CheckCommandParser implements Parser<CheckCommand> {

    /**
     * Parse the given {@code String} of arguments in the context of the CheckCommand
     * @param args
     * @return
     * @throws ParseException
     */
    public CheckCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE));
        }

        List<String> quantities = new ArrayList<>();
        Integer number = Integer.parseInt(trimmedArgs);
        for (Integer i = number; i > 0; i--) {
            quantities.add(Integer.toString(i));
        }

        return new CheckCommand(new QuantityContainsNumberPredicate(quantities));
    }
}
