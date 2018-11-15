package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLOCATED_BUDGET_COMPUTING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLOCATED_BUDGET_ECE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLUB_NAME_COMPUTING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLUB_NAME_ECE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.FinalBudgetsBook;
import seedu.address.model.clubbudget.FinalClubBudget;


/**
 * A utility class containing a list of {@code FinalClubBudget} objects to be used in tests.
 */
public class TypicalFinalClubBudget {

    public static final FinalClubBudget CLUB_BUDGET_1 = new FinalClubBudgetBuilder().withClubName("Computing Club")
            .withAllocatedBudget("1000").build();

    public static final FinalClubBudget CLUB_BUDGET_2 = new FinalClubBudgetBuilder().withClubName("ECE Club")
            .withAllocatedBudget("2100").build();

    public static final FinalClubBudget CLUB_BUDGET_3 = new FinalClubBudgetBuilder().withClubName("Film Club")
            .withAllocatedBudget("450").build();

    public static final FinalClubBudget CLUB_BUDGET_4 = new FinalClubBudgetBuilder().withClubName("Photography Club")
            .withAllocatedBudget("500").build();

    public static final FinalClubBudget CLUB_BUDGET_5 = new FinalClubBudgetBuilder().withClubName("Dance Club")
            .withAllocatedBudget("1200").build();

    // Manually added - Club's details found in {@code CommandTestUtil}
    public static final FinalClubBudget COMPUTING_CLUB = new FinalClubBudgetBuilder()
            .withClubName(VALID_CLUB_NAME_COMPUTING)
            .withAllocatedBudget(VALID_ALLOCATED_BUDGET_COMPUTING).build();

    public static final FinalClubBudget ECE_CLUB = new FinalClubBudgetBuilder().withClubName(VALID_CLUB_NAME_ECE)
            .withAllocatedBudget(VALID_ALLOCATED_BUDGET_ECE).build();

    private TypicalFinalClubBudget() {} // prevents instantiation

    /**
     * Returns a {@code FinalBudgetsBook} with all the typical clubs.
     */
    public static FinalBudgetsBook getTypicalFinalBudgetsBook() {
        FinalBudgetsBook fbb = new FinalBudgetsBook();
        for (FinalClubBudget budget : getTypicalFinalBudgets()) {
            fbb.addClubBudget(budget);
        }
        return fbb;
    }

    /**
     * Returns a list of all typical clubs
     */
    public static List<FinalClubBudget> getTypicalFinalBudgets() {
        return new ArrayList<>(Arrays.asList(CLUB_BUDGET_1, CLUB_BUDGET_2, CLUB_BUDGET_3, CLUB_BUDGET_4,
                CLUB_BUDGET_5));
    }

}
