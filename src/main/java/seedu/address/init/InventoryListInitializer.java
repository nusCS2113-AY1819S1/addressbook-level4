package seedu.address.init;

import static seedu.address.authentication.AuthenticationLevelConstant.AUTH_ACCOUNTANT;
import static seedu.address.authentication.AuthenticationLevelConstant.AUTH_ADMIN;
import static seedu.address.authentication.AuthenticationLevelConstant.AUTH_MANAGER;
import static seedu.address.authentication.AuthenticationLevelConstant.AUTH_STOCK_TAKER;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.MainApp;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.CurrentUser;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.events.model.ChangeModelEvent;
import seedu.address.commons.events.model.InitInventoryListEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.InventoryList;
import seedu.address.model.LoginInfoModel;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyInventoryList;
import seedu.address.model.UserPrefs;
import seedu.address.model.transaction.ReadOnlyTransactionList;
import seedu.address.model.transaction.TransactionList;
import seedu.address.model.user.accountant.AccountantModelManager;
import seedu.address.model.user.admin.AdminModelManager;
import seedu.address.model.user.manager.ManagerModelManager;
import seedu.address.model.user.stocktaker.StockTakerModelManager;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.Storage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * Contain item that has to be init after login
 */
public class InventoryListInitializer {
    public static final Version VERSION = new Version(0, 6, 0, true);
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);


    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;
    private LoginInfoModel loginInfoModel;
    private TransactionList transactionList;


    public InventoryListInitializer (Config config, Storage storage,
                                     UserPrefs userPrefs, LoginInfoModel loginInfoModel) {
        this.storage = storage;
        this.userPrefs = userPrefs;
        this.loginInfoModel = loginInfoModel;
        this.config = config;
        EventsCenter.getInstance().registerHandler(this);
    }

    /**
     * init Drink I/O after login
     */
    public void initAfterLogin() {
        model = initModelManager();
        logic = new LogicManager (model);
        ui = new UiManager (logic, config, userPrefs);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s inventory list and {@code userPrefs}. <br>
     * The data from the sample inventory list will be used instead if {@code storage}'s inventory list is not found,
     * or an empty inventory list will be used instead if errors occur when reading {@code storage}'s inventory list.
     */
    private Model initModelManager() {

        ReadOnlyInventoryList initialData = readInventoryDataFromStorage();
        ReadOnlyTransactionList initialTransactionData = readTransactionListFromStorage();

        return chooseModelAccordingToAuthentication (initialData, initialTransactionData);
    }

    /**
     *  Returns {@code ReadOnlyInventoryList} after reading from storage
     */
    private ReadOnlyInventoryList readInventoryDataFromStorage() {
        Optional<ReadOnlyInventoryList> inventoryListOptional;
        try {
            inventoryListOptional = storage.readInventoryList();
            if (!inventoryListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample Inventory List");
            }
            return inventoryListOptional.orElseGet(SampleDataUtil::getSampleInventoryList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Inventory List");
            return new InventoryList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Inventory List");
            return new InventoryList();
        }

    }
    /**
     *  Returns {@code ReadOnlyTransactionList} after reading from storage
     */
    private ReadOnlyTransactionList readTransactionListFromStorage() {
        Optional<ReadOnlyTransactionList> transactionListOptional;
        try {
            transactionListOptional = storage.readTransactionList();
            if (!transactionListOptional.isPresent()) {
                logger.info("Transaction data file not found. Will be starting with a sample Transaction List");
            }
            return transactionListOptional.orElseGet(SampleDataUtil::getSampleTransactionList);
        } catch (DataConversionException e) {
            logger.warning("Transaction data file not in the correct format."
                    + " Will be starting with an empty Transaction List");
            return new TransactionList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the transaction datafile."
                    + "Will be starting with an empty Transaction List");
            return new TransactionList();
        }
    }

    /**
     * Returns {@code Model} according to their AuthenticationLevel
     */
    private Model chooseModelAccordingToAuthentication (ReadOnlyInventoryList initialData,
                                                        ReadOnlyTransactionList initialTransactionData) {
        switch (CurrentUser.getAuthenticationLevel()) {
        case AUTH_ADMIN:
            return new AdminModelManager(initialData, userPrefs, loginInfoModel, initialTransactionData);
        case AUTH_MANAGER:
            return new ManagerModelManager(initialData, userPrefs, loginInfoModel, initialTransactionData);
        case AUTH_STOCK_TAKER:
            return new StockTakerModelManager(initialData, userPrefs, loginInfoModel, initialTransactionData);
        case AUTH_ACCOUNTANT:
            return new AccountantModelManager(initialData, userPrefs, loginInfoModel, initialTransactionData);
        default:
            logger.severe("Database authentication level do not match with predefined authentication level");
            return new ModelManager(initialData, userPrefs, loginInfoModel, initialTransactionData);
        }
    }

    @Subscribe
    public void handleInitInventoryListEvent(InitInventoryListEvent event) {
        initAfterLogin ();
    }

    @Subscribe
    public void handleChangeModelEvent(ChangeModelEvent event) {
        model = initModelManager();
        logic.changeModelAfterReLogin (model);
    }

}
