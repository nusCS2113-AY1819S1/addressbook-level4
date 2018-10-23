package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Combines the predicates to allow SetPredicate to be called
 */
public class CombinedFriendPredicate implements Predicate<Person> {

    private final Predicate<Person> predicate1;
    private final FriendListPredicate friendListPredicate;

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
