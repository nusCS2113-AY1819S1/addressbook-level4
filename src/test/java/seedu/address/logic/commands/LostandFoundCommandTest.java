package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.Command.MESSAGE_LOGIN;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

import static seedu.address.testutil.LostandFoundFeatureTest.getExpectedCommandResult;
import static seedu.address.testutil.LostandFoundFeatureTest.getExpectedFoundItems;
import static seedu.address.testutil.LostandFoundFeatureTest.getExpectedLostItems;
import static seedu.address.testutil.LostandFoundFeatureTest.getExpectedMessageOutput;
import static seedu.address.testutil.LostandFoundFeatureTest.getStockList;

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

//@@author HeHaowei

public class LostandFoundCommandTest {
    private Model model = new ModelManager(getStockList(), new UserPrefs(), getTypicalAccountList());
    private CommandHistory commandHistory = new CommandHistory();
    private List<Item> lastShownList = model.getFilteredItemList();
    private LostandFoundCommand lostandfoundCommand = new LostandFoundCommand();
    private ArrayList<SimpleItem> actualLostItems = new ArrayList<>();
    private ArrayList<SimpleItem> actualFoundItems = new ArrayList<>();

    @Test
    public void checkSortSimpleItems() {
        lostandfoundCommand.sortSimpleItems(lastShownList, actualLostItems);
        assertEquals(actualLostItems, getExpectedLostItems());
    }
    @Test
    public void checkGetMessageOutput() {
        lostandfoundCommand.sortSimpleItems(lastShownList, actualLostItems);
        String actualMessageOutput = lostandfoundCommand.getMessageOutput(actualLostItems);
        assertEquals(actualMessageOutput, getExpectedMessageOutput());
    }
    @Test
    public void executeLostandfoundCommandWithoutLogin() {
        assertCommandFailure(new StatusCommand(), model, commandHistory, MESSAGE_LOGIN);
    }

    @Test
    public void executeLoststatusCommandWithLogin() {
        Username admin = new Username("admin");
        model.setLoggedInUser(admin);
        try {
            CommandResult actualCommandResult = lostandfoundCommand.execute(model, commandHistory);
            assertEquals(actualCommandResult, getExpectedCommandResult());
        } catch (CommandException e) {
            throw new AssertionError("The account should be logged in.");
        }
    }
}
