package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.model.joboffer.JobOffer;

/**
 * Deletes a jobOffer identified using it's displayed index from the recruit book.
 */

public class DeleteJobOfferCommand extends Command {

    public static final String COMMAND_WORD = "deletej";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the job offer identified by the index number used in the displayed job list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_JOB_OFFER_SUCCESS = "Deleted job offer: %1$s";

    private final Index targetIndex;

    public DeleteJobOfferCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<JobOffer> lastShownList = model.getFilteredCompanyJobList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        JobOffer jobOfferToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteJobOffer(jobOfferToDelete);
        model.commitCompanyBook();
        return new CommandResult(String.format(MESSAGE_DELETE_JOB_OFFER_SUCCESS, jobOfferToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteJobOfferCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteJobOfferCommand) other).targetIndex)); // state check
    }

}
