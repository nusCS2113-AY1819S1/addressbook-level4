package seedu.address.model.person;

import java.util.Set;
import java.util.function.Predicate;

/**
 * Creates a predicate from the Person to be used to create the friend list
 */
public class FriendListPredicate implements Predicate<Person> {

    private final Person currentUser;

    public FriendListPredicate(Person currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public boolean test(Person person) {
        Set<Friend> friendList = currentUser.getFriends();
        for (Friend friend : friendList) {
            if (currentUser.getName().equals(person.getName())) {
                return false;
            }
            if (person.getName().equals(friend.getFriendName())) {
                return true;
            }
        }
        return false;
    }
}
