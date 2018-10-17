package seedu.planner.model.record;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.planner.testutil.LimitBuilder;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.planner.testutil.TypicalLimits.LIMIT_100;
import static seedu.planner.testutil.TypicalLimits.LIMIT_500;
import static seedu.planner.testutil.TypicalLimits.LIMIT_DATE_START_DIFF;


public class LimitTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Limit limit = new LimitBuilder().build();
        thrown.expect(UnsupportedOperationException.class);

    }

    @Test
    public void isSameDatesLimit() {
        // same object -> returns true
        assertTrue(LIMIT_100.isSameLimitDates(LIMIT_100));

        // null -> returns false
        assertTrue(LIMIT_100.isSameLimitDates(null));

        // different date parameter and income -> returns false. will update later


        // different dateStart -> returns false
        assertTrue(LIMIT_100.isSameLimitDates(LIMIT_DATE_START_DIFF));


        // different dateEnd -> returns false
        assertTrue(LIMIT_100.isSameLimitDates(LIMIT_DATE_START_DIFF));


        // same dateStart, same date end, different money -> returns true
        assertTrue(LIMIT_100.isSameLimitDates(LIMIT_500));



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

        // different record -> returns false
        assertFalse(LIMIT_100.equals(LIMIT_500));

        // different Date Start -> returns false
        Limit editedLimitStart = new LimitBuilder(LIMIT_DATE_START_DIFF).build();
        assertFalse(LIMIT_100.equals(editedLimitStart));

        // different Date End -> returns false
        Limit editedLimitEnd = new LimitBuilder(LIMIT_DATE_START_DIFF).build();
        assertFalse(LIMIT_100.equals(editedLimitEnd));

    }
}
