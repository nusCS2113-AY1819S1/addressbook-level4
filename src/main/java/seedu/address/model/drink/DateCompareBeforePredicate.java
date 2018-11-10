package seedu.address.model.drink;

import java.util.function.Predicate;

/**
 * Tests that a {@code Drink}'s {@code Date} is earlier than the date given.
 */
public class DateCompareBeforePredicate implements Predicate<Drink> {
    public final Date date;

    public DateCompareBeforePredicate(Date date) {
        this.date = date;
    }

    @Override
    public boolean test(Drink drink) {
        return drink.getEarliestBatchDate().isBefore(date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateCompareBeforePredicate // instanceof handles nulls
                && date.equals(((DateCompareBeforePredicate) other).date)); // state check
    }
}
