package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.ReadOnlyCandidateBook;
import seedu.recruit.model.candidate.Candidate;
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
        assertEquals(Arrays.asList(validCandidate), modelStub.candidatesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Candidate validCandidate = new PersonBuilder().build();
        AddCandidateCommand addCommand = new AddCandidateCommand(validCandidate);
        CommandTestUtil.ModelStub modelStub = new ModelStubWithPerson(validCandidate);

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
     * A Model stub that contains a single candidate.
     */
    private class ModelStubWithPerson extends CommandTestUtil.ModelStub {
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
    private class ModelStubAcceptingPersonAdded extends CommandTestUtil.ModelStub {
        final ArrayList<Candidate> candidatesAdded = new ArrayList<>();

        @Override
        public boolean hasCandidate(Candidate candidate) {
            requireNonNull(candidate);
            return candidatesAdded.stream().anyMatch(candidate::isSamePerson);
        }

        @Override
        public void addCandidate(Candidate candidate) {
            requireNonNull(candidate);
            candidatesAdded.add(candidate);
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
