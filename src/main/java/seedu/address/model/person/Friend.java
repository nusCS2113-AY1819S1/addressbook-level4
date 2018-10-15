package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents the list of friends that a user can have
 */
public class Friend {

    public static final String MESSAGE_CONSTRAINTS = "Friend names must be one of the persons in the list.";
    public final Name friendName;

    public Friend(Name friendName) {
        requireNonNull(friendName);
        this.friendName = friendName;
    }

    public Name getFriendName() {
        return friendName;
    }

    private Boolean isValidFriendName(String friendName) {

        return true;
    }
}
