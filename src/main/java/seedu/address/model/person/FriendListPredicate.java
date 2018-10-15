package seedu.address.model.person;

import java.util.Set;
import java.util.function.Predicate;

public class FriendListPredicate implements Predicate<Person> {

    private final Set<Friend> friendList;

    public FriendListPredicate(Set<Friend> friendList) {
        this.friendList = friendList;
    }

    @Override
    public boolean test(Person person) {
        for (Friend friend : friendList) {
            if (person.getName().equals(friend.getFriendName())) return true;
        }
        return false;
    }
}
