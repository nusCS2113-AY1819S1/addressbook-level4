package seedu.address.logic;

import java.util.List;

import seedu.address.model.Model;

import seedu.address.model.budgetelements.ClubBudgetElements;
import seedu.address.model.clubbudget.FinalClubBudget;

/**
 * Checks whether the FinalBudgetsBook is empty to determine whether budgets have already been calculated
 */

public class BudgetCalculationManager {
    private static boolean haveBudgetsBeenCalculated = false;

    public BudgetCalculationManager() {}

    public boolean getHaveBudgetsBeenCalculated(Model model) {
        List<FinalClubBudget> listOfBudgets = model.getFilteredClubBudgetsList();
        haveBudgetsBeenCalculated = (listOfBudgets.size() != 0);
        return haveBudgetsBeenCalculated;
    }

    /**
     * @param model the ClubBudgetElementsBook stored in {@code Model}
     * @return true if ClubBudgetElementsBook is empty
     */
    public boolean isClubBudgetElementsBookEmpty(Model model) {
        List<ClubBudgetElements> listOfClubs = model.getFilteredClubsList();
        return (listOfClubs.size() == 0);
    }
}
