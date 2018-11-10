package seedu.planner.model.autocomplete;

import java.util.HashMap;

import seedu.planner.model.record.Record;
import seedu.planner.model.record.UniqueRecordList;

public class DateMap {

    private HashMap<String, Integer> dateMap = new HashMap<>();

    public DateMap(){
    }

    public int size() {
        return dateMap.size();
    }

    public HashMap<String, Integer> makeDateMapFromRecordList(UniqueRecordList internalList) {
        return dateMap = internalList.makeDateMap();
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
     * Removes a record from the date map.
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

    public void setDateMap(HashMap<String, Integer> dateMap) {
        this.dateMap = dateMap;
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
