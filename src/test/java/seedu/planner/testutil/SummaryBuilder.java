package seedu.planner.testutil;

import seedu.planner.model.Month;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.summary.DaySummary;
import seedu.planner.model.summary.MonthSummary;
import seedu.planner.model.summary.SummaryAbs;

/**
 * A utility class to help buildDaySummary Summary objects for testing
 */
public class SummaryBuilder {

    public static final String DEFAULT_DATE = "10-2-2018";
    public static final String DEFAULT_TOTAL_EXPENSE = "-10";
    public static final String DEFAULT_TOTAL_INCOME = "+300";
    public static final String DEFAULT_TOTAL = "+290";
    public static final String DEFAULT_MONTH = "feb-2018";

    private Date date;
    private Month month;
    private MoneyFlow totalExpense;
    private MoneyFlow totalIncome;
    private MoneyFlow total;

    public SummaryBuilder() {
        date = new Date(DEFAULT_DATE);
        month = new Month(DEFAULT_MONTH);
        totalExpense = new MoneyFlow(DEFAULT_TOTAL_EXPENSE);
        totalIncome = new MoneyFlow(DEFAULT_TOTAL_INCOME);
        total = new MoneyFlow(DEFAULT_TOTAL);
    }

    public SummaryBuilder(DaySummary summaryToCopy) {
        date = summaryToCopy.getDate();
        totalExpense = summaryToCopy.getTotalExpense();
        totalIncome = summaryToCopy.getTotalIncome();
        total = summaryToCopy.getTotal();
    }

    public SummaryBuilder(MonthSummary summaryToCopy) {
        month = summaryToCopy.getMonth();
        totalExpense = summaryToCopy.getTotalExpense();
        totalIncome = summaryToCopy.getTotalIncome();
        total = summaryToCopy.getTotal();
    }

    /**
     * Sets the {@code date} of the {@code Summary} that we are building.
     */
    public SummaryBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code month} of the {@code Summary} that we are building.
     */
    public SummaryBuilder withMonth(String month) {
        this.month = new Month(month);
        return this;
    }

    /**
     * Sets the {@code totalExpense} of the {@code Summary} that we are building.
     */
    public SummaryBuilder withTotalExpense(String totalExpense) {
        this.totalExpense = new MoneyFlow(totalExpense);
        return this;
    }

    /**
     * Sets the {@code totalIncome} of the {@code Summary} that we are building.
     */
    public SummaryBuilder withTotalIncome(String totalIncome) {
        this.totalIncome = new MoneyFlow(totalIncome);
        return this;
    }

    /**
     * Sets the {@code total} of the {@code Summary} that we are building.
     */
    public SummaryBuilder withTotal(String total) {
        this.total = new MoneyFlow(total);
        return this;
    }

    public SummaryAbs buildDaySummary() {
        return new DaySummary(date, totalExpense, totalIncome, total);
    }

    public SummaryAbs buildMonthSummary() {
        return new MonthSummary(month, totalExpense, totalIncome, total);
    }

}
