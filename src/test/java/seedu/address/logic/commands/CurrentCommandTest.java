package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showWorkoutAtIndex;
import static seedu.address.logic.commands.CurrentCommand.MESSAGE_CONTINUE;
import static seedu.address.logic.commands.CurrentCommand.MESSAGE_HIGHER_CALORIES;
import static seedu.address.logic.commands.CurrentCommand.MESSAGE_HIGHER_DURATION;
import static seedu.address.logic.commands.CurrentCommand.MESSAGE_MORE_DIFFICULT;
import static seedu.address.logic.commands.CurrentCommand.MESSAGE_MULTIPLE_CURRENT_WORKOUT;
import static seedu.address.logic.commands.CurrentCommand.createEditedWorkout;
import static seedu.address.testutil.TypicalIndexes.INDEX_EIGHTH_WORKOUT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WORKOUT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_WORKOUT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SIXTH_WORKOUT;
import static seedu.address.testutil.TypicalParameters.getTypicalTrackedDataList;
import static seedu.address.testutil.TypicalWorkouts.getTypicalWorkoutBook;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ProfileWindowManager;
import seedu.address.model.TrackedData;
import seedu.address.model.UserPrefs;
import seedu.address.model.WorkoutBook;
import seedu.address.model.workout.Workout;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code CurrentCommand}.
 */
public class CurrentCommandTest {

    private static String currentDifficulty;
    private static String currentCalories;
    private static String currentDuration;

    private Model model = new ModelManager(getTypicalWorkoutBook(), getTypicalTrackedDataList(), new TrackedData(),
            new UserPrefs());
    private ProfileWindowManager profileWindowManager;
    private CommandHistory commandHistory = new CommandHistory();
    private String fileName;
    private Document doc;

    @Before
    @SuppressWarnings("Duplicates")
    public void setUp() throws IOException {
        CurrentCommand.setCurrentWorkout(false);

        //prevents the Pop up message(GUI) for current command since Travis does not have GUI functionality
        profileWindowManager = ProfileWindowManager.getInstance();
        String workingDir = System.getProperty("user.dir");
        fileName = workingDir + "/ProfileWindow.html";
        doc = Jsoup.parse(new File(fileName), "UTF-8");

        Element divDifficulty = doc.getElementById("difficulty");
        Element divCalories = doc.getElementById("calories");
        Element divDuration = doc.getElementById("duration");

        currentDifficulty = divDifficulty.ownText();
        currentCalories = divCalories.ownText();
        currentDuration = divDuration.ownText();

        profileWindowManager.setDuration("any");
        profileWindowManager.setCalories("any");
        profileWindowManager.setDifficulty("any");
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void execute_validIndexUnfilteredList_success() throws CommandException {
        Workout currentWorkout = model.getFilteredWorkoutList().get(INDEX_EIGHTH_WORKOUT.getZeroBased());
        Workout editedWorkout = createEditedWorkout(currentWorkout);

        CurrentCommand currentCommand = new CurrentCommand(INDEX_EIGHTH_WORKOUT);

        String expectedMessage = String.format(CurrentCommand.MESSAGE_CURRENT_WORKOUT_SUCCESS, editedWorkout);

        Model expectedModel = new ModelManager(model.getWorkoutBook(), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());
        expectedModel.updateWorkout(model.getFilteredWorkoutList().get(7), editedWorkout);
        expectedModel.commitModel();

        assertCommandSuccess(currentCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void execute_validIndexUnfilteredListCompletedTag_success() throws CommandException {
        Workout currentWorkout = model.getFilteredWorkoutList().get(INDEX_SIXTH_WORKOUT.getZeroBased());
        Workout editedWorkout = createEditedWorkout(currentWorkout);

        CurrentCommand currentCommand = new CurrentCommand(INDEX_SIXTH_WORKOUT);

        String expectedMessage = String.format(CurrentCommand.MESSAGE_CURRENT_WORKOUT_SUCCESS, editedWorkout);

        Model expectedModel = new ModelManager(model.getWorkoutBook(), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());
        expectedModel.updateWorkout(model.getFilteredWorkoutList().get(5), editedWorkout);
        expectedModel.commitModel();

        assertCommandSuccess(currentCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_preexistingCurrentWorkoutUnfilteredList_failure() throws CommandException {
        Workout currentWorkout = model.getFilteredWorkoutList().get(INDEX_FIRST_WORKOUT.getZeroBased());
        Workout editedWorkout = createEditedWorkout(currentWorkout);
        model.updateWorkout(currentWorkout, editedWorkout);

        CurrentCommand currentCommand = new CurrentCommand(INDEX_FIRST_WORKOUT);

        assertCommandFailure(currentCommand, model, commandHistory, MESSAGE_MULTIPLE_CURRENT_WORKOUT);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredWorkoutList().size() + 1);
        CurrentCommand currentCommand = new CurrentCommand(outOfBoundIndex);

        assertCommandFailure(currentCommand, model, commandHistory, Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws CommandException {
        showWorkoutAtIndex(model, INDEX_EIGHTH_WORKOUT);

        Workout editedWorkout = createEditedWorkout(model.getFilteredWorkoutList()
                .get(INDEX_FIRST_WORKOUT.getZeroBased()));
        CurrentCommand currentCommand = new CurrentCommand(INDEX_FIRST_WORKOUT);

        String expectedMessage = String.format(CurrentCommand.MESSAGE_CURRENT_WORKOUT_SUCCESS, editedWorkout);

        Model expectedModel = new ModelManager(new WorkoutBook(model.getWorkoutBook()), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());
        expectedModel.updateWorkout(model.getFilteredWorkoutList().get(0), editedWorkout);
        expectedModel.commitModel();

        assertCommandSuccess(currentCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_preexistingCurrentWorkoutFilteredList_failure() throws CommandException {
        Workout currentWorkout = model.getFilteredWorkoutList().get(INDEX_FIRST_WORKOUT.getZeroBased());
        Workout editedWorkout = createEditedWorkout(currentWorkout);
        model.updateWorkout(currentWorkout, editedWorkout);

        showWorkoutAtIndex(model, INDEX_FIRST_WORKOUT);
        CurrentCommand currentCommand = new CurrentCommand(INDEX_FIRST_WORKOUT);

        assertCommandFailure(currentCommand, model, commandHistory, MESSAGE_MULTIPLE_CURRENT_WORKOUT);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showWorkoutAtIndex(model, INDEX_FIRST_WORKOUT);

        Index outOfBoundIndex = INDEX_SECOND_WORKOUT;
        // ensures that outOfBoundIndex is still in bounds of workout book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getWorkoutBook().getWorkoutList().size());

        CurrentCommand currentCommand = new CurrentCommand(outOfBoundIndex);

        assertCommandFailure(currentCommand, model, commandHistory, Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Workout currentWorkout = model.getFilteredWorkoutList().get(INDEX_FIRST_WORKOUT.getZeroBased());
        CurrentCommand currentCommand = new CurrentCommand(INDEX_FIRST_WORKOUT);
        Workout editedWorkout = createEditedWorkout(currentWorkout);
        Model expectedModel = new ModelManager(new WorkoutBook(model.getWorkoutBook()), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());
        expectedModel.updateWorkout(currentWorkout, editedWorkout);
        expectedModel.commitModel();

        // current -> first workout set to current
        currentCommand.execute(model, commandHistory);

        // undo -> reverts workout book back to previous state and filtered workout list to show all workouts
        expectedModel.undoModel();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first workout deleted again
        expectedModel.redoModel();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredWorkoutList().size() + 1);
        CurrentCommand currentCommand = new CurrentCommand(outOfBoundIndex);

        // execution failed -> workout book state not added into model
        assertCommandFailure(currentCommand, model, commandHistory, Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);

        // single workout book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void popUpMessageTest() {
        boolean difficulty;
        boolean calories;
        boolean duration;

        difficulty = false;
        calories = false;
        duration = true;
        assertEquals(CurrentCommand.popUpMessage(difficulty, calories, duration), MESSAGE_HIGHER_DURATION
                + MESSAGE_CONTINUE);

        difficulty = false;
        calories = true;
        duration = false;
        assertEquals(CurrentCommand.popUpMessage(difficulty, calories, duration), MESSAGE_HIGHER_CALORIES
                + MESSAGE_CONTINUE);

        difficulty = true;
        calories = false;
        duration = false;
        assertEquals(CurrentCommand.popUpMessage(difficulty, calories, duration), MESSAGE_MORE_DIFFICULT
                + MESSAGE_CONTINUE);

        difficulty = true;
        calories = true;
        duration = false;
        assertEquals(CurrentCommand.popUpMessage(difficulty, calories, duration), "This workout is more difficult"
                + " than your indicated workout difficulty and requires more calories to"
                + " be burnt than your preferred calories.\n" + MESSAGE_CONTINUE);

        difficulty = true;
        calories = false;
        duration = true;
        assertEquals(CurrentCommand.popUpMessage(difficulty, calories, duration), "This workout is more difficult"
                + " than your indicated workout difficulty and will take longer than " + "your preferred duration.\n"
                + MESSAGE_CONTINUE);

        difficulty = false;
        calories = true;
        duration = true;
        assertEquals(CurrentCommand.popUpMessage(difficulty, calories, duration), "This workout requires more"
                + " calories to be burnt than your preferred calories and will take longer than your preferred"
                + " duration.\n" + MESSAGE_CONTINUE);

        difficulty = true;
        calories = true;
        duration = true;
        assertEquals(CurrentCommand.popUpMessage(difficulty, calories, duration), "This workout is more difficult"
                + " than your indicated workout difficulty, requires more calories to be burnt than your preferred"
                + " calories and will take longer than your preferred duration.\n"
                + MESSAGE_CONTINUE);
    }

    @Test
    public void equals() {
        CurrentCommand currentFirstCommand = new CurrentCommand(INDEX_FIRST_WORKOUT);

        // same object -> returns true
        assertTrue(currentFirstCommand.equals(currentFirstCommand));

        // same values -> returns true
        CurrentCommand deleteFirstCommandCopy = new CurrentCommand(INDEX_FIRST_WORKOUT);
        assertTrue(currentFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(currentFirstCommand.equals(1));

        // null -> returns false
        assertFalse(currentFirstCommand.equals(null));
    }

    @After
    @SuppressWarnings("Duplicates")
    public void revert() throws IOException {
        String workingDir = System.getProperty("user.dir");
        fileName = workingDir + "/ProfileWindow.html";
        doc = Jsoup.parse(new File(fileName), "UTF-8");

        Element divDifficulty = doc.getElementById("difficulty");
        Element divCalories = doc.getElementById("calories");
        Element divDuration = doc.getElementById("duration");

        divDifficulty.text(currentDifficulty);
        divCalories.text(currentCalories);
        divDuration.text(currentDuration);
    }
}
