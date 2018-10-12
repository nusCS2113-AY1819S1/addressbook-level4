package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ReadOnlyCandidateBook;
import seedu.recruit.model.ReadOnlyCompanyBook;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.company.CompanyName;
import seedu.recruit.model.joboffer.JobOffer;
import seedu.recruit.testutil.PersonBuilder;

public class AddCandidateCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCandidateCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Candidate validCandidate = new PersonBuilder().build();

        CommandResult commandResult = new AddCandidateCommand(validCandidate).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCandidateCommand.MESSAGE_SUCCESS, validCandidate), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validCandidate), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Candidate validCandidate = new PersonBuilder().build();
        AddCandidateCommand addCommand = new AddCandidateCommand(validCandidate);
        ModelStub modelStub = new ModelStubWithPerson(validCandidate);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCandidateCommand.MESSAGE_DUPLICATE_PERSON);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Candidate alice = new PersonBuilder().withName("Alice").build();
        Candidate bob = new PersonBuilder().withName("Bob").build();
        AddCandidateCommand addAliceCommand = new AddCandidateCommand(alice);
        AddCandidateCommand addBobCommand = new AddCandidateCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCandidateCommand addAliceCommandCopy = new AddCandidateCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different candidate -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addCandidate(Candidate candidate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetCandidateData(ReadOnlyCandidateBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyCandidateBook getCandidateBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCandidate(Candidate candidate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCandidate(Candidate target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateCandidate(Candidate target, Candidate editedCandidate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortByName() {
            throw new AssertionError( "This method should not be called.");
        }

        @Override
        public ObservableList<Candidate> getFilteredCandidateList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCandidateList(Predicate<Candidate> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoCandidateBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoCandidateBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoCandidateBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoCandidateBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitCandidateBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetCompanyData(ReadOnlyCompanyBook newData) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public ReadOnlyCompanyBook getCompanyBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCompany(Company jobOffer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCompany(Company target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCompany(Company jobOffer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateCompany(Company target, Company editedJobOffer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getCompanyIndexFromName(CompanyName companyName) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public Company getCompanyFromIndex(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Company> getFilteredCompanyList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCompanyList(Predicate<Company> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<JobOffer> getFilteredCompanyJobList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCompanyJobList(Predicate<JobOffer> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoCompanyBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoCompanyBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoCompanyBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoCompanyBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitCompanyBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addJobOffer(CompanyName companyName, JobOffer jobOffer) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single candidate.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Candidate candidate;

        ModelStubWithPerson(Candidate candidate) {
            requireNonNull(candidate);
            this.candidate = candidate;
        }

        @Override
        public boolean hasCandidate(Candidate candidate) {
            requireNonNull(candidate);
            return this.candidate.isSamePerson(candidate);
        }
    }

    /**
     * A Model stub that always accept the candidate being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Candidate> personsAdded = new ArrayList<>();

        @Override
        public boolean hasCandidate(Candidate candidate) {
            requireNonNull(candidate);
            return personsAdded.stream().anyMatch(candidate::isSamePerson);
        }

        @Override
        public void addCandidate(Candidate candidate) {
            requireNonNull(candidate);
            personsAdded.add(candidate);
        }

        @Override
        public void commitCandidateBook() {
            // called by {@code AddCandidateCommand#execute()}
        }

        @Override
        public ReadOnlyCandidateBook getCandidateBook() {
            return new CandidateBook();
        }
    }


}
