package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Group's Name in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class GroupName {
    public static final String MESSAGE_GROUP_NAME_CONSTRAINTS =
            "GroupName should only contain alphanumeric characters or certain symbols such as '[', ']' and '-'";

    /**
     * The first character of the address must not be a whitespace,
     * Allow user to input Group Name with '-', '[' and ']' symbol with alphanumeric characters
     * Example: TUT[E01]
     */
    public static final String GROUP_NAME_VALIDATION_REGEX = "[\\p{Alnum}\\-\\[\\]][\\p{Alnum}\\-\\[\\]]*";

    public final String groupName;

    /**
     * Constructs a {@code groupName}.
     *
     * @param groupName A valid groupName.
     */
    public GroupName(String groupName) {
        requireNonNull(groupName);
        checkArgument(isValidGroupName(groupName), MESSAGE_GROUP_NAME_CONSTRAINTS);
        this.groupName = groupName;
    }

    public static boolean isValidGroupName(String test) {
        return test.matches(GROUP_NAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return groupName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupName // instanceof handles nulls
                && groupName.equals(((GroupName) other).groupName)); // state check
    }

    @Override
    public int hashCode() {
        return groupName.hashCode();
    }
}
