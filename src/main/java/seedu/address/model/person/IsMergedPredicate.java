package seedu.address.model.person;

import java.util.function.Predicate;
import seedu.address.model.tag.Tag;


/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class IsMergedPredicate implements Predicate<Person> {

    public IsMergedPredicate() {

    }

    @Override
    public boolean test(Person person) {
        for(Tag it: person.getTags()){
            if(it.toString().equalsIgnoreCase("[merged]")){
                return true;
            }
        }
        return false;
    }

}
