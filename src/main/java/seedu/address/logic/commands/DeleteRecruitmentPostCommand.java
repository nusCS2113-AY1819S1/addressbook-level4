package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recruitment.Recruitment;

/**
 * Deletes a recruitmentPost identified using it's displayed index from the recruitment list.
 */
public class DeleteRecruitmentPostCommand extends Command {
    public static final String COMMAND_WORD = "deleteRecruitmentPost";
    public static final String COMMAND_ALIAS = "drp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the recruitment post identified by the index number used in the displayed recruitment list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_RECRUITMENT_POST_SUCCESS = "Deleted Recruitment Post: %1$s";

    private final Index targetIndex;

    public DeleteRecruitmentPostCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Recruitment> lastShownList = model.getFilteredRecruitmentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECRUITMENT_POST_DISPLAYED_INDEX);
        }

        Recruitment recruitmentPostToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteRecruitmentPost(recruitmentPostToDelete);
        model.commitRecruitmentPostList();
        return new CommandResult(String.format(MESSAGE_DELETE_RECRUITMENT_POST_SUCCESS, recruitmentPostToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteRecruitmentPostCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteRecruitmentPostCommand) other).targetIndex)); // state check
    }
}
