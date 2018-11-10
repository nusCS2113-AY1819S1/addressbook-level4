package seedu.planner.model.autocomplete;

import java.util.HashMap;

import seedu.planner.model.record.Name;
import seedu.planner.model.record.Record;
import seedu.planner.model.record.UniqueRecordList;

public class NameMap {

    private HashMap<String, Integer> nameMap = new HashMap<>();

    public NameMap(){
    }

    public int size() {
        return nameMap.size();
    }

    public HashMap<String, Integer> makeNameMapFromRecordList(UniqueRecordList internalList) {
        return nameMap = internalList.makeNameMap();
    }

    /**
     * Adds the name of a record to the name map
     * @param record to be added to the name map
     */
    public void addRecordToNameMap(Record record) {
        String name = record.getName().fullName;
        if (nameMap.containsKey(name)) {
            nameMap.replace(name, nameMap.get(name) + 1);
        } else {
            nameMap.put(name, 1);
        }
    }

    /**
     * Removes a record's name from the name map
     * @param record to be removed from the name map
     */
    public void removeRecordFromNameMap(Record record) {
        String name = record.getName().fullName;
        if (nameMap.containsKey(name)) {
            nameMap.replace(name, nameMap.get(name) - 1);
            if (nameMap.get(name) == 0) {
                nameMap.remove(name);
            }
        }
    }

    /**
     * Updates the name of a target record by removing the previous record's name
     * and adding the name of the new record
     * @param initialRecord holds the record to be deleted
     * @param editedRecord holds the record to replace the deleted record
     */
    public void updateRecordInNameMap(Record initialRecord, Record editedRecord) {
        removeRecordFromNameMap(initialRecord);
        addRecordToNameMap(editedRecord);
    }

    public void setNameMap(HashMap<String, Integer> nameMap) {
        this.nameMap = nameMap;
    }

    public HashMap<String, Integer> getAsReadOnlyNameMap() {
        return this.nameMap;
    }

    @Override
    public String toString() {
        return nameMap.size() + " Key Values in Map";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameMap // instanceof handles nulls
                && this.getAsReadOnlyNameMap().equals(((NameMap) other).getAsReadOnlyNameMap()));
    }
}
