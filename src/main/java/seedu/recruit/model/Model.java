package seedu.recruit.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.recruit.commons.util.EmailUtil;
import seedu.recruit.logic.parser.Prefix;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.company.CompanyName;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Candidate> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Company> PREDICATE_SHOW_ALL_COMPANIES = unused -> true;
    Predicate<JobOffer> PREDICATE_SHOW_ALL_JOBOFFERS = unused -> true;

    // ================================== CandidateBook functions ====================================== //
    /** Clears existing backing model and replaces with the provided new data. */
    void resetCandidateData(ReadOnlyCandidateBook newData);

    /** Returns the CandidateBook */
    ReadOnlyCandidateBook getCandidateBook();

    /**
     * Returns true if a candidate with the same identity as {@code candidate} exists in the recruit book.
     */
    boolean hasCandidate(Candidate candidate);

    /**
     * Deletes the given candidate.
     * The candidate must exist in the recruit book.
     */
    void deleteCandidate(Candidate target);

    /**
     * Adds the given candidate.
     * {@code candidate} must not already exist in the recruit book.
     */
    void addCandidate(Candidate candidate);

    /**
     * Replaces the given candidate {@code target} with {@code editedCandidate}.
     * {@code target} must exist in the CandidateBook.
     * The candidate identity of {@code editedCandidate} must not be the same as another existing candidate in the
     * recruit book.
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
     * Returns true if the model has undone recruit book states to restore.
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

    // ================================== CompanyBook functions ===================================== //

    /** Clears existing backing model and replaces with the provided new data. */
    void resetCompanyData(ReadOnlyCompanyBook newData);

    /** Returns the CompanyBook */
    ReadOnlyCompanyBook getCompanyBook();

    /**
     * Returns true if a company with the same identity as {@code company} exists in the CompanyBook.
     */
    boolean hasCompany(Company company);

    /**
     * Deletes the given company.
     * The company must exist in the CompanyBook.
     */
    void deleteCompany(Company target);

    /**
     * Adds the given company.
     * {@code company} must not already exist in the CompanyBook.
     */
    void addCompany(Company company);

    /**
     * Replaces the given company {@code target} with {@code editedCompany}.
     * {@code target} must exist in the CompanyBook.
     * The company identity of {@code editedCompany} must not be the same as another existing company in the
     * CompanyBook.
     */
    void updateCompany(Company target, Company editedCompany);

    /** Returns an unmodifiable view of the filtered company list */
    ObservableList<Company> getFilteredCompanyList();

    /** Returns index of Company using @param companyName
     *  companyName is enforced to be unique in CompanyBook
     */
    int getCompanyIndexFromName(CompanyName companyName);

    /** Returns the Company object based on @param index
     */

    public Company getCompanyFromIndex(int index);

    /**
     * Updates the filter of the filtered company list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCompanyList(Predicate<Company> predicate);

    /**
     * Sorts the candidates in CandidateBook
     */
    void sortCandidates(Prefix prefix);

    /**
     * Returns true if the model has previous CompanyBook states to restore.
     */
    boolean canUndoCompanyBook();

    /**
     * Returns true if the model has undone CompanyBook states to restore.
     */
    boolean canRedoCompanyBook();

    /**
     * Restores the model's CompanyBook to its previous state.
     */
    void undoCompanyBook();

    /**
     * Restores the model's CompanyBook to its previously undone state.
     */
    void redoCompanyBook();

    /**
     * Saves the current CompanyBook state for undo/redo.
     */
    void commitCompanyBook();

    // ================================== Job Offer functions ===================================== //

    /**
     * Adds a given job offer to the specified company name
     * @code companyName has to exist in the CompanyBook
     * @code jobOffer must not already exist inside the job list of companyName
     */
    void addJobOffer(CompanyName companyName, JobOffer jobOffer);

    /** Returns an unmodifiable view of the filtered job lists of all companies */
    ObservableList<JobOffer> getFilteredCompanyJobList();

    /**
     * Updates the filter of the filtered company job list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCompanyJobList(Predicate<JobOffer> predicate);

    EmailUtil getEmailUtil();

    void setEmailUtil(EmailUtil emailUtil);
}
