package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MONEYFLOW;

import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.Model;

import seedu.planner.model.record.Limit;
import seedu.planner.model.record.Record;

/**
* This Command is used as a limit function, Currently the user can input two Dates and one MoneyFlow,
* and the command will check whether the the total expense during this period has exceeded the limit.
* */
public class LimitCommand extends Command {
    public static final String COMMAND_WORD = "limit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Check the limit for a period of time. "
            + "Parameters: "
            + PREFIX_DATE + "DATE_START " + "DATE_END "
            + PREFIX_MONEYFLOW + "LIMIT_MONEY "

            + "Example: " + COMMAND_WORD + " "

            + PREFIX_DATE + "18-9-2018 " + "20-9-2018 "
            + PREFIX_MONEYFLOW + "100 ";

    public static final String MESSAGE_BSSIC = "The limit you have set: %.2f \n"
            + "The total money you have spent: %.2f\n";

    public static final String MESSAGE_EXCEED = "Your spend exceeded the limit !!! "; //%l$s";
    public static final String MESSAGE_NOT_EXCEED = "Your spend did not exceed the limit ^o^";
    private Limit limit;
    private Record recordNow;
    private int countRecord = 0;
    private double sumOfSpend = 0;

    public LimitCommand (Limit limitin) {
        requireNonNull(limitin);
        limit = limitin;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        while (countRecord < model.getFinancialPlanner().getRecordList().size()) {
            recordNow = model.getFinancialPlanner().getRecordList().get(countRecord++);

            if ((recordNow.getDate().isEarlierThan(limit.getDateEnd())
                   && recordNow.getDate().isLaterThan(limit.getDateStart())
                    || recordNow.getDate().equals(limit.getDateStart())
                        || recordNow.getDate().equals(limit.getDateEnd()))) {
                sumOfSpend += recordNow.getMoneyFlow().toDouble();
            }
        }

        if (limit.getLimitMoneyFlow().isNotLarger(sumOfSpend)) {
            return new CommandResult(
                    String.format(MESSAGE_BSSIC, -1 * limit.getLimitMoneyFlow().toDouble(), (-1 * sumOfSpend))
                            + MESSAGE_NOT_EXCEED);
        } else {
            return new CommandResult(
                    String.format(MESSAGE_BSSIC, -1 * limit.getLimitMoneyFlow().toDouble(), (-1 * sumOfSpend))
                            + MESSAGE_EXCEED);

        }
    }
    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof LimitCommand // instanceof handles nulls
                && limit.equals(((LimitCommand) other).limit));
    }
}
