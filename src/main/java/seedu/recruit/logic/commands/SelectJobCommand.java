package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.commons.events.ui.JumpToCompanyJobListRequestEvent;
import seedu.recruit.commons.events.ui.ShowCompanyBookRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * Selects a job offer identified using it's displayed index from the recruit book.
 * If this command is called in Shortlist Command,
 * user can only select jobs from a selected company.
 */
public class SelectJobCommand extends Command {

    public static final String COMMAND_WORD = "selectJob";

    public static final String COMMAND_LOGIC_STATE_FOR_SHORTLIST = "SelectJobForShortlist";

    public static final String COMMAND_LOGIC_STATE_FOR_SHORTLIST_DELETE = "SelectJobForShortlistDelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the job identified by the index number used in the displayed job list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_JOB_SUCCESS = "Selected Job Offer: %1$s\n";

    public static final String MESSAGE_SELECT_JOB_SUCCESS_NEXT_STEP_IN_SHORTLIST =
            "Please select a candidate to shortlist.\n";

    public static final String MESSAGE_SELECT_JOB_SUCCESS_NEXT_STEP_IN_SHORTLIST_DELETE =
            "Please select a candidate to delete.\n";

    private static JobOffer selectedJobOffer;

    private static Index targetIndex;

    public SelectJobCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    public static JobOffer getSelectedJobOffer() {
        return selectedJobOffer;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<JobOffer> filteredCompanyJobList = model.getFilteredCompanyJobList();

        if (targetIndex.getZeroBased() >= filteredCompanyJobList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_JOB_OFFER_DISPLAYED_INDEX);
        }

        selectedJobOffer = filteredCompanyJobList.get(targetIndex.getZeroBased());

        /**
         * If user is inside Shortlist command, user can only select jobs from a selected company.
         */
        if (ShortlistCandidateInitializationCommand.isShortlisting()) {
            //selectedJobOffer = getSelectedJobFromSelectedCompany();
            EventsCenter.getInstance().post(new JumpToCompanyJobListRequestEvent(targetIndex));
            LogicManager.setLogicState(SelectCandidateCommand.COMMAND_LOGIC_STATE);
            return new CommandResult(String.format(MESSAGE_SELECT_JOB_SUCCESS,
                    targetIndex.getOneBased()) + MESSAGE_SELECT_JOB_SUCCESS_NEXT_STEP_IN_SHORTLIST
                    + SelectCandidateCommand.MESSAGE_USAGE);
        }

        /**
         * If user is inside DeleteShortlistedCandidate command, user can only select jobs from a selected company.
         */
        if (DeleteShortlistedCandidateInitializationCommand.isDeleting()) {
            //selectedJobOffer = getSelectedJobFromSelectedCompany();git
            EventsCenter.getInstance().post(new JumpToCompanyJobListRequestEvent(targetIndex));
            LogicManager.setLogicState(DeleteShortlistedCandidateCommand.COMMAND_LOGIC_STATE);
            return new CommandResult(String.format(MESSAGE_SELECT_JOB_SUCCESS,
                    targetIndex.getOneBased()) + MESSAGE_SELECT_JOB_SUCCESS_NEXT_STEP_IN_SHORTLIST_DELETE
                    + DeleteShortlistedCandidateCommand.MESSAGE_USAGE);
        }

        EventsCenter.getInstance().post(new ShowCompanyBookRequestEvent());
        EventsCenter.getInstance().post(new JumpToCompanyJobListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_JOB_SUCCESS, targetIndex.getOneBased()));
    }

    /*public JobOffer getSelectedJobFromSelectedCompany() throws CommandException {
        List<JobOffer> filteredCompanyJobList =
                SelectCompanyCommand.getSelectedCompany().getUniqueJobList().getInternalList();

        if (targetIndex.getZeroBased() >= filteredCompanyJobList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_JOB_OFFER_DISPLAYED_INDEX);
        }

        selectedJobOffer = filteredCompanyJobList.get(targetIndex.getZeroBased());
        return selectedJobOffer;
    }*/

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectJobCommand // instanceof handles nulls
                && targetIndex.equals(((SelectJobCommand) other).targetIndex)); // state check
    }
}
