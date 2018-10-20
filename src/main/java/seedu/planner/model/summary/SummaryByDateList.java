package seedu.planner.model.summary;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.Record;

/**
 * This class represents a list containing all summary objects computed from a given list of records
 * and a predicate criteria. The internal implementation is a HashMap but it returns a list
 * and implements only list functions
 */
public class SummaryByDateList {

    private HashMap<Date, Summary> summaryMap = new HashMap<>();

    public SummaryByDateList(List<Record> recordList , Predicate<Record> predicate) {
        for (Record r : recordList) {
            if (predicate.test(r)) {
                addRecordToMap(r);
            }
        }
    }

    public ObservableList<Summary> getSummaryList() {
        List<Summary> list = summaryMap.keySet().stream().map(k -> summaryMap.get(k)).collect(Collectors.toList());
        return FXCollections.observableList(list);
    }

    /** Adds a record to the {@code summaryMap} while following some rules.
     * If there exists a summary with {@code Date} of record, then record is added to the summary.
     * Else, it creates a summary with the details of the record.
     * @param record given record
     * @see Summary#add(Record)
     */
    private void addRecordToMap(Record record) {
        Date date = record.getDate();
        if (summaryMap.containsKey(date)) {
            summaryMap.get(date).add(record);
        } else {
            summaryMap.put(date, new Summary(record));
        }
    }

    public int size() {
        return summaryMap.size();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SummaryByDateList // instanceof handles nulls
                && summaryMap.equals(((SummaryByDateList) other).summaryMap));
    }
}
