package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.ui.ShowShortlistPanelRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;

/**
 * First stage of the 5-stage Shortlist command.
 * Shortlists selected candidates for a job offer
 */
public class ShortlistCandidateInitializationCommand extends Command {
    public static final String COMMAND_WORD = "shortlist";

    public static final String COMMAND_LOGIC_STATE = "primary";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shortlists selected candidate(s) for a job offer.\n";

    public static final String MESSAGE_ENTERING_SHORTLIST_PROCESS =
            "Entering shortlisting process.\n";

    public static final String MESSAGE_NEXT_STEP =
            "Please select a company.\n";

    /** Keeps track of the status of the shortlist process.
     * Returns true if process is ongoing.
     * Returns false is process is completed.
     */
    private static boolean shortlistStatus;

    /** Returns the status of the shortlist process */
    public static boolean isShortlisting() {
        return shortlistStatus;
    }

    /** Sets the status of the shortlist process as the end */
    public static void isDoneShortlisting() {
        shortlistStatus = false;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        EventsCenter.getInstance().post(new ShowShortlistPanelRequestEvent());
        shortlistStatus = true;
        LogicManager.setLogicState(SelectCompanyCommand.COMMAND_LOGIC_STATE);
        return new CommandResult(MESSAGE_ENTERING_SHORTLIST_PROCESS + MESSAGE_NEXT_STEP
                + SelectCompanyCommand.MESSAGE_USAGE);
    }
}
