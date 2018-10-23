package seedu.address.model.person;

import java.util.Set;
import java.util.function.Predicate;

public class CombinedFriendPredicate implements Predicate<Person> {

    Predicate<Person> predicate1;
    FriendListPredicate friendListPredicate;

    public CombinedFriendPredicate(Predicate<Person> predicate, FriendListPredicate friendListPredicate) {
        this.predicate1 = predicate;
        this.friendListPredicate = friendListPredicate;
    }

    @Override
    public boolean test(Person person) {
        if (predicate1.test(person) && friendListPredicate.test(person)) {
            return true;
        }
        return false;
    }
}
