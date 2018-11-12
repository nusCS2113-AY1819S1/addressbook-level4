package seedu.planner.logic.commands;
import static java.util.Objects.requireNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MONEYFLOW;

import seedu.planner.commons.core.Messages;
import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.Model;
import seedu.planner.model.record.Limit;
//@@Author OscarZeng


/**
 * This command will allow user to modify the existing limit according to the two dates.
 * The newly input limit will replace the old limit.
 */
public class EditLimitCommand extends Command {
    public static final String COMMAND_WORD = "editlimit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edit an existing limit according to the dates. "
            + "Parameters: "
            + PREFIX_DATE + "DATE_START " + "DATE_END "
            + PREFIX_MONEYFLOW + "NEW_LIMIT_MONEY "
            + "(Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_MONEYFLOW + "NEW_LIMIT_MONEY) \n"

            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "18-9-2018 " + "20-9-2018 "
            + PREFIX_MONEYFLOW + "200 "
            + "(" + COMMAND_WORD + " "
            + PREFIX_DATE + "18-9-2018 "
            + PREFIX_MONEYFLOW + "200)";


    public static final String MESSAGE_SUCCESS = "The limit has been edited. \n";
    public static final String MESSAGE_SAME_LIMIT = "The edited limit has the same moneyFlow as the original limit.\n";
    private Limit originalLimit;
    private String output;
    private Limit newLimit;

    public EditLimitCommand (Limit limitIn) {
        requireNonNull(limitIn);
        newLimit = limitIn;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (!model.hasSameDateLimit(newLimit)) {
            throw new CommandException(Messages.MESSAGE_LIMITS_DO_NOT_EXIST);
        }
        originalLimit = model.getSameDatesLimit(newLimit.getDateStart(), newLimit.getDateEnd());

        if (originalLimit.getLimitMoneyFlow().equals(newLimit.getLimitMoneyFlow())) {
            throw new CommandException(MESSAGE_SAME_LIMIT);
        }
        model.updateLimit(originalLimit, newLimit);

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
                || (other instanceof EditLimitCommand // instanceof handles nulls
                && newLimit.equals(((EditLimitCommand) other).newLimit));
    }

}
