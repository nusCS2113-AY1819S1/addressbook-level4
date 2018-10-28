package seedu.address.testutil;

import seedu.address.model.budgetelements.ClubName;
import seedu.address.model.clubbudget.FinalClubBudget;

/**
 * A utility class to help with building FinalClubBudget objects.
 */
public class FinalClubBudgetBuilder {

    public static final String DEFAULT_CLUB_NAME = "Computing Club";
    public static final String DEFAULT_ALLOCATED_BUDGET = "1000";

    private ClubName clubName;
    private int allocatedBudget;

    public FinalClubBudgetBuilder() {
        clubName = new ClubName(DEFAULT_CLUB_NAME);
        allocatedBudget = Integer.parseInt(DEFAULT_ALLOCATED_BUDGET);
    }

    /**
     * Initializes the FinalClubBudgetBuilder with the data of {@code finalBudgetToCopy}.
     */
    public FinalClubBudgetBuilder(FinalClubBudget finalBudgetToCopy) {
        clubName = finalBudgetToCopy.getClubName();
        allocatedBudget = finalBudgetToCopy.getAllocatedBudget();
    }

    /**
     * Sets the {@code ClubName} of the {@code FinalClubBudget} that we are building.
     */
    public FinalClubBudgetBuilder withClubName(String clubName) {
        this.clubName = new ClubName(clubName);
        return this;
    }

    /**
     * Sets the {@code allocatedBudget} of the {@code FinalClubBudget} that we are building.
     */
    public FinalClubBudgetBuilder withAllocatedBudget(String allocatedBudget) {
        this.allocatedBudget = Integer.parseInt(allocatedBudget);
        return this;
    }

    public FinalClubBudget build() {
        return new FinalClubBudget(clubName, allocatedBudget);
    }

}
