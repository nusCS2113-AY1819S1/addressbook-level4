package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Group's location in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGroupLocation(String)}
 */
public class GroupLocation {
    public static final String MESSAGE_GROUP_LOCATION_CONSTRAINTS =
            "GroupLocation can take '-' and alphanumeric characters, and should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * Allow user to input '-' and alphanumeric characters
     */
    public static final String LOCATION_VALIDATION_REGEX = "[\\p{Alnum}\\-][\\p{Alnum}\\-]*";

    public final String value;

    /**
     * Constructs a {@code GroupLocation}.
     *
     * @param groupLocation A valid location.
     */
    public GroupLocation(String groupLocation) {
        requireNonNull(groupLocation);
        checkArgument(isValidGroupLocation(groupLocation), MESSAGE_GROUP_LOCATION_CONSTRAINTS);
        value = groupLocation;
    }

    public static boolean isValidGroupLocation(String test) {
        return test.matches(LOCATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupLocation // instanceof handles nulls
                && value.equals(((GroupLocation) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
