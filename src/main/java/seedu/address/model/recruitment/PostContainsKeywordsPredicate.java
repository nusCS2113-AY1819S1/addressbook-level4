package seedu.address.model.recruitment;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Recruitment}'s {@code Name} matches any of the post position given.
 */
public class PostContainsKeywordsPredicate implements Predicate <Recruitment> {
    private final List<String> keywords;

    public PostContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Recruitment recruitment) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsSentenceIgnoreCase(recruitment.getPost().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PostContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PostContainsKeywordsPredicate) other).keywords)); // state check
    }

}
