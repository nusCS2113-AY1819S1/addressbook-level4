package seedu.planner.model.summary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.planner.testutil.Assert.assertThrows;
import static seedu.planner.testutil.TypicalRecords.CAIFAN;
import static seedu.planner.testutil.TypicalRecords.CHICKENRICE;
import static seedu.planner.testutil.TypicalRecords.INDO;
import static seedu.planner.testutil.TypicalRecords.MALA;
import static seedu.planner.testutil.TypicalRecords.RANDOM;
import static seedu.planner.testutil.TypicalRecords.WORK;
import static seedu.planner.testutil.TypicalRecords.ZT;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import seedu.planner.model.Month;
import seedu.planner.model.record.Record;
import seedu.planner.testutil.RecordBuilder;
import seedu.planner.testutil.SummaryBuilder;
//@@author tenvinc

public class SummaryByMonthListTest {

    private static final Record CAIFAN_JAN =
            new RecordBuilder(CAIFAN).withDate("1-1-2018").build();
    private static final Record INDO_FEB =
            new RecordBuilder(INDO).withDate("2-2-2018").build();
    private static final Record INDO_JAN =
            new RecordBuilder(INDO).withDate("1-1-2018").build();
    private static final Record RANDOM_MAR =
            new RecordBuilder(RANDOM).withDate("3-3-2018").build();
    private static final Record WORK_APR =
            new RecordBuilder(WORK).withDate("4-4-2018").build();
    private static final Record MALA_APR =
            new RecordBuilder(MALA).withDate("4-4-2018").build();
    private static final Record MALA_MAY =
            new RecordBuilder(MALA).withDate("5-5-2018").build();
    private static final Record ZT_JUN =
            new RecordBuilder(ZT).withDate("6-6-2018").build();
    private static final Record CHICKEN_RICE_JUL =
            new RecordBuilder(CHICKENRICE).withDate("7-7-2018").build();


    private static final List<Record> recordListAllUniqueMonths = Arrays.asList(CAIFAN_JAN, INDO_FEB, RANDOM_MAR,
            WORK_APR, MALA_MAY, ZT_JUN, CHICKEN_RICE_JUL);

    private static List<Record> recordListOverlappingMonths = Arrays.asList(CAIFAN_JAN, INDO_JAN, RANDOM_MAR,
            WORK_APR, MALA_APR, ZT_JUN, CHICKEN_RICE_JUL);

    @Test
    public void equals() {
        SummaryByMonthList summaryByMonthListOne = new SummaryByMonthList(recordListAllUniqueMonths);
        SummaryByMonthList summaryByMonthListTwo = new SummaryByMonthList(recordListOverlappingMonths);

        // same object -> returns true
        assertTrue(summaryByMonthListOne.equals(summaryByMonthListOne));

        // same values -> returns true
        SummaryByMonthList summaryByMonthListOneCopy = new SummaryByMonthList(recordListAllUniqueMonths);
        assertTrue(summaryByMonthListOne.equals(summaryByMonthListOneCopy));

        // different types -> returns false
        assertFalse(summaryByMonthListOne.equals(1));

        // different SummaryList types -> returns false
        SummaryByDateList summaryByByDateList =
                new SummaryByDateList(recordListAllUniqueMonths);
        assertFalse(summaryByMonthListOne.equals(summaryByByDateList));

        // null -> returns false
        assertFalse(summaryByMonthListOne.equals(null));

        // different values -> returns false
        assertFalse(summaryByMonthListOne.equals(summaryByMonthListTwo));
    }

    @Test
    public void constructor_nullParam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SummaryByMonthList(
                null));
    }

    @Test
    public void constructor_recordListWithUniqueMonthsNoFilter_success() {
        HashMap<Month, Summary<Month>> expectedMap = new HashMap<>();
        for (Record r : recordListAllUniqueMonths) {
            Month month = new Month(r.getDate().getMonth(), r.getDate().getYear());
            expectedMap.put(month, new Summary<>(r, month));
        }
        assertEquals(new SummaryByMonthList(recordListAllUniqueMonths).getSummaryMap(),
                expectedMap);
    }

    @Test
    public void constructor_recordListWithOverlappingMonthsNoFilter_success() {
        Summary<Month> summaryJan = new SummaryBuilder(CAIFAN_JAN).buildMonthSummary();
        summaryJan.add(INDO_JAN);

        Summary<Month> summaryApr = new SummaryBuilder(WORK_APR).buildMonthSummary();
        summaryApr.add(MALA_APR);

        List<Summary<Month>> summaryListOverLappingMonths = Arrays.asList(summaryJan,
                new SummaryBuilder(RANDOM_MAR).buildMonthSummary(), summaryApr,
                new SummaryBuilder(ZT_JUN).buildMonthSummary(),
                new SummaryBuilder(CHICKEN_RICE_JUL).buildMonthSummary());

        HashMap<Month, Summary<Month>> expectedMap = new HashMap<>();

        for (Summary<Month> s : summaryListOverLappingMonths) {
            expectedMap.put(s.getIdentifier(), s);
        }
        assertEquals(new SummaryByMonthList(recordListOverlappingMonths).getSummaryMap(),
                expectedMap);
    }
}
