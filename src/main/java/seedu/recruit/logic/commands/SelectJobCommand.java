package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.commons.events.ui.JumpToCompanyJobListRequestEvent;
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

    public static final String COMMAND_LOGIC_STATE = "SelectJob";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the job identified by the index number used in the displayed job list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_JOB_SUCCESS = "Selected Job Offer: %1$s\n";

    private static JobOffer selectedJobOffer;

    private static Index targetIndex;

    public SelectJobCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<JobOffer> filteredCompanyJobList = model.getFilteredCompanyJobList();

        if (targetIndex.getZeroBased() >= filteredCompanyJobList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_JOB_OFFER_DISPLAYED_INDEX);
        }

        selectedJobOffer = filteredCompanyJobList.get(targetIndex.getZeroBased());
        EventsCenter.getInstance().post(new JumpToCompanyJobListRequestEvent(targetIndex));

        /**
         * If user is inside Shortlist command,
         * user can only select jobs from a selected company.
         */
        if (ShortlistCommand.isProcessing()) {
            filteredCompanyJobList = SelectCompanyCommand.getSelectedCompany().getUniqueJobList().getInternalList();

            if (targetIndex.getZeroBased() >= filteredCompanyJobList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_JOB_OFFER_DISPLAYED_INDEX);
            }

            selectedJobOffer = filteredCompanyJobList.get(targetIndex.getZeroBased());
            LogicManager.setLogicState(SelectCandidateCommand.COMMAND_LOGIC_STATE);
            return new CommandResult(String.format(MESSAGE_SELECT_JOB_SUCCESS,
                    targetIndex.getOneBased()) + SelectCandidateCommand.MESSAGE_USAGE);
        }

        return new CommandResult(String.format(MESSAGE_SELECT_JOB_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectJobCommand // instanceof handles nulls
                && targetIndex.equals(((SelectJobCommand) other).targetIndex)); // state check
    }

    public static JobOffer getSelectedJobOffer() {
        return selectedJobOffer;
    }
}
