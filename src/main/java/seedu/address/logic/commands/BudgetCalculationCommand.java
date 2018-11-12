package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_BUDGETS_ALREADY_CALCULATED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_BUDGET;

import java.text.DecimalFormat;
import java.util.List;

import seedu.address.logic.BudgetCalculationManager;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.arithmetic.TotalAttendees;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.budgetelements.ClubBudgetElements;
import seedu.address.model.clubbudget.FinalClubBudget;
import seedu.address.model.clubbudget.TotalBudget;

/**
 * Calculates the budgets to be allocated to all the clubs in the list of clubs in the address book.
 */

public class BudgetCalculationCommand extends Command {

    public static final String COMMAND_WORD = "calculatebudget";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Calculates the budgets to be allocated to all the "
            + "clubs in the list of clubs in the address book. "
            + "Parameters:  "
            + PREFIX_TOTAL_BUDGET + "TOTAL BUDGET (IN SGD) "
            + "Example: " + COMMAND_WORD + " " + PREFIX_TOTAL_BUDGET + "50000 ";

    public static final String MESSAGE_CALCULATE_BUDGET_SUCCESS = "The budgets have been calculated.";
    public static final String MESSAGE_INVALID_TOTAL_BUDGET =
            "Please enter a valid total budget! Total Budget can only be positive whole numbers,i.e. even zero.";
    public static final String MESSAGE_DUPLICATE_CLUB = "This is a duplicate club.";
    public static final String MESSAGE_BUDGETS_NOT_CALCULATED =
            "No clubs have submitted their budget calculation data yet, so 'calculatebudget' command can't be used yet";

    private final TotalBudget totalBudget;

    /**
     * @param totalBudget of the person in the filtered clubs list to calculate the budget with
     */
    public BudgetCalculationCommand(TotalBudget totalBudget) {
        requireNonNull(totalBudget);

        this.totalBudget = totalBudget;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<ClubBudgetElements> listOfClubs = model.getFilteredClubsList();
        BudgetCalculationManager budgetCalculationManager = new BudgetCalculationManager();

        if (Integer.parseInt(totalBudget.toString()) < 0) {
            throw new CommandException(MESSAGE_INVALID_TOTAL_BUDGET);
        } else if (budgetCalculationManager.getHaveBudgetsBeenCalculated(model)) {
            return new CommandResult(String.format(MESSAGE_BUDGETS_ALREADY_CALCULATED));
        } else if (budgetCalculationManager.isClubBudgetElementsBookEmpty(model)) {
            return new CommandResult(String.format(MESSAGE_BUDGETS_NOT_CALCULATED));
        } else {
            int i;

            double budgetPerPerson;

            TotalAttendees totalAttendees = new TotalAttendees(listOfClubs);

            budgetPerPerson = Double.parseDouble(totalBudget.toString()) / (totalAttendees.calculateTotalAttendees());

            for (i = 0; i < listOfClubs.size(); i++) {

                ClubBudgetElements currentClubForBudget = listOfClubs.get(i);

                int currentEo = Integer.parseInt(currentClubForBudget.getExpectedTurnout().toString());

                int currentNoe = Integer.parseInt(currentClubForBudget.getNumberOfEvents().toString());

                int totalClubMembers = currentEo * currentNoe;

                double currentClubsBudget = budgetPerPerson * totalClubMembers;

                DecimalFormat decim = new DecimalFormat("0.00");

                double currentClubsBudgetDouble = Double.parseDouble(decim.format(currentClubsBudget));

                FinalClubBudget toAdd = new FinalClubBudget(currentClubForBudget.getClubName(),
                        currentClubsBudgetDouble);

                if (model.hasClubBudget(toAdd)) {
                    throw new CommandException(MESSAGE_DUPLICATE_CLUB);
                }
                model.addClubBudget(toAdd);

            }
            model.commitFinalBudgetsBook();
            return new CommandResult(String.format(MESSAGE_CALCULATE_BUDGET_SUCCESS));
        }
    }
}
