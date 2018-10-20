package seedu.planner.model.summary;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import seedu.planner.commons.util.MoneyUtil;
import seedu.planner.model.Month;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.record.Record;

/**
 * This class represents all financial activity in a month
 */
public class MonthSummary {
    private Month month;
    private MoneyFlow totalExpense;
    private MoneyFlow totalIncome;
    private MoneyFlow total;

    public MonthSummary(Record record) {
        requireNonNull(record);
        month = new Month(record.getDate().getMonth(), record.getDate().getYear());
        MoneyFlow money = record.getMoneyFlow();
        if (isExpense(money)) {
            totalExpense = money;
            totalIncome = new MoneyFlow("+0");
        } else {
            totalIncome = money;
            totalExpense = new MoneyFlow("-0");
        }
        total = money;
    }

    public MonthSummary(Month month, MoneyFlow totalExpense, MoneyFlow totalIncome, MoneyFlow total) {
        requireAllNonNull(month, totalExpense, totalIncome, total);
        this.month = month;
        this.totalExpense = totalExpense;
        this.totalIncome = totalIncome;
        this.total = total;
    }

    public Month getMonth() {
        return month;
    }

    public MoneyFlow getTotalExpense() {
        return totalExpense;
    }

    public MoneyFlow getTotalIncome() {
        return totalIncome;
    }

    public MoneyFlow getTotal() {
        return total;
    }

    /**
     * Adds the record's moneyflow to totalExpense, totalIncome or total
     */
    public void add(Record record) {
        requireNonNull(record);
        MoneyFlow money = record.getMoneyFlow();
        if (isExpense(money)) {
            totalExpense = MoneyUtil.add(totalExpense, money);
        } else {
            totalIncome = MoneyUtil.add(totalIncome, money);
        }
        total = MoneyUtil.add(total, money);
    }

    /**
     * Subtracts the record's moneyflow from totalExpense or totalIncome depending on the type of moneyflow
     * and total
     */
    public void remove(Record record) {
        requireNonNull(record);
        MoneyFlow money = record.getMoneyFlow();
        if (isExpense(money)) {
            totalExpense = MoneyUtil.subtract(totalExpense, money);
        } else {
            totalIncome = MoneyUtil.subtract(totalIncome, money);
        }
        total = MoneyUtil.subtract(total, money);
    }

    private boolean isExpense(MoneyFlow money) {
        return money.toDouble() < 0;
    }

    @Override
    public boolean equals(Object other) {
        return this == other // short circuit if same object
                || (other instanceof MonthSummary // instanceof handles nulls
                && month.equals(((MonthSummary) other).month)
                && totalExpense.equals(((MonthSummary) other).totalExpense)
                && totalIncome.equals(((MonthSummary) other).totalIncome)
                && total.equals(((MonthSummary) other).total));
    }

    @Override
    public String toString() {
        return "Month: " + month + "\n"
                + "Total Expense: " + totalExpense + "\n"
                + "Total Income: " + totalIncome + "\n"
                + "Total: " + total;
    }
}
