package seedu.planner.model.summary;

import static org.junit.Assert.assertEquals;
import static seedu.planner.model.Model.PREDICATE_SHOW_ALL_RECORDS;
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

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import seedu.planner.model.Month;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;
import seedu.planner.model.record.Record;
import seedu.planner.testutil.RecordBuilder;
import seedu.planner.testutil.SummaryBuilder;

/**
 * This test tests both SummaryByDateList and SummaryByMonthList because this 2 classes
 * share a great number of similarities
 */
public class SummaryListTest {

    private static final RecordBuilder recordBuilder = new RecordBuilder();
    private static final SummaryBuilder summaryBuilder = new SummaryBuilder();

    private static final Record CAIFAN_JAN =
            new Record(CAIFAN.getName(), new Date("1-1-2018"), CAIFAN.getMoneyFlow(), CAIFAN.getTags());
    private static final Record INDO_FEB =
            new Record(INDO.getName(), new Date("2-2-2018"), INDO.getMoneyFlow(), INDO.getTags());
    private static final Record INDO_JAN =
            new Record(INDO.getName(), new Date("1-1-2018"), INDO.getMoneyFlow(), INDO.getTags());
    private static final Record RANDOM_MAR =
            new Record(RANDOM.getName(), new Date("3-3-2018"), RANDOM.getMoneyFlow(), RANDOM.getTags());
    private static final Record WORK_APR =
            new Record(WORK.getName(), new Date("4-4-2018"), WORK.getMoneyFlow(), WORK.getTags());
    private static final Record MALA_APR =
            new Record(MALA.getName(), new Date("4-4-2018"), MALA.getMoneyFlow(), MALA.getTags());
    private static final Record MALA_MAY =
            new Record(MALA.getName(), new Date("5-5-2018"), MALA.getMoneyFlow(), MALA.getTags());
    private static final Record ZT_JUN =
            new Record(ZT.getName(), new Date("6-6-2018"), ZT.getMoneyFlow(), ZT.getTags());
    private static final Record CHICKEN_RICE_JUL =
            new Record(CHICKENRICE.getName(), new Date("7-7-2018"), CHICKENRICE.getMoneyFlow(), CHICKENRICE.getTags());


    private static final List<Record> recordListAllUniqueDates = Arrays.asList(CAIFAN_JAN, INDO_FEB, RANDOM_MAR,
            WORK_APR, MALA_MAY, ZT_JUN, CHICKEN_RICE_JUL);

    private static List<Record> recordListOverlappingDates = Arrays.asList(CAIFAN_JAN, INDO_JAN, RANDOM_MAR,
            WORK_APR, MALA_APR, ZT_JUN, CHICKEN_RICE_JUL);

    @Test
    public void constructor_nullParam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SummaryByDateList(null, null));
        assertThrows(NullPointerException.class, () -> new SummaryByDateList(recordListAllUniqueDates, null));
        assertThrows(NullPointerException.class, () -> new SummaryByDateList(
                null, PREDICATE_SHOW_ALL_RECORDS));
        assertThrows(NullPointerException.class, () -> new SummaryByMonthList(null, null));
        assertThrows(NullPointerException.class, () -> new SummaryByMonthList(recordListAllUniqueDates, null));
        assertThrows(NullPointerException.class, () -> new SummaryByMonthList(
                null, PREDICATE_SHOW_ALL_RECORDS));
    }

    @Test
    public void constructor_recordListWithUniqueDatesNoFilter_success() {
        HashMap<Date, SummaryAbs> expectedMap = new HashMap<>();
        for (Record r : recordListAllUniqueDates) {
            expectedMap.put(r.getDate(), new DaySummary(r));
        }
        assertEquals(new SummaryByDateList(recordListAllUniqueDates, PREDICATE_SHOW_ALL_RECORDS).getSummaryMap(),
                expectedMap);
    }

    @Test
    public void constructor_recordListWithUniqueMonthsNoFilter_success() {
        HashMap<Month, SummaryAbs> expectedMap = new HashMap<>();
        for (Record r : recordListAllUniqueDates) {
            Month month = new Month(r.getDate().getMonth(), r.getDate().getYear());
            expectedMap.put(month, new MonthSummary(r));
        }
        assertEquals(new SummaryByMonthList(recordListAllUniqueDates, PREDICATE_SHOW_ALL_RECORDS).getSummaryMap(),
                expectedMap);
    }

    @Test
    public void constructor_recordListWithOverlappingDatesNoFilter_success() {
        DaySummary summaryJan = new DaySummary(CAIFAN_JAN);
        summaryJan.add(INDO_JAN);

        DaySummary summaryApr = new DaySummary(WORK_APR);
        summaryApr.add(MALA_APR);

        List<DaySummary> summaryListOverLappingDates = Arrays.asList(summaryJan, new DaySummary(RANDOM_MAR), summaryApr,
                new DaySummary(ZT_JUN), new DaySummary(CHICKEN_RICE_JUL));

        HashMap<Date, SummaryAbs> expectedMap = new HashMap<>();
        for (DaySummary s : summaryListOverLappingDates) {
            expectedMap.put(s.getDate(), s);
        }
        assertEquals(new SummaryByDateList(recordListOverlappingDates, PREDICATE_SHOW_ALL_RECORDS).getSummaryMap(),
                expectedMap);
    }

    @Test
    public void constructor_recordListWithOverlappingMonthsNoFilter_success() {
        MonthSummary summaryJan = new MonthSummary(CAIFAN_JAN);
        summaryJan.add(INDO_JAN);

        MonthSummary summaryApr = new MonthSummary(WORK_APR);
        summaryApr.add(MALA_APR);

        List<MonthSummary> summaryListOverLappingDates = Arrays.asList(summaryJan, new MonthSummary(RANDOM_MAR),
                summaryApr, new MonthSummary(ZT_JUN), new MonthSummary(CHICKEN_RICE_JUL));

        HashMap<Month, SummaryAbs> expectedMap = new HashMap<>();
        for (MonthSummary s : summaryListOverLappingDates) {
            expectedMap.put(s.getMonth(), s);
        }
        assertEquals(new SummaryByMonthList(recordListOverlappingDates, PREDICATE_SHOW_ALL_RECORDS).getSummaryMap(),
                expectedMap);
    }

    /**
     * This test tests whether the filter method is working as intended.
     */
    @Test
    public void constructor_recordListWithUniqueDatesFilter_success() {
        Date startTestDate = new Date("1-1-2018");
        Date endTestDate = new Date("5-6-2018");
        DateIsWithinIntervalPredicate predicate = new DateIsWithinIntervalPredicate(startTestDate, endTestDate);
        FilteredList<Record> filteredList = new FilteredList(FXCollections.observableList(recordListAllUniqueDates));
        filteredList.setPredicate(predicate);
        HashMap<Date, SummaryAbs> expectedMap = new HashMap<>();
        for (Record r : filteredList) {
            expectedMap.put(r.getDate(), new DaySummary(r));
        }
        assertEquals(new SummaryByDateList(recordListAllUniqueDates, predicate).getSummaryMap(),
                expectedMap);
    }

    /**
     * @see #constructor_recordListWithUniqueDatesFilter_success
     */
    @Test
    public void constructor_recordListWithUniqueMonthsFilter_success() {
        Date startTestDate = new Date("1-1-2018");
        Date endTestDate = new Date("30-6-2018");
        DateIsWithinIntervalPredicate predicate = new DateIsWithinIntervalPredicate(startTestDate, endTestDate);
        FilteredList<Record> filteredList = new FilteredList(FXCollections.observableList(recordListAllUniqueDates));
        filteredList.setPredicate(predicate);
        HashMap<Month, SummaryAbs> expectedMap = new HashMap<>();
        for (Record r : filteredList) {
            Month month = new Month(r.getDate().getMonth(), r.getDate().getYear());
            expectedMap.put(month, new MonthSummary(r));
        }
        assertEquals(new SummaryByMonthList(recordListAllUniqueDates, predicate).getSummaryMap(),
                expectedMap);
    }
}
