package seedu.address.model.clubbudget;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.budgetelements.ClubName;

/**
 * Represents the final allocated budget of a club in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class FinalClubBudget {
    // Identity fields
    private final ClubName clubname;

    // Data fields
    private final int allocatedBudget;

    /**
     * Every field must be present and not null.
     */
    public FinalClubBudget(ClubName clubname, int allocatedBudget) {
        requireAllNonNull(clubname, allocatedBudget);
        this.clubname = clubname;
        this.allocatedBudget = allocatedBudget;
    }

    public ClubName getClubName() {
        return clubname;
    }

    public int getAllocatedBudget() {
        return allocatedBudget;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */


    public boolean isSameFinalClubBudget(FinalClubBudget otherFinalClubBudget) {
        if (otherFinalClubBudget == this) {
            return true;
        }

        return otherFinalClubBudget != null
                && otherFinalClubBudget.getClubName().equals(getClubName());
    }

    /**
     * Returns true if both clubs have the same identity fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FinalClubBudget)) {
            return false;
        }

        FinalClubBudget otherFinalClubBudget = (FinalClubBudget) other;
        return otherFinalClubBudget.getClubName().equals(getClubName());
    }


    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(clubname, allocatedBudget);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getClubName())
                .append(" Final allocated budget: ")
                .append(getAllocatedBudget());
        return builder.toString();
    }

}
