package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.LoginParser;
import seedu.address.logic.parser.ProManageParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private ProManageParser proManageParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
    }

    /**
     * Login for the app
     *
     * @param commandText
     * @return CommandResult if successfully login
     * @throws CommandException
     * @throws ParseException
     */

    private CommandResult loginCommand(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            this.proManageParser = LoginParser.loginCommand(commandText);
            return new CommandResult(String.format("Successfully login as %s", proManageParser.getIdentity()));
        } finally {
            //history.add(commandText);
        }
    }


    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        if (proManageParser == null) {
            loginCommand("manager");
        }
        try {
            Command command = proManageParser.parseCommand(commandText);
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
    public ObservableList<Event> getFilteredEventList() {
        return model.getFilteredEventList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
