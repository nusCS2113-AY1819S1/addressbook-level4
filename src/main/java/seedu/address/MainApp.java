package seedu.address;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
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
import seedu.address.controller.LoginController;
import seedu.address.init.InitAddressBook;
import seedu.address.logic.Logic;
import seedu.address.model.LoginInfoManager;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlAddressBookStorage;
import seedu.address.storage.logininfo.JsonLoginInfoStorage;
import seedu.address.storage.logininfo.LoginInfoStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiPart;



/**
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 6, 0, true);
    public static final String FXML_LOGIN_PATH = "LoginPage.fxml";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);


    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;
    //author @tianhang
    protected Stage loginWindow;
    private FXMLLoader fxmlLoader;
    private LoginInfoManager loginInfoList;
    private LoginController loginController;
    private InitAddressBook initAddressBook;
    private Stage mainWindow;
    //author @tianhang

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing AddressBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        LoginInfoStorage loginInfoStorage = new JsonLoginInfoStorage (config.getUserLoginInfoilePath ());
        loginInfoList = initLoginInfo (loginInfoStorage);
        AddressBookStorage addressBookStorage = new XmlAddressBookStorage(userPrefs.getAddressBookFilePath());
        storage = new StorageManager(addressBookStorage, userPrefsStorage, loginInfoStorage);

        initAddressBook = new InitAddressBook (config, storage, userPrefs, loginInfoList);
        initLogging(config);
        fxmlLoader = new FXMLLoader();
        initEventsCenter();

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

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }
    /**
     * Returns a {@code LoginInfoManager} using the hardcoded file path,
     * or a new {@code LoginInfoManager} with default configuration if errors occur when
     * reading from the file.
     */
    protected LoginInfoManager initLoginInfo(LoginInfoStorage storage) {
        Path loginInfoFilePath = storage.getLoginInfoFilePath ();
        logger.info("Using Login information file : " + loginInfoFilePath);

        LoginInfoManager initLoginInfoManager;
        try {
            Optional<LoginInfoManager> loginInfoOptional = storage.readLoginInfo();
            initLoginInfoManager = loginInfoOptional.orElse(new LoginInfoManager ());
        } catch (DataConversionException e) {
            logger.warning("Login Info file at " + loginInfoFilePath + " is not in the correct format. "
                    + "Using empty database");
            initLoginInfoManager = new LoginInfoManager ();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Please find ADMIN");
            initLoginInfoManager = new LoginInfoManager ();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveLoginInfo (initLoginInfoManager);
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

    @Override
    public void start(Stage primaryStage) {
        loginWindow = primaryStage;
        logger.info("Starting AddressBook " + MainApp.VERSION);
        showLoginPage();
    }

    /**
     * Start Login scene
     */
    private void showLoginPage() {
        settingUpLoginWindow();
        settingUpLoginController();
    }
    private void settingUpLoginWindow() {
        URL fxmlLoginFileUrl = UiPart.getFxmlFileUrl(FXML_LOGIN_PATH);
        Parent root = loadFxmlFile(fxmlLoginFileUrl, loginWindow);
        //loginWindow.initStyle(StageStyle.UNDECORATED);
        loginWindow.setTitle("Login Page");
        loginWindow.setScene(new Scene(root));
        loginWindow.show();
    }
    private void settingUpLoginController() {
        loginController = new LoginController ();
        passInLoginList();
    }

    /**
     * pass the loginInfoManager to controller
     */
    private void passInLoginList() {
        loginController.getLoginInfoList (loginInfoList);
    }
    /**
     * loads the file from {@code location} and set {@code root}
     * @param location
     * @param root
     * @return root of primary stage
     */
    private Parent loadFxmlFile(URL location, Stage stage) {
        System.out.println(location);
        requireNonNull(location);
        fxmlLoader.setLocation(location);
        Parent root = null;
        try {
                root = fxmlLoader.load ();

        } catch (IOException e) {
            //System.out.println("the exception is " + e);
            throw new AssertionError(e);
        }
        return root;
    }
    @Override
    public void stop() {
        closeUiWindow();
        Platform.exit();
        System.exit(0);
    }
    private void closeUiWindow(){
        logger.info("============================ [ Stopping DRINK I/O ] =============================");
        //EventsCenter.getInstance().post(new StopUiEvent ());
        try {
            storage.saveLoginInfo (loginInfoList);
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        loginWindow.close ();
    }
    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        stop();
    }
    @Subscribe
    public void handleLogoutEvent(LogoutEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Window currentStage = Stage.getWindows().filtered(window -> window.isShowing()).get (0);
        currentStage.hide ();
        loginWindow.show ();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
