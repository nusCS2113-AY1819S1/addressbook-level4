package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.ui.ShowCandidateBookRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;

/**
 * Reverts the {@code model}'s CandidateBook to its previously undone state.
 */
public class RedoCandidateBookCommand extends Command {

    public static final String COMMAND_WORD = "redoc";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more CandidateBook commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        EventsCenter.getInstance().post(new ShowCandidateBookRequestEvent());

        if (!model.canRedoCandidateBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoCandidateBook();
        model.updateFilteredCandidateList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
