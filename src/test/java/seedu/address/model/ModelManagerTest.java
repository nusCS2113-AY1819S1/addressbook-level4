package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WORKOUTS;
import static seedu.address.testutil.TypicalParameters.NAME_PARAMETER;
import static seedu.address.testutil.TypicalParameters.TYPE_PARAMETER;
import static seedu.address.testutil.TypicalWorkouts.ALICE_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.BENSON_WORKOUT;

import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.TrackedDataListBuilder;
import seedu.address.testutil.WorkoutBookBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasWorkout_nullWorkout_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasWorkout(null);
    }

    @Test
    public void hasWorkout_workoutNotInWorkoutBook_returnsFalse() {
        assertFalse(modelManager.hasWorkout(ALICE_WORKOUT));
    }

    @Test
    public void hasWorkout_workoutInWorkoutBook_returnsTrue() {
        modelManager.addWorkout(ALICE_WORKOUT);
        assertTrue(modelManager.hasWorkout(ALICE_WORKOUT));
    }

    @Test
    public void hasParameter_nullWorkout_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasParameter(null);
    }

    @Test
    public void hasParameter_parameterNotInTrackedDataList_returnsFalse() {
        assertFalse(modelManager.hasParameter(NAME_PARAMETER));
    }

    @Test
    public void hasParameter_parameterInTrackedDataList_returnsTrue() {
        modelManager.addDataToTrack(NAME_PARAMETER);
        assertTrue(modelManager.hasParameter(NAME_PARAMETER));
    }

    @Test
    public void getFilteredWorkoutList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredWorkoutList().remove(0);
    }

    @Test
    public void getFilteredTrackedDataList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredTrackedDataList().remove(0);
    }

    @Test
    public void getFilteredTrackedData_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredTrackedData().remove(0);
    }

    @Test
    public void equals() {
        WorkoutBook workoutBook = new WorkoutBookBuilder().withWorkout(ALICE_WORKOUT).withWorkout(BENSON_WORKOUT)
                .build();
        WorkoutBook differentWorkoutBook = new WorkoutBook();
        TrackedDataList trackedDataList = new TrackedDataListBuilder().withParameter(NAME_PARAMETER)
                .withParameter(TYPE_PARAMETER).build();
        TrackedDataList differentTrackedDataList = new TrackedDataList();
        TrackedData trackedData = new TrackedData();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(workoutBook, trackedDataList, trackedData, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(workoutBook, trackedDataList, trackedData, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different workoutBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentWorkoutBook, trackedDataList, trackedData,
                userPrefs)));

        // different trackedDataList -> returns false
        assertFalse(modelManager.equals(new ModelManager(workoutBook, differentTrackedDataList, trackedData,
                userPrefs)));

        /* different filteredList -> returns false
        String[] keywords = ALICE_WORKOUT.getName().fullName.replace("workout","").split("\\s+");
        modelManager.updateFilteredWorkoutList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(workoutBook, userPrefs)));
        This test case is removed as the word "workout" can exists in another person's workout name */

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredWorkoutList(PREDICATE_SHOW_ALL_WORKOUTS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setWorkoutBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(workoutBook, trackedDataList, trackedData,
                differentUserPrefs)));
    }
}
