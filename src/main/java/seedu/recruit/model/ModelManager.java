package seedu.recruit.model;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.recruit.commons.core.ComponentManager;
import seedu.recruit.commons.core.LogsCenter;
import seedu.recruit.commons.events.logic.UserAuthenticatedEvent;
import seedu.recruit.commons.events.model.CandidateBookChangedEvent;
import seedu.recruit.commons.events.model.CompanyBookChangedEvent;
import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.parser.Prefix;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.company.CompanyName;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * Represents the in-memory model of the recruit book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedRecruitBook versionedRecruitBook;
    private final FilteredList<Candidate> filteredCandidates;
    private final FilteredList<Company> filteredCompanies;
    private final FilteredList<JobOffer> filteredJobs;
    private EmailUtil emailUtil;

    /**
     * Initializes a ModelManager with the given candidateBook and userPrefs.
     */
    public ModelManager(ReadOnlyCandidateBook candidateBook, ReadOnlyCompanyBook companyBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(candidateBook, userPrefs);

        logger.fine("Initializing with recruit book: " + candidateBook + companyBook
                + " and user prefs " + userPrefs);

        EmailUtil.setEmailSettings(userPrefs.getEmailSettings());
        versionedRecruitBook = new VersionedRecruitBook(candidateBook, companyBook);
        filteredCandidates = new FilteredList<>(versionedRecruitBook.getCandidateList());
        filteredCompanies = new FilteredList<>(versionedRecruitBook.getCompanyList());
        filteredJobs = new FilteredList<>(versionedRecruitBook.getCompanyJobList());
        emailUtil = new EmailUtil();
        if (userPrefs.getHashedPassword() != null) {
            hideAll();
        }
    }

    public ModelManager() {
        this(new CandidateBook(), new CompanyBook(), new UserPrefs());
    }

    private void hideAll() {
        filteredCompanies.setPredicate(PREDICATE_HIDE_ALL_COMPANIES);
        filteredCandidates.setPredicate(PREDICATE_HIDE_ALL_PERSONS);
        filteredJobs.setPredicate(PREDICATE_HIDE_ALL_JOBOFFERS);
    }

    private void showAll() {
        filteredCompanies.setPredicate(PREDICATE_SHOW_ALL_COMPANIES);
        filteredCandidates.setPredicate(PREDICATE_SHOW_ALL_PERSONS);
        filteredJobs.setPredicate(PREDICATE_SHOW_ALL_JOBOFFERS);
    }

    @Subscribe
    public void handleUserAuthenticatedEvent(UserAuthenticatedEvent event) {
        showAll();
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
        return versionedRecruitBook.equals(other.versionedRecruitBook)
                && filteredCandidates.equals(other.filteredCandidates)
                && versionedRecruitBook.equals(other.versionedRecruitBook)
                && filteredCompanies.equals(other.filteredCompanies);
    }

    // =========== RecruitBook-level functions ============================================================ //

    @Override
    public boolean canUndoRecruitBook() {
        return versionedRecruitBook.canUndo();
    }

    @Override
    public boolean canRedoRecruitBook() {
        return versionedRecruitBook.canRedo();
    }

    @Override
    public void undoRecruitBook() {
        versionedRecruitBook.undo();
        indicateCandidateBookChanged();
        indicateCompanyBookChanged();
    }

    @Override
    public void redoRecruitBook() {
        versionedRecruitBook.redo();
        indicateCandidateBookChanged();
        indicateCompanyBookChanged();
    }

    @Override
    public void commitRecruitBook() {
        versionedRecruitBook.commit();
    }

    // ================================== CandidateBook functions ====================================== //

    @Override
    public void resetCandidateData(ReadOnlyCandidateBook newData) {
        versionedRecruitBook.setCandidates(newData.getCandidateList());
        indicateCandidateBookChanged();
    }

    @Override
    public ReadOnlyCandidateBook getCandidateBook() {
        return versionedRecruitBook.getCandidateBook();
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateCandidateBookChanged() {
        raise(new CandidateBookChangedEvent(versionedRecruitBook.getCandidateBook()));
    }

    @Override
    public boolean hasCandidate(Candidate candidate) {
        requireNonNull(candidate);
        return versionedRecruitBook.hasCandidate(candidate);
    }

    @Override
    public void deleteCandidate(Candidate target) {
        versionedRecruitBook.removeCandidate(target);
        indicateCandidateBookChanged();
    }

    @Override
    public void addCandidate(Candidate candidate) {
        versionedRecruitBook.addCandidate(candidate);
        updateFilteredCandidateList(PREDICATE_SHOW_ALL_PERSONS);
        indicateCandidateBookChanged();
    }

    @Override
    public void updateCandidate(Candidate target, Candidate editedCandidate) {
        requireAllNonNull(target, editedCandidate);
        versionedRecruitBook.cascadeJobListWithEditedCandidate(target, editedCandidate);
        versionedRecruitBook.updateCandidate(target, editedCandidate);
        indicateCandidateBookChanged();
        indicateCompanyBookChanged();
    }

    @Override
    public void sortCandidates(Prefix prefix) {
        versionedRecruitBook.sortCandidates(prefix);
        indicateCandidateBookChanged();
    }

    @Override
    public ObservableList<Candidate> getMasterCandidateList() {
        return versionedRecruitBook.getCandidateList();
    }

    // =========== Filtered Candidate List Accessors =================================================== //

    /**
     * Returns an unmodifiable view of the list of {@code Candidate} backed by the internal list of
     * {@code versionedRecruitBook}
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




    // ================================== CompanyBook functions ======================================== //

    @Override
    public void resetCompanyData(ReadOnlyCompanyBook newData) {
        versionedRecruitBook.setCompanyList(newData.getCompanyList());
        versionedRecruitBook.setCompanyJobList(newData.getCompanyJobList());
        indicateCompanyBookChanged();
    }

    @Override
    public ReadOnlyCompanyBook getCompanyBook() {
        return versionedRecruitBook.getCompanyBook();
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateCompanyBookChanged() {
        raise(new CompanyBookChangedEvent(versionedRecruitBook.getCompanyBook()));
    }

    @Override
    public boolean hasCompany(Company company) {
        requireNonNull(company);
        return versionedRecruitBook.hasCompany(company);
    }

    @Override
    public void deleteCompany(Company target) {
        versionedRecruitBook.removeCompany(target);
        indicateCompanyBookChanged();
    }

    @Override
    public void addCompany(Company company) {
        versionedRecruitBook.addCompany(company);
        updateFilteredCompanyList(PREDICATE_SHOW_ALL_COMPANIES);
        indicateCompanyBookChanged();
    }

    @Override
    public int getCompanyIndexFromName(CompanyName companyName) {
        return versionedRecruitBook.getCompanyIndexFromName(companyName);
    }

    @Override
    public Company getCompanyFromIndex(int index) {
        return versionedRecruitBook.getCompanyFromIndex(index);
    }

    @Override
    public void updateCompany(Company target, Company editedCompany) {
        requireAllNonNull(target, editedCompany);

        versionedRecruitBook.updateCompany(target, editedCompany);
        indicateCompanyBookChanged();
    }

    @Override
    public void sortCompanies(Prefix prefix) {
        versionedRecruitBook.sortCompanies(prefix);
        indicateCompanyBookChanged();
    }

    @Override
    public void cascadeToJobOffers(CompanyName targetName, CompanyName editedName) {
        versionedRecruitBook.cascadeJobListWithEditedCompanyName(targetName, editedName);
        indicateCompanyBookChanged();
    }

    // =========== Filtered Company List Accessors ===================================================== //

    /**
     * Returns an unmodifiable view of the list of {@code Company} backed by the internal companybook list of
     * {@code versionedRecruitBook}
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


    // ================================== Job Offer functions ========================================== //

    @Override
    public void addJobOffer(JobOffer jobOffer) {
        requireAllNonNull(jobOffer);
        versionedRecruitBook.addJobOffer(jobOffer);
        indicateCompanyBookChanged();
    }

    @Override
    public boolean hasJobOffer(JobOffer jobOffer) {
        requireAllNonNull(jobOffer);
        return versionedRecruitBook.hasJobOffer(jobOffer);
    }

    @Override
    public void updateJobOfferInCompanyBook(JobOffer target, JobOffer editedJobOffer) {
        requireAllNonNull(target, editedJobOffer);
        versionedRecruitBook.updateJobOffer(target, editedJobOffer);
    }

    @Override
    public void deleteJobOffer(JobOffer jobOffer) {
        requireNonNull(jobOffer);
        versionedRecruitBook.removeJobOffer(jobOffer);
        indicateCompanyBookChanged();
    }

    @Override
    public void sortJobOffers(Prefix prefix) {
        versionedRecruitBook.sortJobOffers(prefix);
        indicateCompanyBookChanged();
    }

    @Override
    public ObservableList<JobOffer> getMasterJobList() {
        return versionedRecruitBook.getCompanyJobList();
    }

    // =========== Filtered Company Job List Accessors ===================================================== //

    /**
     * Returns an unmodifiable view of the job lists of all companies {@code Company} backed by the internal
     * company job list of {@code versionedRecruitBook}
     */
    @Override
    public ObservableList<JobOffer> getFilteredCompanyJobList() {
        return FXCollections.unmodifiableObservableList(filteredJobs);
    }

    @Override
    public void updateFilteredCompanyJobList(Predicate<JobOffer> predicate) {
        requireNonNull(predicate);
        filteredJobs.setPredicate(predicate);
    }

    // ================================== Shortlist Command functions ====================================== //

    @Override
    public void shortlistCandidateToJobOffer(Candidate candidate, JobOffer jobOffer) {
        jobOffer.shortlistCandidate(candidate);
        indicateCompanyBookChanged();
    }

    @Override
    public void deleteShortlistedCandidateFromJobOffer(Candidate candidate, JobOffer jobOffer) {
        jobOffer.deleteShortlistedCandidate(candidate);
        indicateCompanyBookChanged();
    }

    // ================================== Email Command functions ====================================== //

    public EmailUtil getEmailUtil() {
        return emailUtil;
    }

    public void setEmailUtil(EmailUtil emailUtil) {
        this.emailUtil = emailUtil;
    }

    public void resetEmailUtil() {
        emailUtil = new EmailUtil();
    }
}
