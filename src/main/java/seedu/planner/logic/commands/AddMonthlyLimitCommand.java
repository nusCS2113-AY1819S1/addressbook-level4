package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MONEYFLOW;

import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.Model;
import seedu.planner.model.record.Limit;



/**
 * This Command is to set a limit for continuous month, which means the this limit is always for current month.
 * */
public class AddMonthlyLimitCommand extends Command {
    public static final String COMMAND_WORD = "addmonthlylimit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set a limit for continuous months. "
            + "Parameters: "
            + PREFIX_MONEYFLOW + "LIMIT_MONEY " + "\n"
            + "Example:"
            + PREFIX_MONEYFLOW + "500" + "\n";

    public static final String MESSAGE_HAS_LIMIT = "There is already a monthly limit. \n";
    public static final String MESSAGE_SUCCESS = "The monthly limit has been added";
    public static final String DATE_SPECIAL_FOR_MONTHLY = "01-01-0001";



    private Limit limit;

    private String output;

    public AddMonthlyLimitCommand (Limit limitIn) {
        requireNonNull(limitIn);
        limit = limitIn;

    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (model.hasSameDateLimit(limit)) {
            throw new CommandException(MESSAGE_HAS_LIMIT);
        }

        model.addLimit(limit);
        output = MESSAGE_SUCCESS + model.generateLimitOutput(model.isExceededLimit(limit),
                model.getTotalSpend(limit), limit);
        model.commitFinancialPlanner();
        return new CommandResult(output);
    }

    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.planner.logic.commands.AddMonthlyLimitCommand // instanceof handles nulls
                && limit.equals(((seedu.planner.logic.commands.AddMonthlyLimitCommand) other).limit));
    }
}
