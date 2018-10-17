//@@author Lunastryke
package seedu.address.model.drink;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.drink.exceptions.BatchNotFoundException;
import seedu.address.model.drink.exceptions.DuplicateBatchException;
import seedu.address.model.drink.exceptions.EmptyBatchListException;

/**
 * A list of batches of a particular drink that enforces uniqueness between its elements and does not allow nulls.
 * A batch is considered unique by comparing using {@code Batch#isSameBatch(Batch)}. As such, adding and updating of
 * batches uses Batch#isSameBatch(Batch) for equality so as to ensure that the batch being added or updated is
 * unique in terms of identity in the UniqueBatchList. However, the removal of a batch uses Batch#equals(Object) so
 * as to ensure that the batch with exactly the same fields will be removed.
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
     * Removes the equivalent batch from the list.
     * The batch must exist in the list.
     */
    public void remove(Batch toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new BatchNotFoundException();
        }
    }

    /**
     * @return the batch with the oldest import date in the list
     */
    public Batch getOldestBatch() {
        sortBatches(internalList);
        return internalList.get(0);
    }

    /**
     * Sets the quantity for the batch with the oldest date
     * @param value a valid quantity value expressed as an integer
     */
    public void setOldestBatchQuantity(int value) {
        getOldestBatch().setBatchQuantity(value);
    }

    /**
     * Sorts the Batch list by date
     * The batch list must not be empty
     */
    public void sortBatches(ObservableList<Batch> internalList) {
        if (internalList.isEmpty()) {
            throw new EmptyBatchListException();
        }
        internalList.sort((Batch batch1, Batch batch2) -> batch1.getBatchDate().getBatchDate()
                .compareTo(batch2.getBatchDate().getBatchDate()));
    }

    public void setBatches(UniqueBatchList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code batches}.
     * {@code batches} must not contain duplicate batches.
     */
    public void setBatches(List<Batch> batches) {
        requireAllNonNull(batches);
        if (!batchesAreUnique(batches)) {
            throw new DuplicateBatchException();
        }

        internalList.setAll(batches);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Batch> asUnmodifiableObservableList() {
        sortBatches(internalList);
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
