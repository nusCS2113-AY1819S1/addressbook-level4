package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

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
            + ": Deletes the candidate identified by the index number used in the displayed candidate list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Candidate: %1$s";

    private final Index targetIndex;

    public DeleteCandidateCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Candidate> lastShownList = model.getFilteredCandidateList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Candidate candidateToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteCandidate(candidateToDelete);
        model.commitCandidateBook();
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, candidateToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCandidateCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCandidateCommand) other).targetIndex)); // state check
    }
}
