package seedu.planner.model.record;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.commons.util.DateUtil.isEarlierThan;
import static seedu.planner.commons.util.DateUtil.isLaterThan;

import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;

/**
 * Tests that a {@code Record}'s {@code Date} falls within the time interval from {@code startDate} to {@code endDate}
 * This relationship is inclusive on both ends, meaning the date can be equal to {@code startDate} or {@code endDate}
 */
public class DateIsWithinIntervalPredicate implements Predicate<Record> {
    private Logger logger = LogsCenter.getLogger(DateIsWithinIntervalPredicate.class);

    private final Date startDate;
    private final Date endDate;

    public DateIsWithinIntervalPredicate(Date startDate, Date endDate) {
        requireAllNonNull(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public DateIsWithinIntervalPredicate(String startDate, String endDate) {
        this.startDate = new Date (startDate);
        this.endDate = new Date (endDate);
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    /**
     * Check if Start Date is smaller than/equal to End Date
     * @return the boolean if StartDate is smaller than/equal to End Date
     */
    public boolean isValidPredicate() {
        return (isEarlierThan(startDate, endDate) || startDate.equals(endDate));
    }

    @Override
    public boolean test(Record record) {
        Date recordDate = record.getDate();
        return recordDate.equals(startDate) || recordDate.equals(endDate)
            || isLaterThan(recordDate, startDate) && isEarlierThan(recordDate ,endDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateIsWithinIntervalPredicate // instanceof handles nulls
                && startDate.equals(((DateIsWithinIntervalPredicate) other).startDate)) // start date check
                && endDate.equals(((DateIsWithinIntervalPredicate) other).endDate); // end date check
    }

}
