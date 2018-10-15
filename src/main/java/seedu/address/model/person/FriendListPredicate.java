package seedu.address.model.person;

import java.util.HashSet;
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
            if (friend.getFriendName().equals(person.getName())) return true;
        }
       return false;
    }
}
