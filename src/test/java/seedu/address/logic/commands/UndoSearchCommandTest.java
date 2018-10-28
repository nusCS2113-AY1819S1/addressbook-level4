package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAccounts.getTypicalLoginBook;
import static seedu.address.testutil.TypicalClubBudgetElements.getTypicalClubBudgetElementsBook;
import static seedu.address.testutil.TypicalFinalClubBudget.getTypicalFinalBudgetsBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UndoSearchCommandTest {
    private final Model model = new ModelManager(getTypicalLoginBook(), getTypicalAddressBook(),
            getTypicalClubBudgetElementsBook(), getTypicalFinalBudgetsBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalLoginBook(),
            getTypicalAddressBook(), getTypicalClubBudgetElementsBook(), getTypicalFinalBudgetsBook(),
            new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();


    @Test
    public void execute_emptySearchHistory_showFailureMessage() {
        assertCommandSuccess(new UndoSearchCommand(), model,
                commandHistory, UndoSearchCommand.MESSAGE_FAILURE, expectedModel);
    }

    @Test
    public void execute_nonEmptySearchHistory_showSuccessMessage() {
        prepareSearchedModel();
        assertCommandSuccess(new UndoSearchCommand(), model,
                commandHistory, UndoSearchCommand.MESSAGE_SUCCESS, expectedModel);
    }

    private void prepareSearchedModel() {
        model.executeSearch(person -> true);
    }
}
