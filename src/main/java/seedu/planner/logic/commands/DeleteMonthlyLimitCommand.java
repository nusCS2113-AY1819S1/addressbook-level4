package seedu.planner.logic.commands;

import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.Model;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.Limit;
import seedu.planner.model.record.MoneyFlow;

import static java.util.Objects.requireNonNull;

public class DeleteMonthlyLimitCommand extends Command {
    public static final String COMMAND_WORD = "deletemonthlylimit";
    public static final String MESSAGE_SUCCESS = "The monthly limit has been deleted";
    public static final String MESSAGE_FAILURE = "There is no monthly limit!";
    public static final Date DATE_SPECIAL_FOR_MONTHLY = new Date("01-01-9999");

    private Limit monthlyLimit = new Limit(DATE_SPECIAL_FOR_MONTHLY, DATE_SPECIAL_FOR_MONTHLY, new MoneyFlow("-1"));
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.hasSameDateLimit(monthlyLimit)) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        model.deleteLimit(model.getSameDatesLimit(DATE_SPECIAL_FOR_MONTHLY, DATE_SPECIAL_FOR_MONTHLY));
        model.commitFinancialPlanner();
        return new CommandResult(MESSAGE_SUCCESS);

    }
}
