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
        // Does nothing
    }

    public void add(Record record) {
        // Does nothing
    }

    public void delete(Record record) {
        // Does nothing
    }
}
