package seedu.planner.model.summary;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.planner.commons.util.CompareUtil;
import seedu.planner.model.Month;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.record.Record;
import seedu.planner.ui.SummaryEntry;

//@@author tenvinc
/**
 * This class represents a list containing all Summary objects computed from a given list of records
 * and a predicate criteria. The internal implementation is a HashMap but it returns a list
 * and implements only list functions.
 */
public class SummaryByMonthList extends SummaryList {

    protected HashMap<Month, Summary<Month>> summaryMap = new HashMap<>();

    public SummaryByMonthList(List<Record> recordList) {
        super();
        requireNonNull(recordList);
        for (Record r : recordList) {
            addRecordToMap(r);
            updateTotals(r);
        }
    }

    @Override
    public ObservableList<SummaryEntry> getSummaryList() {
        List<SummaryEntry> list = summaryMap.keySet().stream().sorted(CompareUtil.compareMonth())
                .map(k -> SummaryEntry.convertToUiFriendly(summaryMap.get(k)))
                .collect(Collectors.toList());
        return FXCollections.observableList(list);
    }

    public HashMap<Month, Summary<Month>> getSummaryMap() {
        return summaryMap;
    }

    /** Adds a record to the {@code summaryMap} while following some rules.
     * If there exists a summary with {@code Month} of record, then record is added to the summary.
     * Else, it creates a summary with the details of the record.
     * @param record given record
     * @see Summary#add(Record)
     */
    protected void addRecordToMap(Record record) {
        Month month = new Month(record.getDate().getMonth(), record.getDate().getYear());
        if (summaryMap.containsKey(month)) {
            summaryMap.get(month).add(record);
        } else {
            summaryMap.put(month, new Summary<>(record, month));
        }
    }

    @Override
    protected void updateTotals(Record record) {
        super.updateTotals(record);
    }

    @Override
    public MoneyFlow getTotal() {
        return total;
    }

    @Override
    public MoneyFlow getTotalIncome() {
        return totalIncome;
    }

    @Override
    public MoneyFlow getTotalExpense() {
        return totalExpense;
    }

    @Override
    public String getIdentifierName() {
        return Month.class.getSimpleName();
    }

    @Override
    public int size() {
        return summaryMap.size();
    }

    @Override
    public boolean isEmpty() {
        return summaryMap.size() == 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SummaryByMonthList // instanceof handles nulls
                && summaryMap.equals(((SummaryByMonthList) other).summaryMap));
    }
}
