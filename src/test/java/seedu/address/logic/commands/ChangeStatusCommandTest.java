package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import static seedu.address.logic.commands.ChangeStatusCommand.MESSAGE_STATUS_CONSTRAINTS;
import static seedu.address.logic.commands.Command.MESSAGE_LOGIN;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.StatusFeatureTestObjects.getChangeStatusDescriptor;
import static seedu.address.testutil.StatusFeatureTestObjects.getExpectedIndex;
import static seedu.address.testutil.StatusFeatureTestObjects.getExpectedUpdatedItem;
import static seedu.address.testutil.StatusFeatureTestObjects.getItemToUpdate;
import static seedu.address.testutil.StatusFeatureTestObjects.getStockList;
import static seedu.address.testutil.TypicalAccounts.getTypicalAccountList;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Username;
import seedu.address.model.item.Item;

//@@author ChewKinWhye

public class ChangeStatusCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getStockList(), new UserPrefs(), getTypicalAccountList());
    private List<Item> lastShownList = model.getFilteredItemList();
    private ChangeStatusCommand changeStatusCommand = new ChangeStatusCommand(getChangeStatusDescriptor());

    @Test
    public void constructorNullChangeStatusDescriptorThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ChangeStatusCommand(null);
    }
    @Test
    public void changeStatusDescriptorConstructor() {
        ChangeStatusCommand.ChangeStatusDescriptor changeStatusDescriptor =
                new ChangeStatusCommand.ChangeStatusDescriptor(getChangeStatusDescriptor());
        ChangeStatusCommand.ChangeStatusDescriptor changeStatusDescriptorDuplicate =
                new ChangeStatusCommand.ChangeStatusDescriptor(changeStatusDescriptor);
        assertEquals(changeStatusDescriptor, changeStatusDescriptorDuplicate);
    }
    @Test
    public void changeStatusCommandGetIndexTest() {
        try {
            Index actualIndex = changeStatusCommand.getIndex(lastShownList, getChangeStatusDescriptor());
            assertEquals(actualIndex, getExpectedIndex());
        } catch (CommandException e) {
            throw new AssertionError("The item is present in the stock list.");
        }
    }
    @Test
    public void createUpdatedItemTest() {
        try {
            Item actualUpdatedItem = changeStatusCommand
                    .createUpdatedItem(getItemToUpdate(), getChangeStatusDescriptor());
            assertEquals(actualUpdatedItem, getExpectedUpdatedItem());
        } catch (Exception e) {
            throw new AssertionError("The updated item does not have invalid status");
        }
    }
    @Test
    public void changeStatusCommandExecuteTestWithoutLogin() {
        assertCommandFailure(changeStatusCommand, model, commandHistory, MESSAGE_LOGIN);
    }
    @Test
    public void changeStatusCommandExecuteTestWithLogin() {
        Username admin = new Username("admin");
        model.setLoggedInUser(admin);
        try {
            changeStatusCommand.execute(model, commandHistory);
        } catch (CommandException e) {
            throw new AssertionError("The execute function is valid");
        }
        assertCommandFailure(changeStatusCommand, model, commandHistory, MESSAGE_STATUS_CONSTRAINTS);
    }

}
