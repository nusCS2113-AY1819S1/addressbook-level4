package seedu.address.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.record.Record;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagData;
import seedu.address.model.tag.TagList;

public class TagsBook implements ReadOnlyTagsBook {

    private final TagList records;

    {
        records = new TagList();
    }

    public void addTags(Record toAdd) {
        TagData tagData = new TagData(toAdd.getName(), toAdd.getDate(), toAdd.getMoneyFlow());
        for (Tag r : toAdd.getTags()){
            convertTags(r, tagData);
        }
    }

    public void convertTags(Tag tag, TagData tagData) {
        records.add(tag, tagData);
    }

    @Override
    public ObservableList<TagData> getRecordList(Tag tag) {
        return FXCollections.unmodifiableObservableList(records.get(tag));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsBook // instanceof handles nulls
                && records.equals(((TagsBook) other).records));
    }

    @Override
    public int hashCode() { return records.hashCode(); }

}
