package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.TrackCommand.MESSAGE_DUPLICATE_PARAMETER;
import static seedu.address.logic.commands.TrackCommand.MESSAGE_MISSING_PARAMETER;
import static seedu.address.logic.commands.TrackCommand.MESSAGE_START_SUCCESS;
import static seedu.address.logic.commands.TrackCommand.MESSAGE_STOP_SUCCESS;
import static seedu.address.logic.commands.TrackCommand.MESSAGE_SUBCOMMAND_CONSTRAINTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.address.testutil.TypicalParameters.NAME_PARAMETER;
import static seedu.address.testutil.TypicalParameters.getTypicalTrackedDataList;
import static seedu.address.testutil.TypicalWorkouts.getTypicalWorkoutBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TrackedData;
import seedu.address.model.UserPrefs;
import seedu.address.model.workout.Parameter;

/**
 * Contains unit tests for TrackCommand
 */
public class TrackCommandTest {

    private Model model = new ModelManager(getTypicalWorkoutBook(), getTypicalTrackedDataList(), new TrackedData(),
           new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validSubcommandStart_success() {
        String subcommand = "start";
        Parameter parameter = new Parameter(PREFIX_MUSCLE, "quadriceps");
        TrackCommand trackCommand = new TrackCommand(subcommand, parameter);

        String expectedMessage = String.format(MESSAGE_START_SUCCESS, parameter.getPrefix(), parameter.getValue());

        Model expectedModel = new ModelManager(model.getWorkoutBook(), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());
        expectedModel.addDataToTrack(parameter);
        expectedModel.commitModel();

        assertCommandSuccess(trackCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validSubcommandStop_success() {
        String subcommand = "stop";
        Parameter parameter = NAME_PARAMETER;
        TrackCommand trackCommand = new TrackCommand(subcommand, parameter);

        String expectedMessage = String.format(MESSAGE_STOP_SUCCESS, parameter.getPrefix(), parameter.getValue());

        Model expectedModel = new ModelManager(model.getWorkoutBook(), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());
        expectedModel.removeDataFromTrack(parameter);
        expectedModel.commitModel();

        assertCommandSuccess(trackCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidSubcommand_failure() {
        String subcommand = "test";
        Parameter parameter = new Parameter(PREFIX_MUSCLE, "hamstring");
        TrackCommand trackCommand = new TrackCommand(subcommand, parameter);

        String expectedMessage = String.format(MESSAGE_SUBCOMMAND_CONSTRAINTS);

        assertCommandFailure(trackCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_validSubcommandStartWithExistingParameter_failure() {
        Parameter defaultParameter = new Parameter(PREFIX_MUSCLE, "hamstring");
        model.addDataToTrack(defaultParameter);

        String subcommand = "start";
        Parameter parameter = new Parameter(PREFIX_MUSCLE, "hamstring");
        TrackCommand trackCommand = new TrackCommand(subcommand, parameter);

        String expectedMessage = String.format(MESSAGE_DUPLICATE_PARAMETER);

        assertCommandFailure(trackCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_validSubcommandStopWithNonExistentParameter_failure() {
        String subcommand = "stop";
        Parameter parameter = new Parameter(PREFIX_MUSCLE, "brain");
        TrackCommand trackCommand = new TrackCommand(subcommand, parameter);

        String expectedMessage = String.format(MESSAGE_MISSING_PARAMETER);

        assertCommandFailure(trackCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void equals() {
        final TrackCommand testCommand = new TrackCommand("start", new Parameter(PREFIX_MUSCLE, "bicep"));

        //check 1: same object -> returns true
        assertEquals(testCommand, testCommand);

        //check 2: same values -> return true
        TrackCommand sameValuesInCommand = new TrackCommand("start", new Parameter(PREFIX_MUSCLE, "bicep"));
        assertEquals(sameValuesInCommand, testCommand);

        //check 3: null -> return false
        assertNotEquals(null, testCommand);

        //check 4: different command -> return false
        assertNotEquals(testCommand, new ClearCommand());

        //check 5: different subcommand -> return false
        TrackCommand differentSubcommand = new TrackCommand("stop", new Parameter(PREFIX_MUSCLE, "bicep"));
        assertNotEquals(testCommand, differentSubcommand);

        //check 6: different parameter -> return false
        TrackCommand differentParameter = new TrackCommand("start", new Parameter(PREFIX_MUSCLE, "tricep"));
        assertNotEquals(testCommand, differentParameter);
    }
}
