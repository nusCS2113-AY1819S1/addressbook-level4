package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.record.Record;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagMap;

public class TagsBook implements ReadOnlyTagsBook {

    private final TagMap tagMap;

    {
        tagMap = new TagMap();
    }

    public TagsBook() {}

    public void addTags(Record toAdd) {
        for (Tag r : toAdd.getTags()){
            addTags(r, toAdd);
        }
    }

    public void addTags(Tag tag, Record tagData) {
        tagMap.add(tag, tagData);
    }

    @Override
    public String toString() {
        return tagMap.toString();
        // TODO: refine later
    }

    @Override
    public ObservableList<Record> getRecordList(Tag tag) {
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
