package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.joboffer.JobOffer;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Candidate> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<JobOffer> PREDICATE_SHOW_ALL_JOB_OFFERS = unused -> true;

    // ================================== CandidateBook functions ====================================== //
    /** Clears existing backing model and replaces with the provided new data. */
    void resetCandidateData(ReadOnlyCandidateBook newData);

    /** Returns the CandidateBook */
    ReadOnlyCandidateBook getCandidateBook();

    /**
     * Returns true if a candidate with the same identity as {@code candidate} exists in the address book.
     */
    boolean hasCandidate(Candidate candidate);

    /**
     * Deletes the given candidate.
     * The candidate must exist in the address book.
     */
    void deleteCandidate(Candidate target);

    /**
     * Adds the given candidate.
     * {@code candidate} must not already exist in the address book.
     */
    void addCandidate(Candidate candidate);

    /**
     * Replaces the given candidate {@code target} with {@code editedCandidate}.
     * {@code target} must exist in the CandidateBook.
     * The candidate identity of {@code editedCandidate} must not be the same as another existing candidate in the
     * address book.
     */
    void updateCandidate(Candidate target, Candidate editedCandidate);

    /** Returns an unmodifiable view of the filtered candidate list */
    ObservableList<Candidate> getFilteredCandidateList();

    /**
     * Updates the filter of the filtered candidate list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCandidateList(Predicate<Candidate> predicate);

    /**
     * Returns true if the model has previous CandidateBook states to restore.
     */
    boolean canUndoCandidateBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoCandidateBook();

    /**
     * Restores the model's CandidateBook to its previous state.
     */
    void undoCandidateBook();

    /**
     * Restores the model's Candidatebook to its previously undone state.
     */
    void redoCandidateBook();

    /**
     * Saves the current CandidateBook state for undo/redo.
     */
    void commitCandidateBook();

    // ================================== JobBook functions ===================================== //

    /** Clears existing backing model and replaces with the provided new data. */
    void resetJobOfferData(ReadOnlyJobBook newData);

    /** Returns the JobBook */
    ReadOnlyJobBook getJobBook();

    /**
     * Returns true if a jobOffer with the same identity as {@code jobOffer} exists in the address book.
     */
    boolean hasJobOffer(JobOffer jobOffer);

    /**
     * Deletes the given jobOffer.
     * The jobOffer must exist in the address book.
     */
    void deleteJobOffer(JobOffer target);

    /**
     * Adds the given jobOffer.
     * {@code jobOffer} must not already exist in the address book.
     */
    void addJobOffer(JobOffer jobOffer);

    /**
     * Replaces the given jobOffer {@code target} with {@code editedJobOffer}.
     * {@code target} must exist in the JobBook.
     * The jobOffer identity of {@code editedJobOffer} must not be the same as another existing jobOffer in the
     * address book.
     */
    void updateJobOffer(JobOffer target, JobOffer editedJobOffer);

    /** Returns an unmodifiable view of the filtered jobOffer list */
    ObservableList<JobOffer> getFilteredJobList();

    /**
     * Updates the filter of the filtered jobOffer list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredJobOfferList(Predicate<JobOffer> predicate);

    /**
     * Returns true if the model has previous JobBook states to restore.
     */
    boolean canUndoJobBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoJobBook();

    /**
     * Restores the model's JobBook to its previous state.
     */
    void undoJobBook();

    /**
     * Restores the model's JobBook to its previously undone state.
     */
    void redoJobBook();

    /**
     * Saves the current JobBook state for undo/redo.
     */
    void commitJobBook();
}
