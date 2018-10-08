package seedu.recruit.model.joboffer;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.recruit.model.joboffer.exceptions.DuplicateJobException;
import seedu.recruit.model.joboffer.exceptions.JobNotFoundException;


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

public class UniqueJobList implements Iterable<JobOffer> {

    private final ObservableList<JobOffer> internalList = FXCollections.observableArrayList();


    public ObservableList<JobOffer> getInternalList() {
        return internalList;
    }

    /**
     * Returns size of list
     */

    public int size() {
        return internalList.size();
    }

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
        if (contains(toAdd)) {
            throw new DuplicateJobException();
        }
        internalList.add(toAdd);
    }


    /**
     * Replaces the job offer {@code target} in the list with {@code editedJobOffer}.
     * {@code target} must exist in the list.
     * The job offer identity of {@code editedJobOffer} must not be the same as another existing job offer in the list.
     */
    public void setJobOffer(JobOffer target, JobOffer editedJobOffer) {
        requireAllNonNull(target, editedJobOffer);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new JobNotFoundException();
        }

        if (!target.isSameJobOffer(editedJobOffer) && contains(editedJobOffer)) {
            throw new DuplicateJobException();
        }

        internalList.set(index, editedJobOffer);
    }

    /**
     * Removes the equivalent job offer from the list.
     * The job offer must exist in the list.
     */
    public void remove(JobOffer toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new JobNotFoundException();
        }
    }

    public void setJobOffers (UniqueJobList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code jobOffers}.
     * {@code jobOffers} must not contain duplicate jobs.
     */
    public void setJobOffers(List<JobOffer> jobOffers) {
        requireAllNonNull(jobOffers);
        if (!jobsAreUnique(jobOffers)) {
            throw new DuplicateJobException();
        }

        internalList.setAll(jobOffers);
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueJobList // instanceof handles nulls
                && internalList.equals(((UniqueJobList) other).internalList));
    }

    /**
     * Returns true if {@code jobOffers} contains only unique jobs.
     */
    private boolean jobsAreUnique(List<JobOffer> jobOffers) {
        for (int i = 0; i < jobOffers.size() - 1; i++) {
            for (int j = i + 1; j < jobOffers.size(); j++) {
                if (jobOffers.get(i).isSameJobOffer(jobOffers.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
