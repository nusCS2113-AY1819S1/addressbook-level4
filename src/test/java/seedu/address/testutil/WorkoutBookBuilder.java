package seedu.address.testutil;

import seedu.address.model.WorkoutBook;
import seedu.address.model.workout.Workout;

/**
 * A utility class to help with building Workoutbook objects.
 * Example usage: <br>
 *     {@code WorkoutBook ab = new WorkoutBookBuilder().withWorkout(AMY_WORKOUT).build();}
 */
public class WorkoutBookBuilder {

    private WorkoutBook workoutBook;

    public WorkoutBookBuilder() {
        workoutBook = new WorkoutBook();
    }

    public WorkoutBookBuilder(WorkoutBook workoutBook) {
        this.workoutBook = workoutBook;
    }

    /**
     * Adds a new {@code Workout} to the {@code WorkoutBook} that we are building.
     */
    public WorkoutBookBuilder withWorkout(Workout workout) {
        workoutBook.addWorkout(workout);
        return this;
    }

    public WorkoutBook build() {
        return workoutBook;
    }
}
