package seedu.planner.logic.commands;
import static java.util.Objects.requireNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;

import seedu.planner.commons.core.Messages;
import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.Model;
import seedu.planner.model.record.Limit;

//@@Author OscarZeng
/**
 * Delete an existing limit according to the dates input.
 * If there is no limit for that period of time, the command will
 * report an error.
 */
public class DeleteLimitCommand extends Command {
    public static final String COMMAND_WORD = "deletelimit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": delete certain limit according to the dates. "
            + "Parameters: "
            + PREFIX_DATE + "DATE_START " + "DATE_END "
            + "(Parameters: "
            + PREFIX_DATE + "DATE) " + "\n"

            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "18-9-2018 " + "20-9-2018 "
            + "(Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "20-9-2018) \n";


    public static final String MESSAGE_SUCCESS = "The limit has been deleted. ";

    private Limit limit;

    public DeleteLimitCommand (Limit limitIn) {
        requireNonNull(limitIn);
        limit = limitIn;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (!model.hasSameDateLimit(limit)) {
            throw new CommandException(Messages.MESSAGE_LIMITS_DO_NOT_EXIST);
        }
        model.deleteLimit(model.getSameDatesLimit(limit.getDateStart(), limit.getDateEnd()));
        model.commitFinancialPlanner();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteLimitCommand // instanceof handles nulls
                && limit.equals(((DeleteLimitCommand) other).limit));
    }

}
