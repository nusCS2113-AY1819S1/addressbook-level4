package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EQUIPMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTRUCTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.workout.Workout;

/**
 * Adds a workout to the workout book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a workout to the workout book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_TYPE + "TYPE "
            + PREFIX_DURATION + "DURATION "
            + PREFIX_DIFFICULTY + "DIFFICULTY "
            + PREFIX_EQUIPMENT + "EQUIPMENT "
            + PREFIX_MUSCLE + "MUSCLE "
            + PREFIX_CALORIES + "CALORIES "
            + PREFIX_INSTRUCTION + "INSTRUCTION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "john doe's workout "
            + PREFIX_TYPE + "strength "
            + PREFIX_DURATION + "20m "
            + PREFIX_DIFFICULTY + "beginner "
            + PREFIX_EQUIPMENT + "dumbbell "
            + PREFIX_MUSCLE + "bicep "
            + PREFIX_CALORIES + "150 "
            + PREFIX_INSTRUCTION + "set1: bicep curl reps: 4-6 "
            + PREFIX_TAG + "heavy "
            + PREFIX_TAG + "favourite";

    public static final String MESSAGE_SUCCESS = "New workout added with default future tag: %1$s";
    public static final String MESSAGE_DUPLICATE_WORKOUT = "This workout already exists in the workout book";
    public static final String MESSAGE_TAG_FAILURE = "Tags cannot be exact words of current or completed";

    private final Workout toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Workout}
     */
    public AddCommand(Workout workout) {
        requireNonNull(workout);
        toAdd = workout;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasWorkout(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_WORKOUT);
        }

        model.addWorkout(toAdd);
        model.commitModel();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
