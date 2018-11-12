package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.workout.Parameter;
import seedu.address.model.workout.WorkoutContainsParameterPredicate;

/**
 * Selects a person identified using its displayed index from the workout book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the parameter identified by the index number used in the displayed tracked parameter list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PARAMETER_SUCCESS = "Selected Parameter: %1$s";


    private final Index targetIndex;

    private WorkoutContainsParameterPredicate predicate;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Parameter> filteredParameters = model.getFilteredTrackedDataList();

        if (targetIndex.getZeroBased() >= filteredParameters.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PARAMETER_DISPLAYED_INDEX);
        }

        Parameter parameter = filteredParameters.get(targetIndex.getZeroBased());
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(parameter);
        predicate = new WorkoutContainsParameterPredicate(parameters);
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        model.updateFilteredTrackedData(predicate);

        return new CommandResult(String.format(MESSAGE_SELECT_PARAMETER_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
