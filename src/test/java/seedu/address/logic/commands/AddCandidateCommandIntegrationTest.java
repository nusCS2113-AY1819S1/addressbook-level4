package seedu.address.logic.commands;

import org.junit.Before;
import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.candidate.Candidate;
import seedu.address.testutil.PersonBuilder;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCandidateCommand}.
 */
public class AddCandidateCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Candidate validCandidate = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validCandidate);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddCandidateCommand(validCandidate), model, commandHistory,
                String.format(AddCandidateCommand.MESSAGE_SUCCESS, validCandidate), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Candidate candidateInList = model.getAddressBook().getCandidatelist().get(0);
        assertCommandFailure(new AddCandidateCommand(candidateInList), model, commandHistory,
                AddCandidateCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
