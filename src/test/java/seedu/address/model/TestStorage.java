
package seedu.address.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.SalesHistoryChangedEvent;
import seedu.address.commons.events.model.UserDatabaseChangedEvent;
import seedu.address.commons.events.model.UserDeletedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.login.User;
import seedu.address.model.saleshistory.ReadOnlySalesHistory;
import seedu.address.storage.Storage;

/**
 * Acts as a dummy StorageManager
 */
public class TestStorage extends ComponentManager implements Storage {

    public TestStorage() {
    }

    @Override
    public Path getUserPrefsFilePath() {
        return null;
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return null;
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
    }

    @Override
    public Path getDistributorInfoFilePath() {
        return null;
    }

    @Override
    public Path getProductInfoBookFilePath() {
        return null;
    }

    public Path getAddressBookFilePath() {
        return null;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return null;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) {
        return null;
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
    }

    /**
     * Saves the current version of the Address Book to the hard disk.
     * Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    @Override
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
    }


    @Override
    public Path getUserDatabaseFilePath() {
        return null;
    }

    @Override
    public Optional<ReadOnlyUserDatabase> readUserDatabase() throws DataConversionException, IOException {
        return null;
    }

    @Override
    public Optional<ReadOnlyUserDatabase> readUserDatabase(Path filePath)
            throws DataConversionException, IOException {
        return null;
    }

    @Override
    public void saveUserDatabase(ReadOnlyUserDatabase userDatabase) throws IOException {
    }

    @Override
    public void saveUserDatabase(ReadOnlyUserDatabase userDatabase, Path filePath) throws IOException {
    }

    public void update(User user) {
    }

    @Override
    public void deleteAddressBook(User user) {
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
    public void handleUserDeletedEvent(UserDeletedEvent event) {

    }

    @Override
    public void handleSalesHistoryChangedEvent(SalesHistoryChangedEvent event) {

    }


    @Override
    public void handleUserDatabaseChangedEvent(UserDatabaseChangedEvent abce) {
    }
}
