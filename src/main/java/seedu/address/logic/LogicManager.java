package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CompleteTaskCommand;
import seedu.address.logic.commands.DeferDeadlineCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectDeadlineCommand;
import seedu.address.logic.commands.TrackProductivityCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.TaskBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final TaskBookParser taskBookParser;
    //@@author chelseyong
    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        // need to add the commands into the list<CommandParser> commands in TaskBookParser
        taskBookParser = new TaskBookParser(new AddTaskCommand(),
                new ClearCommand(),
                new CompleteTaskCommand(),
                new DeferDeadlineCommand(),
                new DeleteCommand(),
                new ListCommand(),
                new TrackProductivityCommand(),
                new SelectDeadlineCommand(),
                new HelpCommand(),
                new ExitCommand(),
                new HistoryCommand(),
                new UndoCommand(),
                new RedoCommand());
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = taskBookParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }
    //@@author
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
