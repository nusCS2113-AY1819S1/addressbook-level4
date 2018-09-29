package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.record.exceptions.DuplicateRecordException;

public class ObservableTagList implements Iterable<TagData> {

    private final ObservableList<TagData> internalList = FXCollections.observableArrayList();

    public boolean contains(TagData toCheck) {
        return internalList.stream().anyMatch(toCheck::isSameTag);
    }

    public void add(TagData toAdd) {
        if (contains(toAdd)) {
            throw new DuplicateRecordException();
        }
        internalList.add(toAdd);
    }

    public ObservableList<TagData> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<TagData> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ObservableTagList // instanceof handles nulls
                && internalList.equals(((ObservableTagList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
