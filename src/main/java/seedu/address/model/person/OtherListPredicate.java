package seedu.address.model.person;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.security.UserStub;

/**
 * Creates a predicate to be used to find the non-friends in persons list
 */
public class OtherListPredicate implements Predicate<Person> {

    private final Set<Friend> otherList;
    private Person currentUser = UserStub.getUser();

    public OtherListPredicate(Set<Friend> friendList) {
        this.otherList = friendList;
    }

    @Override
    public boolean test(Person person) {
        for (Friend other : otherList) {
            if (currentUser.getName().equals(person.getName())) {
                return false;
            }
            if (person.getName().equals(other.getFriendName())) {
                return false;
            }
        }
        return true;
    }
}
