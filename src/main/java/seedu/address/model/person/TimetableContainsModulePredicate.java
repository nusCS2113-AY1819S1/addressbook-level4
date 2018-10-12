package seedu.address.model.person;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

import java.util.List;
import java.util.function.Predicate;

public class TimetableContainsModulePredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TimetableContainsModulePredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        for (Tag module : person.getTags()) {
            for (String check : keywords) {
                check="["+check+"]";
                if (check.equalsIgnoreCase(module.toString()))
                    return true;
            }
        }
        return false;
    }
        @Override
        public boolean equals (Object other){
            return other == this // short circuit if same object
                    || (other instanceof seedu.address.model.person.TimetableContainsModulePredicate // instanceof handles nulls
                    && keywords.equals(((seedu.address.model.person.TimetableContainsModulePredicate) other).keywords)); // state check
        }
    }
