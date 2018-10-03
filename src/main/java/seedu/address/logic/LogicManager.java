package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.*;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
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

    @Override
    public CommandResult executeNoLoginCommands (String commandText, Command command) throws CommandException {
        CommandResult result;
        try {
            if (commandText.split(" ")[0].equals(LoginCommand.COMMAND_WORD)
                    || commandText.split(" ")[0].equals(HelpCommand.COMMAND_WORD)
                    || commandText.split(" ")[0].equals(ExitCommand.COMMAND_WORD)
                    || commandText.split(" ")[0].equals(CreateUserCommand.COMMAND_WORD)
                    || commandText.split(" ")[0].equals(DeleteUserCommand.COMMAND_WORD)
                    || commandText.split(" ")[0].equals(ChangeUserPasswordCommand.COMMAND_WORD)) {
                result = command.execute();
            } else {
                result = null;
            }
        } finally { }

        return result;
    }

    @Override
    public boolean hasLoggedIn() {
        return model.hasLoggedIn();
    }

    @Override
    public User getLoggedInUser() {
        return model.getLoggedInUser();
    }

}
