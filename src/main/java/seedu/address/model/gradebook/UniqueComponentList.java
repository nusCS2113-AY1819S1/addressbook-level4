package seedu.address.model.gradebook;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.gradebook.exceptions.DuplicateComponentException;

/**
 * A list of components that enforces uniqueness between its elements and does not allow nulls.
 * A component is considered unique by comparing using {@code Component#isSameComponent(Component)}. As such, adding and
 * updating of components uses Component#isSameComponent(Component) for equality so as to ensure that the component
 * being added or updated is unique in terms of identity in the UniqueComponentList. However, the removal of a component
 * uses Component#equals(Object) so as to ensure that the component with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Component#isSameComponent(Component)
 */
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

    /**
     * Adds a component to the list.
     * The component must not already exist in the list.
     */
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

    /**
     * Returns true if {@code components} contains only unique components.
     */
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
