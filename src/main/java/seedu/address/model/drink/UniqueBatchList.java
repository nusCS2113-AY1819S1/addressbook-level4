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
import seedu.address.model.drink.exceptions.InsufficientQuantityException;

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
    private Quantity totalQuantity = new Quantity("0");

    public Quantity getTotalQuantity() {
        return totalQuantity;
    }

    // Methods likely to be used by other classes

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
     * If a batch with the same date exists, collates the batches
     */
    public void addBatch(Batch toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateBatchException();
        }
        if (batchSameDate(toAdd)) {
            for (Batch b : internalList) {
                if (b.isSameDate(toAdd)) {
                    increaseBatchQuantity(b, toAdd.getBatchQuantity().getValue());
                }
            }
        } else {
            internalList.add(toAdd);
        }
        updateTotalQuantity();
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
        updateTotalQuantity();
    }

    /**
     * Removes empty batches from the list
     */
    public void clearEmptyBatches() {
        int counter = 0;
        for (Batch b : internalList) {
            if (b.getBatchQuantity().getValue() == 0) {
                counter++;
            }
        }
        if (counter > 0) {
            internalList.remove(0, counter - 1);
        }
    }

    /**
     * Updates the quantities in the batches whenever a transaction is made
     * Decreases quantities in the batches by the value specified
     * Decreases quantity in the batches by the order of date imported, with the oldest batches first
     * @param quantity a valid quantity value expressed as an integer
     */
    public void updateBatchTransaction(Quantity quantity) throws InsufficientQuantityException,
            EmptyBatchListException {
        int toDecrease = quantity.getValue();

        try {
            decreaseTotalQuantity(toDecrease);
        } catch (InsufficientQuantityException e) {
            throw e;
        }
        try {
            sortBatches();
        } catch (EmptyBatchListException e) {
            throw e;
        }
        for (Batch b : internalList) {
            int batchQuantity = b.getBatchQuantity().getValue();
            if (toDecrease == 0) {
                break;
            }
            if (toDecrease >= batchQuantity) {
                setBatchQuantity(b, 0);
            } else {
                decreaseBatchQuantity(b, toDecrease);
            }
            toDecrease -= batchQuantity;
        }
        clearEmptyBatches();
    }

    /**
     * Gets the batch with the oldest date
     * @return a Batch object
     */
    public Batch getEarliestBatch() throws EmptyBatchListException {
        try {
            sortBatches();
        } catch (EmptyBatchListException e) {
            throw e;
        }
        return internalList.get(0);
    }

    /**
     * Gets the batch with the newest date
     * @return a Batch object
     */
    public Batch getLatestBatch() throws EmptyBatchListException {
        try {
            sortBatches();
        } catch (EmptyBatchListException e) {
            throw e;
        }
        return internalList.get(internalList.size() - 1);
    }

    public int getNumberBatches() {
        return internalList.size();
    }

    /**
     * Gets the date of the oldest batch
     * @return a BatchDate object
     */
    public BatchDate getEarliestBatchDate() throws EmptyBatchListException {
        return getEarliestBatch().getBatchDate();
    }

    /**
     * Gets the date of the newest batch
     * @return a BatchDate object
     */
    public BatchDate getLatestBatchDate() throws EmptyBatchListException {
        return getLatestBatch().getBatchDate();
    }

    // Quantity Related Methods

    /**
     * Sets total quantity with the specified amount
     * @param value a valid quantity value expressed as an integer
     */
    public void setTotalQuantity(int value) {
        this.totalQuantity.setValue(value);
    }

    /**
     * Decreases total quantity by value specified
     * @param value a valid quantity value expressed as an integer
     */
    public void decreaseTotalQuantity(int value) throws InsufficientQuantityException {
        if (value > this.totalQuantity.getValue()) {
            throw new InsufficientQuantityException();
        }
        this.totalQuantity.decreaseValue(value);
    }
    /**
     * Increases total quantity by value specified
     * @param value a valid quantity value expressed as an integer
     */
    public void increaseTotalQuantity(int value) {
        this.totalQuantity.increaseValue(value);
    }

    /**
     * Updates the total quantity of stock from all the batches in the list
     */
    public void updateTotalQuantity() {
        setTotalQuantity(0);
        if (!internalList.isEmpty()) {
            for (Batch b : internalList) {
                increaseTotalQuantity(b.getBatchQuantity().getValue());
            }
        }
    }

    /**
     * Sets the quantity for the indicated batch
     * @param batchToEdit a valid batch object
     * @param value a valid quantity value expressed as an integer
     */
    public void setBatchQuantity(Batch batchToEdit, int value) {
        batchToEdit.setBatchQuantity(value);
    }

    public void decreaseBatchQuantity(Batch batchToEdit, int value) {
        batchToEdit.decreaseBatchQuantity(value);
    }

    public void increaseBatchQuantity(Batch batchToEdit, int value) {
        batchToEdit.increaseBatchQuantity(value);
    }

    // Getters and Setters

    /**
     * @param index  a valid index value expressed as an integer
     * @return the batch with the specified index in the list
     */
    public Batch getBatch(int index) {
        try {
            sortBatches();
            return internalList.get(index);
        } catch (EmptyBatchListException e) {
            return null;
        }
    }

    public Batch getBatch(BatchId batchId) {
        for (Batch b : internalList) {
            if (b.getBatchId() == batchId) {
                return b;
            }
        }
        throw new BatchNotFoundException();
    }

    public Batch getBatch(BatchDate date) {
        for (Batch b : internalList) {
            if (b.getBatchDate() == date) {
                return b;
            }
        }
        throw new BatchNotFoundException();
    }

    /**
     * Replaces the batch {@code target} in the list with {@code editedBatch}.
     * {@code target} must exist in the list.
     * The batch identity of {@code editedBatch} must not be the same as another existing batch in the list.
     */
    public void setBatch(Batch target, Batch editedBatch) {
        requireAllNonNull(target, editedBatch);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new BatchNotFoundException();
        }

        if (!target.isSameBatch(editedBatch) && contains(editedBatch)) {
            throw new DuplicateBatchException();
        }

        internalList.set(index, editedBatch);
    }

    public void setBatches(UniqueBatchList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        sortBatches();
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

    // Other supporting functions

    /**
     * Sorts the Batch list by date
     * The batch list must not be empty
     */
    public void sortBatches() throws EmptyBatchListException {
        if (this.internalList.isEmpty()) {
            throw new EmptyBatchListException();
        }
        this.internalList.sort(Comparators.BATCHDATE);
    }

    /**
     * Returns true if another batch with the same date exists else returns false
     */
    public boolean batchSameDate(Batch batchToCheck) {
        for (Batch b : internalList) {
            if (b.isSameDate(batchToCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Batch> asUnmodifiableObservableList() throws EmptyBatchListException {
        try {
            sortBatches();
        } catch (EmptyBatchListException e) {
            throw e;
        }
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
