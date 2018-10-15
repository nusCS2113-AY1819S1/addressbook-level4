package seedu.address.model.person;

import java.util.Set;
import java.util.function.Predicate;

public class OtherListPredicate implements Predicate<Person> {

    private final Set<Friend> otherList;

    public OtherListPredicate(Set<Friend> friendList) {
        this.otherList = friendList;
    }

    @Override
    public boolean test(Person person) {
        for (Friend other : otherList) {
            if (person.getName().equals(other.getFriendName())) {
                return false;
            }
        }
        return true;
    }
}
