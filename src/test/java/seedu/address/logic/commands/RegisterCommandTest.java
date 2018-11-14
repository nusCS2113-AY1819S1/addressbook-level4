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
import seedu.address.model.login.exceptions.DuplicateUserException;
import seedu.address.model.login.exceptions.UserNotFoundException;
import seedu.address.model.product.Product;
import seedu.address.model.timeidentifiedclass.Reminder;
import seedu.address.model.timeidentifiedclass.Transaction;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;

//@@amirulmaricar
public class RegisterCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_createUserAcceptedByModel_createSuccessful() throws Exception {
        RegisterCommandTest.ModelStubAcceptingRegisterUser modelStub =
                new RegisterCommandTest.ModelStubAcceptingRegisterUser();

        User validUser = registerValidUser();

        CommandResult commandResult =
                getRegisterUserCommandForRegisterUserAttempt(validUser, modelStub)
                        .execute(modelStub, new CommandHistory());

        assertEquals(String.format(RegisterCommand.MESSAGE_SUCCESS, validUser.getUsername().toString()),
                commandResult.feedbackToUser);
    }


    @Test
    public void execute_duplicateUser_throwsCommandException() throws Exception {
        RegisterCommandTest.ModelStubThrowingDuplicateUserException modelStub =
                new RegisterCommandTest.ModelStubThrowingDuplicateUserException();

        User validUser = registerValidUser();

        thrown.expect(CommandException.class);
        thrown.expectMessage(RegisterCommand.MESSAGE_DUPLICATE_USER);

        getRegisterUserCommandForRegisterUserAttempt(validUser, modelStub)
                .execute(modelStub, new CommandHistory());
    }

    /**
     * Generates a new CreateUserCommand with the details of the given person.
     */
    private RegisterCommand getRegisterUserCommandForRegisterUserAttempt(User user, Model model) throws Exception {

        RegisterCommand command = new RegisterCommand(user);
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
        public boolean checkAuthentication(Username username, Password password) {
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

        @Override
        public void commitSalesHistory() {
            fail("This method should not be called");
        }

        public boolean hasProductName(String name) {
            return false;
        }

        @Override
        public String getTransactionAsString(String date) throws InvalidTimeFormatException {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void removeReminder(String reminderTime) throws InvalidTimeFormatException, NoSuchElementException {
            fail("This method should not be called");
        }

        @Override
        public ArrayList<Reminder> getAllReminders() {
            fail("This method should not be called");
            return null;
        }

        @Override
        public ArrayList<Reminder> getOverdueRemindersForThread() {
            fail("This method should not be called");
            return null;
        }


    }

    /**
     * A Model stub that always accepts the registration attempt.
     */
    private class ModelStubAcceptingRegisterUser extends RegisterCommandTest.ModelStub {
        final ArrayList<User> usersAdded = new ArrayList<>();

        @Override
        public void addUser(User user) throws DuplicateUserException {
            requireNonNull(user);
            usersAdded.add(user);
        }

    }

    /**
     * A Model stub that always throw a DuplicateUserException when trying to login.
     */
    private class ModelStubThrowingDuplicateUserException extends RegisterCommandTest.ModelStub {

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
            fail("This method should not be called.");
        }

        @Override
        public void addUser(User person) throws DuplicateUserException {
            throw new DuplicateUserException();
        }

    }

    private User registerValidUser() {
        return new User(new Username("John"), new Password("pass"));
    }
}
