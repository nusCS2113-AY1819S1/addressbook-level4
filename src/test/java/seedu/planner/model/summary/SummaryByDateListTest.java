package seedu.planner.model.summary;

import static org.junit.Assert.assertEquals;

import java.util.function.Predicate;

import org.junit.Test;

import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;
import seedu.planner.model.record.Record;
import seedu.planner.testutil.TypicalRecords;

public class SummaryByDateListTest {

    @Test
    public void constructor_validList_returnsList() {
        Predicate<Record> predicate = new DateIsWithinIntervalPredicate(new Date("28-2-2018"), new Date("26-9-2018"));
        FinancialPlanner financialPlanner = TypicalRecords.getTypicalFinancialPlanner();
        SummaryByDateList summaryList = new SummaryByDateList(financialPlanner.getRecordList(), predicate);
        assertEquals(summaryList.getSummaryList(), financialPlanner.getSummaryList(
                new Date("28-2-2018"), new Date("26-9-2018")));
    }
}
