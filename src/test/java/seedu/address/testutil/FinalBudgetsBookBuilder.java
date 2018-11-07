package seedu.address.testutil;

import seedu.address.model.FinalBudgetsBook;
import seedu.address.model.clubbudget.FinalClubBudget;

/**
 * A utility class to help with building FinalBudgetsBook objects.
 */
public class FinalBudgetsBookBuilder {

    private FinalBudgetsBook finalBudgetsBook;

    public FinalBudgetsBookBuilder() {
        finalBudgetsBook = new FinalBudgetsBook();
    }

    public FinalBudgetsBookBuilder(FinalBudgetsBook finalBudgetsBook) {
        this.finalBudgetsBook = finalBudgetsBook;
    }

    /**
     * Adds a new {@code FinalClubBudget} to the {@code FinalBudgetsBook} that we are building.
     */
    public FinalBudgetsBookBuilder withFinalClubBudget(FinalClubBudget budget) {
        finalBudgetsBook.addClubBudget(budget);
        return this;
    }

    public FinalBudgetsBook build() {
        return finalBudgetsBook;
    }
}
