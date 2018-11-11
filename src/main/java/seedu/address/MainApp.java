//@@author liu-tianhang
package seedu.address;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.LogoutEvent;
import seedu.address.commons.events.ui.StopUiEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.init.InventoryListInitializer;
import seedu.address.logic.Logic;
import seedu.address.model.LoginInfoManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.InventoryListStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlInventoryListStorage;
import seedu.address.storage.logininfo.JsonLoginInfoStorage;
import seedu.address.storage.logininfo.LoginInfoStorage;
import seedu.address.storage.transactions.TransactionListStorage;
import seedu.address.storage.transactions.XmlTransactionListStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiPart;
import seedu.address.ui.controller.LoginController;


/**
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 7, 0, true);
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Config config;
    protected UserPrefs userPrefs;
    protected Stage loginWindow;
    private FXMLLoader fxmlLoader;
    private LoginInfoManager loginInfoList;
    private LoginController loginController;
    private String loginPathPath;
    private InventoryListInitializer inventoryListInitializer;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing DRINK I/O ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        LoginInfoStorage loginInfoStorage = new JsonLoginInfoStorage(config.getUserLoginInfoFilePath());
        loginInfoList = initLoginInfo(loginInfoStorage);
        loginPathPath = config.getLoginPagePath().toString();
        InventoryListStorage inventoryListStorage = new XmlInventoryListStorage(userPrefs.getInventoryListFilePath());
        TransactionListStorage transactionListStorage =
                new XmlTransactionListStorage(userPrefs.getTransactionListFilePath());
        storage = new StorageManager(inventoryListStorage, userPrefsStorage, loginInfoStorage, transactionListStorage);

        inventoryListInitializer = new InventoryListInitializer(config, storage, userPrefs, loginInfoList);
        initLogging(config);
        fxmlLoader = new FXMLLoader();
        initEventsCenter();

    }

    @Override
    public void start(Stage primaryStage) {
        loginWindow = primaryStage;
        logger.info("Starting Drink I/O " + MainApp.VERSION);
        showLoginPage();
    }

    @Override
    public void stop() {
        saveUserPrefs ();
        saveLoginInfo();
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code LoginInfoManager} using the file at {@code storage}'s login info file path,
     * or a new {@code LoginInfoManager} with default configuration if errors occur when
     * reading from the file.
     */
    protected LoginInfoManager initLoginInfo(LoginInfoStorage storage) {
        Path loginInfoFilePath = storage.getLoginInfoFilePath();
        logger.info("Using Login information file : " + loginInfoFilePath);

        LoginInfoManager initLoginInfoManager;
        try {
            Optional<LoginInfoManager> loginInfoOptional = storage.readLoginInfo();
            initLoginInfoManager = loginInfoOptional.orElse(new LoginInfoManager());
        } catch (DataConversionException e) {
            logger.warning("Login Info file at " + loginInfoFilePath + " is not in the correct format. "
                    + "Using empty database");
            initLoginInfoManager = new LoginInfoManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Please find ADMIN");
            initLoginInfoManager = new LoginInfoManager();
        } catch (Exception e) {
            e.fillInStackTrace();
            initLoginInfoManager = new LoginInfoManager();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveLoginInfo(initLoginInfoManager);
        } catch (IOException e) {
            logger.warning("Failed to save LoginInfoManager file : " + StringUtil.getDetails(e));
        }

        return initLoginInfoManager;
    }


    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }


    /**
     * Start Login scene
     */
    private void showLoginPage() {
        settingUpLoginWindow();
        settingUpLoginController();
    }

    private void settingUpLoginWindow() {
        URL fxmlLoginFileUrl = UiPart.getFxmlFileUrl(loginPathPath);
        Parent root = loadFxmlFile(fxmlLoginFileUrl, loginWindow);

        loginWindow.setTitle("Login Page");
        loginWindow.setScene(new Scene(root));
        loginWindow.show();
    }

    private void settingUpLoginController() {
        loginController = new LoginController();
        passInLoginList();
    }

    /**
     * pass the loginInfoManager to controller
     */
    private void passInLoginList() {
        loginController.getLoginInfoList(loginInfoList);
    }

    /**
     * loads the file from {@code location}, set {@code stage} and return the {@code root}
     */
    private Parent loadFxmlFile(URL location, Stage stage) {
        requireNonNull(location);

        fxmlLoader.setLocation(location);
        Parent root;
        try {
            root = fxmlLoader.load();

        } catch (IOException e) {

            throw new AssertionError(e);
        }
        return root;
    }


    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        stop ();
    }

    @Subscribe
    public void handleLogoutEvent(LogoutEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        hideCurrentWindow();
        saveLoginInfo();
        saveUserPrefs ();
        showLoginWindow();
    }

    private void showLoginWindow() {
        loginWindow.show();
    }

    /**
     * Hide the current stage that is showing
     */
    private void hideCurrentWindow() {
        EventsCenter.getInstance ().post (new StopUiEvent ());
    }

    /**
     * Save UserPref into storage
     */
    private void saveUserPrefs() {
        logger.info("============================ [ Stopping DRINK I/O ] =============================");
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }

    /**
     * Save Login information into storage
     */
    private void saveLoginInfo() {
        try {
            storage.saveLoginInfo(loginInfoList);
        } catch (IOException e) {
            logger.severe("Failed to save Login information " + StringUtil.getDetails(e));
        }
    }
}
