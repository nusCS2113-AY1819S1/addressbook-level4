package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.ui.MainWindow;

/**
 * First stage of Shortlist command.
 * Shortlists selected candidates for a job offer
 */
public class ShortlistCommand extends Command {
    public static final String COMMAND_WORD = "shortlist";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shortlists selected candidate(s) for a job offer.\n";

    public static final String MESSAGE_ENTERING_SHORTLIST_PROCESS =
            "Entering shortlisting process. \n" + "Please select a company.\n";

    private static boolean processStatus;

    public static boolean isProcessing() {
        return processStatus;
    }

    public static void isDoneProcessing() {
        processStatus = false;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        MainWindow.switchToShortlistPanel();
        processStatus = true;
        LogicManager.setLogicState(SelectCompanyCommand.COMMAND_LOGIC_STATE);
        return new CommandResult(MESSAGE_USAGE + MESSAGE_ENTERING_SHORTLIST_PROCESS
                + SelectCompanyCommand.MESSAGE_USAGE);
    }
}
