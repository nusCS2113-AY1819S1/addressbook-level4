package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.model.candidate.Candidate;

/**
 * Deletes a candidate identified using it's displayed index from the recruit book.
 */
public class DeleteCandidateCommand extends Command {

    public static final String COMMAND_WORD = "deletec";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the candidate(s) identified by the index number(s) used in the displayed candidate list.\n"
            + "Parameters: INDEX,INDEX-INDEX ... (INDEX must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1,2,7-9";

    public static final String MESSAGE_DELETE_CANDIDATE_SUCCESS = "Deleted Candidate(s):\n%1$s";

    private final Set<Index> targetIndexes;

    public DeleteCandidateCommand(Set<Index> targetIndexes) {
        this.targetIndexes = targetIndexes;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Candidate> lastShownList = model.getFilteredCandidateList();
        StringBuilder deletedCandidates = new StringBuilder();

        //Check if any of the specified indexes are invalid
        for (Index index: targetIndexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
        for (Index index: targetIndexes) {
            Candidate candidateToDelete = lastShownList.get(index.getZeroBased());
            deletedCandidates.append(candidateToDelete + "\n");
            model.deleteCandidate(candidateToDelete);
        }


        model.commitCandidateBook();
        return new CommandResult(String.format(MESSAGE_DELETE_CANDIDATE_SUCCESS, deletedCandidates));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCandidateCommand // instanceof handles nulls
                && targetIndexes.equals(((DeleteCandidateCommand) other).targetIndexes)); // state check
    }
}
