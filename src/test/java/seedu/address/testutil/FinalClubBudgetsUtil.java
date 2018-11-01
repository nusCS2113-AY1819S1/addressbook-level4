package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CLUB_NAME;

import seedu.address.logic.commands.BudgetCalculationCommand;
import seedu.address.model.clubbudget.FinalClubBudget;


/**
 * A utility class for FinalClubBudget.
 */
public class FinalClubBudgetsUtil {

    /**
     * Returns a calculate budget command string for adding the {@code club}.
     */
    public static String getCalculateBudgetCommand(FinalClubBudget budget) {
        return BudgetCalculationCommand.COMMAND_WORD + " " + getFinalClubBudgetDetails(budget);
    }

    /**
     * Returns the part of command string for the given {@code budget}'s details.
     */
    public static String getFinalClubBudgetDetails(FinalClubBudget budget) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_CLUB_NAME + budget.getClubName().toString() + " ");
        sb.append(budget.getAllocatedBudget() + " ");
        return sb.toString();
    }
}
