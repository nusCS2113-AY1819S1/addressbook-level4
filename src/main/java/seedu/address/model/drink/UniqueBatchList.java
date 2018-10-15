//@@author Lunastryke
package seedu.address.model.drink;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.drink.exceptions.BatchNotFoundException;
import seedu.address.model.drink.exceptions.DuplicateBatchException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * A list of batches of a particular drink that enforces uniqueness between its elements and does not allow nulls.
 * A batch is considered unique by comparing using {@code Batch#isSameBatch(Batch)}. As such, adding and updating of
 * batches uses Batch#isSameBatch(Batch) for equality so as to ensure that the batch being added or updated is
 * unique in terms of identity in the UniqueBatchList. However, the removal of a batch uses Batch#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Batch#isSameBatch(Batch)
 */
public class UniqueBatchList implements Iterable<Batch> {
    private final ObservableList<Batch> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent batch as the given argument.
     */
    public boolean contains(Batch toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameBatch);
    }

    /**
     * Adds a batch to the list.
     * The batch must not already exist in the list.
     */
    public void add(Batch toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateBatchException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the batch {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setBatch(Batch target, Batch editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new BatchNotFoundException();
        }

        if (!target.isSameBatch(editedPerson) && contains(editedPerson)) {
            throw new DuplicateBatchException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent batch from the list.
     * The batch must exist in the list.
     */
    public void remove(Batch toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new BatchNotFoundException();
        }
    }

    public void setPersons(UniqueBatchList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Batch> persons) {
        requireAllNonNull(persons);
        if (!batchesAreUnique(persons)) {
            throw new DuplicateBatchException();
        }

        internalList.setAll(persons);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Batch> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Batch> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueBatchList // instanceof handles nulls
                && internalList.equals(((UniqueBatchList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique batches.
     */
    private boolean batchesAreUnique(List<Batch> batches) {
        for (int i = 0; i < batches.size() - 1; i++) {
            for (int j = i + 1; j < batches.size(); j++) {
                if (batches.get(i).isSameBatch(batches.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
