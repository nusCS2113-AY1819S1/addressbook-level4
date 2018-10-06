package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.CandidateBookChangedEvent;
import seedu.address.commons.events.model.JobBookChangedEvent;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.joboffer.JobOffer;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedCandidateBook versionedCandidateBook;
    private final VersionedJobBook versionedJobBook;
    private final FilteredList<Candidate> filteredCandidates;
    private final FilteredList<JobOffer> filteredJobOffers;

    /**
     * Initializes a ModelManager with the given candidateBook and userPrefs.
     */
    public ModelManager(ReadOnlyCandidateBook candidateBook, ReadOnlyJobBook jobBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(candidateBook, userPrefs);

        logger.fine("Initializing with address book: " + candidateBook + " and user prefs " + userPrefs);

        versionedCandidateBook = new VersionedCandidateBook(candidateBook);
        versionedJobBook = new VersionedJobBook(jobBook);
        filteredCandidates = new FilteredList<>(versionedCandidateBook.getCandidatelist());
        filteredJobOffers = new FilteredList<>(versionedJobBook.getJobOfferList());
    }

    public ModelManager() {
        this(new CandidateBook(), new JobBook(), new UserPrefs());
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedCandidateBook.equals(other.versionedCandidateBook)
                && filteredCandidates.equals(other.filteredCandidates)
                && versionedJobBook.equals(other.versionedJobBook)
                && filteredJobOffers.equals(other.filteredJobOffers);
    }

    // ================================== CandidateBook functions ====================================== //

    @Override
    public void resetCandidateData(ReadOnlyCandidateBook newData) {
        versionedCandidateBook.resetData(newData);
        indicateCandidateBookChanged();
    }

    @Override
    public ReadOnlyCandidateBook getCandidateBook() {
        return versionedCandidateBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateCandidateBookChanged() {
        raise(new CandidateBookChangedEvent(versionedCandidateBook));
    }

    @Override
    public boolean hasCandidate(Candidate candidate) {
        requireNonNull(candidate);
        return versionedCandidateBook.hasPerson(candidate);
    }

    @Override
    public void deleteCandidate(Candidate target) {
        versionedCandidateBook.removePerson(target);
        indicateCandidateBookChanged();
    }

    @Override
    public void addCandidate(Candidate candidate) {
        versionedCandidateBook.addPerson(candidate);
        updateFilteredCandidateList(PREDICATE_SHOW_ALL_PERSONS);
        indicateCandidateBookChanged();
    }

    @Override
    public void updateCandidate(Candidate target, Candidate editedCandidate) {
        requireAllNonNull(target, editedCandidate);

        versionedCandidateBook.updatePerson(target, editedCandidate);
        indicateCandidateBookChanged();
    }

    //=========== Filtered Candidate List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Candidate} backed by the internal list of
     * {@code versionedCandidateBook}
     */
    @Override
    public ObservableList<Candidate> getFilteredCandidateList() {
        return FXCollections.unmodifiableObservableList(filteredCandidates);
    }

    @Override
    public void updateFilteredCandidateList(Predicate<Candidate> predicate) {
        requireNonNull(predicate);
        filteredCandidates.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoCandidateBook() {
        return versionedCandidateBook.canUndo();
    }

    @Override
    public boolean canRedoCandidateBook() {
        return versionedCandidateBook.canRedo();
    }

    @Override
    public void undoCandidateBook() {
        versionedCandidateBook.undo();
        indicateCandidateBookChanged();
    }

    @Override
    public void redoCandidateBook() {
        versionedCandidateBook.redo();
        indicateCandidateBookChanged();
    }

    @Override
    public void commitCandidateBook() {
        versionedCandidateBook.commit();
    }


    // ================================== JobBook functions ===================================== //

    @Override
    public void resetJobOfferData(ReadOnlyJobBook newData) {
        versionedJobBook.resetData(newData);
        indicateJobBookChanged();
    }

    @Override
    public ReadOnlyJobBook getJobBook() {
        return versionedJobBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateJobBookChanged() {
        raise(new JobBookChangedEvent(versionedJobBook));
    }

    @Override
    public boolean hasJobOffer(JobOffer jobOffer) {
        requireNonNull(jobOffer);
        return versionedJobBook.hasJobOffer(jobOffer);
    }

    @Override
    public void deleteJobOffer(JobOffer target) {
        versionedJobBook.removeJobOffer(target);
        indicateJobBookChanged();
    }

    @Override
    public void addJobOffer(JobOffer jobOffer) {
        versionedJobBook.addJobOffer(jobOffer);
        updateFilteredJobOfferList(PREDICATE_SHOW_ALL_JOB_OFFERS);
        indicateJobBookChanged();
    }

    @Override
    public void updateJobOffer(JobOffer target, JobOffer editedJobOffer) {
        requireAllNonNull(target, editedJobOffer);

        versionedJobBook.updateJobOffer(target, editedJobOffer);
        indicateJobBookChanged();
    }

    //=========== Filtered JobOffer List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code JobOffer} backed by the internal list of
     * {@code versionedJobBook}
     */
    @Override
    public ObservableList<JobOffer> getFilteredJobList() {
        return FXCollections.unmodifiableObservableList(filteredJobOffers);
    }

    @Override
    public void updateFilteredJobOfferList(Predicate<JobOffer> predicate) {
        requireNonNull(predicate);
        filteredJobOffers.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoJobBook() {
        return versionedJobBook.canUndo();
    }

    @Override
    public boolean canRedoJobBook() {
        return versionedJobBook.canRedo();
    }

    @Override
    public void undoJobBook() {
        versionedJobBook.undo();
        indicateJobBookChanged();
    }

    @Override
    public void redoJobBook() {
        versionedJobBook.redo();
        indicateJobBookChanged();
    }

    @Override
    public void commitJobBook() {
        versionedJobBook.commit();
    }

}
