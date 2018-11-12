package seedu.address.testutil;

import seedu.address.model.TrackedData;
import seedu.address.model.workout.Workout;

/**
 * A utility class to help with building TrackedData objects.
 * Example usage: <br>
 *     {@code TrackedData td = new TrackedDataBuilder().withWorkout(AMY_WORKOUT).build();}
 */
public class TrackedDataBuilder {

    private TrackedData trackedData;

    public TrackedDataBuilder() {
        trackedData = new TrackedData();
    }

    public TrackedDataBuilder(TrackedData trackedData) {
        this.trackedData = trackedData;
    }

    /**
     * Adds a new {@code Workout} to the {@code TrackedData} that we are building.
     */
    public TrackedDataBuilder withWorkout(Workout workout) {
        trackedData.addWorkout(workout);
        return this;
    }

    public TrackedData build() {
        return trackedData;
    }
}
