package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.StatisticCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.BookInventory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyBookInventory;
import seedu.address.model.UserPrefs;
import seedu.address.model.statistic.Statistic;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.request.ReadOnlyRequests;
import seedu.address.request.RequestList;
import seedu.address.request.requestmodel.RequestModel;
import seedu.address.request.requestmodel.RequestModelManager;
import seedu.address.request.requeststorage.RequestListStorage;
import seedu.address.request.requeststorage.RequestListStorageManager;
import seedu.address.request.requeststorage.RequestStorage;
import seedu.address.request.requeststorage.XmlRequestListStorage;
import seedu.address.storage.BookInventoryStorage;
import seedu.address.storage.InventoryStorage;
import seedu.address.storage.InventoryStorageManager;
import seedu.address.storage.JsonStatisticStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StatisticsStorage;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlBookInventoryStorage;
import seedu.address.ui.SubmitBox;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;


/**
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected InventoryStorage storage;
    protected RequestStorage requestStorage;
    protected StatisticsStorage statisticsStorage;
    protected Model model;
    protected RequestModel requestModel;
    protected Config config;
    protected UserPrefs userPrefs;
    protected SubmitBox submitBox;
    protected StatisticCenter statisticCenter = StatisticCenter.getInstance();

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing BookInventory ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        BookInventoryStorage bookInventoryStorage = new XmlBookInventoryStorage(userPrefs.getBookInventoryFilePath());
        storage = new InventoryStorageManager(bookInventoryStorage, userPrefsStorage);
        RequestListStorage requestListStorage = new XmlRequestListStorage(userPrefs.getRequestListFilePath());
        requestStorage = new RequestListStorageManager(requestListStorage, userPrefsStorage);
        statisticsStorage = new JsonStatisticStorage(userPrefs.getStatisticFilePath());
        initLogging(config);

        model = initModelManager(storage, userPrefs);

        requestModel = initModelManager(requestStorage, userPrefs);


        logic = new LogicManager(model, requestModel);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
        initStatisticCenter();
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s BookInventory and {@code userPrefs}. <br>
     * The data from the sample inventory book will be used instead if {@code storage}'s inventory book is not found,
     * or an empty inventory book will be used instead if errors occur when reading {@code storage}'s inventory book.
     */
    private Model initModelManager(InventoryStorage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyBookInventory> bookInventoryOptional;
        ReadOnlyBookInventory initialData;
        try {
            bookInventoryOptional = storage.readBookInventory();
            if (!bookInventoryOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample BookInventory");
            }
            initialData = bookInventoryOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty BookInventory");
            initialData = new BookInventory();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty BookInventory");
            initialData = new BookInventory();
        }

        return new ModelManager(initialData, userPrefs);
    }

    /**
     * Returns a {@code RequestModelManager} with the data from
     * {@code requestStorage}'s request list and {@code userPrefs}. <br>
     * The data from the sample request list will be used instead if {@code requestStorage}'s request list is not found,
     * or an empty request list will be used instead if errors occur when reading {@code storage}'s request list.
     */
    private RequestModel initModelManager(RequestListStorage requestStorage, UserPrefs userPrefs) {
        Optional<ReadOnlyRequests> requestListOptional;
        ReadOnlyRequests initialData;
        try {
            requestListOptional = requestStorage.readRequestList();
            if (!requestListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample RequestList");
            }
            initialData = requestListOptional.orElseGet(SampleDataUtil::getSampleRequestList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty RequestList");
            initialData = new RequestList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty RequestList");
            initialData = new RequestList();
        }

        return new RequestModelManager(initialData, userPrefs);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty BookInventory");
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
     * Loads copy of statistic from json
     **/
    private void initStatisticCenter() {
        Path statisticFilePath = statisticsStorage.getStatisticFilePath();
        logger.info("Using statistic file : " + statisticFilePath);

        try {
            Optional<Statistic> statisticOptional = statisticsStorage.readStatistic();
            StatisticCenter.getInstance().loadStatistic(statisticOptional.orElse(new Statistic(11, 2018)));
        } catch (DataConversionException e) {
            logger.warning("Statistic file at " + statisticFilePath + " is not in the correct format. "
                    + "Using default statistics");
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty statistic");
        }

        if (StatisticCenter.getInstance().getStatistic().getInventory().toString().equals(Statistic.STARTING_FIGURE)) {
            StatisticCenter.getInstance().calibrateInventory(model.getBookInventory());
        }
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting BookInventory " + MainApp.VERSION);
        ui.start(primaryStage);
        primaryStage.setOnCloseRequest(e -> {
            final boolean exited = SubmitBox.display("Exit BookInventory",
                    "Are you sure you want to exit BookInventory?");
            if (!exited) {
                e.consume();
            } else {
                stop();
            }
        });
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping BookInventory ] =============================");
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
            requestStorage.saveUserPrefs(userPrefs);

        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }

        try {
            JsonUtil.saveJsonFile(
                    StatisticCenter.getInstance().getStatistic(), statisticsStorage.getStatisticFilePath());
        } catch (IOException e) {
            logger.warning("Failed to save statistic file : " + StringUtil.getDetails(e));
        }
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
