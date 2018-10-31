package seedu.recruit;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.recruit.commons.core.Config;
import seedu.recruit.commons.core.GuiSettings;
import seedu.recruit.commons.exceptions.DataConversionException;
import seedu.recruit.commons.util.FileUtil;
import seedu.recruit.commons.util.XmlUtil;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.CompanyBook;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.ReadOnlyCandidateBook;
import seedu.recruit.model.ReadOnlyCompanyBook;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.storage.UserPrefsStorage;
import seedu.recruit.storage.XmlSerializableCandidateBook;
import seedu.recruit.storage.XmlSerializableCompanyBook;
import seedu.recruit.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path SAVE_LOCATION_FOR_CANDIDATE_BOOK_TESTING =
            TestUtil.getFilePathInSandboxFolder("sampleCandidateData.xml");
    public static final Path SAVE_LOCATION_FOR_COMPANY_BOOK_TESTING =
            TestUtil.getFilePathInSandboxFolder("sampleCompanyData.xml");
    public static final String APP_TITLE = "Test App";

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyCandidateBook> initialCandidateDataSupplier = () -> null;
    protected Supplier<ReadOnlyCompanyBook> initialCompanyDataSupplier = () -> null;
    protected Path saveCandidateFileLocation = SAVE_LOCATION_FOR_CANDIDATE_BOOK_TESTING;
    protected Path saveCompanyFileLocation = SAVE_LOCATION_FOR_COMPANY_BOOK_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyCandidateBook> initialCandidateDataSupplier,
                   Supplier<ReadOnlyCompanyBook> initialCompanyDataSupplier,
                   Path saveCandidateFileLocation, Path saveCompanyFileLocation) {
        super();
        this.initialCandidateDataSupplier = initialCandidateDataSupplier;
        this.initialCompanyDataSupplier = initialCompanyDataSupplier;
        this.saveCandidateFileLocation = saveCandidateFileLocation;
        this.saveCompanyFileLocation = saveCompanyFileLocation;

        // If some initial local candidate data has been provided, write those to the file
        if (initialCandidateDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableCandidateBook(this.initialCandidateDataSupplier.get()),
                    this.saveCandidateFileLocation);
        }

        // If some initial local company data has been provided, write those to the file
        if (initialCompanyDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableCompanyBook(this.initialCompanyDataSupplier.get()),
                    this.saveCompanyFileLocation);
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
        userPrefs.setCandidateBookFilePath(saveCandidateFileLocation);
        userPrefs.setCompanyBookFilePath(saveCompanyFileLocation);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the candidate book data stored inside the storage file.
     */
    public CandidateBook readStorageCandidateBook() {
        try {
            return new CandidateBook(storage.readCandidateBook().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the CandidateBook format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns a defensive copy of the company book data stored inside the storage file.
     */
    public CompanyBook readStorageCompanyBook() {
        try {
            return new CompanyBook(storage.readCompanyBook().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the CompanyBook format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns the file path of the candidate book in the storage file.
     */
    public Path getCandidateStorageSaveLocation() {
        return storage.getCandidateBookFilePath();
    }

    /**
     * Returns the file path of the company book in the storage file
     */
    public Path getCompanyStorageSaveLocation() {
        return storage.getCompanyBookFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager((model.getCandidateBook()), model.getCompanyBook(), new UserPrefs());
        ModelHelper.setCandidateFilteredList(copy, model.getFilteredCandidateList());
        ModelHelper.setCompanyFilteredList(copy, model.getFilteredCompanyList());
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
