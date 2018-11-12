package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.Command.MESSAGE_LOGIN;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.StatusFeatureTestObjects.getExpectedCommandResult;
import static seedu.address.testutil.StatusFeatureTestObjects.getExpectedFaultyItems;
import static seedu.address.testutil.StatusFeatureTestObjects.getExpectedMessageOutput;
import static seedu.address.testutil.StatusFeatureTestObjects.getExpectedOnLoanItems;
import static seedu.address.testutil.StatusFeatureTestObjects.getExpectedReadyItems;
import static seedu.address.testutil.StatusFeatureTestObjects.getStockList;
import static seedu.address.testutil.TypicalAccounts.getTypicalAccountList;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Username;
import seedu.address.model.item.Item;
import seedu.address.model.item.SimpleItem;

//@@author ChewKinWhye

public class StatusCommandTest {
    private Model model = new ModelManager(getStockList(), new UserPrefs(), getTypicalAccountList());
    private CommandHistory commandHistory = new CommandHistory();
    private List<Item> lastShownList = model.getFilteredItemList();
    private StatusCommand statusCommand = new StatusCommand();
    private ArrayList<SimpleItem> actualReadyItems = new ArrayList<>();
    private ArrayList<SimpleItem> actualOnLoanItems = new ArrayList<>();
    private ArrayList<SimpleItem> actualFaultyItems = new ArrayList<>();

    @Test
    public void checkSortSimpleItems() {
        statusCommand.sortSimpleItems(lastShownList, actualReadyItems, actualOnLoanItems, actualFaultyItems);
        assertEquals(actualReadyItems, getExpectedReadyItems());
        assertEquals(actualOnLoanItems, getExpectedOnLoanItems());
        assertEquals(actualFaultyItems, getExpectedFaultyItems());
    }
    @Test
    public void checkGetMessageOutput() {
        statusCommand.sortSimpleItems(lastShownList, actualReadyItems, actualOnLoanItems, actualFaultyItems);
        String actualMessageOutput = statusCommand.getMessageOutput(actualReadyItems,
                actualOnLoanItems, actualFaultyItems);
        assertEquals(actualMessageOutput, getExpectedMessageOutput());
    }
    @Test
    public void executeStatusCommandWithoutLogin() {
        assertCommandFailure(new StatusCommand(), model, commandHistory, MESSAGE_LOGIN);
    }

    @Test
    public void executeStatusCommandWithLogin() {
        Username admin = new Username("admin");
        model.setLoggedInUser(admin);
        try {
            CommandResult actualCommandResult = statusCommand.execute(model, commandHistory);
            assertEquals(actualCommandResult, getExpectedCommandResult());
        } catch (CommandException e) {
            throw new AssertionError("The account should be logged in.");
        }
    }
}
