package seedu.address.model.book;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Book}'s {@code Name} matches any of the substring keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Book> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Book book) {
        return keywords.stream()
                .anyMatch(keyword -> book.getName().fullName.toLowerCase().contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
