package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.workout.Parameter;

/**
 * Allows users to track parameters in subsequently added workouts
 */
public class TrackCommand extends Command {

    public static final String COMMAND_WORD = "track";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": allows the user to start/stop tracking desired parameters, for any new"
            + "workouts that marked as completed. VALUE has to be one string/value with no spaces.\n"
            + "Parameters: SUBCOMMAND PREFIX/VALUE\n"
            + "Example: " + COMMAND_WORD + " start muscle/bicep";

    public static final String MESSAGE_START_SUCCESS = "Now tracking %1$s%2$s";
    public static final String MESSAGE_STOP_SUCCESS = "Tracking of %1$s%2$s has been stopped";
    public static final String MESSAGE_DUPLICATE_PARAMETER = "This parameter is already being tracked";
    public static final String MESSAGE_MISSING_PARAMETER = "This parameter is not currently being tracked";

    public static final String MESSAGE_SUBCOMMAND_CONSTRAINTS = "subcommand must be \"start\" or \"stop\"";
    public static final String MESSAGE_VALUE_CONSTRAINTS = "VALUE has to be one string/value with no spaces.";
    public static final String SUBCOMMAND_VALIDATION_REGEX = "(start)|(stop)";

    private final String subcommand;
    private final Parameter parameter;

    /**
     * @param subcommand to indicate starting/stopping of tracking
     * @param parameter to be tracked
     */
    public TrackCommand(String subcommand, Parameter parameter) {
        requireAllNonNull(subcommand, parameter);

        this.subcommand = subcommand;
        this.parameter = parameter;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (subcommand.equals("start")) {
            if (model.hasParameter(parameter)) {
                throw new CommandException(MESSAGE_DUPLICATE_PARAMETER);
            }
            model.addDataToTrack(parameter);
            model.commitModel();
            return new CommandResult(String.format(MESSAGE_START_SUCCESS, parameter.getPrefix(), parameter.getValue()));
        } else if (subcommand.equals("stop")) {
            if (!model.hasParameter(parameter)) {
                throw new CommandException(MESSAGE_MISSING_PARAMETER);
            }
            model.removeDataFromTrack(parameter);
            model.commitModel();
            return new CommandResult(String.format(MESSAGE_STOP_SUCCESS, parameter.getPrefix(), parameter.getValue()));
        }
        throw new CommandException(MESSAGE_SUBCOMMAND_CONSTRAINTS);
    }

    /**
     *
     * WIP
     */
    public boolean equals(Object other) {
        //check 1: same object
        if (other == this) {
            return true;
        }

        //check 2: null
        if (!(other instanceof TrackCommand)) {
            return false;
        }

        //check 3: check all attributes
        TrackCommand t = (TrackCommand) other;
        return subcommand.equals(t.subcommand) && parameter.equals(t.parameter);
    }

    public static boolean isValidSubcommand(String test) {
        return test.matches(SUBCOMMAND_VALIDATION_REGEX);
    }
}
