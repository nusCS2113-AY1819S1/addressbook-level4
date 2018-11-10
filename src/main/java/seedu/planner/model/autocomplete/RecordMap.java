package seedu.planner.model.autocomplete;

import seedu.planner.model.record.Record;
import seedu.planner.model.record.UniqueRecordList;

public class RecordMap {

    private NameMap nameMap = new NameMap();
    private DateMap dateMap = new DateMap();
    private TagMap tagMap = new TagMap();

    public void makeRecordMapFromRecordList(UniqueRecordList internalList) {
        RecordMap recordMap = internalList.makeRecordMap();
        this.nameMap = recordMap.getAsReadOnlyNameMap();
        this.dateMap = recordMap.getAsReadOnlyDateMap();
        this.tagMap = recordMap.getAsReadOnlyTagMap();
    }

    public void addRecordToRecordMap(Record record) {
        nameMap.addRecordToNameMap(record);
        dateMap.addRecordToDateMap(record);
        tagMap.addRecordToTagMap(record);
    }

    public void removeRecordFromRecordMap(Record target) {
        nameMap.removeRecordFromNameMap(target);
        dateMap.removeRecordFromDateMap(target);
        tagMap.removeRecordFromTagMap(target);
    }

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
                && this.getAsReadOnlyNameMap().equals(((NameMap) other).getAsReadOnlyNameMap())
                && this.getAsReadOnlyTagMap().equals(((TagMap) other).getAsReadOnlyTagMap())
                && this.getAsReadOnlyDateMap().equals(((DateMap) other).getAsReadOnlyDateMap()));
    }
}
