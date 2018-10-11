package seedu.recruit.logic.commands;

import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCandidateCommand}.
 */
public class AddCandidateCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new CompanyBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Candidate validCandidate = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getCandidateBook(), new CompanyBook(), new UserPrefs());
        expectedModel.addCandidate(validCandidate);
        expectedModel.commitCandidateBook();

        assertCommandSuccess(new AddCandidateCommand(validCandidate), model, commandHistory,
                String.format(AddCandidateCommand.MESSAGE_SUCCESS, validCandidate), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Candidate candidateInList = model.getCandidateBook().getCandidatelist().get(0);
        assertCommandFailure(new AddCandidateCommand(candidateInList), model, commandHistory,
                AddCandidateCommand.MESSAGE_DUPLICATE_PERSON);
    }

}