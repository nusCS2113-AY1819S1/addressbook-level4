package seedu.address.model.person;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableList;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getLowerCaseKeywords() {
        return keywords.stream()
                .map(String::toLowerCase)
                .collect(collectingAndThen(toList(), ImmutableList::copyOf));
    }

    @Override
    public boolean test(Person person) {
        HashSet<String> tagsAsString = retrievePersonTagsAsString(person);
        for (String s:keywords) {
            if (tagsAsString.contains(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a HashSet of String representing a {@code Person}'s Tags
     */
    private HashSet<String> retrievePersonTagsAsString(Person person) {
        HashSet<String> tagsAsString = new HashSet<>();
        Set<Tag> tags = person.getTags();
        for (Tag tag:tags) {
            tagsAsString.add(tag.toSearchString().toLowerCase());
        }
        return tagsAsString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
