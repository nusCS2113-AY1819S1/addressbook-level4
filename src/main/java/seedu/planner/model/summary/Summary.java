package seedu.planner.model.summary;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import seedu.planner.commons.util.MoneyUtil;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.record.Record;

/**
 * This object represents the in memory model of a summary of all financial activity within a day.
 */
public class Summary {
    private Date date;
    private MoneyFlow totalExpense;
    private MoneyFlow totalIncome;
    private MoneyFlow total;

    public Summary(Record record) {
        requireNonNull(record);
        date = record.getDate();
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

    public Summary(Date date, MoneyFlow totalExpense, MoneyFlow totalIncome, MoneyFlow total) {
        requireAllNonNull(date, totalExpense, totalIncome, total);
        this.date = date;
        this.totalExpense = totalExpense;
        this.totalIncome = totalIncome;
        this.total = total;
    }

    public Date getDate() {
        return date;
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
                || (other instanceof Summary // instanceof handles nulls
                    && date.equals(((Summary) other).date)
                    && totalExpense.equals(((Summary) other).totalExpense)
                    && totalIncome.equals(((Summary) other).totalIncome)
                    && total.equals(((Summary) other).total));
    }

    @Override
    public String toString() {
        return "Date: " + date + "\n"
                + "Total Expense: " + totalExpense + "\n"
                + "Total Income: " + totalIncome + "\n"
                + "Total: " + total;
    }
}
