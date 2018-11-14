package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ChangePasswordCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeregisterCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.RegisterCommand;
import seedu.address.logic.commands.ThreadDueRemindersCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AllCommandsParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.login.User;
import seedu.address.model.product.Product;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AllCommandsParser allCommandsParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        allCommandsParser = new AllCommandsParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        commandText.toLowerCase();
        try {
            Command command = allCommandsParser.parseCommand(commandText);
            CommandResult result = null;
            boolean isThreadCommand = commandText.equals(ThreadDueRemindersCommand.COMMAND_WORD);

            if (model.hasLoggedIn()) {
                if (!isThreadCommand) {
                    logger.info("----------------[USER COMMAND][" + commandText + "]");
                }
                result = command.execute(model, history);
            } else if (!isThreadCommand) {
                logger.info("User attempts to use a command without logging in first.");
                result = executeUnauthenticatedCommands(commandText, command);
                history.clear();
            }

            return result;
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Distributor> getFilteredDistributorList() {
        return model.getFilteredDistributorList();
    }

    @Override
    public ObservableList<Product> getFilteredProductList() {
        return model.getFilteredProductList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    @Override
    public CommandResult executeUnauthenticatedCommands (String commandText, Command command) throws CommandException {
        CommandResult result;
        try {
            if (commandText.split(" ")[0].equals(LoginCommand.COMMAND_WORD)
                    || commandText.split(" ")[0].equals(HelpCommand.COMMAND_WORD)
                    || commandText.split(" ")[0].equals(ExitCommand.COMMAND_WORD)
                    || commandText.split(" ")[0].equals(RegisterCommand.COMMAND_WORD)
                    || commandText.split(" ")[0].equals(DeregisterCommand.COMMAND_WORD)
                    || commandText.split(" ")[0].equals(ChangePasswordCommand.COMMAND_WORD)) {
                result = command.execute(model, history);
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
