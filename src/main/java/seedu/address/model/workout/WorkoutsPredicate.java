package seedu.address.model.workout;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Workout}'s {@code Workouts} matches any of the workouts given.
 */
public class WorkoutsPredicate implements Predicate<Workout> {
    private final List<Workout> keyWorkouts;

    public WorkoutsPredicate(List<Workout> keyWorkouts) {
        this.keyWorkouts = keyWorkouts;
    }

    @Override
    public boolean test(Workout workout) {
        return keyWorkouts.stream()
                .anyMatch(keyWorkouts -> workout.equals(keyWorkouts));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WorkoutsPredicate // instanceof handles nulls
                && keyWorkouts.equals(((WorkoutsPredicate) other).keyWorkouts)); // state check
    }
}
