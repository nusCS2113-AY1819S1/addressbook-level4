package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

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
            + ": Deletes the job offer(s) identified by the index number(s) used in the displayed job list.\n"
            + "Parameters: INDEX,INDEX-INDEX ... (INDEX must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1,2,7-9";

    public static final String MESSAGE_DELETE_JOB_OFFER_SUCCESS = "Deleted job offer:\n%1$s";

    private final Set<Index> targetIndexes;

    public DeleteJobOfferCommand(Set<Index> targetIndexes) {
        this.targetIndexes = targetIndexes;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<JobOffer> lastShownList = model.getFilteredCompanyJobList();
        StringBuilder deletedJobOffers = new StringBuilder();

        //Check if any of the specified indexes are invalid
        for (Index index: targetIndexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_JOB_OFFER_DISPLAYED_INDEX);
            }
        }

        for (Index index: targetIndexes) {
            JobOffer jobOfferToDelete = lastShownList.get(index.getZeroBased());
            model.deleteJobOffer(jobOfferToDelete);
            deletedJobOffers.append(jobOfferToDelete + "\n");
        }
        model.commitCompanyBook();
        return new CommandResult(String.format(MESSAGE_DELETE_JOB_OFFER_SUCCESS, deletedJobOffers));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteJobOfferCommand // instanceof handles nulls
                && targetIndexes.equals(((DeleteJobOfferCommand) other).targetIndexes)); // state check
    }

}
