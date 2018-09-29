package seedu.address.model.tag;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.model.record.exceptions.DuplicateRecordException;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class TagMap {

    private final ObservableMap<Tag, ObservableList<TagData>> internalMap = FXCollections.observableHashMap();

    public boolean contains(Tag tag, TagData toCheck) {
        requireAllNonNull(tag, toCheck);
        ObservableList<TagData> observableList = internalMap.get(tag);
        if (observableList != null)
            return internalMap.get(tag).stream().anyMatch(toCheck::isSameTag); // FIXME: CRASHES HERE NOW
        else return false;

    }

    public void add(Tag tag, TagData toAdd) {
        requireAllNonNull(tag, toAdd);
        if (contains(tag, toAdd)) {
            throw new DuplicateRecordException();
        }

        ObservableList<TagData> observableList = internalMap.get(tag);

        if (!internalMap.containsKey(tag) || observableList.size() == 0) {
            ObservableList<TagData> newList = FXCollections.observableArrayList();
            newList.add(toAdd);
            internalMap.put(tag, newList);
        }

        else {
            observableList.add(toAdd);
        }

    }

    public ObservableList<TagData> asUnmodifiableObservableList(Tag tag) {
        return FXCollections.unmodifiableObservableList(internalMap.get(tag));
    }

    @Override
    public String toString() {
        return internalMap.size() + " records";
        // TODO: refine later
    }

    @Override
    public int hashCode() {
        return internalMap.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagMap // instanceof handles nulls
                && internalMap.equals(((TagMap) other).internalMap));
    }

}


