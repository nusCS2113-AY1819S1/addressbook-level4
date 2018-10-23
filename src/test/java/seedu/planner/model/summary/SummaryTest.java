package seedu.planner.model.summary;

import static org.junit.Assert.assertEquals;
import static seedu.planner.testutil.SummaryBuilder.DEFAULT_TOTAL;
import static seedu.planner.testutil.SummaryBuilder.DEFAULT_TOTAL_EXPENSE;
import static seedu.planner.testutil.SummaryBuilder.DEFAULT_TOTAL_INCOME;

import org.junit.Test;

import seedu.planner.commons.util.MoneyUtil;
import seedu.planner.model.Month;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.record.Record;
import seedu.planner.testutil.Assert;
import seedu.planner.testutil.RecordBuilder;
import seedu.planner.testutil.SummaryBuilder;

/**
 * This tests both the DaySummary and MonthSummary classes
 */
public class SummaryTest {

    private static final String DEFAULT_DATE = "10-2-2018";
    private static final String DEFAULT_MONTH = "feb-2018";
    private static final String DEFAULT_MONEYFLOW_EXPENSE = "-10.00";
    private static final String DEFAULT_MONEYFLOW_INCOME = "+100";
    private static final String DEFAULT_MONEYFLOW_TOTAL = "+90";

    private static final SummaryBuilder summaryBuilder = new SummaryBuilder();

    @Test
    public void constructor_nullRecordDaySummary_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DaySummary(null));
    }

    @Test
    public void constructor_nullRecordMonthSummary_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MonthSummary(null));
    }

    @Test
    public void constructor_nullParamDaySummary_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DaySummary(null,
                new MoneyFlow(DEFAULT_MONEYFLOW_EXPENSE), new MoneyFlow(DEFAULT_MONEYFLOW_INCOME),
                new MoneyFlow(DEFAULT_MONEYFLOW_TOTAL)));
        Assert.assertThrows(NullPointerException.class, () -> new DaySummary(new Date(DEFAULT_DATE),
                null, new MoneyFlow(DEFAULT_MONEYFLOW_INCOME),
                new MoneyFlow(DEFAULT_MONEYFLOW_TOTAL)));
        Assert.assertThrows(NullPointerException.class, () -> new DaySummary(new Date(DEFAULT_DATE),
                new MoneyFlow(DEFAULT_MONEYFLOW_EXPENSE), null,
                new MoneyFlow(DEFAULT_MONEYFLOW_TOTAL)));
        Assert.assertThrows(NullPointerException.class, () -> new DaySummary(new Date(DEFAULT_DATE),
                new MoneyFlow(DEFAULT_MONEYFLOW_EXPENSE), new MoneyFlow(DEFAULT_MONEYFLOW_INCOME),
                null));
    }

    @Test
    public void constructor_nullParamMonthSummary_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MonthSummary(null,
                new MoneyFlow(DEFAULT_MONEYFLOW_EXPENSE), new MoneyFlow(DEFAULT_MONEYFLOW_INCOME),
                new MoneyFlow(DEFAULT_MONEYFLOW_TOTAL)));
        Assert.assertThrows(NullPointerException.class, () -> new MonthSummary(new Month(DEFAULT_MONTH),
                null, new MoneyFlow(DEFAULT_MONEYFLOW_INCOME), new MoneyFlow(DEFAULT_MONEYFLOW_TOTAL)));
        Assert.assertThrows(NullPointerException.class, () -> new MonthSummary(new Month(DEFAULT_MONTH),
                new MoneyFlow(DEFAULT_MONEYFLOW_EXPENSE), null,
                new MoneyFlow(DEFAULT_MONEYFLOW_TOTAL)));
        Assert.assertThrows(NullPointerException.class, () -> new MonthSummary(new Month(DEFAULT_MONTH),
                new MoneyFlow(DEFAULT_MONEYFLOW_EXPENSE), new MoneyFlow(DEFAULT_MONEYFLOW_INCOME),
                null));
    }

    @Test
    public void add_nullRecord_throwsNullPointerException() {
        final SummaryAbs daySummary = summaryBuilder.buildDaySummary();
        Assert.assertThrows(NullPointerException.class, () -> daySummary.add(null));

        final SummaryAbs monthSummary = summaryBuilder.buildMonthSummary();
        Assert.assertThrows(NullPointerException.class, () -> monthSummary.add(null));
    }

    @Test
    public void add_recordWithExpenseMoneyFlowToDaySummary_success() {
        SummaryAbs summary = summaryBuilder.buildDaySummary();
        RecordBuilder recordBuilder = new RecordBuilder();
        Record toAdd = recordBuilder.withDate(DEFAULT_DATE).withMoneyFlow(DEFAULT_MONEYFLOW_EXPENSE).build();
        summary.add(toAdd);

        SummaryBuilder toCompareBuilder = new SummaryBuilder();
        MoneyFlow targetTotalExpense = MoneyUtil.add(new MoneyFlow(DEFAULT_TOTAL_EXPENSE), toAdd.getMoneyFlow());
        MoneyFlow targetTotal = MoneyUtil.add(new MoneyFlow(DEFAULT_TOTAL), toAdd.getMoneyFlow());

        assertEquals(summary, toCompareBuilder.withTotalExpense(targetTotalExpense.value)
                .withTotal(targetTotal.value).buildDaySummary());
    }

    @Test
    public void add_recordWithExpenseMoneyFlowToMonthSummary_success() {
        SummaryAbs summary = summaryBuilder.buildMonthSummary();
        RecordBuilder recordBuilder = new RecordBuilder();
        Record toAdd = recordBuilder.withDate(DEFAULT_DATE).withMoneyFlow(DEFAULT_MONEYFLOW_EXPENSE).build();
        summary.add(toAdd);

        SummaryBuilder toCompareBuilder = new SummaryBuilder();
        MoneyFlow targetTotalExpense = MoneyUtil.add(new MoneyFlow(DEFAULT_TOTAL_EXPENSE), toAdd.getMoneyFlow());
        MoneyFlow targetTotal = MoneyUtil.add(new MoneyFlow(DEFAULT_TOTAL), toAdd.getMoneyFlow());
        Month targetMonth = new Month(toAdd.getDate().getMonth(), toAdd.getDate().getYear());

        assertEquals(summary, toCompareBuilder.withMonth(targetMonth.value).withTotalExpense(targetTotalExpense.value)
                .withTotal(targetTotal.value).buildMonthSummary());
    }

    @Test
    public void add_recordWithIncomeMoneyFlowToDaySummary_success() {
        SummaryAbs summary = summaryBuilder.buildDaySummary();
        RecordBuilder recordBuilder = new RecordBuilder();
        Record toAdd = recordBuilder.withDate(DEFAULT_DATE).withMoneyFlow(DEFAULT_MONEYFLOW_INCOME).build();
        summary.add(toAdd);

        SummaryBuilder toCompareBuilder = new SummaryBuilder();
        MoneyFlow targetTotalIncome = MoneyUtil.add(new MoneyFlow(DEFAULT_TOTAL_INCOME), toAdd.getMoneyFlow());
        MoneyFlow targetTotal = MoneyUtil.add(new MoneyFlow(DEFAULT_TOTAL), toAdd.getMoneyFlow());

        assertEquals(summary, toCompareBuilder.withTotalIncome(targetTotalIncome.value)
                .withTotal(targetTotal.value).buildDaySummary());
    }

    @Test
    public void add_recordWithIncomeMoneyFlowToMonthSummary_success() {
        SummaryAbs summary = summaryBuilder.buildMonthSummary();
        RecordBuilder recordBuilder = new RecordBuilder();
        Record toAdd = recordBuilder.withDate(DEFAULT_DATE).withMoneyFlow(DEFAULT_MONEYFLOW_INCOME).build();
        summary.add(toAdd);

        SummaryBuilder toCompareBuilder = new SummaryBuilder();
        MoneyFlow targetTotalIncome = MoneyUtil.add(new MoneyFlow(DEFAULT_TOTAL_INCOME), toAdd.getMoneyFlow());
        MoneyFlow targetTotal = MoneyUtil.add(new MoneyFlow(DEFAULT_TOTAL), toAdd.getMoneyFlow());
        Month targetMonth = new Month(toAdd.getDate().getMonth(), toAdd.getDate().getYear());

        assertEquals(summary, toCompareBuilder.withMonth(targetMonth.value).withTotalIncome(targetTotalIncome.value)
                .withTotal(targetTotal.value).buildMonthSummary());
    }
}
