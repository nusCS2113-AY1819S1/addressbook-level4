package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EQUIPMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.workout.DurationPredicate;
import seedu.address.model.workout.EquipmentPredicate;
import seedu.address.model.workout.TypePredicate;

/**
 * Filters the workouts with duration, type and equipment
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filter all workouts based on keywords "
            + "Parameters: "
            + PREFIX_TYPE + "TYPE "
            + PREFIX_DURATION + "DURATION "
            + PREFIX_EQUIPMENT + "EQUIPMENT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "strength "
            + PREFIX_DURATION + "20m "
            + PREFIX_EQUIPMENT + "dumbbell ";

    public static final String MESSAGE_NOT_FILTERED = "All fields to filter must be provided.";
    public static final String MESSAGE_CONSTRAINTS = "The keyword should not be more than one word";

    private final TypePredicate typePredicate;
    private final EquipmentPredicate equipmentPredicate;
    private final DurationPredicate durationPredicate;

    public FilterCommand(DurationPredicate durationPredicate, TypePredicate typePredicate,
                         EquipmentPredicate equipmentPredicate) {
        requireNonNull(durationPredicate);
        requireNonNull(typePredicate);
        requireNonNull(equipmentPredicate);

        this.durationPredicate = durationPredicate;
        this.typePredicate = typePredicate;
        this.equipmentPredicate = equipmentPredicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredWorkoutList(typePredicate.and(durationPredicate).and(equipmentPredicate));
        return new CommandResult(
                String.format(Messages.MESSAGE_WORKOUTS_LISTED_OVERVIEW, model.getFilteredWorkoutList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && typePredicate.equals(((FilterCommand) other).typePredicate)
                && equipmentPredicate.equals(((FilterCommand) other).equipmentPredicate)
                && durationPredicate.equals(((FilterCommand) other).durationPredicate)); // state check
    }

}
