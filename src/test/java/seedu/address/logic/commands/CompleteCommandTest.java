package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showWorkoutAtIndex;
import static seedu.address.logic.commands.CompleteCommand.createEditedWorkout;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WORKOUT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_WORKOUT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_WORKOUT;
import static seedu.address.testutil.TypicalParameters.getTypicalTrackedDataList;
import static seedu.address.testutil.TypicalWorkouts.getTypicalWorkoutBookForCompleteCommand;

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
import seedu.address.model.workout.Workout;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code CompleteCommand}.
 */
public class CompleteCommandTest {

    private static String currentDifficulty;
    private static String currentCalories;
    private static String currentDuration;

    private Model model = new ModelManager(getTypicalWorkoutBookForCompleteCommand(), getTypicalTrackedDataList(),
            new TrackedData(), new UserPrefs());
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
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredWorkoutList().size() + 1);
        CompleteCommand completeCommand = new CompleteCommand(outOfBoundIndex);

        assertCommandFailure(completeCommand, model, commandHistory, Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws CommandException {
        Workout currentWorkout = model.getFilteredWorkoutList().get(INDEX_THIRD_WORKOUT.getZeroBased());
        Workout editedWorkout = createEditedWorkout(currentWorkout);

        CompleteCommand completeCommand = new CompleteCommand(INDEX_THIRD_WORKOUT);

        String expectedMessage = String.format(CompleteCommand.MESSAGE_COMPLETE_WORKOUT_SUCCESS, editedWorkout);

        Model expectedModel = new ModelManager(model.getWorkoutBook(), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());
        expectedModel.updateWorkout(model.getFilteredWorkoutList().get(2), editedWorkout);
        expectedModel.commitModel();

        assertCommandSuccess(completeCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showWorkoutAtIndex(model, INDEX_FIRST_WORKOUT);

        Index outOfBoundIndex = INDEX_SECOND_WORKOUT;
        // ensures that outOfBoundIndex is still in bounds of workout book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getWorkoutBook().getWorkoutList().size());

        CompleteCommand completeCommand = new CompleteCommand(outOfBoundIndex);

        assertCommandFailure(completeCommand, model, commandHistory, Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredWorkoutList().size() + 1);
        CompleteCommand completeCommand = new CompleteCommand(outOfBoundIndex);

        // execution failed -> workout book state not added into model
        assertCommandFailure(completeCommand, model, commandHistory, Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);

        // single workout book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        CompleteCommand completeFirstCommand = new CompleteCommand(INDEX_FIRST_WORKOUT);
        CompleteCommand completeSecondCommand = new CompleteCommand(INDEX_SECOND_WORKOUT);

        // same object -> returns true
        assertTrue(completeFirstCommand.equals(completeFirstCommand));

        // same values -> returns true
        CompleteCommand deleteFirstCommandCopy = new CompleteCommand(INDEX_FIRST_WORKOUT);
        assertTrue(completeFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(completeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(completeFirstCommand.equals(null));

        // different workout -> returns false
        assertFalse(completeFirstCommand.equals(completeSecondCommand));
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
