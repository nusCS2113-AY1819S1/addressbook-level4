package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.CommandsEnum;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.User;
import seedu.address.model.person.Person;
import seedu.address.model.person.TimeTable;
import seedu.address.security.SecurityAuthenticationException;

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
    public CommandsEnum parseCommandWord(String commandText) throws ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        history.add(commandText);
        return addressBookParser.parseCommand(commandText);
    }

    @Override
    public CommandResult execute(String commandText)
            throws CommandException, ParseException, SecurityAuthenticationException {
        logger.info("Parsing Arguments");

        Command command = addressBookParser.parseCommandArguments();
        return command.execute(model, history);
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Person> getFriendList() {
        return model.getFriendList();
    }

    public ObservableList<Person> getOtherList() {
        return model.getOtherList();
    }

    public ObservableList<Person> getMeList() {
        return model.getMeList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    @Override
    public void matchUserToPerson(String name) {
        model.matchUserToPerson(name);
    }

    @Override
    public void clearUser() {
        model.clearUser();
    }

    @Override
    public User getUser() {
        return model.getUser();
    }

    @Override
    public void updateTimeTable(TimeTable timeTable) {
        model.updateTimeTable(timeTable);
    }
}
