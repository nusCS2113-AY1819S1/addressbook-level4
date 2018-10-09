package seedu.planner.model.record;

import java.util.function.Predicate;

/**
 * Tests that a {@code Record}'s {@code Date} is equal required Date.
 * We can use this Predicate when we want to access many records have the same days and use them to manage daily.
 */
public class DateEqualPredicate implements Predicate<Record> {
    private final Date requiredDate;

    public DateEqualPredicate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    public Date getRequiredDate() {
        return requiredDate;
    }

    @Override
    public boolean test(Record record) {
        Date recordDate = record.getDate();
        return recordDate.equals(requiredDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof DateEqualPredicate
                && requiredDate.equals((((DateEqualPredicate) other).requiredDate));
    }
}
