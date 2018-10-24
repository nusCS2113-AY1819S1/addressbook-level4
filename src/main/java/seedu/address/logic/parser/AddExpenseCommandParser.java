package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE_VALUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddExpenseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.ExpenseCategory;
import seedu.address.model.expense.ExpenseDate;
import seedu.address.model.expense.ExpenseValue;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddExpenseCommand object
 */
public class AddExpenseCommandParser implements Parser<AddExpenseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddExpenseCommand
     * and returns an AddExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddExpenseCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EXPENSE_CATEGORY,
                PREFIX_EXPENSE_VALUE, PREFIX_EXPENSE_DATE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_EXPENSE_CATEGORY, PREFIX_EXPENSE_VALUE, PREFIX_EXPENSE_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExpenseCommand.MESSAGE_USAGE));
        }

        ExpenseCategory expenseCategory =
                ParserUtil.parseExpenseCategory(argMultimap.getValue(PREFIX_EXPENSE_CATEGORY).get());
        ExpenseDate expenseDate = ParserUtil.parseExpenseDate(argMultimap.getValue(PREFIX_EXPENSE_DATE).get());
        ExpenseValue expenseValue = ParserUtil.parseExpenseValue(argMultimap.getValue(PREFIX_EXPENSE_VALUE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Expense expense = new Expense(expenseCategory, expenseDate, expenseValue, tagList);


        return new AddExpenseCommand(expense);
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
