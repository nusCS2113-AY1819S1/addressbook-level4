package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_BUDGET;

import java.util.List;

import seedu.address.logic.CommandHistory;
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
            + "Example: " + COMMAND_WORD + " 50000 ";

    public static final String MESSAGE_CALCULATE_BUDGET_SUCCESS = "The budgets have been calculated.";
    public static final String MESSAGE_INVALID_TOTAL_BUDGET = "Please enter a valid total budget!";

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

        if (Integer.parseInt(totalBudget.toString()) <= 0) {
            throw new CommandException(MESSAGE_INVALID_TOTAL_BUDGET);
        }
        int i;

        int totalAttendees = 0;

        int budgetPerPerson;

        for (i = 0; i < listOfClubs.size(); i++) {

            ClubBudgetElements currentClub = listOfClubs.get(i);

            int currentExpectedTurnout = Integer.parseInt(currentClub.getExpectedTurnout().toString());

            int currentNumberOfEvents = Integer.parseInt(currentClub.getNumberOfEvents().toString());

            totalAttendees += (currentExpectedTurnout * currentNumberOfEvents);

        }

        budgetPerPerson = Integer.parseInt(totalBudget.toString()) / totalAttendees;

        for (i = 0; i < listOfClubs.size(); i++) {

            ClubBudgetElements currentClubForBudget = listOfClubs.get(i);

            int currenteo = Integer.parseInt(currentClubForBudget.getExpectedTurnout().toString());

            int currentnoe = Integer.parseInt(currentClubForBudget.getNumberOfEvents().toString());

            int totalClubMembers = currenteo * currentnoe;

            int currentClubsBudget = budgetPerPerson * totalClubMembers;

            FinalClubBudget toAdd = new FinalClubBudget(currentClubForBudget.getClubName(), currentClubsBudget);

            if (model.hasClubBudget(toAdd)) {
                //throw new CommandException(MESSAGE_DUPLICATE_CLUB);
            }

            model.addClubBudget(toAdd);

        }

        //model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_CALCULATE_BUDGET_SUCCESS));
    }
}
