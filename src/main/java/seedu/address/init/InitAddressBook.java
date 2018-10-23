package seedu.address.init;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXMLLoader;

import seedu.address.MainApp;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.CurrentUser;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.events.model.InitInventoryListEvent;
import seedu.address.commons.events.ui.ChangeMainStageEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.AddressBook;
import seedu.address.model.LoginInfoManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
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
public class InitAddressBook {
    public static final Version VERSION = new Version(0, 6, 0, true);
    public static final String FXML_LOGIN_PATH = "LoginPage.fxml";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);


    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;
    private FXMLLoader fxmlLoader;
    private LoginInfoManager loginInfoList;


    public InitAddressBook(Config config, Storage storage, UserPrefs userPrefs, LoginInfoManager loginInfoList) {
        this.storage = storage;
        this.userPrefs = userPrefs;
        this.loginInfoList = loginInfoList;
        this.config = config;
        EventsCenter.getInstance().registerHandler(this);
    }

    /**
     * init Drink I/O after login
     */
    public void initAfterLogin() {
        model = initModelManager(storage, userPrefs);
        logic = new LogicManager (model, loginInfoList);
        ui = new UiManager (logic, config, userPrefs);

        fxmlLoader = new FXMLLoader();

    }

    @Subscribe
    public void handleinitInventoryListEvent(InitInventoryListEvent event) {
        initAfterLogin ();
    }

    @Subscribe
    public void handleChangeMainStageEvent(ChangeMainStageEvent event) {
        ui.start(event.mainStage);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
            initialData = new AddressBook ();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        }
        if (CurrentUser.getAuthenticationLevel ().equals ("admin")) {
            return new AdminModelManager (initialData , userPrefs);
        }
        if (CurrentUser.getAuthenticationLevel ().equals ("STOCK_TAKER")) {
            return new StockTakerModelManager (initialData , userPrefs);
        }

        if (CurrentUser.getAuthenticationLevel ().equals ("ACCOUNTANT")) {
            return new AccountantModelManager (initialData, userPrefs);
        }
        if (CurrentUser.getAuthenticationLevel ().equals ("MANAGER")) {
            return new ManagerModelManager (initialData, userPrefs);
        }
        return new ModelManager (initialData, userPrefs);
    }


}
