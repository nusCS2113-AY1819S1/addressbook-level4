package seedu.planner.model.summary;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.planner.commons.util.CompareUtil;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.record.Record;
import seedu.planner.model.tag.Tag;
import seedu.planner.ui.SummaryEntry;
//@@author tenvinc

/**
 * This class represents a list containing all Summary objects computed from a given list of records
 * and a predicate criteria. The internal implementation is a HashMap but it returns a list
 * and implements only list functions
 */
public class SummaryByCategoryList extends SummaryList {
    private HashMap<Set<Tag>, Summary<Set<Tag>>> summaryMap = new HashMap<>();

    public SummaryByCategoryList(List<Record> recordList) {
        super();
        requireNonNull(recordList);
        for (Record r : recordList) {
            addRecordToMap(r);
            updateTotals(r);
        }
    }

    @Override
    public ObservableList<SummaryEntry> getSummaryList() {

        List<SummaryEntry> list = summaryMap.keySet().stream().sorted(CompareUtil.compareTags())
                .map(k -> SummaryEntry.convertToUiFriendly(summaryMap.get(k)))
                .collect(Collectors.toList());
        return FXCollections.observableList(list);
    }

    public HashMap<Set<Tag>, Summary<Set<Tag>>> getSummaryMap() {
        return summaryMap;
    }

    /** Adds a record to the {@code summaryMap} while following some rules.
     * If there exists a summary with {@code Date} of record, then record is added to the summary.
     * Else, it creates a summary with the details of the record.
     * @param record given record
     * @see Summary#add(Record)
     */
    @Override
    protected void addRecordToMap(Record record) {
        Set<Tag> tags = record.getTags();
        if (summaryMap.containsKey(tags)) {
            summaryMap.get(tags).add(record);
        } else {
            summaryMap.put(tags, new Summary<>(record, tags));
        }
    }

    @Override
    protected void updateTotals(Record record) {
        super.updateTotals(record);
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
        return "Category";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SummaryByCategoryList // instanceof handles nulls
                && summaryMap.equals(((SummaryByCategoryList) other).summaryMap));
    }
}
