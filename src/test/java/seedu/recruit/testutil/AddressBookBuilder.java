package seedu.recruit.testutil;

import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code CandidateBook ab = new AddressBookBuilder().withCandidate("John", "Doe").buildCandidateBook();}
 *     {@code CompanyBook abc = new AddressBookBuilder().withCompany("BMW", "AUDI").buildCompanyBook();}
 */
public class AddressBookBuilder {

    private CandidateBook candidateBook;
    private CompanyBook companyBook;

    public AddressBookBuilder() {
        candidateBook = new CandidateBook();
        companyBook = new CompanyBook();
    }

    public AddressBookBuilder(CandidateBook candidateBook, CompanyBook companyBook) {
        this.candidateBook = candidateBook;
        this.companyBook = companyBook;
    }

    /**
     * Adds a new {@code Candidate} to the {@code CandidateBook} that we are building.
     */
    public AddressBookBuilder withCandidate(Candidate candidate) {
        candidateBook.addPerson(candidate);
        return this;
    }

    /**
     * Adds a new {@code Company} to the {@code CompanyBook} that we are building.
     */
    public AddressBookBuilder withCompany(Company company) {
        companyBook.addCompany(company);
        return this;
    }

    public CandidateBook buildCandidateBook() {
        return candidateBook;
    }

    public CompanyBook buildCompanyBook() {
        return companyBook;
    }
}
