package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import static seedu.address.logic.commands.Command.MESSAGE_LOGIN;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.LostandFoundFeatureTest.getExpectedIndextwo;
import static seedu.address.testutil.LostandFoundFeatureTest.getExpectedUpdatedItemtwo;
import static seedu.address.testutil.LostandFoundFeatureTest.getFoundDescriptor;
import static seedu.address.testutil.LostandFoundFeatureTest.getItemtwoToUpdate;
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

public class FoundCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getStockList(), new UserPrefs(), getTypicalAccountList());
    private FoundCommand foundCommand = new FoundCommand(getExpectedIndextwo(), getFoundDescriptor());

    @Test
    public void constructorNullFoundDescriptorThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new FoundCommand(getExpectedIndextwo(), null);
    }

    @Test
    public void createUpdatedItemTest() {
        try {
            Item actualUpdatedItem = foundCommand.createFoundItem(getItemtwoToUpdate(), getFoundDescriptor());
            assertEquals(actualUpdatedItem, getExpectedUpdatedItemtwo());
        } catch (Exception e) {
            throw new AssertionError("The updated item does not have invalid status");
        }
    }
    @Test
    public void foundCommandExecuteTestWithoutLogin() {
        assertCommandFailure(foundCommand, model, commandHistory, MESSAGE_LOGIN);
    }

}
