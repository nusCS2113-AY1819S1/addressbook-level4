package seedu.planner.model.summary;

import seedu.planner.model.record.Date;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.record.Record;

/**
 * This object represents the in memory model of a summary of all financial activity within a day.
 */
// TODO: refactor this such that it does not need to know how to add
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
        } else {
            totalIncome = money;
        } total = money;
    }

    /**
     * Adds the record's moneyflow to totalExpense, totalIncome or total\
     */
    // TODO: Please refactor this and bring into Moneyflow class
    public void add(Record record) {
        MoneyFlow money = record.getMoneyFlow();
        if (isExpense(money)) {
            double newMoneyAmount = money.toDouble() + totalExpense.toDouble();
            String newMoneyString = String.format("%.2f", newMoneyAmount);
            totalExpense = new MoneyFlow(newMoneyString);
        } else {
            double newMoneyAmount = money.toDouble() + totalExpense.toDouble();
            String newMoneyString = String.format("+%.2f", newMoneyAmount);
            totalExpense = new MoneyFlow(newMoneyString);
        }
        double newTotalAmount = money.toDouble() + total.toDouble();
        String newTotalString;
        if (newTotalAmount > 0) {
            newTotalString = String.format("+%.2f", newTotalAmount);
        } else {
            newTotalString = String.format("%.2f", newTotalAmount);
        }
        total = new MoneyFlow(newTotalString);
    }

    public void remove(Record record) {
        // Does nothing
    }

    private boolean isExpense(MoneyFlow money) {
        return money.toDouble() < 0;
    }
}
