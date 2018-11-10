package seedu.recruit.testutil;

import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.RecruitBook;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;

/**
 * A utility class to help with building CandidateBook objects.
 * Example usage: <br>
 *     {@code CandidateBook ab = new RecruitBookBuilder().withCandidate("John", "Doe").buildCandidateBook();}
 *     {@code CompanyBook abc = new RecruitBookBuilder().withCompany("BMW", "AUDI").buildCompanyBook();}
 */
public class RecruitBookBuilder {

    private CandidateBook candidateBook;
    private CompanyBook companyBook;

    public RecruitBookBuilder() {
        candidateBook = new CandidateBook();
        companyBook = new CompanyBook();
    }

    public RecruitBookBuilder(CandidateBook candidateBook, CompanyBook companyBook) {
        this.candidateBook = candidateBook;
        this.companyBook = companyBook;
    }

    /**
     * Adds a new {@code Candidate} to the {@code CandidateBook} that we are building.
     */
    public RecruitBookBuilder withCandidate(Candidate candidate) {
        candidateBook.addCandidate(candidate);
        return this;
    }

    /**
     * Adds a new {@code Company} to the {@code CompanyBook} that we are building.
     */
    public RecruitBookBuilder withCompany(Company company) {
        companyBook.addCompany(company);
        return this;
    }

    public CandidateBook buildCandidateBook() {
        return candidateBook;
    }

    public CompanyBook buildCompanyBook() {
        return companyBook;
    }

    public RecruitBook buildRecruitBook() {
        return new RecruitBook(candidateBook, companyBook);
    }
}
