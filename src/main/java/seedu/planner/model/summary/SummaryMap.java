package seedu.planner.model.summary;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.Record;
import seedu.planner.model.record.exceptions.RecordNotFoundException;

/**
 * This object represents the in memory model of a HashMap containing {@Summary} as values and
 * retrieved using {@Date} as keys. It supports the usual addition, deletion, editing and find operations
 */
public class SummaryMap {

    private HashMap<Date, Summary> summaryMap;

    public SummaryMap() {
        summaryMap = new HashMap();
    }

    /**
     * Adds the record's moneyflow into the summary hashMap
     */
    public void add(Record record) {
        Date dateOfRecord = record.getDate();
        if (!isDatePresentInMap(dateOfRecord)) {
            summaryMap.put(dateOfRecord, new Summary(record));
        } else {
            summaryMap.get(dateOfRecord).add(record);
        }
    }

    /**
     * Adds a summary object into the summary hashMap
     */
    public void add(Summary summary) {
        requireNonNull(summary);
        Date dateOfSummary = summary.getDate();
        summaryMap.put(dateOfSummary, summary);
    }

    /**
     * Subtracts the record's moneyflow from the summary hashMap
     * The date must exist in the hashMap.
     */
    public void remove(Record record) {
        Date dateOfRecord = record.getDate();
        if (!isDatePresentInMap(dateOfRecord)) {
            throw new RecordNotFoundException();
        }
        summaryMap.get(dateOfRecord).remove(record);
    }

    /**
     * Updates the summary map with the moneyflow of {@code target} and {@code editedRecord}
     * Subtracts target's moneyflow from summary hashMap before adding editedRecord's moneyflow to summary hashMap
     */
    public void update(Record target, Record editedRecord) {
        remove(target);
        add(target);
    }

    /**
     * Displays the lists of summary objects in a specific time period in the format of a string
     */
    //TODO: change this when changing to UI
    public ObservableList<Summary> getSummaryList(Date startDate, Date endDate) {
        List<Summary> summaryList = summaryMap.keySet().stream()
                .filter(s -> isDateWithinInterval(s, startDate, endDate))
                .map(s -> summaryMap.get(s))
                .collect(Collectors.toList());
        return FXCollections.unmodifiableObservableList(FXCollections.observableList(summaryList));
    }

    public int size() {
        return summaryMap.size();
    }

    public Map<Date, Summary> getMap() {
        return summaryMap;
    }

    private boolean isDatePresentInMap(Date date) {
        return summaryMap.containsKey(date);
    }

    // TODO: Refactor this into the date util
    private boolean isDateWithinInterval(Date toCheck, Date startDate, Date endDate) {
        return toCheck.equals(startDate) || toCheck.equals(endDate)
                || (toCheck.isLaterThan(startDate) && toCheck.isEarlierThan(endDate));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SummaryMap // instanceof handles nulls
                    && summaryMap.equals(((SummaryMap) other).summaryMap));
    }
}
