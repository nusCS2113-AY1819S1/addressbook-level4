package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import seedu.address.model.product.Product;
import seedu.address.model.timeidentifiedclass.Reminder;
import seedu.address.model.timeidentifiedclass.Transaction;
import seedu.address.model.timeidentifiedclass.exceptions.DuplicateReminderException;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;

public class AddReminderCommandTest {

    private static final String FAILURE_METHOD_MESSAGE = "This method should not be called.";
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullReminder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddReminderCommand(null);
    }

    @Test
    public void constructor_validNonNullReminder() {
        new AddReminderCommand(new ValidReminderStub());
    }

    @Test
    public void execute_reminderAddedByModel_addSuccessful() throws Exception {
        Reminder reminder = new ValidReminderStub();
        Model model = new ModelStubAcceptsReminder();
        CommandHistory commandHistory = new CommandHistory();
        CommandResult commandResult = new AddReminderCommand(reminder).execute(model, commandHistory);

        assertEquals(commandResult.feedbackToUser, String.format(AddReminderCommand.MESSAGE_SUCCESS,
                reminder.getReminderMessage(), reminder.getReminderTime()));
        assertEquals(commandHistory, EMPTY_COMMAND_HISTORY);
    }

    @Test
    public void execute_invalidTimeFormat_throwsCommandException() throws Exception {
        Reminder reminder = new ValidReminderStub();
        Model modelThrowsInvalidTime = new ModelStubThrowsInvalidTimeFormatException();
        CommandHistory commandHistory = new CommandHistory();

        thrown.expect(CommandException.class);
        thrown.expectMessage(InvalidTimeFormatException.EXCEPTION_MESSAGE + ". Upon adding this reminder");
        new AddReminderCommand(reminder).execute(modelThrowsInvalidTime, commandHistory);
    }

    @Test
    public void execute_duplicateReminder_throwsCommandException() throws Exception {
        Reminder reminder = new ValidReminderStub();
        Model modelThrowsInvalidTime = new ModelStubThrowsDuplicateReminderException();
        CommandHistory commandHistory = new CommandHistory();

        thrown.expect(CommandException.class);
        thrown.expectMessage(DuplicateReminderException.EXCEPTION_MESSAGE);
        new AddReminderCommand(reminder).execute(modelThrowsInvalidTime, commandHistory);
    }

    @Test
    public void equals() {
        Reminder equalReminder = new ValidReminderStub();
        AddReminderCommand toTest = new AddReminderCommand(equalReminder);
        int someIntegerValue = 1;
        String someString = "This is just a random string that can be anything for this test.";

        // object should equal itself
        assertTrue(toTest.equals(toTest));

        // should equal another AddReminderCommand with the same reminder
        assertTrue(toTest.equals(new AddReminderCommand(equalReminder)));

        // should not equal to null
        assertFalse(toTest.equals(null));

        // should not equal to other data types
        assertFalse(toTest.equals(someIntegerValue));
        assertFalse(toTest.equals(someString));

        // should not equal to another AddReminderCommand with different reminder
        Reminder differentReminder = new ValidReminderStub();

        AddReminderCommand unequalReminder = new AddReminderCommand(differentReminder);
        assertFalse(toTest.equals(unequalReminder));

    }

    // ========================================== Stub classes ======================================
    private class ModelStubAcceptsReminder extends ModelStub {
        public ModelStubAcceptsReminder () {
            //do nothing.
        }

        @Override
        public void addReminder(Reminder toAdd) {
            // do nothing.
        }

        @Override
        public void commitSalesHistory() {
            // do nothing.
        }
    }

    private class ModelStubThrowsInvalidTimeFormatException extends ModelStub {
        public ModelStubThrowsInvalidTimeFormatException() {
            //do nothing.
        }

        @Override
        public void addReminder(Reminder toAdd) throws InvalidTimeFormatException {
            throw new InvalidTimeFormatException();
        }
    }

    private class ModelStubThrowsDuplicateReminderException extends ModelStub {
        public ModelStubThrowsDuplicateReminderException() {
            //do nothing.
        }

        @Override
        public void addReminder(Reminder toAdd) throws DuplicateReminderException {
            throw new DuplicateReminderException();
        }
    }

    /**
     * The following is a {@code ModelStub} stub class that throws Assertion error for all methods in {@link Model}
     */
    private class ModelStub implements Model {

        @Override
        public void addDistributor(Distributor distributor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addProduct(Product product) {

        }

        @Override
        public void updateDistributor(Distributor target, Distributor editedDistributor) {
        }

        @Override
        public void updateProduct(Product target, Product editedProduct) {

        }

        @Override
        public void resetData(ReadOnlyProductDatabase newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyDistributorBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProductDatabase getProductInfoBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyDistributorBook getDistributorInfoBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDistributor(Distributor distributor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDistributorName(Distributor distributor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDistributorPhone(Distributor distributor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasProduct(Product product) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasProductName(String name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDistributor(Distributor target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteProduct(Product target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Product> getFilteredProductList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Distributor> getFilteredDistributorList() {
            return null;
        }

        @Override
        public void updateFilteredDistributorList(Predicate<Distributor> predicate) {

        }

        @Override
        public void updateFilteredProductList(Predicate<Product> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoProductDatabase() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoProductDatabase() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoDistributorBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoDistributorBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoDistributorBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoProductDatabase() {

        }

        @Override
        public void redoDistributorBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitProductDatabase() {

        }

        @Override
        public void commitDistributorBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTransaction(Transaction transaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Transaction getLastTransaction() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitSalesHistory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addReminder(Reminder reminder) throws InvalidTimeFormatException, DuplicateReminderException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeReminder(String reminderTime) throws InvalidTimeFormatException, NoSuchElementException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ArrayList<Reminder> getAllReminders() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ArrayList<Reminder> getOverdueReminders() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ArrayList<Reminder> getOverdueRemindersForThread() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getDaysTransactionsAsString(String date) throws InvalidTimeFormatException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getTransactionAsString(String date) throws InvalidTimeFormatException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasLoggedIn() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLoginStatus(boolean status) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public User getLoggedInUser() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean checkCredentials(Username username, Password password) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean checkAuthentication(Username username, Password password) {
            requireNonNull(username);
            requireNonNull(password);
            setLoginStatus(true);
            return true;
        }

        @Override
        public void updateUserPassword(User target, User userWithNewPassword) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProductDatabase getAddressBook() {
            return null;
        }

        @Override
        public ReadOnlyDistributorBook getDistributorBook() {
            return null;
        }

        @Override
        public void addUser(User person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteUser(User target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProductDatabase getUserDatabase() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUsersList(UniqueUserList uniqueUserList) {
            throw new AssertionError("This method should not be called.");
        }
    }
    private class ValidReminderStub extends Reminder {
        /**
         * An example of valid {@code Reminder} time and message. For more details on what constitutes a valid time
         * and message, please refer to {@link Reminder}
         */
        private static final String VALID_TIME = "2018/11/11 12:00:00";
        private static final String DIFFERENT_VALID_TIME = "2018/11/11 12:00:01";
        private static final String MESSAGE = "Some random message";

        public ValidReminderStub() {
            this.time = VALID_TIME;
            this.reminderMessage = MESSAGE;
        }

        public void setDifferentTime() {
            this.time = DIFFERENT_VALID_TIME;
        }
    }
}
