package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.record.Record;
import seedu.address.model.tag.Tag;

public interface ReadOnlyTagsBook {

    ObservableList<Record> getRecordList(Tag tag);
}
