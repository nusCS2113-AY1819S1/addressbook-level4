package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.record.Record;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagData;
import seedu.address.model.tag.TagMap;

public class TagsBook implements ReadOnlyTagsBook {

    private final TagMap tagMap;

    {
        tagMap = new TagMap();
    }

    public void addTags(Record toAdd) {
        TagData tagData = new TagData(toAdd.getName(), toAdd.getDate(), toAdd.getMoneyFlow());
        for (Tag r : toAdd.getTags()){
            convertTags(r, tagData);
        }
    }

    public void convertTags(Tag tag, TagData tagData) {
        tagMap.add(tag, tagData);
    }

    @Override
    public String toString() {
        return tagMap.toString();
        // TODO: refine later
    }

    @Override
    public ObservableList<TagData> getRecordList(Tag tag) {
        return tagMap.asUnmodifiableObservableList(tag);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsBook // instanceof handles nulls
                && tagMap.equals(((TagsBook) other).tagMap));
    }

    @Override
    public int hashCode() { return tagMap.hashCode(); }

}
