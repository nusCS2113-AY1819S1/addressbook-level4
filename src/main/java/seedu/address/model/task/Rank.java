package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

//@@author JeremyInElysium
/**
 * Represents a description in the milestone of a task.
 */
public class Rank {

    public static final String MESSAGE_RANK_CONSTRAINTS =
            "Rank can only contain positive integers.";

    /**
     * The input must not be a whitespace, zero or a negative integer
     */
    public static final String RANK_VALIDATION_REGEX = "^^\\d*[1-9]\\d*$";

    public final String rank;

    /**
     * Creates a constructor for the rank
     * Guarantees that the rank is not null
     * @param rank a valid rank
     */
    public Rank(String rank) {
        requireNonNull(rank);
        this.rank = rank;
    }

    /**
     * Checks whether rank entered by the user is valid
     * @param rank
     * @return true if valid
     */
    public static boolean isValidRank(String rank) {
        return rank.matches(RANK_VALIDATION_REGEX);
    }

    public String getRank() {
        return this.rank;
    }

    public Integer getRankInteger() {
        return Integer.valueOf(this.rank);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Rank // instanceof handles nulls
                && rank.equals(((Rank) other).rank));
    }

    @Override
    public String toString() {
        return rank;
    }
}
