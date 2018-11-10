//@@author liu-tianhang
package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.user.AccountantParser;
import seedu.address.logic.parser.user.AdminParser;
import seedu.address.logic.parser.user.ManagerParser;
import seedu.address.logic.parser.user.StockTakerParser;
import seedu.address.model.Model;
import seedu.address.model.drink.Drink;
import seedu.address.model.user.accountant.AccountantModel;
import seedu.address.model.user.admin.AdminModel;
import seedu.address.model.user.manager.ManagerModel;
import seedu.address.model.user.stocktaker.StockTakerModel;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final CommandHistory history;
    private final AdminParser adminParser;
    private final StockTakerParser stockTakerParser;
    private final ManagerParser managerParser;
    private final AccountantParser accountantParser;
    private Model model;
    public LogicManager(Model newModel) {

        model = newModel;
        history = new CommandHistory();
        //addressBookParser = new AddressBookParser();
        adminParser = new AdminParser();
        stockTakerParser = new StockTakerParser();
        managerParser = new ManagerParser();
        accountantParser = new AccountantParser();
    }
    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        Command command = parseCommandAccordingToAuthentication(commandText);
        history.add(commandText);
        return command.execute(model, history);
    }

    /**
     *  Parse respective command according to their model
     *  Returns {@code command} according to {@code commandText}
     *  @throws ParseException if there is no model
     */
    private Command parseCommandAccordingToAuthentication(String commandText) throws ParseException {
        if (model instanceof AdminModel) {
            return adminParser.parseCommand(commandText);
        } else if (model instanceof StockTakerModel) {
            return stockTakerParser.parseCommand(commandText);
        } else if (model instanceof AccountantModel) {
            return accountantParser.parseCommand(commandText);
        } else if (model instanceof ManagerModel) {
            return managerParser.parseCommand(commandText);
        }
        throw new ParseException ("Unknown Command");
    }

    @Override
    public ObservableList<Drink> getFilteredDrinkList() {
        return model.getFilteredDrinkList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    public void changeModelAfterReLogin(Model model) {
        this.model = model;
    }
}
