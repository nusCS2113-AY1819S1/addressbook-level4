package seedu.planner.model.summary;

import java.util.HashMap;

import seedu.planner.model.record.Date;
import seedu.planner.model.record.Record;

/**
 * This object represents the in memory model of a HashMap containing {@Summary} as values and
 * retrieved using {@Date} as keys. It supports the usual addition, deletion, editing and find operations
 */
public class SummaryHashMap {

    private HashMap<Date, Summary> summaryMap;

    public SummaryHashMap() {
        summaryMap = new HashMap<Date, Summary>();
    }

    /**
     * Adds the record into the summary hashMap
     */
    public void add(Record record) {
        Date dateOfRecord = record.getDate();
        if (!isDatePresentInMap(dateOfRecord)) {
            summaryMap.put(dateOfRecord, new Summary(record));
        }
        summaryMap.get(dateOfRecord).add(record);
    }

    private boolean isDatePresentInMap(Date date) {
        return summaryMap.containsKey(date);
    }
}
