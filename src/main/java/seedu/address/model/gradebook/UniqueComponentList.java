package seedu.address.model.gradebook;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of components that enforces uniqueness between its elements and does not allow nulls.
 * A component is considered unique by comparing using {@code GradebookComponent#isSameComponent(GradebookComponent)}.
 * As such, adding and updating of components uses GradebookComponent#isSameComponent(GradebookComponent) for equality
 * so as to ensure that the component being added or updated is unique in terms of identity in the UniqueComponentList.
 * However, the removal of a component uses GradebookComponent#equals(Object) so as to ensure that the component with
 * exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see GradebookComponent#isSameComponent(GradebookComponent)
 */
public class UniqueComponentList implements Iterable<GradebookComponent> {

    private final ObservableList<GradebookComponent> internalList = FXCollections.observableArrayList();

    public ObservableList<GradebookComponent> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<GradebookComponent> iterator() {
        return internalList.iterator();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
