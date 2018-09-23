package seedu.address.model.tag;

import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Test for the keywords and if it's present in the collections
 */
public class TagsContainsKeywords implements Predicate<Person> {
    private final Set<Tag> tagKeywords;

    public TagsContainsKeywords(Set<Tag> tagKeywords) {
        this.tagKeywords = tagKeywords;
    }

    @Override
    public boolean test(Person person) {
        return !Collections.disjoint(person.getTags(), tagKeywords);
    }

}
