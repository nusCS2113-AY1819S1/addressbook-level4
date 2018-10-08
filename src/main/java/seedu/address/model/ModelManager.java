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
import seedu.address.commons.events.model.CompanyBookChangedEvent;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.company.Company;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedCandidateBook versionedCandidateBook;
    private final VersionedCompanyBook versionedJobBook;
    private final FilteredList<Candidate> filteredCandidates;
    private final FilteredList<Company> filteredCompanies;

    /**
     * Initializes a ModelManager with the given candidateBook and userPrefs.
     */
    public ModelManager(ReadOnlyCandidateBook candidateBook, ReadOnlyCompanyBook jobBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(candidateBook, userPrefs);

        logger.fine("Initializing with address book: " + candidateBook + " and user prefs " + userPrefs);

        versionedCandidateBook = new VersionedCandidateBook(candidateBook);
        versionedJobBook = new VersionedCompanyBook(jobBook);
        filteredCandidates = new FilteredList<>(versionedCandidateBook.getCandidatelist());
        filteredCompanies = new FilteredList<>(versionedJobBook.getCompanyList());
    }

    public ModelManager() {
        this(new CandidateBook(), new CompanyBook(), new UserPrefs());
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
                && filteredCompanies.equals(other.filteredCompanies);
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


    // ================================== CompanyBook functions ===================================== //

    @Override
    public void resetCompanyData(ReadOnlyCompanyBook newData) {
        versionedJobBook.resetData(newData);
        indicateCompanyBookChanged();
    }

    @Override
    public ReadOnlyCompanyBook getCompanyBook() {
        return versionedJobBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateCompanyBookChanged() {
        raise(new CompanyBookChangedEvent(versionedJobBook));
    }

    @Override
    public boolean hasCompany(Company jobOffer) {
        requireNonNull(jobOffer);
        return versionedJobBook.hasCompany(jobOffer);
    }

    @Override
    public void deleteCompany(Company target) {
        versionedJobBook.removeCompany(target);
        indicateCompanyBookChanged();
    }

    @Override
    public void addCompany(Company jobOffer) {
        versionedJobBook.addCompany(jobOffer);
        updateFilteredCompanyList(PREDICATE_SHOW_ALL_COMPANIES);
        indicateCompanyBookChanged();
    }

    @Override
    public void updateCompany(Company target, Company editedCompany) {
        requireAllNonNull(target, editedCompany);

        versionedJobBook.updateCompany(target, editedCompany);
        indicateCompanyBookChanged();
    }

    //=========== Filtered Company List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Company} backed by the internal list of
     * {@code versionedJobBook}
     */
    @Override
    public ObservableList<Company> getFilteredCompanyList() {
        return FXCollections.unmodifiableObservableList(filteredCompanies);
    }

    @Override
    public void updateFilteredCompanyList(Predicate<Company> predicate) {
        requireNonNull(predicate);
        filteredCompanies.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoCompanyBook() {
        return versionedJobBook.canUndo();
    }

    @Override
    public boolean canRedoCompanyBook() {
        return versionedJobBook.canRedo();
    }

    @Override
    public void undoCompanyBook() {
        versionedJobBook.undo();
        indicateCompanyBookChanged();
    }

    @Override
    public void redoCompanyBook() {
        versionedJobBook.redo();
        indicateCompanyBookChanged();
    }

    @Override
    public void commitCompanyBook() {
        versionedJobBook.commit();
    }

}
