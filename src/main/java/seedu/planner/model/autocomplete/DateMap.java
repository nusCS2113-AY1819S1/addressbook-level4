package seedu.planner.model.autocomplete;

import java.util.HashMap;

import seedu.planner.model.record.Limit;
import seedu.planner.model.record.Record;

//@@author tztzt
/**
 *  This object represents the in memory model of a HashMap containing integers as values which can be retrieved with
 *  Strings as the key. It keeps track of the number of usage of each unique date in the model.
 *  It supports addition, deletion, updating and finding the size of the Hashmap. It stores dates
 *  from either records or limits.
 */
public class DateMap {

    private HashMap<String, Integer> dateMap = new HashMap<>();

    public int size() {
        return dateMap.size();
    }

    /**
     * Adds the date of a record to the date map
     * @param record to be added to the date map
     */
    public void addRecordToDateMap(Record record) {
        String date = record.getDate().getStandardValue();
        if (dateMap.containsKey(date)) {
            dateMap.replace(date, dateMap.get(date) + 1);
        } else {
            dateMap.put(date, 1);
        }
    }

    /**
     * Removes a record's date from the date map.
     * @param record to be removed from the date map
     */
    public void removeRecordFromDateMap(Record record) {
        String date = record.getDate().getStandardValue();
        if (dateMap.containsKey(date)) {
            dateMap.replace(date, dateMap.get(date) - 1);
            if (dateMap.get(date) == 0) {
                dateMap.remove(date);
            }
        }
    }

    /**
     * Updates the date of a target record by removing the previous record's date
     * and adding the date of the new record
     * @param initialRecord holds the record to be deleted
     * @param editedRecord holds the record to replace the deleted record
     */
    public void updateRecordInDateMap(Record initialRecord, Record editedRecord) {
        removeRecordFromDateMap(initialRecord);
        addRecordToDateMap(editedRecord);
    }

    /**
     * Replaces the HashMap in the DateMap with a new HashMap created from limits
     * @param limitMap is the HashMap to replace the old HashMap
     */
    public void setDateMap(HashMap<String, Integer> limitMap) {
        this.dateMap = limitMap;
    }

    /**
     * Adds the dates of a limit object to the DateMap
     * @param limit which dates are to be added to the DateMap
     */
    public void addLimitToDateMap(Limit limit) {
        String date = limit.getDateStart().getStandardValue();
        if (dateMap.containsKey(date)) {
            dateMap.replace(date, dateMap.get(date) + 1);
        } else {
            dateMap.put(date, 1);
        }
        date = limit.getDateEnd().getStandardValue();
        if (dateMap.containsKey(date)) {
            dateMap.replace(date, dateMap.get(date) + 1);
        } else {
            dateMap.put(date, 1);
        }
    }

    /**
     * Removes the dates of a limit object from the DateMap
     * @param target which dates are to be removed from the DateMap
     */
    public void removeLimitFromDateMap(Limit target) {
        String date = target.getDateStart().getStandardValue();
        if (dateMap.containsKey(date)) {
            dateMap.replace(date, dateMap.get(date) - 1);
            if (dateMap.get(date) == 0) {
                dateMap.remove(date);
            }
        }
        date = target.getDateEnd().getStandardValue();
        if (dateMap.containsKey(date)) {
            dateMap.replace(date, dateMap.get(date) - 1);
            if (dateMap.get(date) == 0) {
                dateMap.remove(date);
            }
        }
    }

    /**
     * Updates a limit object in the DateMap by removing its previous date entries and entering
     * new limit object dates in the DateMap
     * @param target initial limit object whose dates are to be removed
     * @param editedLimit final limit object whose dates are to be added
     */
    public void updateLimitInDateMap(Limit target, Limit editedLimit) {
        removeLimitFromDateMap(target);
        addLimitToDateMap(editedLimit);
    }

    public HashMap<String, Integer> getAsReadOnlyDateMap() {
        return this.dateMap;
    }

    @Override
    public String toString() {
        return dateMap.size() + " Key Values in Map";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateMap // instanceof handles nulls
                && this.getAsReadOnlyDateMap().equals(((DateMap) other).getAsReadOnlyDateMap()));
    }
}
