package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ProductDatabase;
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
import seedu.address.model.timeidentifiedclass.exceptions.DuplicateReminderException;
import seedu.address.model.timeidentifiedclass.exceptions.DuplicateTransactionException;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;
import seedu.address.testutil.DistributorBuilder;
import seedu.address.testutil.ProductBuilder;

public class AddProductCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        Distributor validDistributor = new DistributorBuilder().build();
        thrown.expect(NullPointerException.class);
        new AddCommand(null,validDistributor);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Product validPerson = new ProductBuilder().build();
        Distributor validDistributor = new DistributorBuilder().build();
        CommandResult commandResult = new AddCommand(validPerson,validDistributor).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Product validPerson = new ProductBuilder().build();
        Distributor validDistributor = new DistributorBuilder().build();

        AddCommand addCommand = new AddCommand(validPerson,validDistributor);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PRODUCT);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Distributor validDistributor = new DistributorBuilder().build();

        Product alice = new ProductBuilder().withName("Alice").build();
        Product bob = new ProductBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice,validDistributor);
        AddCommand addBobCommand = new AddCommand(bob,validDistributor);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice,validDistributor);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different Product -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addProduct(Product person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateDistributor(Distributor target, Distributor editedDistributor) {

        }

        @Override
        public void resetData(ReadOnlyProductDatabase newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyDistributorBook newData) {

        }

        @Override
        public ReadOnlyProductDatabase getProductInfoBook() {
            return null;
        }

        @Override
        public ReadOnlyDistributorBook getDistributorInfoBook() {
            return null;
        }

        @Override
        public boolean hasDistributor(Distributor distributor) {
            return false;
        }

        @Override
        public boolean hasDistributorName(Distributor distributor) {
            return false;
        }

        @Override
        public boolean hasDistributorPhone(Distributor distributor) {
            return false;
        }

        @Override
        public ReadOnlyProductDatabase getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyDistributorBook getDistributorBook() {
            return null;
        }

        @Override
        public boolean hasProduct(Product person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasProductName(String name) {
            return false;
        }

        @Override
        public void deleteDistributor(Distributor target) {

        }

        @Override
        public void deleteProduct (Product target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDistributor(Distributor distributor) {

        }

        @Override
        public void updateProduct(Product target, Product editedPerson) {
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
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoDistributorBook() {
            return false;
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoDistributorBook() {
            return false;
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoDistributorBook() {

        }

        @Override
        public void redoProductDatabase() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoDistributorBook() {

        }

        @Override
        public void commitProductDatabase() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitDistributorBook() {

        }

        @Override
        public void addTransaction(Transaction transaction)
                throws InvalidTimeFormatException, DuplicateTransactionException {

        }

        @Override
        public void addReminder(Reminder reminder) throws InvalidTimeFormatException, DuplicateReminderException {

        }

        @Override
        public void removeReminder(String reminderTime) throws InvalidTimeFormatException, NoSuchElementException {

        }

        @Override
        public ArrayList<Reminder> getAllReminders() {
            return null;
        }

        @Override
        public ArrayList<Reminder> getOverdueReminders() {
            return null;
        }

        @Override
        public ArrayList<Reminder> getOverdueRemindersForThread() {
            return null;
        }

        @Override
        public String getDaysTransactionsAsString(String date) throws InvalidTimeFormatException {
            return null;
        }

        @Override
        public String getTransactionAsString(String date) throws InvalidTimeFormatException {
            return null;
        }

        @Override
        public Transaction getLastTransaction() {
            return null;
        }

        @Override
        public void commitSalesHistory() {

        }

        @Override
        public void setUsersList(UniqueUserList uniqueUserList) {

        }

        @Override
        public ReadOnlyProductDatabase getUserDatabase() {
            return null;
        }

        @Override
        public void deleteUser(User target) throws UserNotFoundException {

        }

        @Override
        public void addUser(User person) throws DuplicateUserException {

        }

        @Override
        public boolean checkAuthentication(Username username, Password password) throws AuthenticatedException {
            return false;
        }

        @Override
        public boolean checkCredentials(Username username, Password password) throws AuthenticatedException {
            return false;
        }

        @Override
        public boolean hasLoggedIn() {
            return false;
        }

        @Override
        public void setLoginStatus(boolean status) {

        }

        @Override
        public User getLoggedInUser() {
            return null;
        }

        @Override
        public void updateUserPassword(User target, User userWithNewPassword) throws UserNotFoundException {

        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Product product;

        ModelStubWithPerson(Product product) {
            requireNonNull(product);
            this.product = product;
        }

        @Override
        public boolean hasProduct(Product person) {
            requireNonNull(person);
            return this.product.isSameProduct(product);
        }
    }

    /**
     * A Model stub that always accept the Product being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Product> personsAdded = new ArrayList<>();

        @Override
        public boolean hasProduct(Product product) {
            requireNonNull(product);
            return personsAdded.stream().anyMatch(product::isSameProduct);
        }

        @Override
        public void addProduct(Product product) {
            requireNonNull(product);
            personsAdded.add(product);
        }

        @Override
        public void commitProductDatabase() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyProductDatabase getAddressBook() {
            return new ProductDatabase();
        }
    }

}
