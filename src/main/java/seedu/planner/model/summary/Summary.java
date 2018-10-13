package seedu.planner.model.summary;

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
     * Adds the record's moneyflow to totalExpense, totalIncome or total\
     */
    // TODO: Please refactor this and bring into Moneyflow class
    public void add(Record record) {
        MoneyFlow money = record.getMoneyFlow();
        if (isExpense(money)) {
            double newMoneyAmount = totalExpense.toDouble() + money.toDouble() ;
            String newMoneyString = String.format("%.2f", newMoneyAmount);
            totalExpense = new MoneyFlow(newMoneyString);
        } else {
            double newMoneyAmount = totalIncome.toDouble() + money.toDouble();
            String newMoneyString = String.format("+%.2f", newMoneyAmount);
            totalIncome = new MoneyFlow(newMoneyString);
        }
        double newTotalAmount = total.toDouble() + money.toDouble();
        String newTotalString;
        if (newTotalAmount > 0) {
            newTotalString = String.format("+%.2f", newTotalAmount);
        } else {
            newTotalString = String.format("%.2f", newTotalAmount);
        }
        total = new MoneyFlow(newTotalString);
    }

    /**
     * Subtracts the record's moneyflow from totalExpense or totalIncome depending on the type of moneyflow
     * and total
     */
    // TODO: Please refactor this and bring into Moneyflow class
    public void remove(Record record) {
        MoneyFlow money = record.getMoneyFlow();
        if (isExpense(money)) {
            double newMoneyAmount = totalExpense.toDouble() - money.toDouble();
            assert(newMoneyAmount <= 0);
            String newMoneyString = String.format("%.2f", newMoneyAmount);
            totalExpense = new MoneyFlow(newMoneyString);
        } else {
            double newMoneyAmount = totalIncome.toDouble() - money.toDouble() ;
            assert(newMoneyAmount >= 0);
            String newMoneyString = String.format("+%.2f", newMoneyAmount);
            totalIncome = new MoneyFlow(newMoneyString);
        }
        double newTotalAmount = total.toDouble() - money.toDouble();
        String newTotalString;
        if (newTotalAmount > 0) {
            newTotalString = String.format("+%.2f", newTotalAmount);
        } else {
            newTotalString = String.format("%.2f", newTotalAmount);
        }
        total = new MoneyFlow(newTotalString);
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
}
