package seedu.planner.model.summary;

import static org.junit.Assert.assertEquals;
import static seedu.planner.testutil.SummaryBuilder.DEFAULT_TOTAL;
import static seedu.planner.testutil.SummaryBuilder.DEFAULT_TOTAL_EXPENSE;
import static seedu.planner.testutil.SummaryBuilder.DEFAULT_TOTAL_INCOME;

import java.util.Set;

import org.junit.Test;

import seedu.planner.commons.util.MoneyUtil;
import seedu.planner.model.Month;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.record.Record;
import seedu.planner.model.tag.Tag;
import seedu.planner.testutil.Assert;
import seedu.planner.testutil.RecordBuilder;
import seedu.planner.testutil.SummaryBuilder;
//@@author tenvinc
public class SummaryTest {

    private static final String DEFAULT_IDENTIFIER = "10-2-2018";
    private static final String DEFAULT_MONEYFLOW_EXPENSE = "-10.00";
    private static final String DEFAULT_MONEYFLOW_INCOME = "+100";
    private static final String DEFAULT_MONEYFLOW_TOTAL = "+90";

    private static final SummaryBuilder summaryBuilder = new SummaryBuilder();

    @Test
    public void constructor_nullRecordSummary_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Summary<>(new RecordBuilder().build(), null));
        Assert.assertThrows(NullPointerException.class, () -> new Summary<>(null, new Date(DEFAULT_IDENTIFIER)));
    }

    @Test
    public void constructor_nullParamSummary_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Summary<Date>(null,
                new MoneyFlow(DEFAULT_MONEYFLOW_EXPENSE), new MoneyFlow(DEFAULT_MONEYFLOW_INCOME),
                new MoneyFlow(DEFAULT_MONEYFLOW_TOTAL)));
        Assert.assertThrows(NullPointerException.class, () -> new Summary<>(new Date(DEFAULT_IDENTIFIER),
                null, new MoneyFlow(DEFAULT_MONEYFLOW_INCOME),
                new MoneyFlow(DEFAULT_MONEYFLOW_TOTAL)));
        Assert.assertThrows(NullPointerException.class, () -> new Summary<>(new Date(DEFAULT_IDENTIFIER),
                new MoneyFlow(DEFAULT_MONEYFLOW_EXPENSE), null,
                new MoneyFlow(DEFAULT_MONEYFLOW_TOTAL)));
        Assert.assertThrows(NullPointerException.class, () -> new Summary<>(new Date(DEFAULT_IDENTIFIER),
                new MoneyFlow(DEFAULT_MONEYFLOW_EXPENSE), new MoneyFlow(DEFAULT_MONEYFLOW_INCOME),
                null));
    }

    @Test
    public void add_nullRecord_throwsNullPointerException() {
        Summary<Date> daySummary = summaryBuilder.buildDaySummary();
        Assert.assertThrows(NullPointerException.class, () -> daySummary.add(null));

        Summary<Month> monthSummary = summaryBuilder.buildMonthSummary();
        Assert.assertThrows(NullPointerException.class, () -> monthSummary.add(null));

        Summary<Set<Tag>> categorySummary = summaryBuilder.buildCategorySummary();
        Assert.assertThrows(NullPointerException.class, () -> categorySummary.add(null));
    }

    @Test
    public void add_recordWithExpenseMoneyFlowToSummary_success() {
        Summary<Date> summary = summaryBuilder.buildDaySummary();
        RecordBuilder recordBuilder = new RecordBuilder();
        Record toAdd = recordBuilder.withDate(DEFAULT_IDENTIFIER).withMoneyFlow(DEFAULT_MONEYFLOW_EXPENSE).build();
        summary.add(toAdd);

        SummaryBuilder toCompareBuilder = new SummaryBuilder();
        MoneyFlow targetTotalExpense = MoneyUtil.add(new MoneyFlow(DEFAULT_TOTAL_EXPENSE), toAdd.getMoneyFlow());
        MoneyFlow targetTotal = MoneyUtil.add(new MoneyFlow(DEFAULT_TOTAL), toAdd.getMoneyFlow());

        assertEquals(summary, toCompareBuilder.withTotalExpense(targetTotalExpense.value)
                .withTotal(targetTotal.value).buildDaySummary());
    }

    @Test
    public void add_recordWithIncomeMoneyFlowToSummary_success() {
        Summary<Date> summary = summaryBuilder.buildDaySummary();
        RecordBuilder recordBuilder = new RecordBuilder();
        Record toAdd = recordBuilder.withDate(DEFAULT_IDENTIFIER).withMoneyFlow(DEFAULT_MONEYFLOW_INCOME).build();
        summary.add(toAdd);

        SummaryBuilder toCompareBuilder = new SummaryBuilder();
        MoneyFlow targetTotalIncome = MoneyUtil.add(new MoneyFlow(DEFAULT_TOTAL_INCOME), toAdd.getMoneyFlow());
        MoneyFlow targetTotal = MoneyUtil.add(new MoneyFlow(DEFAULT_TOTAL), toAdd.getMoneyFlow());

        assertEquals(summary, toCompareBuilder.withTotalIncome(targetTotalIncome.value)
                .withTotal(targetTotal.value).buildDaySummary());
    }
}
