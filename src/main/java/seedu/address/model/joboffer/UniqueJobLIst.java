package seedu.address.model.joboffer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;

import static java.util.Objects.requireNonNull;

/**
 * A list of job offers that enforces uniqueness between its elements and does not allow nulls.
 * A job offer is considered unique by comparing using {@code JobOffer#isSameJobOffer(JobOffer)}. As such, adding and
 * updating of job offers uses JobOffer#isSameJobOffer(JobOffer) for equality so as to ensure that the job offer being
 * added or updated is unique in terms of identity in the UniqueJobList. However, the removal of a job offer uses
 * JobOffer#equals(JobOffer) so as to ensure that the job offer with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see JobOffer#isSameJobOffer(JobOffer)
 */

public class UniqueJobLIst implements Iterable<JobOffer> {

    private final ObservableList<JobOffer> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent job offer as the given argument.
     */
    public boolean contains(JobOffer toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameJobOffer);
    }
    /**
     * Adds a job offer to the list.
     * The job offer must not already exist in the list.
     */
    public void add(JobOffer toAdd) {
        requireNonNull(toAdd);
        /*if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }*/
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent candidate from the list.
     * The job offer must exist in the list.
     */
    public void remove(JobOffer toRemove) {
        requireNonNull(toRemove);
        /*if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }*/
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<JobOffer> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<JobOffer> iterator() {
        return internalList.iterator();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
