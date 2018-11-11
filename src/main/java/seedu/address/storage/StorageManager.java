package seedu.address.storage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

import com.google.common.eventbus.Subscribe;

import javafx.concurrent.Task;
import javafx.util.Duration;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.AddressBookLocalRestoreEvent;
import seedu.address.commons.events.model.AddressBookOnlineRestoreEvent;
//import seedu.address.commons.events.model.BooksLocalBackupEvent;
import seedu.address.commons.events.model.EventBookChangedEvent;
import seedu.address.commons.events.model.EventBookLocalRestoreEvent;
import seedu.address.commons.events.model.EventBookOnlineRestoreEvent;
import seedu.address.commons.events.model.ExpenseBookChangedEvent;
import seedu.address.commons.events.model.ExpenseBookLocalRestoreEvent;
import seedu.address.commons.events.model.ExpenseBookOnlineRestoreEvent;
import seedu.address.commons.events.model.TaskBookChangedEvent;
import seedu.address.commons.events.model.TaskBookLocalRestoreEvent;
import seedu.address.commons.events.model.TaskBookOnlineRestoreEvent;
import seedu.address.commons.events.model.UserPrefsChangedEvent;
import seedu.address.commons.events.storage.DataRestoreExceptionEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.storage.LocalBackupEvent;
import seedu.address.commons.events.storage.LocalRestoreEvent;
import seedu.address.commons.events.storage.OnlineBackupEvent;
import seedu.address.commons.events.storage.OnlineBackupSuccessResultEvent;
import seedu.address.commons.events.storage.OnlineRestoreEvent;
import seedu.address.commons.events.ui.NewNotificationAvailableEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.OnlineBackupFailureException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.ExpenseBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.ReadOnlyExpenseBook;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.TaskBook;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private EventBookStorage eventBookStorage;
    private ExpenseBookStorage expenseBookStorage;
    private TaskBookStorage taskBookStorage;
    private UserPrefsStorage userPrefsStorage;

    private GithubStorage githubStorage;

    public StorageManager(AddressBookStorage addressBookStorage,
                          ExpenseBookStorage expenseBookStorage,
                          EventBookStorage eventBookStorage,
                          TaskBookStorage taskBookStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.eventBookStorage = eventBookStorage;
        this.expenseBookStorage = expenseBookStorage;
        this.taskBookStorage = taskBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    //@@author QzSG
    @Subscribe
    public void handleUserPrefsChangedEvent(UserPrefsChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "User Preferences changed, saving to file"));
        try {
            saveUserPrefs(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    //@@author


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook, Path backupFilePath) throws IOException {
        logger.fine("Attempting to backup address book data file: " + backupFilePath);
        addressBookStorage.backupAddressBook(addressBook, backupFilePath);
    }

    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    //@@author QzSG

    @Subscribe
    public void handleLocalBackupEvent(LocalBackupEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Saving student planner data as backup"));
        backupLocal(event.readOnlyAddressBook, event.readOnlyEventBook, event.readOnlyExpenseBook,
                event.readOnlyTaskBook, event.addressBookPath, event.eventBookPath,
                event.expenseBookPath, event.taskBookPath);
    }

    /**
     * Performs local backup to local storage
     * @param addressData  {@code ReadOnlyAddressBook} addressData
     * @param expenseData  {@code ReadOnlyExpenseBook} expenseData
     * @param taskData  {@code ReadOnlyExpenseBook} expenseData
     * @param addressBookPath Location to save address data to
     * @param expenseBookPath Location to save expense data to
     * @param taskBookPath Location to save task data to
     */
    private void backupLocal(ReadOnlyAddressBook addressData, ReadOnlyEventBook eventData,
                             ReadOnlyExpenseBook expenseData, ReadOnlyTaskBook taskData,
                             Path addressBookPath, Path eventBookPath,
                             Path expenseBookPath, Path taskBookPath) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(getLocalBackupTask(addressData, eventData, expenseData, taskData,
                addressBookPath, eventBookPath, expenseBookPath, taskBookPath));
    }

    /**
     * Creates a local backup tasks and returns the created task.
     * @param addressData  {@code ReadOnlyAddressBook} addressData
     * @param expenseData  {@code ReadOnlyExpenseBook} expenseData
     * @param addressBookPath Location to save address data to
     * @param expenseBookPath Location to save expense data to
     * @return Local Backup Task
     */
    private Task getLocalBackupTask(ReadOnlyAddressBook addressData, ReadOnlyEventBook eventData,
                                    ReadOnlyExpenseBook expenseData, ReadOnlyTaskBook taskData,
                                    Path addressBookPath, Path eventBookPath,
                                    Path expenseBookPath, Path taskBookPath) {
        Task backupTask = new Task<Void>() {
            @Override public Void call() throws Exception {
                backupAddressBook(addressData, addressBookPath);
                backupEventBook(eventData, eventBookPath);
                backupExpenseBook(expenseData, expenseBookPath);
                backupTaskBook(taskData, taskBookPath);
                return null;
            }
        };
        backupTask.setOnSucceeded(event -> {
            raise(new NewNotificationAvailableEvent("Backup Operation", "Local Backup succeeded!",
                    Optional.ofNullable(Duration.seconds(5))));
        });
        backupTask.setOnFailed(event -> {
            raise(new DataSavingExceptionEvent((Exception) backupTask.getException()));
        });
        return backupTask;
    }

    /*
    Listens directly to RestoreCommand
    */
    @SuppressWarnings("unused")
    @Subscribe
    public void handleLocalRestoreEvent(LocalRestoreEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Retrieving student planner data from storage"));
        try {
            ReadOnlyAddressBook restoredReadOnlyAddressBook = readAddressBook(event.addressBookPath).get();
            ReadOnlyEventBook restoredReadOnlyEventBook = readEventBook(event.eventBookPath).get();
            ReadOnlyExpenseBook restoredReadOnlyExpenseBook = readExpenseBook(event.expenseBookPath).get();
            ReadOnlyTaskBook restoredReadOnlyTaskBook = readTaskBook(event.taskBookPath).get();
            raise(new AddressBookLocalRestoreEvent(restoredReadOnlyAddressBook));
            raise(new EventBookLocalRestoreEvent(restoredReadOnlyEventBook));
            raise(new ExpenseBookLocalRestoreEvent(restoredReadOnlyExpenseBook));
            raise(new TaskBookLocalRestoreEvent(restoredReadOnlyTaskBook));
        } catch (IOException | DataConversionException | NoSuchElementException e) {
            raise(new DataRestoreExceptionEvent(e));
        }
    }

    // ================ GitHub Storage methods ==============================
    /*
        Listens directly to BackupCommand
     */
    @SuppressWarnings("unused")
    @Subscribe
    public void handleOnlineBackupEvent(OnlineBackupEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Saving data to online storage"));
        backupOnline(event.target, event.addressData, event.eventData, event.expenseData,
                event.taskData, event.authToken);
    }

    /*
        Listens directly to RestoreCommand
     */
    @SuppressWarnings("unused")
    @Subscribe
    public void handleOnlineRestoreEvent(OnlineRestoreEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Restoring data from online storage"));
        if (event.ref.trim().isEmpty()) {
            raise(new DataRestoreExceptionEvent( new IllegalValueException("Reference field should not be empty")));
        }
        restoreOnline(event.target, event.targetBook, event.ref, event.authToken);
    }

    /**
     * Performs online backup to supported online storage
     * @param target {@code OnlineStorage.Type} such as GITHUB
     * @param addressData  {@code ReadOnlyAddressBook} addressData
     * @param expenseData  {@code ReadOnlyExpenseBook} expenseData
     * @param authToken Personal Access Token for GitHub Authentication
     */
    private void backupOnline(OnlineStorage.Type target, ReadOnlyAddressBook addressData, ReadOnlyEventBook eventData,
                              ReadOnlyExpenseBook expenseData, ReadOnlyTaskBook taskData,
                              Optional<String> authToken) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(getOnlineBackupTask(target, addressData, "AddressBook.bak", authToken));
        executorService.submit(getOnlineBackupTask(target, eventData, "EventBook.bak", authToken));
        executorService.submit(getOnlineBackupTask(target, expenseData, "ExpenseBook.bak", authToken));
        executorService.submit(getOnlineBackupTask(target, taskData, "TaskBook.bak", authToken));
    }

    /**
     * Performs restoration from supported online storage
     * @param target {@code OnlineStorage.Type} such as GITHUB
     * @param ref   Reference String to uniquely identify a file or a url to the backup resource.
     * @param authToken JWT or any other form of access token required by specific online backup service
     */
    private void restoreOnline(OnlineStorage.Type target, UserPrefs.TargetBook targetBook,
                               String ref, Optional<String> authToken) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Task restoreTask = getOnlineRestoreTask(target, targetBook, ref, authToken);

        executorService.submit(restoreTask);
    }

    /**
     * Performs restoration from local storage
     * @param path File path to local backup
     */
    private void restoreLocal(Path path) {
        //TODO
    }

    private Task getOnlineRestoreTask(OnlineStorage.Type target, UserPrefs.TargetBook targetBook,
                                      String ref, Optional<String> authToken) {
        Task restoreTask = new Task<>() {
            @Override public Object call() throws Exception {
                switch(target) {
                    case GITHUB:
                    default:
                        githubStorage = new GithubStorage(Optional.empty());
                        if (targetBook == UserPrefs.TargetBook.AddressBook) {
                            AddressBook restoredAddressBook = XmlUtil.getDataFromString(
                                    githubStorage.readContentFromStorage(targetBook, ref),
                                    XmlSerializableAddressBook.class).toModelType();
                            return restoredAddressBook;
                        }
                        if (targetBook == UserPrefs.TargetBook.EventBook) {
                            EventBook restoredEventBook = XmlUtil.getDataFromString(
                                    githubStorage.readContentFromStorage(targetBook, ref),
                                    XmlSerializableEventBook.class).toModelType();
                            return restoredEventBook;
                        }
                        if (targetBook == UserPrefs.TargetBook.ExpenseBook) {
                            ExpenseBook restoredExpenseBook = XmlUtil.getDataFromString(
                                    githubStorage.readContentFromStorage(targetBook, ref),
                                    XmlSerializableExpenseBook.class).toModelType();
                            return restoredExpenseBook;
                        }
                        if (targetBook == UserPrefs.TargetBook.TaskBook) {
                            TaskBook restoredTaskBook = XmlUtil.getDataFromString(
                                    githubStorage.readContentFromStorage(targetBook, ref),
                                    XmlSerializableTaskBook.class).toModelType();
                            return restoredTaskBook;
                        } else {
                            throw (new IllegalValueException("Invalid book data"));
                        }
                }
            }
        };
        restoreTask.setOnSucceeded(event -> {
            if (targetBook == UserPrefs.TargetBook.AddressBook) {
                raise(new AddressBookOnlineRestoreEvent(((Task<AddressBook>) restoreTask).getValue()));
            }
            if (targetBook == UserPrefs.TargetBook.EventBook) {
                raise(new EventBookOnlineRestoreEvent(((Task<EventBook>) restoreTask).getValue()));
            }
            if (targetBook == UserPrefs.TargetBook.ExpenseBook) {
                raise(new ExpenseBookOnlineRestoreEvent(((Task<ExpenseBook>) restoreTask).getValue()));
            }
            if (targetBook == UserPrefs.TargetBook.TaskBook) {
                raise(new TaskBookOnlineRestoreEvent(((Task<TaskBook>) restoreTask).getValue()));
            }


        });
        restoreTask.setOnFailed(event -> {
            raise(new NewResultAvailableEvent("Online Restore Failed"));
            raise(new DataRestoreExceptionEvent((Exception) restoreTask.getException()));
        });
        return restoreTask;
    }

    /**
     * Creates an online backup tasks based on {@code OnlineStorage.Type} and returns the created task.
     * @param target {@code OnlineStorage.Type} such as GITHUB
     * @param data  {@code Object} data
     * @param fileName Name of save backup file
     * @param authToken Personal Access Token for GitHub Authentication
     * @return
     */
    private Task getOnlineBackupTask(OnlineStorage.Type target, Object data, String fileName,
                                     Optional<String> authToken) {
        Task backupTask = new Task<OnlineBackupSuccessResultEvent>() {
            @Override public OnlineBackupSuccessResultEvent call() throws Exception {
                switch(target) {
                    case GITHUB:
                    default:
                        githubStorage = new GithubStorage(
                                Optional.ofNullable(authToken).orElseThrow(() -> new OnlineBackupFailureException(
                                        "Invalid authentication token received")));
                        URL url = githubStorage.saveContentToStorage(handleBookData(data), fileName,
                                "Student Book Backup");
                        String successMessage = GithubStorage.SUCCESS_MESSAGE;
                        updateMessage(successMessage);
                        String ref = url.getPath().substring(1);
                        return new OnlineBackupSuccessResultEvent(OnlineStorage.Type.GITHUB,
                                handleUserPrefsUpdateField(data), ref);
                }
            }
        };
        backupTask.setOnSucceeded(event -> {
            raise(new NewResultAvailableEvent(backupTask.getMessage()));
            raise((OnlineBackupSuccessResultEvent) backupTask.getValue());
        });
        backupTask.setOnFailed(event -> {
            raise(new NewResultAvailableEvent("Backup Failed"));
            raise(new DataSavingExceptionEvent((Exception) backupTask.getException()));
        });
        return backupTask;
    }

    /**
     * Returns the proper xml to string content based on the type of book data
     * @param data
     * @return Book data serialised as a string
     * @throws IllegalValueException
     * @throws JAXBException
     */
    private String handleBookData(Object data) throws IllegalValueException, JAXBException {
        if (data instanceof ReadOnlyAddressBook) {
            return XmlUtil.convertDataToString(
                    new XmlSerializableAddressBook((ReadOnlyAddressBook) data));
        }
        if (data instanceof ReadOnlyEventBook) {
            return XmlUtil.convertDataToString(
                    new XmlSerializableEventBook((ReadOnlyEventBook) data));
        }
        if (data instanceof ReadOnlyExpenseBook) {
            return XmlUtil.convertDataToString(
                    new XmlSerializableExpenseBook((ReadOnlyExpenseBook) data));
        }
        if (data instanceof ReadOnlyTaskBook) {
            return XmlUtil.convertDataToString(
                    new XmlSerializableTaskBook((ReadOnlyTaskBook) data));
        }
        throw (new IllegalValueException("Invalid data provided"));
    }

    /**
     * Converts data object to its book data type used to update specific User Preference fields
     * @param data
     * @return Type of data book
     * @throws IllegalValueException
     * @throws JAXBException
     */
    private UserPrefs.TargetBook handleUserPrefsUpdateField(Object data) throws IllegalValueException, JAXBException {
        if (data instanceof ReadOnlyAddressBook) {
            return UserPrefs.TargetBook.AddressBook;
        }
        if (data instanceof ReadOnlyEventBook) {
            return UserPrefs.TargetBook.EventBook;
        }
        if (data instanceof ReadOnlyExpenseBook) {
            return UserPrefs.TargetBook.ExpenseBook;
        }
        if (data instanceof ReadOnlyTaskBook) {
            return UserPrefs.TargetBook.TaskBook;
        }
        throw (new IllegalValueException("Invalid data provided"));
    }
    //@@author
    //============ Expense ===============================================================================

    @Override
    public Optional<ReadOnlyExpenseBook> readExpenseBook() throws DataConversionException, IOException {
        return readExpenseBook(expenseBookStorage.getExpenseBookFilePath());
    }

    @Override
    public Optional<ReadOnlyExpenseBook> readExpenseBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return expenseBookStorage.readExpenseBook(filePath);
    }

    @Override
    public Path getExpenseBookFilePath() {
        return expenseBookStorage.getExpenseBookFilePath();
    }

    @Override
    public void saveExpenseBook(ReadOnlyExpenseBook expenseBook) throws IOException {
        saveExpenseBook(expenseBook, expenseBookStorage.getExpenseBookFilePath());
    }

    @Override
    public void saveExpenseBook(ReadOnlyExpenseBook expenseBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        expenseBookStorage.saveExpenseBook(expenseBook, filePath);
    }

    @Override
    public void backupExpenseBook(ReadOnlyExpenseBook expenseBook, Path backupFilePath) throws IOException {
        logger.fine("Attempting to backup expense book data file: " + backupFilePath);
        expenseBookStorage.backupExpenseBook(expenseBook, backupFilePath);
    }

    @Override
    @Subscribe
    public void handleExpenseBookChangedEvent(ExpenseBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveExpenseBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    /*
    @Override
    @Subscribe
    public void handleExpenseBookLocalBackupEvent(ExpenseBookLocalBackupEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Saving student planner data as backup"));
        try {
            backupExpenseBook(event.data, event.filePath);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    */

    //===========Events======================
    @Override
    public Optional<ReadOnlyEventBook> readEventBook() throws DataConversionException, IOException {
        return readEventBook(eventBookStorage.getEventBookFilePath());
    }

    @Override
    public Optional<ReadOnlyEventBook> readEventBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return eventBookStorage.readEventBook(filePath);
    }

    @Override
    public Path getEventBookFilePath() {
        return eventBookStorage.getEventBookFilePath();
    }

    @Override
    public void saveEventBook(ReadOnlyEventBook eventBook) throws IOException {
        saveEventBook(eventBook, eventBookStorage.getEventBookFilePath());
    }

    @Override
    public void saveEventBook(ReadOnlyEventBook eventBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        eventBookStorage.saveEventBook(eventBook, filePath);
    }

    @Override
    public void backupEventBook(ReadOnlyEventBook eventBook, Path backupFilePath) throws IOException {
        logger.fine("Attempting to backup event book data file: " + backupFilePath);
        eventBookStorage.backupEventBook(eventBook, backupFilePath);
    }

    @Override
    @Subscribe
    public void handleEventBookChangedEvent(EventBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveEventBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    //============== Task ================================================================================
    @Override
    public Path getTaskBookFilePath() {
        return taskBookStorage.getTaskBookFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException {
        return readTaskBook(taskBookStorage.getTaskBookFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskBook> readTaskBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskBookStorage.readTaskBook(filePath);
    }

    @Override
    public void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException {
        saveTaskBook(taskBook, taskBookStorage.getTaskBookFilePath());
    }

    @Override
    public void saveTaskBook(ReadOnlyTaskBook taskBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskBookStorage.saveTaskBook(taskBook, filePath);
    }

    @Override
    public void backupTaskBook(ReadOnlyTaskBook taskBook, Path backupFilePath) throws IOException {
        taskBookStorage.backupTaskBook(taskBook, backupFilePath);
    }

    //@@author QzSG
    @Override
    @Subscribe
    public void handleTaskBookChangedEvent(TaskBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTaskBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    //@@author QzSG
}
