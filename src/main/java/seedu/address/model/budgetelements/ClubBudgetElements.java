package seedu.address.model.budgetelements;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import seedu.address.model.tag.Tag;

/**
 * Represents the budget elements of a club in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ClubBudgetElements {

    // Identity fields
    private final ClubName clubname;

    // Data fields
    private final ExpectedTurnout expectedturnout;
    private final NumberOfEvents numberofevents;


    /**
     * Every field must be present and not null.
     */
    public ClubBudgetElements(ClubName clubname, ExpectedTurnout expectedturnout, NumberOfEvents numberofevents) {
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


    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */


    public boolean isSameClubBudgetElements(ClubBudgetElements otherClubBudgetElements) {
        if (otherClubBudgetElements == this) {
            return true;
        }

        return otherClubBudgetElements != null
                && otherClubBudgetElements.getClubName().equals(getClubName())
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
