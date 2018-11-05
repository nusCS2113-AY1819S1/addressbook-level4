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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import seedu.planner.model.Month;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.Record;
import seedu.planner.testutil.RecordBuilder;
import seedu.planner.testutil.SummaryBuilder;
//@@author tenvinc
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

    private static final List<Record> recordListAllUniqueMonths = new ArrayList<>(recordListAllUniqueDates);

    private static List<Record> recordListOverlappingDates = Arrays.asList(CAIFAN_JAN, INDO_JAN, RANDOM_MAR,
            WORK_APR, MALA_APR, ZT_JUN, CHICKEN_RICE_JUL);

    private static List<Record> recordListOverlappingMonths = new ArrayList<>(recordListOverlappingDates);

    @Test
    public void constructor_nullParam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SummaryByDateList(
                null));
    }

    @Test
    public void constructor_recordListWithUniqueDatesNoFilter_success() {
        HashMap<Date, Summary<Date>> expectedMap = new HashMap<>();
        for (Record r : recordListAllUniqueDates) {
            expectedMap.put(r.getDate(), new Summary<>(r, r.getDate()));
        }
        assertEquals(new SummaryByDateList(recordListAllUniqueDates).getSummaryMap(),
                expectedMap);
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
    public void constructor_recordListWithOverlappingDatesNoFilter_success() {
        Summary<Date> summaryJan = new Summary<>(CAIFAN_JAN, CAIFAN_JAN.getDate());
        summaryJan.add(INDO_JAN);

        Summary<Date> summaryApr = new Summary<>(WORK_APR, WORK_APR.getDate());
        summaryApr.add(MALA_APR);

        List<Summary<Date>> summaryListOverLappingDates = Arrays.asList(summaryJan,
                new Summary<>(RANDOM_MAR, RANDOM_MAR.getDate()), summaryApr, new Summary<>(ZT_JUN, ZT_JUN.getDate()),
                new Summary<>(CHICKEN_RICE_JUL, CHICKEN_RICE_JUL.getDate()));

        HashMap<Date, Summary<Date>> expectedMap = new HashMap<>();
        for (Summary<Date> s : summaryListOverLappingDates) {
            expectedMap.put(s.getIdentifier(), s);
        }
        assertEquals(new SummaryByDateList(recordListOverlappingDates).getSummaryMap(),
                expectedMap);
    }

    @Test
    public void constructor_recordListWithOverlappingMonthsNoFilter_success() {
        Summary<Month> summaryJan = new Summary<>(CAIFAN_JAN, new Month(CAIFAN_JAN.getDate().getMonth(),
                CAIFAN_JAN.getDate().getYear()));
        summaryJan.add(INDO_JAN);

        Summary<Month> summaryApr = new Summary<>(WORK_APR, new Month(WORK_APR.getDate().getMonth(),
                WORK_APR.getDate().getYear()));
        summaryApr.add(MALA_APR);

        List<Summary<Month>> summaryListOverLappingMonths = Arrays.asList(summaryJan,
                new Summary<>(RANDOM_MAR, new Month(RANDOM.getDate().getMonth(),
                        RANDOM_MAR.getDate().getYear())),
                summaryApr, new Summary<>(ZT_JUN, new Month(ZT_JUN.getDate().getMonth(),
                        ZT_JUN.getDate().getYear())), new Summary<>(CHICKEN_RICE_JUL,
                        new Month(CHICKEN_RICE_JUL.getDate().getMonth(),
                        CHICKEN_RICE_JUL.getDate().getYear())));

        HashMap<Month, Summary<Month>> expectedMap = new HashMap<>();

        for (Summary<Month> s : summaryListOverLappingMonths) {
            expectedMap.put(s.getIdentifier(), s);
        }
        assertEquals(new SummaryByMonthList(recordListOverlappingMonths).getSummaryMap(),
                expectedMap);
    }
}
