package seedu.address.model.book;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Book}'s {@code Quantity} matches any of the quantities given
 */
public class QuantityContainsNumberPredicate implements Predicate<Book> {
    private final List<String> quantities;

    public QuantityContainsNumberPredicate(List<String> quantities) {
        this.quantities = quantities;
    }

    @Override
    public boolean test(Book book) {
        return quantities.stream()
                .anyMatch(quantity -> StringUtil.containsWordIgnoreCase(book.getQuantity().getValue(), quantity));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuantityContainsNumberPredicate // instanceof handles nulls
                && quantities.equals(((QuantityContainsNumberPredicate) other).quantities)); // state checks
    }
}
