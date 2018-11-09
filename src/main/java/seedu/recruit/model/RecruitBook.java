package seedu.recruit.model;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.recruit.logic.parser.Prefix;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.company.CompanyName;
import seedu.recruit.model.company.UniqueCompanyList;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.model.joboffer.UniqueJobList;

/**
 * Wraps all data at the CandidateBook and CompanyBook into one RecruitBook
 */

public class RecruitBook {
    private CandidateBook candidateBook;
    private CompanyBook companyBook;


    /**
     * Creates a RecruitBook using the CandidateBook in  {@code candidateBooktoBeCopied} and
     * CompanyBook in {@code companyBooktoBeCopied}
     */
    public RecruitBook (ReadOnlyCandidateBook candidateBookToBeCopied, ReadOnlyCompanyBook companyBookToBeCopied) {
        candidateBook = new CandidateBook();
        companyBook = new CompanyBook();
        resetData(candidateBookToBeCopied, companyBookToBeCopied);
    }

    /**
     * Resets the existing data of this {@code RecruitBook} with {@code newCandidateData} and {@code newCompanyData}.
     */

    public void resetData (ReadOnlyCandidateBook newCandidateData, ReadOnlyCompanyBook newCompanyData ) {
        candidateBook.resetData(newCandidateData);
        companyBook.resetData(newCompanyData);
    }

    public CandidateBook getCandidateBook() {
        return candidateBook;
    }

    public CompanyBook getCompanyBook() {
        return companyBook;
    }

    // ================================== CandidateBook operations ====================================== //

    public void setCandidates(List<Candidate> candidates) {
        candidateBook.setCandidates(candidates);
    }

    public boolean hasCandidate(Candidate candidate) {
        return candidateBook.hasCandidate(candidate);
    }

    public void addCandidate(Candidate p) {
        candidateBook.addCandidate(p);
    }

    public void updateCandidate(Candidate target, Candidate editedCandidate) {
        candidateBook.updateCandidate(target, editedCandidate);
    }


    public void sortCandidates(Prefix prefix) {
        candidateBook.sortCandidates(prefix);
    }

    public void removeCandidate(Candidate candidate) {
        candidateBook.removeCandidate(candidate);
    }

    public ObservableList<Candidate> getCandidateList() {
        return this.candidateBook.getCandidateList();
    }




    // ================================== CompanyBook operations ====================================== //

    public void setCompanyList(List<Company> companyList) {
        companyBook.setCompanyList(companyList);
    }

    public void setCompanyList(UniqueCompanyList companyList) {
        companyBook.setCompanyList(companyList);
    }

    public boolean hasCompany(Company company) {
        return companyBook.hasCompany(company);
    }

    public void addCompany(Company company) {
        companyBook.addCompany(company);
    }

    public int getCompanyIndexFromName(CompanyName companyName) {
        return companyBook.getCompanyIndexFromName(companyName);
    }

    public Company getCompanyFromIndex(int index) {
        return companyBook.getCompanyFromIndex(index);
    }

    public void updateCompany(Company target, Company editedCompany) {
        companyBook.updateCompany(target, editedCompany);
    }

    public void sortCompanies(Prefix prefix) {
        companyBook.sortCompanies(prefix);
    }

    public void removeCompany(Company key) {
        companyBook.removeCompany(key);
    }

    public void addJobOffer(JobOffer jobOffer) {
        companyBook.addJobOffer(jobOffer);
    }

    public boolean hasJobOffer(JobOffer jobOffer) {
        return companyBook.hasJobOffer(jobOffer);
    }

    public void updateJobOffer(JobOffer target, JobOffer editedJobOffer) {
        companyBook.updateJobOffer(target, editedJobOffer);
    }

    public void setCompanyJobList(List<JobOffer> companyJobList) {
        companyBook.setCompanyJobList(companyJobList);
    }

    public void setCompanyJobList(UniqueJobList companyJobList) {
        companyBook.setCompanyJobList(companyJobList);
    }

    public void cascadeJobListWithEditedCandidate(Candidate target, Candidate editedCandidate) {
        companyBook.cascadeJobListWithEditedCandidate(target, editedCandidate);
    }


    public void cascadeJobListWithEditedCompanyName(CompanyName targetName, CompanyName editedName) {
        companyBook.cascadeJobListWithEditedCompanyName(targetName, editedName);
    }

    public void sortJobOffers(Prefix prefix) {
        companyBook.sortJobOffers(prefix);
    }

    public void removeJobOffer(JobOffer jobOffer) {
        companyBook.removeJobOffer(jobOffer);
    }

    public ObservableList<Company> getCompanyList() {
        return this.companyBook.getCompanyList();
    }

    public ObservableList<JobOffer> getCompanyJobList() {
        return this.companyBook.getCompanyJobList();
    }

    // ================================== Utility operations ====================================== //

    @Override
    public String toString() {
        return companyBook.toString() + candidateBook.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecruitBook // instanceof handles nulls
                && companyBook.equals(((RecruitBook) other).companyBook))
                && candidateBook.equals(((RecruitBook) other).candidateBook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyBook, candidateBook);
    }
}
