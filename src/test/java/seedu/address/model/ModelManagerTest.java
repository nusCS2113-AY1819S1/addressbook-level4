package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WORKOUTS;
import static seedu.address.testutil.TypicalWorkouts.ALICE_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.BENSON_WORKOUT;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.workout.NameContainsKeywordsPredicate;
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
    public void getFilteredWorkoutList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredWorkoutList().remove(0);
    }

    @Test
    public void equals() {
        WorkoutBook workoutBook = new WorkoutBookBuilder().withWorkout(ALICE_WORKOUT).withWorkout(BENSON_WORKOUT).build();
        WorkoutBook differentWorkoutBook = new WorkoutBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(workoutBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(workoutBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different workoutBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentWorkoutBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE_WORKOUT.getName().fullName.split("\\s+");
        modelManager.updateFilteredWorkoutList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(workoutBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredWorkoutList(PREDICATE_SHOW_ALL_WORKOUTS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setWorkoutBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(workoutBook, differentUserPrefs)));
    }
}
