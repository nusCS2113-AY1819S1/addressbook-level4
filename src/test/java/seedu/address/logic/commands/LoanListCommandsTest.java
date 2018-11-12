package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.Command.MESSAGE_LOGIN;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.ViewLoanListCommand.MESSAGE_EMPTY;
import static seedu.address.testutil.StatusFeatureTestObjects.getExpectedUpdatedArduinoItem;
import static seedu.address.testutil.StatusFeatureTestObjects.getExpectedViewLoanListMessageOutput;
import static seedu.address.testutil.StatusFeatureTestObjects.getLoanListTestFile;
import static seedu.address.testutil.StatusFeatureTestObjects.getLoanerDescription;
import static seedu.address.testutil.StatusFeatureTestObjects.getStockList;
import static seedu.address.testutil.StatusFeatureTestObjects.getTestIndex;
import static seedu.address.testutil.TypicalAccounts.getTypicalAccountList;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Username;
import seedu.address.model.item.Item;

public class LoanListCommandsTest {
    private Model model = new ModelManager(getStockList(), new UserPrefs(), getTypicalAccountList());
    private CommandHistory commandHistory = new CommandHistory();
    private LoanListCommand loanListCommand = new LoanListCommand(getLoanerDescription());
    private ViewLoanListCommand viewLoanListCommand = new ViewLoanListCommand();
    private DeleteLoanListCommand deleteLoanListCommand = new DeleteLoanListCommand(getTestIndex());
    @Test
    public void executeViewLoanListCommandWithoutLogin() {
        assertCommandFailure(viewLoanListCommand, model, commandHistory, MESSAGE_LOGIN);
    }
    @Test
    public void executeDeleteLoanListCommandWithoutLogin() {
        assertCommandFailure(deleteLoanListCommand, model, commandHistory, MESSAGE_LOGIN);
    }
    @Test
    public void executeLoanListCommandWithoutLogin() {
        assertCommandFailure(loanListCommand, model, commandHistory, MESSAGE_LOGIN);
    }
    @Test
    public void executeLoanListAndViewLoanListAndDeleteLoanList() {
        Username admin = new Username("admin");
        model.setLoggedInUser(admin);
        try {
            loanListCommand.updateLoanList(getLoanListTestFile(), getLoanerDescription());
        } catch (JAXBException e) {
            throw new AssertionError("The loan list test file is vaild");
        }

        try {
            loanListCommand.updateStatus(model, commandHistory);
        } catch (CommandException e) {
            throw new AssertionError("The updated status is valid");
        }
        Item actualUpdatedArduinoItem = model.getFilteredItemList().get(0);
        assertEquals(actualUpdatedArduinoItem, getExpectedUpdatedArduinoItem());

        try {
            String actualViewLoanListMessageOutput = viewLoanListCommand.getMessageOutput(getLoanListTestFile());
            assertEquals(actualViewLoanListMessageOutput, getExpectedViewLoanListMessageOutput());
        } catch (CommandException e) {
            throw new AssertionError("Loan list file is not empty");
        }

        try {
            deleteLoanListCommand.deleteLoanList(model, commandHistory, getLoanListTestFile());
        } catch (Exception e) {
            throw new AssertionError("The loan list entry should be deleted");
        }

        try {
            viewLoanListCommand.getMessageOutput(getLoanListTestFile());
        } catch (CommandException e) {
            assertEquals(e.getMessage(), MESSAGE_EMPTY);
        }
    }
}
