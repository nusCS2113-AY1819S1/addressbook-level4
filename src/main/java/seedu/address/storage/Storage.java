package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.AddressBookChangedEvent;
//import seedu.address.commons.events.model.BooksLocalBackupEvent;
import seedu.address.commons.events.model.EventBookChangedEvent;
import seedu.address.commons.events.model.ExpenseBookChangedEvent;
import seedu.address.commons.events.model.TaskBookChangedEvent;
import seedu.address.commons.events.model.UserPrefsChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.storage.LocalBackupEvent;
import seedu.address.commons.events.storage.LocalRestoreEvent;
import seedu.address.commons.events.storage.OnlineBackupEvent;
import seedu.address.commons.events.storage.OnlineRestoreEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.ReadOnlyExpenseBook;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, EventBookStorage, ExpenseBookStorage,
        TaskBookStorage, UserPrefsStorage {
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

    void handleUserPrefsChangedEvent(UserPrefsChangedEvent upce);

    void handleOnlineBackupEvent(OnlineBackupEvent obe);

    void handleLocalBackupEvent(LocalBackupEvent lbe);

    void handleOnlineRestoreEvent(OnlineRestoreEvent ore);

    void handleLocalRestoreEvent(LocalRestoreEvent lre);

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
    //void handleExpenseBookLocalBackupEvent(ExpenseBookLocalBackupEvent abce);

    //=====================Events===========================
    @Override
    Path getEventBookFilePath();

    @Override
    Optional<ReadOnlyEventBook> readEventBook() throws DataConversionException, IOException;

    @Override
    void saveEventBook(ReadOnlyEventBook eventBook) throws IOException;

    /**
     * Saves the current version of the Event Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleEventBookChangedEvent(EventBookChangedEvent abce);

    //=========== Task ===================================================================================
    @Override
    Path getTaskBookFilePath();

    @Override
    Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException;

    @Override
    void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException;

    //@@author QzSG
    /**
     * Saves the current version of the Task Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskBookChangedEvent(TaskBookChangedEvent tbce);

}
