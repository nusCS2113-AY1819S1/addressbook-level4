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

public class DeregisterCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_deleteAcceptedByModel_deleteSuccessful() throws Exception {
        DeregisterCommandTest.ModelStubAcceptingDeleteUser modelStub =
                new DeregisterCommandTest.ModelStubAcceptingDeleteUser();

        User validUser = registerValidUser();

        CommandResult commandResult =
                getDeleteUserCommandForDeleteUserAttempt(validUser, modelStub).execute(modelStub, new CommandHistory());

        assertEquals(String.format(DeregisterCommand.MESSAGE_SUCCESS, validUser.getUsername().toString()),
                commandResult.feedbackToUser);
    }


    @Test
    public void execute_userNotFound_throwsCommandException() throws Exception {
        DeregisterCommandTest.ModelStubThrowingUserNotFoundException modelStub =
                new DeregisterCommandTest.ModelStubThrowingUserNotFoundException();

        User validUser = registerValidUser();

        thrown.expect(CommandException.class);
        thrown.expectMessage(DeregisterCommand.MESSAGE_DELETE_FAILURE);

        getDeleteUserCommandForDeleteUserAttempt(validUser, modelStub).execute(modelStub, new CommandHistory());
    }

    @Test
    public void execute_notLoggedOut_throwsCommandException() throws Exception {
        DeregisterCommandTest.ModelStubThrowingAuthenticationFailedException modelStub =
                new DeregisterCommandTest.ModelStubThrowingAuthenticationFailedException();

        User validUser = registerValidUser();

        thrown.expect(CommandException.class);
        thrown.expectMessage(DeregisterCommand.MESSAGE_NOT_LOGGED_OUT);

        getDeleteUserCommandForDeleteUserAttempt(validUser, modelStub).execute(modelStub, new CommandHistory());
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private DeregisterCommand getDeleteUserCommandForDeleteUserAttempt(User user, Model model) throws Exception {

        DeregisterCommand command = new DeregisterCommand(user.getUsername(), user.getPassword());
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
        public boolean checkAuthentication(Username username, Password password) {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public boolean checkCredentials(Username username, Password password) throws AuthenticatedException {
            return true;
        }

        @Override
        public void updateUserPassword(User target, User userWithNewPassword) throws UserNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addUser(User person) throws DuplicateUserException {
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
    private class ModelStubAcceptingDeleteUser extends DeregisterCommandTest.ModelStub {
        final ArrayList<User> usersAdded = new ArrayList<>();

        @Override
        public boolean hasProductName(String name) {
            return false;
        }

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
            fail("This should not be called");
        }

        @Override
        public void deleteUser(User user) throws UserNotFoundException {
            usersAdded.add(registerValidUser());
            requireNonNull(user);
            usersAdded.remove(user);
        }

    }

    /**
     * A Model stub that always throw a DuplicateProductException when trying to login.
     */
    private class ModelStubThrowingUserNotFoundException extends DeregisterCommandTest.ModelStub {

        @Override
        public boolean hasProductName(String name) {
            return false;
        }

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
        public void deleteUser(User user) throws UserNotFoundException {
            throw new UserNotFoundException();
        }

    }

    /**
     * A Model stub that always throw a AuthenticatiedException when trying to login.
     */
    private class ModelStubThrowingAuthenticationFailedException extends DeregisterCommandTest.ModelStub {

        @Override
        public boolean hasProductName(String name) {
            return false;
        }

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
        public boolean checkCredentials(Username username, Password password) throws AuthenticatedException {
            throw new AuthenticatedException();
        }
    }

    private User registerValidUser() {
        return new User(new Username("John"), new Password("pass"));
    }
}
