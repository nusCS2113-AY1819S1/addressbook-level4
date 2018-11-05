package seedu.planner.model.summary;

import javafx.collections.ObservableList;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.record.Record;
import seedu.planner.ui.SummaryEntry;

/**
 * This class represents a list containing all Summary objects computed from a given list of records
 * and a predicate criteria. The internal implementation is a HashMap but it returns a list
 * and implements only list functions
 */
public abstract class SummaryList {

    /**
     * Returns the simple class name of the identifier used to categorise each entry in list
     */
    public abstract String getIdentifierName();

    public abstract MoneyFlow getTotal();

    public abstract MoneyFlow getTotalIncome();

    public abstract MoneyFlow getTotalExpense();

    /**
     * Returns the size of the internal map
     */
    public abstract int size();

    /**
     * Checks if the internal map is empty
     * @return true if empty and false if otherwise
     */
    public abstract boolean isEmpty();

    /** Adds a record to the {@code summaryMap} while following some rules.
     * If there exists a summary with {@code Date} of record, then record is added to the summary.
     * Else, it creates a summary with the details of the record.
     * @param record given record
     * @see Summary#add(Record)
     */
    protected abstract void addRecordToMap(Record record);

    /**
     * Converts internal map to a list of SummaryEntry objects and returns a read-only copy of that list
     * @return read-only list of SummaryEntry objects
     */
    public abstract ObservableList<SummaryEntry> getSummaryList();


}
