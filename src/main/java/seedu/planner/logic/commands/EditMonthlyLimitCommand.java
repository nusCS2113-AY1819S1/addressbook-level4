package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MONEYFLOW;

import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.Model;
import seedu.planner.model.record.Limit;
//@@Author OscarZeng

/**
 * This command is to modify the monthly limit money.
 */
public class EditMonthlyLimitCommand extends Command {
    public static final String COMMAND_WORD = "editmonthlylimit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edit the existing monthly limit. "
            + "Parameters: "
            + PREFIX_MONEYFLOW + "LIMIT_MONEY " + "\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_MONEYFLOW + "500" + "\n";


    public static final String MESSAGE_SUCCESS = "The monthly limit has been edited. \n";
    public static final String MESSAGE_FAILURE = "There is no monthly limit. \n";
    public static final String MESSAGE_SAME_LIMIT = "The edited monthly is same as the original monthly limit";

    private Limit newLimit;
    private Limit originalLimit;

    private String output;

    public EditMonthlyLimitCommand (Limit limitIn) {
        requireNonNull(limitIn);
        newLimit = limitIn;

    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (!model.hasSameDateLimit(newLimit)) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        originalLimit = model.getSameDatesLimit(newLimit.getDateStart(), newLimit.getDateEnd());
        model.updateLimit(originalLimit, newLimit);
        if (originalLimit.equals(newLimit)) {
            throw new CommandException(MESSAGE_SAME_LIMIT);
        }
        output = MESSAGE_SUCCESS + "Original Limit:\n"
                + model.generateLimitOutput(model.isExceededLimit(originalLimit),
                model.getTotalSpend(originalLimit), originalLimit)
                + "Modified Limit: \n"
                + model.generateLimitOutput(model.isExceededLimit(newLimit),
                model.getTotalSpend(newLimit), newLimit);
        model.commitFinancialPlanner();
        return new CommandResult(output);
    }

    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.planner.logic.commands.EditMonthlyLimitCommand // instanceof handles nulls
                && newLimit.equals(((seedu.planner.logic.commands.EditMonthlyLimitCommand) other).newLimit));
    }
}
