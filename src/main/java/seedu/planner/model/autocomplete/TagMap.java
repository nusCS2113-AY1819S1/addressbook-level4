package seedu.planner.model.autocomplete;

import java.util.HashMap;

import seedu.planner.model.record.Record;
import seedu.planner.model.tag.Tag;

//@author tztzt
/**
 *  This object represents the in memory model of a HashMap containing integers as values which can be retrieved with
 *  Strings as the key. It keeps track of the number of usage of each unique tag in the model.
 *  It supports addition, deletion, updating and finding the size of the Hashmap.
 */
public class TagMap {

    private HashMap<String, Integer> tagMap = new HashMap<>();

    public int size() {
        return tagMap.size();
    }

    /**
     * Adds the tags of a record to the tag map
     * @param record to be added to the tag map
     */
    public void addRecordToTagMap(Record record) {
        for (Tag tag : record.getTags()) {
            if (tagMap.containsKey(tag.tagName)) {
                tagMap.replace(tag.tagName, tagMap.get(tag.tagName) + 1);
            } else {
                tagMap.put(tag.tagName, 1);
            }
        }
    }

    /**
     * Removes the tags of a record from the tag map
     * @param record to be removed from the tag map
     */
    public void removeRecordFromTagMap(Record record) {
        for (Tag tag : record.getTags()) {
            if (tagMap.containsKey(tag.tagName)) {
                tagMap.replace(tag.tagName, tagMap.get(tag.tagName) - 1);
                if (tagMap.get(tag.tagName) == 0) {
                    tagMap.remove(tag.tagName);
                }
            }
        }
    }

    /**
     * Updates the tags of a target record by removing the previous record's tags
     * and adding the tags of the new record
     * @param initialRecord holds the record to be deleted
     * @param editedRecord holds the record to replace the deleted record
     */
    public void updateRecordInTagMap(Record initialRecord, Record editedRecord) {
        removeRecordFromTagMap(initialRecord);
        addRecordToTagMap(editedRecord);
    }

    public HashMap<String, Integer> getAsReadOnlyTagMap() {
        return this.tagMap;
    }

    @Override
    public String toString() {
        return tagMap.size() + " Key Values in Map";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagMap // instanceof handles nulls
                && this.getAsReadOnlyTagMap().equals(((TagMap) other).getAsReadOnlyTagMap()));
    }
}
