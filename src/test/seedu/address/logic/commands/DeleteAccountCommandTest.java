package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.transaction.TypicalTransactions.getTypicalTransactions;
import static seedu.address.testutil.user.TypicalAccount.ADMIN_ACCOUNT;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.core.LoginInfo;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.user.DeleteAccountCommand;
import seedu.address.model.ReadOnlyInventoryList;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.Price;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.ReadOnlyTransactionList;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;
import seedu.address.model.user.manager.ManagerModel;

public class DeleteAccountCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeleteAccountCommand (null);
    }
    @Test
    public void execute_accountAcceptedByManagerModel_deleteSuccessful() throws Exception {
        ManagerModelStubAcceptingAccountAdded modelStub = new ManagerModelStubAcceptingAccountAdded ();

        LoginInfo validAccount = ADMIN_ACCOUNT;
        modelStub.createNewAccount (validAccount);

        CommandResult commandResult = new DeleteAccountCommand (validAccount.getUserName ())
                                            .execute (modelStub, commandHistory);

        assertEquals(DeleteAccountCommand.MESSAGE_SUCCESS,
                commandResult.feedbackToUser);
        assertTrue(modelStub.loginInfoArrayList.isEmpty ());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_accountNonExistInManagerModel_deleteFail() throws Exception {
        ManagerModelStubAcceptingAccountAdded modelStub = new ManagerModelStubAcceptingAccountAdded ();
        LoginInfo validAccount = ADMIN_ACCOUNT;
        thrown.expect(CommandException.class);
        CommandResult commandResult = new DeleteAccountCommand (validAccount.getUserName ())
                                            .execute (modelStub, commandHistory);
        assertEquals(DeleteAccountCommand.MESSAGE_USERNAME_DO_NOT_EXIST, commandResult.feedbackToUser);
    }


    private class ManagerModelStub implements ManagerModel {

        @Override
        public void createNewAccount (LoginInfo loginInfo) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAccount (UserName userName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData (ReadOnlyInventoryList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyInventoryList getInventoryList () {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDrink (Drink drink) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDrink (Drink target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDrink (Drink drink) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Drink> getFilteredDrinkList () {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDrinkList (Predicate<Drink> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTransactionList getTransactionList () {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Transaction> getFilteredTransactionList() {
            return FXCollections.observableList(getTypicalTransactions());
        }

        @Override
        public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
            requireNonNull(predicate);
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSellingPrice(Drink drinkToEdit, Price newSellingPrice) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateCostPrice(Drink drinkToEdit, Price newCostPrice) {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public void updateTags(Drink drinkToEdit, Set<Tag> newTags) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void changePassword (UserName userName, Password newHashedPassword) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public LoginInfo getLoginInfo (UserName userName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isUserNameExist (UserName userName) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A model stub that contains a single drink
     */
    private class ManagerModelStubWithAccount extends DeleteAccountCommandTest.ManagerModelStub {
        private final LoginInfo loginInfo;

        ManagerModelStubWithAccount(LoginInfo loginInfo) {
            requireNonNull(loginInfo);
            this.loginInfo = loginInfo;
        }
        @Override
        public boolean isUserNameExist (UserName userName) {
            requireNonNull (loginInfo);
            return this.loginInfo.isUserNameMatched (userName);
        }
    }

    /**
     * A model stub that always accept the drink being added.
     */
    public class ManagerModelStubAcceptingAccountAdded extends DeleteAccountCommandTest.ManagerModelStub {
        final ArrayList<LoginInfo> loginInfoArrayList = new ArrayList<> ();

        @Override
        public boolean isUserNameExist (UserName userName) {
            requireNonNull (userName);
            LoginInfo loginInfo = ADMIN_ACCOUNT;
            return loginInfoArrayList.contains (loginInfo);
        }

        @Override
        public void createNewAccount (LoginInfo loginInfo) {
            requireNonNull (loginInfo);
            loginInfoArrayList.add (loginInfo);
        }
        @Override
        public void deleteAccount(UserName userName) {
            requireNonNull (userName);
            loginInfoArrayList.remove (0);
        }

    }
}
