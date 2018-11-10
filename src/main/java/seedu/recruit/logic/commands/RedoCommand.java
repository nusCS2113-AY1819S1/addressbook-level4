package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.model.Model.PREDICATE_SHOW_ALL_COMPANIES;
import static seedu.recruit.model.Model.PREDICATE_SHOW_ALL_JOBOFFERS;
import static seedu.recruit.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.ui.ShowUpdatedCompanyJobListRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;

/**
 * Reverts the {@code model}'s RecruitBook to its previously undone state.
 */


public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more RecruitBook commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoRecruitBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoRecruitBook();
        model.updateFilteredCandidateList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredCompanyJobList(PREDICATE_SHOW_ALL_JOBOFFERS);
        model.updateFilteredCompanyList(PREDICATE_SHOW_ALL_COMPANIES);
        EventsCenter.getInstance().post(new ShowUpdatedCompanyJobListRequestEvent(
                model.getFilteredCompanyJobList().size()));
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
