package seedu.planner.model.summary;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.planner.model.Month;
import seedu.planner.model.record.Record;
import seedu.planner.ui.SummaryEntry;

/**
 * This class represents a list containing all MonthSummary objects computed from a given list of records
 * and a predicate criteria. The internal implementation is a HashMap but it returns a list
 * and implements only list functions.
 */
public class SummaryByMonthList {

    private HashMap<Month, MonthSummary> summaryMap = new HashMap<>();

    public SummaryByMonthList(List<Record> recordList , Predicate<Record> predicate) {
        for (Record r : recordList) {
            if (predicate.test(r)) {
                addRecordToMap(r);
            }
        }
    }

    public ObservableList<SummaryEntry> getSummaryList() {
        List<SummaryEntry> list = summaryMap.keySet().stream().map(k -> convertToUiFriendly(summaryMap.get(k)))
                .collect(Collectors.toList());
        return FXCollections.observableList(list);
    }

    private SummaryEntry convertToUiFriendly(MonthSummary summary) {
        return new SummaryEntry(summary.getMonth().toString(), summary.getTotalIncome().toString(),
                summary.getTotalExpense().toString(), summary.getTotal().toString());
    }

    /** Adds a record to the {@code summaryMap} while following some rules.
     * If there exists a summary with {@code Date} of record, then record is added to the summary.
     * Else, it creates a summary with the details of the record.
     * @param record given record
     * @see Summary#add(Record)
     */
    private void addRecordToMap(Record record) {
        Month month = new Month(record.getDate().getMonth(), record.getDate().getYear());
        if (summaryMap.containsKey(month)) {
            summaryMap.get(month).add(record);
        } else {
            summaryMap.put(month, new MonthSummary(record));
        }
    }

    public int size() {
        return summaryMap.size();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SummaryByMonthList // instanceof handles nulls
                && summaryMap.equals(((SummaryByMonthList) other).summaryMap));
    }
}
