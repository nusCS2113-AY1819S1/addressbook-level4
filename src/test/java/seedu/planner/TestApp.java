package seedu.planner;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.planner.commons.core.Config;
import seedu.planner.commons.core.GuiSettings;
import seedu.planner.commons.exceptions.DataConversionException;
import seedu.planner.commons.util.FileUtil;
import seedu.planner.commons.util.XmlUtil;
import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.UserPrefs;
import seedu.planner.storage.UserPrefsStorage;
import seedu.planner.storage.XmlSerializableLimitList;
import seedu.planner.storage.xmljaxb.XmlSerializableFinancialPlanner;
import seedu.planner.storage.xmljaxb.XmlSerializableSummaryMap;
import seedu.planner.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path RECORD_LIST_LOCATION_FOR_TESTING = TestUtil
            .getFilePathInSandboxFolder("sampleRecordListData.xml");
    public static final Path SUMMARY_MAP_LOCATION_FOR_TESTING = TestUtil
            .getFilePathInSandboxFolder("sampleSummaryMapData.xml");
    public static final Path LIMIT_LIST_LOCATION_FOR_TESTING = TestUtil
            .getFilePathInSandboxFolder("sampleLimitListData.xml");

    public static final String APP_TITLE = "Test App";

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyFinancialPlanner> initialDataSupplier = () -> null;

    protected Path recordListSaveLocation = RECORD_LIST_LOCATION_FOR_TESTING;
    protected Path summaryMapSaveLocation = SUMMARY_MAP_LOCATION_FOR_TESTING;
    protected Path limitListSaveLocation = LIMIT_LIST_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyFinancialPlanner> initialDataSupplier, Path recordListSaveFileLocation,
                   Path limitListSaveFileLocation, Path summaryListSaveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.recordListSaveLocation = recordListSaveFileLocation;
        this.summaryMapSaveLocation = summaryListSaveFileLocation;
        this.limitListSaveLocation = limitListSaveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableFinancialPlanner(this.initialDataSupplier.get()),
                    this.recordListSaveLocation);
            createDataFileWithData(new XmlSerializableLimitList(this.initialDataSupplier.get()),
                    this.limitListSaveLocation);
            createDataFileWithData(new XmlSerializableSummaryMap(this.initialDataSupplier.get()),
                    this.summaryMapSaveLocation);
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
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(600.0, 600.0, (int) x, (int) y));
        userPrefs.setFinancialPlannerFilePath(recordListSaveLocation);
        userPrefs.setSummaryMapFilePath(summaryMapSaveLocation);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the planner book data stored inside the storage file.
     */
    public FinancialPlanner readStorageFinancialPlanner() {
        try {
            return new FinancialPlanner(storage.readFinancialPlanner().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the FinancialPlanner format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns the file path of the storage file.
     */
    public Path getRecordStorageSaveLocation() {
        return storage.getRecordListFilePath();
    }

    /**
     * Returns the file path of the storage file.
     */
    public Path getSummaryStorageSaveLocation() {
        return storage.getSummaryMapFilePath();
    }

    /**
     * Returns the file path of the storage file.
     */
    public Path getLimitStorageSaveLocation() {
        return storage.getLimitListFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager((model.getFinancialPlanner()), new UserPrefs());
        ModelHelper.setFilteredList(copy, model.getFilteredRecordList());
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
