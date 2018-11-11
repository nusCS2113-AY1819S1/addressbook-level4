package seedu.planner.testutil;

import java.util.Set;

import seedu.planner.model.Month;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.record.Record;
import seedu.planner.model.summary.Summary;
import seedu.planner.model.tag.Tag;
import seedu.planner.model.util.SampleDataUtil;
//@@author tenvinc
/**
 * A utility class to help build Summary objects for testing
 */
public class SummaryBuilder {

    public static final String DEFAULT_DATE = "10-2-2018";
    public static final String DEFAULT_TOTAL_EXPENSE = "-10";
    public static final String DEFAULT_TOTAL_INCOME = "+300";
    public static final String DEFAULT_TOTAL = "+290";
    public static final String DEFAULT_MONTH = "feb-2018";
    public static final String DEFAULT_TAG = "default";

    private Date date;
    private Month month;
    private MoneyFlow totalExpense;
    private MoneyFlow totalIncome;
    private MoneyFlow total;
    private Set<Tag> category;

    public SummaryBuilder() {
        date = new Date(DEFAULT_DATE);
        month = new Month(DEFAULT_MONTH);
        category = SampleDataUtil.getTagSet(DEFAULT_TAG);
        totalExpense = new MoneyFlow(DEFAULT_TOTAL_EXPENSE);
        totalIncome = new MoneyFlow(DEFAULT_TOTAL_INCOME);
        total = new MoneyFlow(DEFAULT_TOTAL);
    }

    public SummaryBuilder(Record record) {
        date = record.getDate();
        month = new Month(record.getDate().getMonth(), record.getDate().getYear());
        category = record.getTags();
        if (record.getMoneyFlow().valueDouble > 0) {
            totalExpense = new MoneyFlow(MoneyFlow.REPRESENTATION_ZERO);
            totalIncome = record.getMoneyFlow();
        } else {
            totalExpense = record.getMoneyFlow();
            totalIncome = new MoneyFlow(MoneyFlow.REPRESENTATION_ZERO);
        }
        total = record.getMoneyFlow();
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
     * Sets the {@code category} of the {@code Summary} that we are building.
     */
    public SummaryBuilder withCategory(String... tags) {
        this.category = SampleDataUtil.getTagSet(tags);
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

    public Summary<Date> buildDaySummary() {
        return new Summary<>(date, totalExpense, totalIncome, total);
    }

    public Summary<Month> buildMonthSummary() {
        return new Summary<>(month, totalExpense, totalIncome, total);
    }

    public Summary<Set<Tag>> buildCategorySummary() {
        return new Summary<>(category, totalExpense, totalIncome, total);
    }

}
