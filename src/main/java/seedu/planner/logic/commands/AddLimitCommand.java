package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MONEYFLOW;

import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.Model;
import seedu.planner.model.record.Limit;
//@@author Zeng Hao(Oscar)
/**
* This Command is used as a limit function, Currently the user can input two Dates and one MoneyFlow,
* and the command will check whether the the total expense during this period has exceeded the limit.
 * and the limit will be stored inside the limit storage.
* */

public class AddLimitCommand extends Command {
    public static final String COMMAND_WORD = "addlimit";
    public static final String COMMAND_WORD_UNDERSCORE = "add_limit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set a limit for a period of time. or a single day. "

            + "Parameters: "
            + PREFIX_DATE + "DATE_START " + "DATE_END "
            + PREFIX_MONEYFLOW + "LIMIT_MONEY "
            + "(Parameters: "
            + PREFIX_DATE + "DATE_START "
            + PREFIX_MONEYFLOW + "LIMIT_MONEY) " + "\n"

            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "18-9-2018 " + "20-9-2018 "
            + PREFIX_MONEYFLOW + "100 ("
            + COMMAND_WORD + " "
            + PREFIX_DATE + "18-9-2018 "
            + PREFIX_MONEYFLOW + "100) \n";

    public static final String MESSAGE_SINGLE_DATE = "Date: %s\n";
    public static final String MESSAGE_DOUBLE_DATE = "Date period: %s -- %s\n";
    public static final String MESSAGE_BASIC_SPEND = "The limit you have set: %.2f \n"
            + "Your spend during the limit period: %.2f\n";

    public static final String MESSAGE_BASIC_EARNED = "The limit you have set: %.2f \n"
            + "Your income during the limit period: %.2f\n";
    public static final String MESSAGE_EXCEED = "Your spend exceeded the limit !!! \n";
    public static final String MESSAGE_NOT_EXCEED = "Your spend did not exceed the limit ^o^\n";
    public static final String MESSAGE_LIMITS_SAME_DATE = "There is already a limit for that period of date\n";



    private Limit limit;

    private String output;

    public AddLimitCommand (Limit limitIn) {
        requireNonNull(limitIn);
        limit = limitIn;

    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (model.hasSameDateLimit(limit)) {
            throw new CommandException(MESSAGE_LIMITS_SAME_DATE);
        }

        model.addLimit(limit);
        output = model.generateLimitOutput(model.isExceededLimit(limit),
                model.getTotalSpend(limit), limit);
        model.commitFinancialPlanner();
        return new CommandResult(output);
    }

    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddLimitCommand // instanceof handles nulls
                && limit.equals(((AddLimitCommand) other).limit));
    }
}
