//@@author SHININGGGG
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERIOD;

import java.util.HashMap;
import java.util.Map;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Get advice on how to spend money
 */
public class ExpenditureGetAdviceCommand extends Command {
    public static final String COMMAND_WORD = "ET_advice";
    public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gives user advice on "
            + "how to save money based on last week record.\n"
            + "Parameters: "
            + PREFIX_MONEY + "MONEY "
            + PREFIX_PERIOD + "PERIOD "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MONEY + "1000 "
            + PREFIX_PERIOD + "30 ";

    public static final String MESSAGE_SUCCESS = "Here's the advice on how to "
            + "spend your money in the following period:\n\n%s";

    private String advice;
    private double money;
    private int numOfDays;

    /**
     * Get advice on how to spend money
     */
    public ExpenditureGetAdviceCommand(String m, String n) {
        requireNonNull(m);
        requireNonNull(n);
        money = Double.parseDouble(m);
        numOfDays = Integer.parseInt(n);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        requireNonNull(model);
        double dailyExpense = money / numOfDays;
        double totalExpenditure = 0;
        Map <String, Double> map;
        Map <String, Integer> percentageMap = new HashMap<String, Integer>();
        map = model.getExpenditureRecords();
        StringBuilder x = new StringBuilder();

        x.append("This is a summary of the expenditures you made in the past.\n"
                + "From the first day you used this app till now, you have spent:\n");

        for (Map.Entry s : map.entrySet()) {
            totalExpenditure += map.get(s.getKey());
        }

        for (Map.Entry m : map.entrySet()) {
            percentageMap.put(m.getKey().toString(), (int) Math.round(100 * map.get(m.getKey()) / totalExpenditure));
            x.append(m.getValue())
                .append(" SGD on ")
                .append(m.getKey())
                .append(", which takes approximately ")
                .append((int) Math.round(100 * map.get(m.getKey()) / totalExpenditure))
                .append("% percentage of the total expenditure you made")
                .append("\n");
        }

        x.append("\nBased on the information given, hope you can know better about where did you spend your money.\n");
        x.append("If you plan to spend a total of ")
            .append(money)
            .append(" SGD in a period of ")
            .append(numOfDays)
            .append(" days, on average, the maximum amount of money that you can spend per day is ")
            .append(dailyExpense)
            .append(".\n")
            .append("According to your expenditure history record, here' a more detailed advice "
                    + "on how much money you can spend on each category in the following \nperiod and ")
            .append("please note that the sum of adviced individual expenditure will not necessarily "
                    + "be the same as the target money, there might be a slight \ndifference.\n");

        for (Map.Entry t : map.entrySet()) {
            x.append(t.getKey())
                    .append(": ")
                    .append(money * percentageMap.get(t.getKey()) / 100)
                    .append(" SGD\n");
        }

        x.append("\nHappy planning! : )");

        advice = x.toString();

        return new CommandResult(String.format(MESSAGE_SUCCESS, advice));

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
