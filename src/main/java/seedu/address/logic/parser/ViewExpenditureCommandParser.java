package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewExpenditureCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewExpenditureCommand object
 */
public class ViewExpenditureCommandParser implements Parser<ViewExpenditureCommand> {

    public static final String DATE_VALIDATION_REGEX = "[\\d]{2}" + "-" + "[\\d]{2}" + "-" + "[\\d]{4}";

    /**
     * Parses the given {@code String} of arguments in the context of the ViewExpenditureCommand
     * and returns an ViewExpenditureCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewExpenditureCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String test = args.trim();

        if (test.matches(DATE_VALIDATION_REGEX) || "Food".equals(test)
                || "Drink".equals(test)
                || "Clothing".equals(test)
                || "Electronics".equals(test)
                || "DailyNecessities".equals(test)
                || "Sports".equals(test)
                || "Communications".equals(test)
                || "Travels".equals(test)
                || "Study".equals(test)
                || "Office".equals(test)
                || "Pets".equals(test)
                || "Gifts".equals(test)
                || "Entertainment".equals(test)
                || "Traffic".equals(test)
                || "Shopping".equals(test)
                || "Beauty".equals(test)
                || "Furniture".equals(test) || "all".equals(test)) {
            return new ViewExpenditureCommand(test);
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewExpenditureCommand.MESSAGE_USAGE));
    }
}
