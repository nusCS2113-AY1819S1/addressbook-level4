package seedu.planner.model.autocomplete;

import seedu.planner.model.record.Record;
import seedu.planner.model.record.UniqueRecordList;

/**
 *  This object represents the in memory model of the usages of the Strings of names, dates and tags in the
 *  internal list of records in the form of 3 separate maps.
 */
public class RecordMap {

    private NameMap nameMap = new NameMap();
    private DateMap dateMap = new DateMap();
    private TagMap tagMap = new TagMap();

    /**
     * Converts the list of records in a given list to separate maps to track usage of names, dates and tags.
     * @param internalList is the list of records
     */
    public void makeRecordMapFromRecordList(UniqueRecordList internalList) {
        RecordMap recordMap = internalList.makeRecordMap();
        this.nameMap = recordMap.getAsReadOnlyNameMap();
        this.dateMap = recordMap.getAsReadOnlyDateMap();
        this.tagMap = recordMap.getAsReadOnlyTagMap();
    }

    /**
     * Adds a record into each of the maps in the Record Map.
     * @param record to be added into the Record Map.
     */
    public void addRecordToRecordMap(Record record) {
        nameMap.addRecordToNameMap(record);
        dateMap.addRecordToDateMap(record);
        tagMap.addRecordToTagMap(record);
    }

    /**
     * Deletes a record's details from each of the maps in the Record Map.
     * @param target to be deleted from the Record Map.
     */
    public void removeRecordFromRecordMap(Record target) {
        nameMap.removeRecordFromNameMap(target);
        dateMap.removeRecordFromDateMap(target);
        tagMap.removeRecordFromTagMap(target);
    }

    /**
     * Updates a record's details in each of the maps in the Record Map by deleting the initial data and then
     * inserting the new data.
     * @param target initial record to be deleted from the Record Map.
     * @param editedRecord the record in the Record Map after it has been altered
     */
    public void updateRecordInRecordMap(Record target, Record editedRecord) {
        nameMap.updateRecordInNameMap(target, editedRecord);
        dateMap.updateRecordInDateMap(target, editedRecord);
        tagMap.updateRecordInTagMap(target, editedRecord);
    }

    public void setRecordMap(RecordMap recordMap) {
        this.nameMap = recordMap.getAsReadOnlyNameMap();
        this.dateMap = recordMap.getAsReadOnlyDateMap();
        this.tagMap = recordMap.getAsReadOnlyTagMap();
    }

    public NameMap getAsReadOnlyNameMap() {
        return this.nameMap;
    }

    public DateMap getAsReadOnlyDateMap() {
        return this.dateMap;
    }

    public TagMap getAsReadOnlyTagMap() {
        return this.tagMap;
    }

    @Override
    public String toString() {
        return tagMap.size() + " Tags, " + dateMap.size() + " Dates, " + nameMap.size() + " Names";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecordMap // instanceof handles nulls
                && this.getAsReadOnlyNameMap().equals(((RecordMap) other).getAsReadOnlyNameMap())
                && this.getAsReadOnlyTagMap().equals(((RecordMap) other).getAsReadOnlyTagMap())
                && this.getAsReadOnlyDateMap().equals(((RecordMap) other).getAsReadOnlyDateMap()));
    }
}
