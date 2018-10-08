package seedu.address.logic;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.RecruitBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.candidate.Candidate;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {

    private static LogicState state = new LogicState("primary");

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final RecruitBookParser recruitBookParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        recruitBookParser = new RecruitBookParser();
    }

    @Override
    public CommandResult execute(String commandText)
            throws CommandException, ParseException, IOException, GeneralSecurityException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = recruitBookParser.parseCommand(commandText, state);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Candidate> getFilteredPersonList() {
        return model.getFilteredCandidateList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    public static void setLogicState(String newState) {
        state = new LogicState(newState);
    }
}
