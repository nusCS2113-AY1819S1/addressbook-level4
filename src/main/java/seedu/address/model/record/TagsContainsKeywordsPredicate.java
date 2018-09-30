package seedu.address.model.record;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

public class TagsContainsKeywordsPredicate implements Predicate<Record> {
    private final List<String> keywords;

    public TagsContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public boolean testIfSetContainsString(Record record){
        for (Tag x : record.getTags()){
            if (keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(x.tagName, keyword))){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean test(Record record) {
        return testIfSetContainsString(record);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagsContainsKeywordsPredicate) other).keywords)); // state check
    }
}
