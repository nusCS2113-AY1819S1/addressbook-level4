package seedu.recruit.logic.commands;

import static seedu.recruit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruit.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;

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
        Model model = new ModelManager(getTypicalAddressBook(), new CompanyBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new CompanyBook(), new UserPrefs());
        expectedModel.resetCandidateData(new CandidateBook());
        expectedModel.commitCandidateBook();

        assertCommandSuccess(new ClearCandidateBookCommand(), model, commandHistory,
                ClearCandidateBookCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
