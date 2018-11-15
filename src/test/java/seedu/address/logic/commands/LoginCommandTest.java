package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyDistributorBook;
import seedu.address.model.ReadOnlyProductDatabase;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.login.Password;
import seedu.address.model.login.UniqueUserList;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AuthenticatedException;
import seedu.address.model.login.exceptions.DuplicateUserException;
import seedu.address.model.login.exceptions.UserNotFoundException;
import seedu.address.model.product.Product;
import seedu.address.model.timeidentifiedclass.Reminder;
import seedu.address.model.timeidentifiedclass.Transaction;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;

//@@amirulmaricar
public class LoginCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_loginAcceptedByModel_authenticationSuccess() throws Exception {
        ModelStubAcceptingLogin modelStub = new ModelStubAcceptingLogin();
        LoginAttempt loginAttempt = new LoginAttempt("John", "pass");

        CommandResult commandResult = getLoginCommandForLoginAttempt(loginAttempt.getUsername(),
                loginAttempt.getPassword(), modelStub).execute(modelStub, new CommandHistory());

        assertEquals(LoginCommand.MESSAGE_AUTHENTICATION_SUCCESS, commandResult.feedbackToUser);

    }

    @Test
    public void execute_loginAcceptedByModel_authenticationFail() throws Exception {
        ModelStubAcceptingLogin modelStub = new ModelStubAcceptingLogin();
        LoginAttempt failedLoginAttempt = new LoginAttempt("John", "pass");

        CommandResult commandResult = getLoginCommandForLoginAttempt(failedLoginAttempt.getUsername(),
                failedLoginAttempt.getPassword(), modelStub).execute(modelStub, new CommandHistory());

        assertEquals(LoginCommand.MESSAGE_AUTHENTICATION_SUCCESS, commandResult.feedbackToUser);
    }

    @Test
    public void execute_authenticated_throwsCommandException() throws Exception {
        ModelStubThrowingAuthenticatedException modelStub = new ModelStubThrowingAuthenticatedException();
        LoginAttempt passLoginAttempt = new LoginAttempt("John", "pass");

        thrown.expect(CommandException.class);
        thrown.expectMessage(LoginCommand.MESSAGE_AUTHENTICATED);

        getLoginCommandForLoginAttempt(passLoginAttempt.getUsername(),
                passLoginAttempt.getPassword(), modelStub).execute(modelStub, new CommandHistory());
    }

    /**
     * Generates a new AddCommand with the details of the given product.
     */
    private LoginCommand getLoginCommandForLoginAttempt(Username username, Password password, Model model)
            throws CommandException {
        LoginCommand command = new LoginCommand(username, password);
        command.execute(model, new CommandHistory());
        return command;
    }


    /**
     * A default model stub that have all of the methods failing.
     */
    private abstract class ModelStub implements Model {

        @Override
        public void resetData(ReadOnlyProductDatabase newData) {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyDistributorBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyProductDatabase getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ReadOnlyDistributorBook getDistributorBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public boolean hasLoggedIn() {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public User getLoggedInUser() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void setLoginStatus(boolean status) {
            fail("This method should not be called.");
        }

        @Override
        public boolean checkAuthentication(Username username, Password password) throws AuthenticatedException {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public boolean checkCredentials(Username username, Password password) {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public void updateUserPassword(User target, User userWithNewPassword) throws UserNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addUser(User product) throws DuplicateUserException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteUser(User target) throws UserNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyProductDatabase getUserDatabase() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void setUsersList(UniqueUserList uniqueUserList) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyProductDatabase getProductInfoBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ReadOnlyDistributorBook getDistributorInfoBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public boolean hasDistributor(Distributor distributor) {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public boolean hasDistributorName(Distributor distributor) {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public boolean hasDistributorPhone(Distributor distributor) {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public boolean hasProduct(Product product) {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public boolean hasProductName(String name) {
            fail ("This methos should not be called.");
            return false;
        }


        @Override
        public void deleteDistributor(Distributor distributor) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteProduct(Product product) {
            fail("This method should not be called.");
        }

        @Override
        public void addProduct(Product product) {
            fail("This method should not be called.");
        }

        @Override
        public void addDistributor(Distributor distributor) {
            fail("This method should not be called.");
        }

        @Override
        public void updateDistributor(Distributor target, Distributor editedDistributor) {
            fail("This method should not be called.");
        }

        @Override
        public void updateProduct(Product target, Product editedProduct) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Product> getFilteredProductList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Distributor> getFilteredDistributorList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredDistributorList(Predicate<Distributor> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredProductList(Predicate<Product> predicate) {
            fail("This method should not be called.");
        }

        public boolean canUndoProductDatabase() {
            fail("This method should not be called.");
            return false;
        }

        public boolean canUndoDistributorBook() {
            fail("This method should not be called.");
            return false;
        }

        public boolean canRedoProductDatabase() {
            fail("This method should not be called.");
            return false;
        }

        public boolean canRedoDistributorBook() {
            fail("This method should not be called.");
            return false;
        }

        public void undoProductDatabase() {
            fail("This method should not be called.");
        }

        public void undoDistributorBook() {
            fail("This method should not be called.");
        }

        public void redoProductDatabase() {
            fail("This method should not be called.");
        }

        public void redoDistributorBook() {
            fail("This method should not be called.");
        }

        public void commitProductDatabase() {
            fail("This method should not be called.");
        }

        public void commitDistributorBook() {
            fail("This method should not be called.");
        }

        public void addTransaction(Transaction transaction) {
            fail("This method should not be called.");
        }

        public void addReminder(Reminder reminder) {
            fail("This method should not be called.");
        }

        public ArrayList<Reminder> getOverdueReminders() {
            fail("This method should not be called.");
            return null;
        }

        public String getDaysTransactionsAsString(String day) {
            fail("This method should not be called.");
            return null;
        }

        public String getDaysTransactions() {
            fail("This method should not be called.");
            return null;
        }

        public Transaction getLastTransaction() {
            fail("This method should not be called.");
            return null;
        }

    }

    /**
     * A Model stub that always accepts the login attempt.
     */
    private class ModelStubAcceptingLogin extends ModelStub {

        private boolean loginStatus = false;

        @Override
        public void removeReminder(String reminderTime) throws InvalidTimeFormatException, NoSuchElementException {
            throw new InvalidTimeFormatException();
        }

        @Override
        public ArrayList<Reminder> getAllReminders() {
            return null;
        }

        @Override
        public ArrayList<Reminder> getOverdueRemindersForThread() {
            return null;
        }

        @Override
        public String getTransactionAsString(String date) throws InvalidTimeFormatException {
            return null;
        }

        @Override
        public void commitSalesHistory() {
            fail("This method should not be called");
        }

        @Override
        public boolean checkAuthentication(Username username, Password password) {
            requireNonNull(username);
            requireNonNull(password);
            setLoginStatus(true);
            return true;
        }

        @Override
        public void setLoginStatus(boolean status) {
            this.loginStatus = true;
        }

        @Override
        public boolean hasLoggedIn() {
            return this.loginStatus;
        }
    }

    /**
     * A Model stub that always throw a AuthenticationException when trying to login.
     */
    private class ModelStubThrowingAuthenticatedException extends ModelStub {

        @Override
        public void removeReminder(String reminderTime) throws InvalidTimeFormatException, NoSuchElementException {}

        @Override
        public ArrayList<Reminder> getAllReminders() {
            return null;
        }

        @Override
        public ArrayList<Reminder> getOverdueRemindersForThread() {
            return null;
        }

        @Override
        public String getTransactionAsString(String date) throws InvalidTimeFormatException {
            return null;
        }

        @Override
        public void commitSalesHistory() {
            fail("This should not be called.");
        }

        @Override
        public boolean checkAuthentication(Username username, Password password)
                throws AuthenticatedException {
            throw new AuthenticatedException();
        }

    }

    private class LoginAttempt {
        private Username username;
        private Password password;

        private LoginAttempt(String username, String password) {
            this.username = new Username(username);
            this.password = new Password(password);
        }

        private Password getPassword() {
            return password;
        }

        private Username getUsername() {
            return username;
        }
    }
}
