package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAccounts.getTypicalLoginBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.login.LoginDetails;
import seedu.address.model.login.UserIdContainsKeywordsPredicate;
import seedu.address.testutil.AccountBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code CreateAccountCommand}.
 */
public class CreateAccountCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalLoginBook(), getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newAccount_success() {
        LoginDetails validAccount = new AccountBuilder().build();
        UserIdContainsKeywordsPredicate idPredicate = new AccountBuilder().buildUserIdPredicate();

        Model expectedModel = new ModelManager(model.getLoginBook(), model.getAddressBook(), new UserPrefs());
        expectedModel.createAccount(validAccount);

        assertCommandSuccess(new CreateAccountCommand(idPredicate, validAccount), model, commandHistory,
                String.format(CreateAccountCommand.MESSAGE_SUCCESS, validAccount), expectedModel);
    }

    @Test
    public void execute_duplicateAccount_throwsCommandException() {
        LoginDetails accountInList = model.getLoginBook().getLoginDetailsList().get(0);
        UserIdContainsKeywordsPredicate idPredicate = new AccountBuilder().buildUserIdPredicate();
        assertCommandFailure(new CreateAccountCommand(idPredicate, accountInList), model, commandHistory,
                CreateAccountCommand.MESSAGE_DUPLICATE_ACCOUNT);
    }
}
