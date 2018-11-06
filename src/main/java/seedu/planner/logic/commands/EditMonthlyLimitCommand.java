package seedu.planner.logic.commands;

import seedu.planner.commons.core.Messages;
import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.Model;
import seedu.planner.model.record.Limit;

import static java.util.Objects.requireNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MONEYFLOW;

public class EditMonthlyLimitCommand extends Command{
    public static final String COMMAND_WORD = "editmonthlylimit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edit the existing monthly limit. "
            + "Parameters: "
            + PREFIX_MONEYFLOW + "LIMIT_MONEY " + "\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_MONEYFLOW + "500" + "\n";


    public static final String MESSAGE_SUCCESS = "The monthly limit has been edited. \n";



    private Limit limit;
    private Limit originalLimit;

    private String output;

    public EditMonthlyLimitCommand (Limit limitIn) {
        requireNonNull(limitIn);
        limit = limitIn;

    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (!model.hasSameDateLimit(limit)) {
            throw new CommandException(Messages.MESSAGE_LIMITS_DO_NOT_EXIST);
        }
        originalLimit = model.getSameDatesLimit(limit.getDateStart(), limit.getDateEnd());
        model.updateLimit(originalLimit, limit);

        output = MESSAGE_SUCCESS + "Original Limit:\n"
                + model.generateLimitOutput(model.isExceededLimit(originalLimit),
                model.getTotalSpend(originalLimit), originalLimit)
                + "Modified Limit: \n"
                + model.generateLimitOutput(model.isExceededLimit(limit),
                model.getTotalSpend(limit), limit);
        model.commitFinancialPlanner();
        return new CommandResult(output);
    }

    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.planner.logic.commands.EditMonthlyLimitCommand // instanceof handles nulls
                && limit.equals(((seedu.planner.logic.commands.EditMonthlyLimitCommand) other).limit));
    }
}
