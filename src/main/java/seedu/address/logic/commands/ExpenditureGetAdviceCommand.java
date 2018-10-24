//@@author SHININGGGG
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERIOD;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expenditureinfo.Expenditure;

public class ExpenditureGetAdviceCommand extends Command {
    public static final String COMMAND_WORD = "ET_advice";
    public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gives user advice on how to save money based on last week record.\n"
            + "Parameters: "
            + PREFIX_MONEY + "MONEY "
            + PREFIX_PERIOD + "PERIOD "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MONEY + "1000 "
            + PREFIX_PERIOD + "30 ";

    public static final String MESSAGE_SUCCESS = "Here's the advice on how to spend your money in the following period:\n%s";

    public static String ADVICE;
    public static int money;
    public static int numOfDays;

    /**
     * Get advice on how to spend money
     */
    public ExpenditureGetAdviceCommand(String m, String n) {
        requireNonNull(m);
        requireNonNull(n);
        money = Integer.parseInt(m);
        numOfDays = Integer.parseInt(n);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        float dailyExpense = (float) money / numOfDays;
        Map <String, Integer> map;
        requireNonNull(model);
        map = model.getExpenditureRecords();
        StringBuilder x = new StringBuilder();
        x.append("This is a summary of the expenditures you made in the past. " +
                "From the first day you used this app till now, you have spent\n");
        for(Map.Entry m : map.entrySet()){
                    x.append(m.getValue())
                    .append(" SGD on ")
                    .append(m.getKey())
                    .append("\n");
        }


        x.append("Based on the information given, hope you can know better about where did you spend your money.\n");
        x.append("If you plan to spend a total of ")
                .append(money)
                .append(" SGD in a period of ")
                .append(numOfDays)
                .append(" days, on average, the maximum amount of money that you can spend per day is ")
                .append(dailyExpense);

        ADVICE = x.toString();

        return new CommandResult(String.format(MESSAGE_SUCCESS, ADVICE));

    }

    /*
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
    */

}
