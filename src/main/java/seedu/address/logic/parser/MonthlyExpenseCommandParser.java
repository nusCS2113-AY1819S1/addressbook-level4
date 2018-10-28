package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import seedu.address.logic.commands.MonthlyExpenseCommand;

/**
 * Parses input arguments and creates a new MonthlyExpenseCommand object
 */
public class MonthlyExpenseCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the MonthlyExpenseCommand
     * and returns an MonthlyExpenseCommand object for execution.
     * @throws java.text.ParseException if the user input does not conform the expected format
     */
    public MonthlyExpenseCommand parse(String args) throws seedu.address.logic.parser.exceptions.ParseException {
        args = args.trim();
        String pattern = "MM/yyyy";
        if (args.length() != pattern.length()) {
            throw new seedu.address.logic.parser.exceptions.ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT,
                    MonthlyExpenseCommand.MESSAGE_MONTHLY_EXPENSE_COMMAND_CONSTRAINTS
            ));
        }
        DateFormat dateFormat = new SimpleDateFormat (pattern);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(args);
        } catch (java.text.ParseException pe) {
            throw new seedu.address.logic.parser.exceptions.ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT,
                    MonthlyExpenseCommand.MESSAGE_MONTHLY_EXPENSE_COMMAND_CONSTRAINTS
            ));
        }
        return new MonthlyExpenseCommand(args);
    }
}
