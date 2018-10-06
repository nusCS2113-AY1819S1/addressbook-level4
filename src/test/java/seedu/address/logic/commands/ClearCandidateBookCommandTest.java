package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.CandidateBook;
import seedu.address.model.JobBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCandidateBookCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitCandidateBook();

        assertCommandSuccess(new ClearCandidateBookCommand(), model, commandHistory,
                ClearCandidateBookCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new JobBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new JobBook(), new UserPrefs());
        expectedModel.resetCandidateData(new CandidateBook());
        expectedModel.commitCandidateBook();

        assertCommandSuccess(new ClearCandidateBookCommand(), model, commandHistory,
                ClearCandidateBookCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
