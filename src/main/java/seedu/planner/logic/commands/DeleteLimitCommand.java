package seedu.planner.logic.commands;

import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.Model;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.Limit;

import static java.util.Objects.requireNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;


public class DeleteLimitCommand extends Command{
    public static final String COMMAND_WORD = "deleteLimit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": delete certain limit according to the dates. "
            + "Parameters: "
            + PREFIX_DATE + "DATE_START " + "DATE_END "

            + "Example: " + COMMAND_WORD + " "

            + PREFIX_DATE + "18-9-2018 " + "20-9-2018 ";


    public static final String MESSAGE_SUCCESS = "The limit has been deleted. ";
    public static final String MESSAGE_LIMITS_DO_NOT_EXIST = "There is no limit for that period of date";

    private Limit limit;

    public DeleteLimitCommand (Limit limitIn) {
        requireNonNull(limitIn);
        limit = limitIn;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (!model.hasSameDateLimit(limit)) {
            throw new CommandException(MESSAGE_LIMITS_DO_NOT_EXIST);
        }
        model.deleteLimit(model.getSameDatesLimit(limit.getDateStart(), limit.getDateEnd()));

        return new CommandResult(MESSAGE_SUCCESS + model.autoLimitCheck());
    }

    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteLimitCommand // instanceof handles nulls
                && limit.equals(((DeleteLimitCommand) other).limit));
    }

}
