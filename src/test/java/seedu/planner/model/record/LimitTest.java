package seedu.planner.model.record;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.planner.testutil.TypicalLimits.LIMIT_100;

import static seedu.planner.testutil.TypicalLimits.LIMIT_500;
import static seedu.planner.testutil.TypicalLimits.LIMIT_5000;
import static seedu.planner.testutil.TypicalLimits.LIMIT_ALL_DIFFERENT;
import static seedu.planner.testutil.TypicalLimits.LIMIT_DATE_END_DIFF;
import static seedu.planner.testutil.TypicalLimits.LIMIT_DATE_START_DIFF;
import static seedu.planner.testutil.TypicalLimits.LIMIT_WEEKS_RANGE;
import static seedu.planner.testutil.TypicalLimits.TYPICAL_END_DATE;
import static seedu.planner.testutil.TypicalLimits.TYPICAL_LIMIT_MONEY_100;
import static seedu.planner.testutil.TypicalLimits.TYPICAL_LIMIT_MONEY_101;
import static seedu.planner.testutil.TypicalLimits.TYPICAL_LIMIT_MONEY_EARMED;
import static seedu.planner.testutil.TypicalLimits.TYPICAL_LIMIT_NOT_EXCEEDED;
import static seedu.planner.testutil.TypicalLimits.TYPICAL_NOT_INSIDE_DATE;
import static seedu.planner.testutil.TypicalLimits.TYPICAL_START_DATE;
import static seedu.planner.testutil.TypicalLimits.TYPICAL_WITHIN_DATE;
import static seedu.planner.testutil.TypicalRecords.INDO;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.planner.testutil.LimitBuilder;
import seedu.planner.testutil.RecordBuilder;


public class LimitTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Limit limit = new LimitBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
    }

    @Test
    public void isSameLimit() {
        // same object -> returns true
        assertTrue(LIMIT_100.isSameLimitDates(LIMIT_100));

        // null -> returns false
        assertFalse(LIMIT_100.isSameLimitDates(null));

        // different date parameter and income -> returns false
        assertFalse(LIMIT_100.isSameLimitDates(LIMIT_DATE_START_DIFF));
        assertFalse(LIMIT_100.isSameLimitDates(LIMIT_DATE_END_DIFF));

        //different dates and moneyflow -> return false
        assertFalse(LIMIT_100.isSameLimitDates(LIMIT_ALL_DIFFERENT));

        // different moneyflow -> returns true
        assertTrue(LIMIT_100.isSameLimitDates(LIMIT_500));
        assertTrue(LIMIT_100.isSameLimitDates(LIMIT_5000));

    }
    @Test
    public void isLimitExceeded() {
        // limit exceeded -> returns true
        assertTrue(LIMIT_100.isExceeded(TYPICAL_LIMIT_MONEY_101.toDouble()));
        assertTrue(LIMIT_5000.isExceeded(TYPICAL_LIMIT_EXCEEDED.toDouble()));

        // null -> returns false
        assertFalse(LIMIT_100.isExceeded(null));

        // limit not exceeded -> returns false
        assertFalse(LIMIT_5000.isExceeded(TYPICAL_LIMIT_MONEY_101.toDouble()));
        assertFalse(LIMIT_100.isExceeded(TYPICAL_LIMIT_NOT_EXCEEDED.toDouble()));

        //limit money equals to money spend -> returns false
        assertFalse(LIMIT_100.isExceeded(TYPICAL_LIMIT_MONEY_100.toDouble()));

        //user have earned money -> returns false
        assertFalse(LIMIT_100.isExceeded(TYPICAL_LIMIT_MONEY_EARMED.toDouble()));

    }

    @Test
    public void isInsideDatesPeriod () {
        //date inside range -> returns true
        Record recordInside = new RecordBuilder(INDO).withDate(TYPICAL_WITHIN_DATE.toString()).build();
        assertTrue(LIMIT_100.isInsideDatePeriod(recordInside));

        //date outside range -> returns false
        Record recordOutSide = new RecordBuilder(INDO).withDate(TYPICAL_NOT_INSIDE_DATE.toString()).build();
        assertFalse(LIMIT_100.isInsideDatePeriod(recordOutSide));

        //dateStart is same -> returns true
        Record recordSameStartDate = new RecordBuilder(INDO).withDate(TYPICAL_START_DATE.toString()).build();
        assertTrue(LIMIT_100.isInsideDatePeriod(recordSameStartDate));

        //dateEnd is same -> returns true
        Record recordSameEndDate = new RecordBuilder(INDO).withDate(TYPICAL_END_DATE.toString()).build();
        assertTrue(LIMIT_100.isInsideDatePeriod(recordSameEndDate));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Limit aliceCopy = new LimitBuilder(LIMIT_100).build();
        assertTrue(LIMIT_100.equals(aliceCopy));

        // same object -> returns true
        assertTrue(LIMIT_100.equals(LIMIT_100));

        // null -> returns false
        assertFalse(LIMIT_100.equals(null));

        // different type -> returns false
        assertFalse(LIMIT_100.equals(5));

        // different limit -> returns false
        assertFalse(LIMIT_100.equals(LIMIT_500));

        // different moneyFlow -> returns false
        assertFalse(LIMIT_100.equals(LIMIT_500));

        // different dates parameter -> returns false
        assertFalse(LIMIT_100.equals(LIMIT_WEEKS_RANGE));

    }
}
