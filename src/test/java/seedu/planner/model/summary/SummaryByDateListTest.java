package seedu.planner.model.summary;

import static org.junit.Assert.assertEquals;
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

import seedu.planner.model.record.Date;
import seedu.planner.model.record.Record;
import seedu.planner.testutil.RecordBuilder;
import seedu.planner.testutil.SummaryBuilder;
//@@author tenvinc

public class SummaryByDateListTest {

    private static final RecordBuilder recordBuilder = new RecordBuilder();
    private static final SummaryBuilder summaryBuilder = new SummaryBuilder();

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


    private static final List<Record> recordListAllUniqueDates = Arrays.asList(CAIFAN_JAN, INDO_FEB, RANDOM_MAR,
            WORK_APR, MALA_MAY, ZT_JUN, CHICKEN_RICE_JUL);

    private static List<Record> recordListOverlappingDates = Arrays.asList(CAIFAN_JAN, INDO_JAN, RANDOM_MAR,
            WORK_APR, MALA_APR, ZT_JUN, CHICKEN_RICE_JUL);

    @Test
    public void constructor_nullParam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SummaryByDateList(
                null));
    }

    @Test
    public void constructor_recordListWithUniqueDates_success() {
        HashMap<Date, Summary<Date>> expectedMap = new HashMap<>();
        for (Record r : recordListAllUniqueDates) {
            expectedMap.put(r.getDate(), new Summary<>(r, r.getDate()));
        }
        assertEquals(new SummaryByDateList(recordListAllUniqueDates).getSummaryMap(),
                expectedMap);
    }

    @Test
    public void constructor_recordListWithOverlappingDates_success() {
        Summary<Date> summaryJan = new SummaryBuilder(CAIFAN_JAN).buildDaySummary();
        summaryJan.add(INDO_JAN);

        Summary<Date> summaryApr = new SummaryBuilder(WORK_APR).buildDaySummary();
        summaryApr.add(MALA_APR);

        List<Summary<Date>> summaryListOverLappingDates = Arrays.asList(summaryJan,
                new SummaryBuilder(RANDOM_MAR).buildDaySummary(), summaryApr,
                new SummaryBuilder(ZT_JUN).buildDaySummary(),
                new SummaryBuilder(CHICKEN_RICE_JUL).buildDaySummary());

        HashMap<Date, Summary<Date>> expectedMap = new HashMap<>();
        for (Summary<Date> s : summaryListOverLappingDates) {
            expectedMap.put(s.getIdentifier(), s);
        }
        assertEquals(new SummaryByDateList(recordListOverlappingDates).getSummaryMap(),
                expectedMap);
    }
}
