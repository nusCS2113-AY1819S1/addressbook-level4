package seedu.planner.model.record;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.planner.testutil.TypicalLimits.LIMIT_100;
import static seedu.planner.testutil.TypicalLimits.LIMIT_500;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.planner.model.record.exceptions.LimitNotFoundException;
import seedu.planner.model.record.exceptions.RedundantLimitDatesException;
//@@Author OscarZeng



public class DateBasedLimitListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final DateBasedLimitList dateBasedLimitList = new DateBasedLimitList();

    @Test
    public void check_nullLimitList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        dateBasedLimitList.hasSameDatesLimit(null);
    }

    @Test
    public void hasSameDates_limitsNotInside_returnsFalse() {
        assertFalse(dateBasedLimitList.hasSameDatesLimit(LIMIT_100));
    }

    @Test
    public void hasSameDates_limitInList_returnsTrue() {
        dateBasedLimitList.add(LIMIT_100);
        assertTrue(dateBasedLimitList.hasSameDatesLimit(LIMIT_100));
    }

    @Test
    public void hasSameDates_limitWithSameDatesInList_returnsTrue() {
        dateBasedLimitList.add(LIMIT_100);
        assertTrue(dateBasedLimitList.hasSameDatesLimit(LIMIT_500));
    }

    @Test
    public void add_nullLimit_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        dateBasedLimitList.add(null);
    }

    @Test
    public void add_redundantLimit_throwsDuplicateRecordException() {
        dateBasedLimitList.add(LIMIT_100);
        thrown.expect(RedundantLimitDatesException.class);
        dateBasedLimitList.add(LIMIT_500);
    }

    @Test
    public void setLimit_nullTargetLimit_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        dateBasedLimitList.setLimit(null, LIMIT_100);
    }

    @Test
    public void setLimit_nullEditedLimit_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        dateBasedLimitList.setLimit(LIMIT_100, null);
    }

    @Test
    public void setLimit_targetLimitNotInList_throwsRecordNotFoundException() {
        thrown.expect(LimitNotFoundException.class);
        dateBasedLimitList.setLimit(LIMIT_100, LIMIT_100);
    }

    /*@Test
    public void setLimit_editedLimitIsSameLimit_success() {
        dateBasedLimitList.add(LIMIT_100);
        dateBasedLimitList.setLimit(LIMIT_100, LIMIT_100);
        DateBasedLimitList expectedDateBasedLimitList = new DateBasedLimitList();
        expectedDateBasedLimitList.add(LIMIT_100);
        assertTrue(expectedDateBasedLimitList.equals(dateBasedLimitList));
    }*/


    /*@Test
    public void setLimit_editedLimitHasDifferentMoneyFlow_success() {
        dateBasedLimitList.add(LIMIT_100);
        dateBasedLimitList.setLimit(LIMIT_100, LIMIT_DATE_START_DIFF);
        DateBasedLimitList expectedDateBasedLimitList = new DateBasedLimitList();
        expectedDateBasedLimitList.add(LIMIT_DATE_START_DIFF);
        assertEquals(expectedDateBasedLimitList, dateBasedLimitList);
    }*/


    @Test
    public void remove_nullLimit_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        dateBasedLimitList.remove(null);
    }

    @Test
    public void remove_limitDoesNotExist_throwsLimitNotFoundException() {
        thrown.expect(LimitNotFoundException.class);
        dateBasedLimitList.remove(LIMIT_100);
    }

    @Test
    public void remove_existingLimit_removesRecord() {
        DateBasedLimitList expectedDateBasedLimitList = dateBasedLimitList;
        dateBasedLimitList.add(LIMIT_100);
        dateBasedLimitList.remove(LIMIT_100);

        assertEquals(expectedDateBasedLimitList, dateBasedLimitList);
    }

    @Test
    public void setLimits_nullDateBasedLimitList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        dateBasedLimitList.setLimits((DateBasedLimitList) null);
    }

    /* @Test
    public void setLimits_uniqueLimitList_replacesOwnListWithProvidedUniqueLimitList() {
        dateBasedLimitList.add(LIMIT_100);
        DateBasedLimitList expectedDateBasedLimitList = new DateBasedLimitList();
        expectedDateBasedLimitList.add(LIMIT_500);
        dateBasedLimitList.setLimits(expectedDateBasedLimitList);
        assertEquals(expectedDateBasedLimitList, dateBasedLimitList);
     }*/

    @Test
    public void setLimits_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        dateBasedLimitList.setLimits((List<Limit>) null);
    }

    /* @Test
    public void setLimits_list_replacesOwnListWithProvidedList() {
        DateBasedLimitList expectedDateBasedLimitList = dateBasedLimitList;
        dateBasedLimitList.add(LIMIT_100);
        List<Limit> limitList = Collections.singletonList(LIMIT_DATE_START_DIFF);
        dateBasedLimitList.setLimits(limitList);

        expectedDateBasedLimitList.add(LIMIT_DATE_START_DIFF);
        assertEquals(expectedDateBasedLimitList, dateBasedLimitList);
     }*/

    @Test
    public void setLimits_listWithRedundantDateLimits_throwsDuplicateRecordException() {
        List<Limit> listWithRedundantDateLimits = Arrays.asList(LIMIT_100, LIMIT_100);
        thrown.expect(RedundantLimitDatesException.class);
        dateBasedLimitList.setLimits(listWithRedundantDateLimits);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        dateBasedLimitList.asUnmodifiableObservableList().remove(0);
    }
}
