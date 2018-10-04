package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
//import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddIngredientCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.user.ChangePasswordCommand;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.IngredientManager;
import seedu.address.model.IngredientModel;
import seedu.address.model.LoginInfoList;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;
    private IngredientModel ingredientModel;
    private LoginInfoList loginInfoList;
    public LogicManager(Model model, IngredientModel ingredientModel, LoginInfoList loginInfoList) {
        this.loginInfoList = loginInfoList;
        this.ingredientModel = ingredientModel;
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = addressBookParser.parseCommand(commandText);
            if (command instanceof AddIngredientCommand) {
                AddIngredientCommand command1 = ( AddIngredientCommand ) command;
                return command1.execute (ingredientModel , history);
            }
            if (command instanceof ChangePasswordCommand){
                ChangePasswordCommand command2 = (ChangePasswordCommand) command;
                return command2.execute (loginInfoList, history);
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
