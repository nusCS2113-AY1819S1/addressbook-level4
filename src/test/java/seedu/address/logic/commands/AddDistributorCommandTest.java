package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import seedu.address.model.DistributorBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyDistributorBook;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.login.Password;
import seedu.address.model.login.UniqueUserList;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.product.Product;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;
import seedu.address.model.timeidentifiedclass.shopday.Reminder;
import seedu.address.model.timeidentifiedclass.transaction.Transaction;
import seedu.address.testutil.DistributorBuilder;

public class AddDistributorCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullDistributor_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddDistributorCommand(null);
    }

    @Test
    public void execute_distributorAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingDistributorAdded modelStub = new ModelStubAcceptingDistributorAdded();
        Distributor validDistributor = new DistributorBuilder().build();

        CommandResult commandResult = new AddDistributorCommand(validDistributor).execute(modelStub, commandHistory);

        assertEquals(String.format(AddDistributorCommand.MESSAGE_SUCCESS, validDistributor),
                commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validDistributor), modelStub.distributorsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateDistributor_throwsCommandException() throws Exception {
        Distributor validDistributor = new DistributorBuilder().build();
        AddDistributorCommand addDistributorCommand = new AddDistributorCommand(validDistributor);
        ModelStub modelStub = new ModelStubWithDistributor(validDistributor);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddDistributorCommand.MESSAGE_DUPLICATE_DISTRIBUTOR);
        addDistributorCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Distributor ahHuat = new DistributorBuilder().withName("Ah Huat").build();
        Distributor ahBeng = new DistributorBuilder().withName("Ah Beng").build();
        AddDistributorCommand addAhHuatCommand = new AddDistributorCommand(ahHuat);
        AddDistributorCommand addAhBengCommand = new AddDistributorCommand(ahBeng);

        // same object -> returns true
        assertTrue(addAhHuatCommand.equals(addAhHuatCommand));

        // same values -> returns true
        AddDistributorCommand addAhHuatCommandCopy = new AddDistributorCommand(ahHuat);
        assertTrue(addAhHuatCommand.equals(addAhHuatCommandCopy));

        // different types -> returns false
        assertFalse(addAhHuatCommand.equals(1));

        // null -> returns false
        assertFalse(addAhHuatCommand.equals(null));

        // different product -> returns false
        assertFalse(addAhHuatCommand.equals(addAhBengCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Product product) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDistributor(Distributor distributor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Product target, Product editedProduct) {
        }

        @Override
        public void updateDistributor(Distributor target, Distributor editedDistributor) {
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyDistributorBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getProductInfoBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyDistributorBook getDistributorInfoBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDistributor(Distributor distributor) {
            return false;
        }

        @Override
        public boolean hasPerson(Product product) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDistributor(Distributor target) {

        }

        @Override
        public void deletePerson(Product target) {
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
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
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
        public void redoDistributorBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitDistributorBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTransaction(Transaction transaction) {

        }

        @Override
        public String getDaysHistory(String day) {
            return null;
        }

        @Override
        public String getActiveDayHistory() {
            return null;
        }

        @Override
        public Transaction getLastTransaction() {
            return null;
        }

        @Override
        public void addReminder(Reminder reminder) {
            fail("This method should not be called.");
        }

        @Override
        public void removeReminder(Reminder reminder) throws InvalidTimeFormatException, NoSuchElementException {
            throw new InvalidTimeFormatException();
        }

        @Override
        public ArrayList<Reminder> getDueRemindersInActiveBusinessDayForThread() {
            return null;
        }

        public ArrayList<Reminder> getDueRemindersInActiveBusinessDay() {
            fail("This method should not be called.");
            return null;
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
        public ReadOnlyAddressBook getAddressBook() {
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
        public ReadOnlyAddressBook getUserDatabase() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUsersList(UniqueUserList uniqueUserList) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single product.
     */
    private class ModelStubWithDistributor extends ModelStub {
        private final Distributor distributor;

        ModelStubWithDistributor(Distributor distributor) {
            requireNonNull(distributor);
            this.distributor = distributor;
        }

        @Override
        public boolean hasDistributor(Distributor distributor) {
            requireNonNull(distributor);
            return this.distributor.isSameDistributor(distributor);
        }
    }

    /**
     * A Model stub that always accept the product being added.
     */
    private class ModelStubAcceptingDistributorAdded extends ModelStub {
        final ArrayList<Distributor> distributorsAdded = new ArrayList<>();

        @Override
        public boolean hasDistributor(Distributor distributor) {
            requireNonNull(distributor);
            return distributorsAdded.stream().anyMatch(distributor::isSameDistributor);
        }

        @Override
        public void addDistributor(Distributor distributor) {
            requireNonNull(distributor);
            distributorsAdded.add(distributor);
        }

        @Override
        public void commitDistributorBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyDistributorBook getDistributorInfoBook() {
            return new DistributorBook();
        }
    }

}
