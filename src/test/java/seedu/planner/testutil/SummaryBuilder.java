package seedu.planner.testutil;

import seedu.planner.model.record.Date;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.summary.Summary;

/**
 * A utility class to help build Summay objects for testing
 */
public class SummaryBuilder {

    public static final String DEFAULT_DATE = "10-2-2018";
    public static final String DEFAULT_TOTAL_EXPENSE = "-10";
    public static final String DEFAULT_TOTAL_INCOME = "+300";
    public static final String DEFAULT_TOTAL = "+290";
    public static final String DEFAULT_COUNT = "10";

    private Date date;
    private MoneyFlow totalExpense;
    private MoneyFlow totalIncome;
    private MoneyFlow total;
    private Integer trackedRecordsCount;

    public SummaryBuilder() {
        date = new Date(DEFAULT_DATE);
        totalExpense = new MoneyFlow(DEFAULT_TOTAL_EXPENSE);
        totalIncome = new MoneyFlow(DEFAULT_TOTAL_INCOME);
        total = new MoneyFlow(DEFAULT_TOTAL);
        trackedRecordsCount = Integer.parseInt(DEFAULT_COUNT);
    }

    /**
     * Sets the {@code date} of the {@code Summary} that we are building.
     */
    public SummaryBuilder withDate(String date) {
        this.date = new Date(date);
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

    public Summary build() {
        return new Summary(date, totalExpense, totalIncome, total);
    }

}
