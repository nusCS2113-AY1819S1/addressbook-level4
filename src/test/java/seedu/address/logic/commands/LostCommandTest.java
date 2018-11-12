package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import static seedu.address.logic.commands.Command.MESSAGE_LOGIN;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.LostandFoundFeatureTest.getExpectedIndexone;
import static seedu.address.testutil.LostandFoundFeatureTest.getExpectedUpdatedItemone;
import static seedu.address.testutil.LostandFoundFeatureTest.getItemoneToUpdate;
import static seedu.address.testutil.LostandFoundFeatureTest.getLostDescriptor;
import static seedu.address.testutil.LostandFoundFeatureTest.getStockList;
import static seedu.address.testutil.TypicalAccounts.getTypicalAccountList;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.item.Item;

//@@author HeHaowei

public class LostCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getStockList(), new UserPrefs(), getTypicalAccountList());
    private List<Item> lastShownList = model.getFilteredItemList();
    private LostCommand lostCommand = new LostCommand(getExpectedIndexone(), getLostDescriptor());

    @Test
    public void constructorNullLostDescriptorThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LostCommand(getExpectedIndexone(),null);
    }

    @Test
    public void createUpdatedItemTest() {
        try {
            Item actualUpdatedItem = lostCommand.createLostItem(getItemoneToUpdate(),getLostDescriptor());
            assertEquals(actualUpdatedItem, getExpectedUpdatedItemone());
        } catch (Exception e) {
            throw new AssertionError("The updated item does not have invalid status");
        }
    }
    @Test
    public void lostCommandExecuteTestWithoutLogin() {
        assertCommandFailure(lostCommand, model, commandHistory, MESSAGE_LOGIN);
    }

}
