package seedu.address.model.budgetelements;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents the budget elements of a club in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ClubBudgetElements {

    // Identity fields
    private final ClubName clubName;

    // Data fields
    private final ExpectedTurnout expectedTurnout;
    private final NumberOfEvents numberOfEvents;


    /**
     * Every field must be present and not null.
     */
    public ClubBudgetElements(ClubName clubName, ExpectedTurnout expectedTurnout, NumberOfEvents numberOfEvents) {
        requireAllNonNull(clubName, expectedTurnout, numberOfEvents);
        this.clubName = clubName;
        this.expectedTurnout = expectedTurnout;
        this.numberOfEvents = numberOfEvents;
    }

    public ClubName getClubName() {
        return clubName;
    }

    public ExpectedTurnout getExpectedTurnout() {
        return expectedTurnout;
    }

    public NumberOfEvents getNumberOfEvents() {
        return numberOfEvents;
    }


    /**
     * Returns true if both clubs have the same name
     * This defines a weaker notion of equality between two persons.
     */


    public boolean isSameClub(ClubBudgetElements otherClubBudgetElements) {
        if (otherClubBudgetElements == this) {
            return true;
        }

        return otherClubBudgetElements != null
                && otherClubBudgetElements.getClubName().equals(getClubName());
    }


    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ClubBudgetElements)) {
            return false;
        }

        ClubBudgetElements otherClubBudgetElements = (ClubBudgetElements) other;
        return otherClubBudgetElements.getClubName().equals(getClubName());
    }


    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(clubName, expectedTurnout, numberOfEvents);
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
