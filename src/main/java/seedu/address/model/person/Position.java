package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Position in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPosition(String)}
 */
public class Position {
    //TODO change constraints
    public static final String MESSAGE_POSITION_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    public static final String POSITION_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String position;

    /**
     * Constructs a {@code Position}.
     *
     * @param position A valid position name.
     */
    public Position(String position) {
        requireNonNull(position);
        checkArgument(isValidPosition(position), MESSAGE_POSITION_CONSTRAINTS);
        this.position = position;
    }

    /**
     * No position provided
     */
    //TODO find a better solution to null.
    public Position(){
        this.position = null;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidPosition(String test) {
        return test.matches(POSITION_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return position;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Position // instanceof handles nulls
                && position.equals(((Position) other).position)); // state check
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }
}
