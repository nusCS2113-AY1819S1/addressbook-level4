package seedu.planner.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.planner.commons.core.ComponentManager;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.logic.commands.Command;
import seedu.planner.logic.commands.CommandResult;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.parser.FinancialPlannerParser;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.Model;
import seedu.planner.model.record.Record;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final FinancialPlannerParser financialPlannerParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        financialPlannerParser = new FinancialPlannerParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = financialPlannerParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Record> getFilteredRecordList() {
        return model.getFilteredRecordList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
