package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MONEYFLOW;

import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.Model;
import seedu.planner.model.record.Limit;

    /*
    * This Command is used as a limit function, Currently the user can input two Date and one MoneyFlow,
    * and the command will check whether the the total expense during this period has exceeded the limit.
    * */
    public class LimitCommand extends Command {
    public static final String COMMAND_WORD= "limit";
    public static final String MESSAGE_USAGE= COMMAND_WORD + ": Check the limit for a period of time. "
            + "Parameters: "
            + PREFIX_DATE + "DATE_START "
            + PREFIX_DATE + "DATE_END "
            + PREFIX_MONEYFLOW + "LIMIT_MONEY "

            + "Example: " + COMMAND_WORD + " "

            + PREFIX_DATE + "18-9-2018 "
            + PREFIX_DATE + "20-9-2018 "
            + PREFIX_MONEYFLOW + "-100 ";

    //public static Limit limit;

    public static final String MESSAGE_SUCCESS = "Limit has been set: %1$s";
    public static final String MESSAGE_DUPLICATE_LIMIT = "There is already a limit for this period ";
    private static Limit limit;

    public LimitCommand (Limit limitin) {
        requireNonNull(limitin);
        limit= limitin;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        //if (model.hasRecord(limit)) {
       /* if (false) {
            throw new CommandException(MESSAGE_DUPLICATE_LIMIT);
        }*/
        return new CommandResult(String.format(MESSAGE_SUCCESS, limit));
    }
    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof LimitCommand // instanceof handles nulls
                && limit.equals(((LimitCommand) other).limit));
    }
}
