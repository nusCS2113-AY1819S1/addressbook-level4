package seedu.address.model.tag;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.model.record.exceptions.DuplicateRecordException;

import java.util.List;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class TagList {

    private final ObservableMap<Tag, ObservableList<TagData>> internalTagList = FXCollections.observableHashMap();

    public boolean contains(Tag tag, TagData toCheck) {
        requireAllNonNull(tag, toCheck);
        List<TagData> TagList = internalTagList.get(tag);
        return TagList.contains(toCheck);
    }

    public void add(Tag tag, TagData toAdd) {
        requireAllNonNull(tag, toAdd);
        List<TagData> TagList = internalTagList.get(tag);
        if (contains(tag, toAdd)) {
            throw new DuplicateRecordException();
        }
        TagList.add(toAdd);
    }

    public ObservableList<TagData> get(Tag tag) {
        return internalTagList.get(tag);
    }

    @Override
    public int hashCode() {
        return internalTagList.hashCode();
    }

}


