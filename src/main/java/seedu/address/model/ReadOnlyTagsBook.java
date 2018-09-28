package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagData;

public interface ReadOnlyTagsBook {

    ObservableList<TagData> getRecordList(Tag tag);
}
