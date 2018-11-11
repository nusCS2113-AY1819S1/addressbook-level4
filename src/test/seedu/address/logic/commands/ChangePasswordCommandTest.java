package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.user.TypicalAccount.getTypicalSingleAccount;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.CurrentUser;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.user.ChangePasswordCommand;
import seedu.address.model.InventoryList;
import seedu.address.model.UserPrefs;
import seedu.address.model.transaction.TransactionList;
import seedu.address.model.user.AuthenticationLevel;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;
import seedu.address.model.user.manager.ManagerModel;
import seedu.address.model.user.manager.ManagerModelManager;


public class ChangePasswordCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private ManagerModel model = new ManagerModelManager (new InventoryList () , new UserPrefs (),
            getTypicalSingleAccount(), new TransactionList ());
    private CommandHistory commandHistory = new CommandHistory();
    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ChangePasswordCommand (null, null);
    }

    @Test
    public void execute_changePassword_success() {
        ChangePasswordCommand changePasswordCommand = new ChangePasswordCommand (new Password ("123"),
                                                                                    new Password ("1234"));
        CurrentUser.setLoginInfo (new UserName ("tester"), new AuthenticationLevel ("ADMIN"));
        CommandResult commandResult = changePasswordCommand.execute (model, commandHistory);
        assertEquals(String.format (ChangePasswordCommand.MESSAGE_SUCCESS, "1234"),
                commandResult.feedbackToUser);
    }
    @Test
    public void execute_changePassword_failure() {
        ChangePasswordCommand changePasswordCommand = new ChangePasswordCommand (new Password ("wrongPassword"),
                new Password ("1234"));
        CurrentUser.setLoginInfo (new UserName ("tester"), new AuthenticationLevel ("ADMIN"));
        CommandResult commandResult = changePasswordCommand.execute (model, commandHistory);
        assertEquals(ChangePasswordCommand.MESSAGE_WRONG_PASSWORD,
                        commandResult.feedbackToUser);
    }
}
