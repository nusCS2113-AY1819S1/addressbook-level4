package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.drinkcommands.DrinkCommand;
import seedu.address.logic.drinkcommands.DrinkCommandResult;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.user.AccountantParser;
import seedu.address.logic.parser.user.AdminParser;
import seedu.address.logic.parser.user.ManagerParser;
import seedu.address.logic.parser.user.StockTakerParser;
import seedu.address.model.DrinkModel;
import seedu.address.model.LoginInfoManager;
import seedu.address.model.drink.Drink;


/**
 * The main LogicManager of the app.
 */
public class DrinkLogicManager extends ComponentManager implements DrinkLogic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final DrinkModel model;
    private LoginInfoManager loginInfoManager;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;
    private final AdminParser adminParser;
    private final StockTakerParser stockTakerParser;
    private final ManagerParser managerParser;
    private final AccountantParser accountantParser;

    public DrinkLogicManager(DrinkModel model, LoginInfoManager loginInfoManager) {
        this.loginInfoManager = loginInfoManager;
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
        adminParser = new AdminParser();
        stockTakerParser = new StockTakerParser();
        managerParser = new ManagerParser();
        accountantParser = new AccountantParser();
    }

    @Override
    public DrinkCommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            DrinkCommand command;
            /*
            if (model instanceof AdminModel) {
                command = adminParser.parseCommand (commandText);
            } else if (model instanceof StockTakerModel) {
                command = stockTakerParser.parseCommand (commandText);
            } else if (model instanceof AccountantModel) {
                command = accountantParser.parseCommand (commandText);
            } else if (model instanceof ManagerModel) {
                command = managerParser.parseCommand (commandText);
            } else {
                command = addressBookParser.parseCommand (commandText);
            }

            if (command instanceof UserCommand) {
                UserCommand userCommand = (UserCommand) command;
                return userCommand.execute(loginInfoManager, history);
            }
            return command.execute(model, history);
            */
            return null; // TODO: add this part back in
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Drink> getFilteredDrinkList() {
        return model.getFilteredDrinkList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}

