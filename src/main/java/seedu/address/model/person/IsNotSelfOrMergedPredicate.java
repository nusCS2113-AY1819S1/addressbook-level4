package seedu.address.model.person;

import seedu.address.model.tag.Tag;

import java.util.function.Predicate;

public class IsNotSelfOrMergedPredicate implements Predicate<Person> {

    public IsNotSelfOrMergedPredicate() {

    }

    @Override
    public boolean test(Person person) {
        for(Tag it: person.getTags()){
            if(it.toString().equalsIgnoreCase("[merged]")||it.toString().equalsIgnoreCase("self")){
                return false;
            }
        }
        return true;
    }

}
