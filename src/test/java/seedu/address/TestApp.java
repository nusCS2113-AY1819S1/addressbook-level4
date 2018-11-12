package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.EventList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventList;
import seedu.address.model.UserPrefs;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlSerializableAddressBook;
import seedu.address.storage.XmlSerializableEventList;
import seedu.address.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.xml");
    public static final Path SAVE_LOCATION_FOR_TESTING_EVENT =
            TestUtil.getFilePathInSandboxFolder("sampleEventData.xml");
    public static final String APP_TITLE = "Test App";

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyAddressBook> initialDataSupplier = () -> null;
    protected Supplier<ReadOnlyEventList> initialEventDataSupplier = () -> null;
    protected Path saveFileLocation = SAVE_LOCATION_FOR_TESTING;
    protected Path saveEventFileLocation = SAVE_LOCATION_FOR_TESTING_EVENT;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyAddressBook> initialDataSupplier,
                   Supplier<ReadOnlyEventList> initialEventDataSupplier,
                   Path saveFileLocation, Path saveEventFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.initialEventDataSupplier = initialEventDataSupplier;
        this.saveFileLocation = saveFileLocation;
        this.saveEventFileLocation = saveEventFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableAddressBook(this.initialDataSupplier.get()),
                    this.saveFileLocation);
        }
        if (initialEventDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableEventList(this.initialEventDataSupplier.get()),
                    this.saveEventFileLocation);
        }
    }

    @Override
    protected Config initConfig(Path configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setAppTitle(APP_TITLE);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(700.0, 600.0, (int) x, (int) y));
        userPrefs.setAddressBookFilePath(saveFileLocation);
        userPrefs.setEventlistPath(saveEventFileLocation);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the address book data stored inside the storage file.
     */
    public AddressBook readStorageAddressBook() {
        try {
            return new AddressBook(storage.readAddressBook().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the AddressBook format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns a defensive copy of the event list data stored inside the storage file.
     */
    public EventList readStorageEventList() {
        try {
            return new EventList(storage.readEventList().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the EventList format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns the file path of the storage file for addressbook.
     */
    public Path getStorageSaveLocation() {
        return storage.getAddressBookFilePath();
    }

    /**
     * Returns the file path of the storage file for eventlist.
     */
    public Path getEventStorageSaveLocation() {
        return storage.getEventListFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager((model.getAddressBook()), (model.getEventList()), new UserPrefs());
        ModelHelper.setFilteredList(copy, model.getFilteredPersonList());
        ModelHelper.setEventFilteredList(copy, model.getFilteredEventList());
        return copy;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates an XML file at the {@code filePath} with the {@code data}.
     */
    private <T> void createDataFileWithData(T data, Path filePath) {
        try {
            FileUtil.createIfMissing(filePath);
            XmlUtil.saveDataToFile(filePath, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
