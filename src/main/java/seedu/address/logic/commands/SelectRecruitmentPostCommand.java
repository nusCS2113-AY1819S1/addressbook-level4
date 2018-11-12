package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRecruitmentPostRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recruitment.Recruitment;

/**
 * Selects a schedule identified using it's displayed index from the address book.
 */
public class SelectRecruitmentPostCommand extends Command {
    public static final String COMMAND_WORD = "selectRecruitmentPost";
    public static final String COMMAND_ALIAS = "srp";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the recruitment post identified by the index number used in the displayed recruitment list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SELECT_RECRUITMENT_SUCCESS = "Selected Recruitment Post: %1$s";
    private final Index targetIndex;
    public SelectRecruitmentPostCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Recruitment> filteredRecruitmentList = model.getFilteredRecruitmentList();
        if (targetIndex.getZeroBased() >= filteredRecruitmentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECRUITMENT_POST_DISPLAYED_INDEX);
        }
        EventsCenter.getInstance().post(new JumpToListRecruitmentPostRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_RECRUITMENT_SUCCESS, targetIndex.getOneBased()));
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectRecruitmentPostCommand // instanceof handles nulls
                && targetIndex.equals(((SelectRecruitmentPostCommand) other).targetIndex)); // state check

    }

}
