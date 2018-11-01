package seedu.address.logic;

import java.util.List;

import seedu.address.model.Model;

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
}
