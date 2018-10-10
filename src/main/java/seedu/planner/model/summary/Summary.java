package seedu.planner.model.summary;

import seedu.planner.model.record.Date;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.record.Record;

/**
 * This object represents the in memory model of a summary of all financial activity within a day.
 */
// TODO: refactor this such that the pattern is not needed here
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
        }
    }

    public void add(Record record) {
        // Does nothing
    }

    public void remove(Record record) {
        // Does nothing
    }

    private boolean isExpense(MoneyFlow money) {
        return money.toDouble() < 0;
    }
}
