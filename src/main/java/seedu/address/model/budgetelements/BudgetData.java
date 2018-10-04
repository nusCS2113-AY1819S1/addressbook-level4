package seedu.address.model.budgetelements;

import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class BudgetData {
    // Identity field
    private final ClubName clubname;

    // Data fields
    private final ExpectedTurnout expectedturnout;
    private final NumberOfEvents numberofevents;

    /**
     * Every field must be present and not null.
     */
    public BudgetData(ClubName clubname, ExpectedTurnout expectedturnout, NumberOfEvents numberofevents) {
        requireAllNonNull(clubname, expectedturnout, numberofevents);
        this.clubname = clubname;
        this.expectedturnout = expectedturnout;
        this.numberofevents = numberofevents;
    }

    public ClubName getClubName() {
        return clubname;
    }

    public ExpectedTurnout getExpectedTurnout() {
        return expectedturnout;
    }

    public NumberOfEvents getNumberOfEvents() {
        return numberofevents;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(clubname, expectedturnout, numberofevents);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getClubName())
                .append(" Expected Turnout: ")
                .append(getExpectedTurnout())
                .append(" Number of Events: ")
                .append(getNumberOfEvents());
        return builder.toString();
    }
}
