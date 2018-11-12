package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showAccountAtIndex;
import static seedu.address.testutil.TypicalAccounts.getTypicalAccountList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.address.testutil.TypicalItems.getTypicalStockList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Account;
import seedu.address.model.account.Username;

/**
 * Contains unit tests for
 * {@code DeleteAccountCommand}.
 */


public class DeleteAccountCommandTest {


    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setup() {
        Username admin = new Username("admin");
        model = new ModelManager(getTypicalStockList(), new UserPrefs(), getTypicalAccountList());
        model.setLoggedInUser(admin);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Account accountToDelete = model.getFilteredAccountList().get(INDEX_SECOND_ITEM.getZeroBased());
        DeleteAccountCommand deleteAccountCommand = new DeleteAccountCommand(INDEX_SECOND_ITEM);
        String expectedMessage = String.format(DeleteAccountCommand.MESSAGE_DELETE_ACCOUNT_SUCCESS, accountToDelete);

        ModelManager expectedModel = new ModelManager(model.getStockList(), new UserPrefs(), model.getAccountList());
        expectedModel.deleteAccount(accountToDelete);

        assertCommandSuccess(deleteAccountCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAccountList().size() + 1);
        DeleteAccountCommand deleteAccountCommand = new DeleteAccountCommand(outOfBoundIndex);

        assertCommandFailure(deleteAccountCommand, model, commandHistory, Messages
                .MESSAGE_INVALID_ACCOUNT_DISPLAYED_INDEX);
    }


    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showAccountAtIndex(model, INDEX_FIRST_ITEM);

        Index outOfBoundIndex = INDEX_SECOND_ITEM;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAccountList().getAccountList().size());

        DeleteAccountCommand deleteAccountCommand = new DeleteAccountCommand(outOfBoundIndex);

        assertCommandFailure(deleteAccountCommand, model, commandHistory, Messages
                .MESSAGE_INVALID_ACCOUNT_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        DeleteAccountCommand deleteFirstCommand = new DeleteAccountCommand(INDEX_FIRST_ITEM);
        DeleteAccountCommand deleteSecondCommand = new DeleteAccountCommand(INDEX_SECOND_ITEM);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteAccountCommand deleteFirstCommandCopy = new DeleteAccountCommand(INDEX_FIRST_ITEM);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different item -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}

