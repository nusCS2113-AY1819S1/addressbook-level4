package seedu.planner.model.summary;

import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.record.Record;

/**
 * This class represents a summary in FinancialPlanner which records can be added to.
 */
public abstract class SummaryAbs {

    protected MoneyFlow totalIncome;
    protected MoneyFlow totalExpense;
    protected MoneyFlow total;

    /**
     * Adds record into the summary object
     * @param record
     */
    public abstract void add(Record record);
}
