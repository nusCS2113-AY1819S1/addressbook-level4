package seedu.address.model.tag;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.model.record.exceptions.DuplicateRecordException;

import java.util.List;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class TagMap {

    private final ObservableMap<Tag, ObservableTagList> internalTagList = FXCollections.observableHashMap();

    public boolean contains(Tag tag, TagData toCheck) {
        requireAllNonNull(tag, toCheck);
        return internalTagList.get(tag).contains(toCheck);
    }

    public void add(Tag tag, TagData toAdd) {
        requireAllNonNull(tag, toAdd);
        internalTagList.get(tag).add(toAdd); // FIXME: ALWAYS CRASHES AT THIS LINE
    }

    public ObservableList<TagData> get(Tag tag) {
        return internalTagList.get(tag).asUnmodifiableObservableList();
    }

    @Override
    public int hashCode() {
        return internalTagList.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagMap // instanceof handles nulls
                && internalTagList.equals(((TagMap) other).internalTagList));
    }

}


