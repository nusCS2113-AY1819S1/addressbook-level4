package seedu.divelog.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.divelog.commons.core.ComponentManager;
import seedu.divelog.commons.core.LogsCenter;
import seedu.divelog.logic.commands.Command;
import seedu.divelog.logic.commands.CommandResult;
import seedu.divelog.logic.commands.exceptions.CommandException;
import seedu.divelog.logic.parser.DiveLogParser;
import seedu.divelog.logic.parser.exceptions.ParseException;
import seedu.divelog.model.Model;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.exceptions.InvalidTimeException;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final DiveLogParser diveLogParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        diveLogParser = new DiveLogParser();
    }

    @Override
    public CommandResult execute(String commandText)
            throws CommandException, ParseException, java.text.ParseException, InvalidTimeException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = diveLogParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<DiveSession> getFilteredDiveList() {
        return model.getFilteredDiveList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
