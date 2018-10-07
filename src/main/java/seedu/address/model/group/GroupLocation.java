package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Group's Location in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class GroupLocation {
    public static final String MESSAGE_GROUP_LOCATION_CONSTRAINTS =
            "GroupLocation should only contain alphanumeric characters and a symbol '-' is accepted";

    /**
     * The first character of the address must not be a whitespace,
     * Allow user to input Group Location with '-' symbol and alphanumeric characters
     * Example: E1-06-03
     */
    public static final String LOCATION_VALIDATION_REGEX = "[\\p{Alnum}\\-][\\p{Alnum}\\-]*";

    public final String groupLocation;

    /**
     * Constructs a {@code groupLocation}.
     *
     * @param groupLocation A valid groupName.
     */
    public GroupLocation(String groupLocation) {
        requireNonNull(groupLocation);
        checkArgument(isValidGroupLocation(groupLocation), MESSAGE_GROUP_LOCATION_CONSTRAINTS);
        this.groupLocation = groupLocation;
    }

    public static boolean isValidGroupLocation(String test) {
        return test.matches(LOCATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return groupLocation;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupLocation // instanceof handles nulls
                && groupLocation.equals(((GroupLocation) other).groupLocation)); // state check
    }

    @Override
    public int hashCode() {
        return groupLocation.hashCode();
    }

}
