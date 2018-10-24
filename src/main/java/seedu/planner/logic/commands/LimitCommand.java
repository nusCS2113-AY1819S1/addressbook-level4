package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MONEYFLOW;

import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.Model;

import seedu.planner.model.record.Limit;

/**
* This Command is used as a limit function, Currently the user can input two Dates and one MoneyFlow,
* and the command will check whether the the total expense during this period has exceeded the limit.
 * and the limit will be stored inside the limit storage.
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

    public static final String MESSAGE_BASIC = "Date Period: %s -- %s.\n The limit you have set: %.2f \n";

    public static final String MESSAGE_EXCEED = "Your spend exceeded the limit !!! "; //%l$s";
    public static final String MESSAGE_NOT_EXCEED = "Your spend did not exceed the limit ^o^";
    public static final String MESSAGE_LIMITS_SAME_DATE = "There are already limits for that period of date";

    private Limit limit;
    private String output;

    public LimitCommand (Limit limitIn) {
        requireNonNull(limitIn);
        limit = limitIn;

    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (model.hasSameDateLimit(limit)) {
            throw new CommandException(MESSAGE_LIMITS_SAME_DATE);
        }

        model.addLimit(limit);
        output = model.generateLimitOutput(model.isExceededLimit(limit), limit);

        return new CommandResult(output);
    }

    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof LimitCommand // instanceof handles nulls
                && limit.equals(((LimitCommand) other).limit));
    }
}
