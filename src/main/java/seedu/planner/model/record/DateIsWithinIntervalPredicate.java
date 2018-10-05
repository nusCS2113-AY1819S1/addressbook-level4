package seedu.planner.model.record;

import java.util.function.Predicate;

/**
 * Tests that a {@code Record}'s {@code Date} falls within the time interval from {@code startDate} to {@code endDate}
 * This relationship is inclusive on both ends, meaning the date can be equal to {@code startDate} or {@code endDate}
 */
public class DateIsWithinIntervalPredicate implements Predicate<Record> {

    private final Date startDate;
    private final Date endDate;

    public DateIsWithinIntervalPredicate(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean test(Record record) {
        Date recordDate = record.getDate();
        return recordDate.equals(startDate) || recordDate.equals(endDate)
            || recordDate.isLaterThan(startDate) && recordDate.isEarlierThan(endDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateIsWithinIntervalPredicate // instanceof handles nulls
                && startDate.equals(((DateIsWithinIntervalPredicate) other).startDate)) // start date check
                && endDate.equals(((DateIsWithinIntervalPredicate) other).endDate); // end date check
    }

}
