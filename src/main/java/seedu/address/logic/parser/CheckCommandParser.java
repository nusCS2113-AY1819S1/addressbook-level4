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

    private List<String> quantities = new ArrayList<>();

    /**
     * Parses the given {@code String} of arguments in the context of the CheckCommand
     */
    public CheckCommand parse(String args) throws ParseException {
        quantities.clear();

        String trimmedArgs = args.trim();
        if (isNotNumeric(trimmedArgs) || trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE));
        }

        Integer number = Integer.parseInt(trimmedArgs);
        if (number > 999) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_EXCEEDED_QUANTITY));
        }

        createsListOfNumbers(number);

        return new CheckCommand(new QuantityContainsNumberPredicate(quantities));
    }

    private void createsListOfNumbers(Integer number) {
        for (Integer i = number; i >= 0; i--) {
            quantities.add(Integer.toString(i));
        }
    }

    public boolean isNotNumeric(String trimmedArgs) {
        return trimmedArgs != null && !trimmedArgs.matches("[+]?\\d*");
    }
}
