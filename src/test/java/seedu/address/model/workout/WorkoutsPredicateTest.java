package seedu.address.model.workout;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.WorkoutBuilder;

public class WorkoutsPredicateTest {

    @Test
    public void equals() {
        List<Workout> firstWorkoutsPredicateList = Collections.singletonList(new WorkoutBuilder().withName("Alice")
                .build());
        List<Workout> secondWorkoutsPredicateList = Arrays.asList(new WorkoutBuilder().withName("Alice")
                .build(), new WorkoutBuilder().withName("Bob").build());

        WorkoutsPredicate firstPredicate = new WorkoutsPredicate(firstWorkoutsPredicateList);
        WorkoutsPredicate secondPredicate = new WorkoutsPredicate(secondWorkoutsPredicateList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        WorkoutsPredicate firstPredicateCopy = new WorkoutsPredicate(firstWorkoutsPredicateList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different workout -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_workoutContainedInKeyWorkouts_returnsTrue() {
        // One key workout
        WorkoutsPredicate predicate = new WorkoutsPredicate(Collections.singletonList(new WorkoutBuilder()
                .withName("Carol").build()));
        assertTrue(predicate.test(new WorkoutBuilder().withName("Carol").build()));

        // Multiple key workouts
        predicate = new WorkoutsPredicate(Arrays.asList(new WorkoutBuilder().withName("Alice")
                .build(), new WorkoutBuilder().withName("Bob").build()));
        assertTrue(predicate.test(new WorkoutBuilder().withName("Alice").build()));
    }

    @Test
    public void test_workoutNotContainedInKeyWorkouts_returnsFalse() {
        // Zero key workouts
        WorkoutsPredicate predicate = new WorkoutsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new WorkoutBuilder().withName("Bob").build()));

        // Non-matching key workout
        predicate = new WorkoutsPredicate(Arrays.asList(new WorkoutBuilder().withName("Alice")
                .build(), new WorkoutBuilder().withName("Bob").build()));
        assertFalse(predicate.test(new WorkoutBuilder().withName("Carol").build()));
    }

}
