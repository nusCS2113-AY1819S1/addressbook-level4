package seedu.address.model.distributor;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.distributor.exceptions.DistributorNotFoundException;
import seedu.address.model.distributor.exceptions.DuplicateDistributorException;

/**
 * A list of distributors that enforces uniqueness between its elements and does not allow nulls.
 * A distributor is considered unique by comparing using {@code Distributor#isSameDistributor(Distributor)}.
 * As such, adding and updating of distributors uses Distributor#isSameDistributor(Distributor)
 * for equality so as to ensure that the distributor being added or updated is unique in terms of identity in the
 * UniqueDistributorList. However, the removal of a distributor uses Distributor#equals(Object) so
 * as to ensure that the distributor with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Distributor#isSameDistributor(Distributor)
 */
public class UniqueDistributorList implements Iterable<Distributor> {

    private final ObservableList<Distributor> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent distributor as the given argument.
     */
    public boolean contains(Distributor toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDistributor);
    }

    /**
     * Adds a distributor to the list.
     * The distributor must not already exist in the list.
     */
    public void add(Distributor toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateDistributorException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the distributor {@code target} in the list with {@code editedDistributor}.
     * {@code target} must exist in the list.
     * The distributor identity of {@code editedDistributor} must not be the same as
     * another existing distributor in the list.
     */
    public void setDistributor(Distributor target, Distributor editedDistributor) {
        requireAllNonNull(target, editedDistributor);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DistributorNotFoundException();
        }

        if (!target.isSameDistributor(editedDistributor) && contains(editedDistributor)) {
            throw new DuplicateDistributorException();
        }

        internalList.set(index, editedDistributor);
    }

    /**
     * Removes the equivalent distributor from the list.
     * The distributor must exist in the list.
     */
    public void remove(Distributor toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DistributorNotFoundException();
        }
    }

    public void setDistributors(seedu.address.model.distributor.UniqueDistributorList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code distributors}.
     * {@code distributors} must not contain duplicate distributors.
     */
    public void setDistributors(List<Distributor> distributors) {
        requireAllNonNull(distributors);
        if (!distributorsAreUnique(distributors)) {
            throw new DuplicateDistributorException();
        }

        internalList.setAll(distributors);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Distributor> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Distributor> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.distributor.UniqueDistributorList // instanceof handles nulls
                && internalList.equals(((seedu.address.model.distributor.UniqueDistributorList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code distributors} contains only unique distributors.
     */
    private boolean distributorsAreUnique(List<Distributor> distributors) {
        for (int i = 0; i < distributors.size() - 1; i++) {
            for (int j = i + 1; j < distributors.size(); j++) {
                if (distributors.get(i).isSameDistributor(distributors.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

