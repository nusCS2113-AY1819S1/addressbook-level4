package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.workout.Workout;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Workout> PREDICATE_MATCHING_NO_WORKOUTS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Workout> toDisplay) {
        Optional<Predicate<Workout>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredWorkoutList(predicate.orElse(PREDICATE_MATCHING_NO_WORKOUTS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Workout... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Workout} equals to {@code other}.
     */
    private static Predicate<Workout> getPredicateMatching(Workout other) {
        return workout -> workout.equals(other);
    }
}
