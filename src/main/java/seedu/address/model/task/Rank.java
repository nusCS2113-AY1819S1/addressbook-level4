package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author JeremyInElysium
/**
 * Represents a description in the milestone of a task.
 */
public class Rank {

    public static final String MESSAGE_RANK_CONSTRAINTS =
            "Rank can only contain positive integers greater than zero, and it should not be blank.";

    /**
     * The input must not be a whitespace, zero or a negative integer
     */
    public static final String RANK_VALIDATION_REGEX = "[1-9]{1,2}";

    public final String rank;

    /**
     * Creates a constructor for the rank
     * Guarantees that the rank is not null
     * @param rank a valid rank
     */
    public Rank(String rank) {
        requireNonNull(rank);
        checkArgument(isValidRank(rank), MESSAGE_RANK_CONSTRAINTS);
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

    @Override
    public String toString() {
        return rank;
    }
}
