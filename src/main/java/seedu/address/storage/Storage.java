package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.AddressBookLocalBackupEvent;
import seedu.address.commons.events.model.ExpenseBookChangedEvent;
import seedu.address.commons.events.model.ExpenseBookLocalBackupEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyExpenseBook;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, ExpenseBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);

    /**
     * Saves the current version of the Address Book to the hard disk as a backup.
     *   Creates the backup file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookLocalBackupEvent(AddressBookLocalBackupEvent abce);

    //=========== Expense =================================================================================
    @Override
    Path getExpenseBookFilePath();


    @Override
    Optional<ReadOnlyExpenseBook> readExpenseBook() throws DataConversionException, IOException;

    @Override
    void saveExpenseBook(ReadOnlyExpenseBook expenseBook) throws IOException;

    /**
     * Saves the current version of the Expense Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleExpenseBookChangedEvent(ExpenseBookChangedEvent abce);

    /**
     * Saves the current version of the Expense Book to the hard disk as a backup.
     *   Creates the backup file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleExpenseBookLocalBackupEvent(ExpenseBookLocalBackupEvent abce);
}
