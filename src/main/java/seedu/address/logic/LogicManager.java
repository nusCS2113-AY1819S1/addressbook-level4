package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.Model;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.user.UserCommand;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.ManagerParser;
import seedu.address.logic.parser.user.AccountantParser;
import seedu.address.logic.parser.user.AdminParser;
import seedu.address.logic.parser.user.StockTakerParser;
import seedu.address.logic.parser.exceptions.ParseException;
<<<<<<< HEAD
import seedu.address.model.IngredientManager;
import seedu.address.model.IngredientModel;
=======
import seedu.address.model.LoginInfoManager;
import seedu.address.model.Model;
>>>>>>> PasswordChange
import seedu.address.model.person.Person;
import seedu.address.model.user.accountant.AccountantModel;
import seedu.address.model.user.admin.AdminModel;
import seedu.address.model.user.manager.ManagerModel;
import seedu.address.model.user.stocktaker.StockTakerModel;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final Model model;
    private LoginInfoManager loginInfoManager;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;
    private final AdminParser adminParser;
    private final StockTakerParser stockTakerParser;
    private final ManagerParser managerParser;
    private final AccountantParser accountantParser;
    public LogicManager(Model model, LoginInfoManager loginInfoManager) {
        this.loginInfoManager = loginInfoManager;
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
        adminParser = new AdminParser ();
        stockTakerParser = new StockTakerParser ();
        managerParser = new ManagerParser ();
        accountantParser = new AccountantParser ();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command;
            if (model instanceof AdminModel){
                command = adminParser.parseCommand (commandText);
            } else if (model instanceof StockTakerModel){
                command = stockTakerParser.parseCommand (commandText);
            } else if (model instanceof AccountantModel) {
                command = accountantParser.parseCommand (commandText);
            } else if (model instanceof ManagerModel){
                command = managerParser.parseCommand (commandText);
            } else {
                command = addressBookParser.parseCommand (commandText);
            }
            if (command instanceof UserCommand) {
                UserCommand userCommand = (UserCommand) command;
                return userCommand.execute (loginInfoManager, history);
            }
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
