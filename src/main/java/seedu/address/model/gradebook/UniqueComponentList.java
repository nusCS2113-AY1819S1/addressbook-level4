package seedu.address.model.gradebook;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Iterator;

import seedu.address.model.gradebook.exceptions.DuplicateComponentException;

public class UniqueComponentList implements Iterable<Component> {

    private final ObservableList<Component> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent component as the given argument.
     */
    public boolean contains(Component toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameComponent);
    }

    public void setComponents(List<Component> components) {
        requireAllNonNull(components);
        if (!componentsAreUnique(components)) {
            throw new DuplicateComponentException();
        }

        internalList.setAll(components);
    }

    public void add(Component toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateComponentException();
        }
        internalList.add(toAdd);
    }

    public ObservableList<Component> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Component> iterator() {
        return internalList.iterator();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    private boolean componentsAreUnique(List<Component> components) {
        for (int i = 0; i < components.size() - 1; i++) {
            for (int j = i + 1; j < components.size(); j++) {
                if (components.get(i).isSameComponent(components.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
