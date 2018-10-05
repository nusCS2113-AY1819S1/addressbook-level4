package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.joboffer.JobOffer;
import seedu.address.model.joboffer.UniqueJobList;



/**
 * Wraps all data at the JobBook level
 * Duplicates are not allowed (by .isSameJobOffer comparison)
 */

public class JobBook implements ReadOnlyJobBook {
    private final UniqueJobList jobOffers;

    {
        jobOffers = new UniqueJobList();
    }

    public JobBook() {}

    /**
     * Creates a JobBook using the job offers in the {@code toBeCopied}
     */
    public JobBook(ReadOnlyJobBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the job list with {@code jobOffers}.
     * {@code jobOffers} must not contain duplicate job offers.
     */
    public void setJobOffers(List<JobOffer> jobOffers) {
        this.jobOffers.setJobOffers(jobOffers);
    }

    /**
     * Resets the existing data of this {@code JobBook} with {@code newData}.
     */
    public void resetData(ReadOnlyJobBook newData) {
        requireNonNull(newData);

        setJobOffers(newData.getJobOfferList());
    }

    //// job offer-level operations

    /**
     * Returns true if a job offer with the same identity as {@code jobOffer} exists in the address book.
     */
    public boolean hasJobOffer(JobOffer jobOffer) {
        requireNonNull(jobOffer);
        return jobOffers.contains(jobOffer);
    }

    /**
     * Adds a job offer to the address book.
     * The job offer must not already exist in the address book.
     */
    public void addJobOffer(JobOffer p) {
        jobOffers.add(p);
    }

    /**
     * Replaces the given job offer {@code target} in the list with {@code editedJobOffer}.
     * {@code target} must exist in the address book.
     * The job offer identity of {@code editedJobOffer} must not be the same as another existing job offer in the
     * address book.
     */
    public void updateJobOffer(JobOffer target, JobOffer editedJobOffer) {
        requireNonNull(editedJobOffer);

        jobOffers.setJobOffer(target, editedJobOffer);
    }

    /**
     * Removes {@code key} from this {@code JobBook}.
     * {@code key} must exist in the address book.
     */
    public void removeJobOffer(JobOffer key) {
        jobOffers.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return jobOffers.asUnmodifiableObservableList().size() + " jobOffers";
        // TODO: refine later
    }

    @Override
    public ObservableList<JobOffer> getJobOfferList() {
        return jobOffers.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobBook // instanceof handles nulls
                && jobOffers.equals(((JobBook) other).jobOffers));
    }

    @Override
    public int hashCode() {
        return jobOffers.hashCode();
    }

}
