package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

public class IsNotSelfOrMergedPredicate implements Predicate<Person> {

    public IsNotSelfOrMergedPredicate() {

    }

    @Override
    public boolean test(Person person) {
        for (Tag it : person.getTags()) {
            if (it.toString().equalsIgnoreCase("[merged]") || it.toString().equalsIgnoreCase("self")) {
                return false;
            }
        }
        return true;
    }

}
