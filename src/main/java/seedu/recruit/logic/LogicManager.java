package seedu.recruit.logic;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.recruit.commons.core.ComponentManager;
import seedu.recruit.commons.core.LogsCenter;
import seedu.recruit.logic.commands.Command;
import seedu.recruit.logic.commands.CommandResult;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.logic.parser.RecruitBookParser;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.Model;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.company.Company;
import seedu.recruit.model.joboffer.JobOffer;

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
    public ObservableList<Company> getFilteredCompanyList() {
        return model.getFilteredCompanyList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    public static void setLogicState(String newState) {
        state = new LogicState(newState);
    }
}
