package seedu.address.logic.commands;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.events.model.DistributorBookChangedEvent;
import seedu.address.commons.events.model.ProductDatabaseChangedEvent;
import seedu.address.commons.events.model.SalesHistoryChangedEvent;
import seedu.address.commons.events.model.UserDatabaseChangedEvent;
import seedu.address.commons.events.model.UserDeletedEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyDistributorBook;
import seedu.address.model.ReadOnlyProductDatabase;
import seedu.address.model.ReadOnlyUserDatabase;
import seedu.address.model.UserPrefs;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.login.User;
import seedu.address.model.product.Product;
import seedu.address.model.saleshistory.ReadOnlySalesHistory;
import seedu.address.model.timeidentifiedclass.Reminder;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;
import seedu.address.storage.Storage;

public class AddReminderCommandTest {

    private static final String FAILURE_METHOD_MESSAGE = "This method should not be called.";
    private Model model = new ModelManager(new ReadOnlyProductDatabaseStub(), new ReadOnlyDistributorBookStub(),
            new UserPrefsStub(), new ReadOnlyUserDatabaseStub(), new StorageStub());

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullReminder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddReminderCommand(null);
    }


    // Stub class declarations
    private class ReadOnlyProductDatabaseStub implements ReadOnlyProductDatabase {
        @Override
        public ObservableList<Product> getProductList() {
            fail(FAILURE_METHOD_MESSAGE);
            return null;
        }
    }

    private class ReadOnlyDistributorBookStub implements ReadOnlyDistributorBook {
        @Override
        public ObservableList<Distributor> getDistributorList() {
            fail(FAILURE_METHOD_MESSAGE);
            return null;
        }
    }

    private class UserPrefsStub extends UserPrefs {

        public UserPrefsStub() {
            //do nothing
        }

        @Override
        public GuiSettings getGuiSettings() {
            fail(FAILURE_METHOD_MESSAGE);
            return null;
        }

        @Override
        public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
            fail(FAILURE_METHOD_MESSAGE);
        }

        @Override
        public void setGuiSettings(double width, double height, int x, int y) {
            super.setGuiSettings(width, height, x, y);
        }

        @Override
        public Path getAddressBookFilePath() {
            fail(FAILURE_METHOD_MESSAGE);
            return null;
        }

        @Override
        public Path getSalesHistoryFilePath() {
            fail(FAILURE_METHOD_MESSAGE);
            return null;
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            fail(FAILURE_METHOD_MESSAGE);
        }

        @Override
        public Path getDistributorBookFilePath() {
            fail(FAILURE_METHOD_MESSAGE);
            return null;
        }

        @Override
        public void setDistributorBookFilePath(Path addressBookFilePath) {
            fail(FAILURE_METHOD_MESSAGE);
        }

        @Override
        public Path getUsersFilePath() {
            fail(FAILURE_METHOD_MESSAGE);
            return null;
        }

        @Override
        public void setUsersFilePath(Path usersFilePath) {
            fail(FAILURE_METHOD_MESSAGE);
        }

        @Override
        public boolean equals(Object other) {
            return super.equals(other);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    private class ReadOnlyUserDatabaseStub implements ReadOnlyUserDatabase {

        public ReadOnlyUserDatabaseStub() {
            // do nothing
        }

        @Override
        public ObservableList<User> getUsersList() {
            fail(FAILURE_METHOD_MESSAGE);
            return null;
        }
    }

    /**
     * This is a storage stub that does nothing.
     */
    private class StorageStub implements Storage {

        public StorageStub() {
            // do nothing.
        }

        @Override
        public Path getUserPrefsFilePath() {
            return null;
        }

        @Override
        public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
            return Optional.empty();
        }

        @Override
        public void saveUserPrefs(UserPrefs userPrefs) throws IOException {

        }

        @Override
        public Path getProductInfoBookFilePath() {
            return null;
        }

        @Override
        public Path getDistributorBookFilePath() {
            return null;
        }

        @Override
        public Optional<ReadOnlyProductDatabase> readAddressBook() throws DataConversionException, IOException {
            return Optional.empty();
        }

        @Override
        public Optional<ReadOnlyProductDatabase> readAddressBook(Path filePath) throws DataConversionException,
                IOException {
            return Optional.empty();
        }

        @Override
        public Optional<ReadOnlyDistributorBook> readDistributorBook() throws DataConversionException,
                IOException {
            return Optional.empty();
        }

        @Override
        public Optional<ReadOnlyDistributorBook> readDistributorBook(Path filePath) throws DataConversionException {
            return Optional.empty();
        }

        @Override
        public void saveAddressBook(ReadOnlyProductDatabase addressBook) throws IOException {

        }

        @Override
        public void saveAddressBook(ReadOnlyProductDatabase addressBook, Path filePath) throws IOException {

        }

        @Override
        public void saveDistributorBook(ReadOnlyDistributorBook distributorBook) throws IOException {

        }

        @Override
        public void saveDistributorBook(ReadOnlyDistributorBook distributorBook, Path filePath) throws IOException {

        }

        @Override
        public void handleAddressBookChangedEvent(ProductDatabaseChangedEvent abce) {

        }

        @Override
        public void handleDistributorBookChangedEvent(DistributorBookChangedEvent abce) {

        }

        @Override
        public void handleUserDatabaseChangedEvent(UserDatabaseChangedEvent abce) {

        }

        @Override
        public void handleUserDeletedEvent(UserDeletedEvent event) throws IOException {

        }

        @Override
        public void handleSalesHistoryChangedEvent(SalesHistoryChangedEvent event) {

        }

        @Override
        public Path getUserDatabaseFilePath() {
            return null;
        }

        @Override
        public Optional<ReadOnlyUserDatabase> readUserDatabase() throws DataConversionException, IOException {
            return Optional.empty();
        }

        @Override
        public Optional<ReadOnlyUserDatabase> readUserDatabase(Path filePath) throws DataConversionException, IOException {
            return Optional.empty();
        }

        @Override
        public void saveUserDatabase(ReadOnlyUserDatabase userDatabase) throws IOException {

        }

        @Override
        public void saveUserDatabase(ReadOnlyUserDatabase userDatabase, Path filePath) throws IOException {

        }

        @Override
        public void deleteAddressBook(User user) throws IOException {

        }

        @Override
        public Path getSalesHistoryFilePath() {
            return null;
        }

        @Override
        public Optional<ReadOnlySalesHistory> readSalesHistory() throws DataConversionException, IOException {
            return Optional.empty();
        }

        @Override
        public Optional<ReadOnlySalesHistory> readSalesHistory(Path filePath) throws DataConversionException, IOException {
            return Optional.empty();
        }

        @Override
        public void saveSalesHistory(ReadOnlySalesHistory salesHistory) throws IOException {

        }

        @Override
        public void saveSalesHistory(ReadOnlySalesHistory salesHistory, Path filePath) throws IOException {

        }

        @Override
        public void deleteSalesHistory() throws IOException {

        }

        @Override
        public void deleteDistributorBook(User user) throws IOException {

        }

        @Override
        public void update(User user) {

        }
    }

    private class ValidReminderStub extends Reminder {
        /**
         * An example of valid {@code Reminder} time and message. For more details on what constitutes a valid time
         * and message, please refer to {@link Reminder}
         */
        private static final String VALID_TIME = "2018/11/11 12:00:00";
        private static final String VALID_MESSAGE = "Can be anything except null.";

        public ValidReminderStub() throws InvalidTimeFormatException {
            super(VALID_TIME, VALID_MESSAGE);
        }
    }
}
