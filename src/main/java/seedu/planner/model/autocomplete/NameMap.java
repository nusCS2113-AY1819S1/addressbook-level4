package seedu.planner.model.autocomplete;

import java.util.HashMap;

import seedu.planner.model.record.Record;

//@author tztzt
/**
 *  This object represents the in memory model of a HashMap containing integers as values which can be retrieved with
 *  Strings as the key. It keeps track of the number of usage of each unique word used in the records' names
 *  in the model.
 *  It supports addition, deletion, updating and finding the size of the Hashmap.
 */
public class NameMap {

    private HashMap<String, Integer> nameMap = new HashMap<>();

    public int size() {
        return nameMap.size();
    }

    /**
     * Adds the name of a record to the name map
     * @param record to be added to the name map
     */
    public void addRecordToNameMap(Record record) {
        String name = record.getName().fullName;
        String[] nameStrings = name.split("\\s+");
        for (String string : nameStrings) {
            if (nameMap.containsKey(string)) {
                nameMap.replace(string, nameMap.get(string) + 1);
            } else {
                nameMap.put(string, 1);
            }
        }
    }

    /**
     * Removes a record's name from the name map
     * @param record to be removed from the name map
     */
    public void removeRecordFromNameMap(Record record) {
        String name = record.getName().fullName;
        String[] nameStrings = name.split("\\s+");
        for (String string : nameStrings) {
            if (nameMap.containsKey(string)) {
                nameMap.replace(string, nameMap.get(string) - 1);
                if (nameMap.get(string) == 0) {
                    nameMap.remove(string);
                }
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
