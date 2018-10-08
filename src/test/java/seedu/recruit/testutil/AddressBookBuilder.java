package seedu.recruit.testutil;

import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.candidate.Candidate;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code CandidateBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private CandidateBook candidateBook;

    public AddressBookBuilder() {
        candidateBook = new CandidateBook();
    }

    public AddressBookBuilder(CandidateBook candidateBook) {
        this.candidateBook = candidateBook;
    }

    /**
     * Adds a new {@code Candidate} to the {@code CandidateBook} that we are building.
     */
    public AddressBookBuilder withPerson(Candidate candidate) {
        candidateBook.addPerson(candidate);
        return this;
    }

    public CandidateBook build() {
        return candidateBook;
    }
}
