package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Group's Name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGroupName(String)}
 */
public class GroupName {
    public static final String MESSAGE_GROUP_NAME_CONSTRAINTS =
            "Group names should only contain alphanumeric characters and " +
                    "certain characters such as '[', ']' and '-' and should it not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * Allows user to input alphanumeric and '-', '[' and ']' characters
     */
    public static final String GROUP_NAME_VALIDATION_REGEX = "[\\p{Alnum}\\-\\[\\]][\\p{Alnum}\\-\\[\\]]*";

    public final String groupName;

    /**
     * Constructs a {@code GroupName}.
     *
     * @param groupName A valid group name.
     */
    public GroupName(String groupName) {
        requireNonNull(groupName);
        checkArgument(isValidGroupName(groupName), MESSAGE_GROUP_NAME_CONSTRAINTS);
        this.groupName = groupName;
    }

    /**
     * Returns true if a given string is a valid group name.
     */
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
