package t13g2.forum.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import t13g2.forum.commons.core.ComponentManager;
import t13g2.forum.commons.core.LogsCenter;
import t13g2.forum.logic.commands.Command;
import t13g2.forum.logic.commands.CommandResult;
import t13g2.forum.logic.commands.exceptions.CommandException;
import t13g2.forum.logic.parser.AddressBookParser;
import t13g2.forum.logic.parser.exceptions.ParseException;
import t13g2.forum.model.Model;
import t13g2.forum.model.person.Person;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = addressBookParser.parseCommand(commandText);
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
