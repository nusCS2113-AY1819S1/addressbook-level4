package seedu.address.testutil;

import seedu.address.model.TrackedDataList;
import seedu.address.model.workout.Parameter;

/**
 * A utility class to help with building TrackedDataList objects.
 * Example usage: <br>
 *     {@code TrackedDataList tdl = new TrackedDataListBuilder().withParameters(parameter).build();}
 */
public class TrackedDataListBuilder {

    private TrackedDataList trackedDataList;

    public TrackedDataListBuilder() {
        trackedDataList = new TrackedDataList();
    }

    public TrackedDataListBuilder(TrackedDataList trackedDataList) {
        this.trackedDataList = trackedDataList;
    }

    /**
     * Adds a new {@code Workout} to the {@code WorkoutBook} that we are building.
     */
    public TrackedDataListBuilder withParameter(Parameter parameter) {
        trackedDataList.addParameter(parameter);
        return this;
    }

    public TrackedDataList build() {
        return trackedDataList;
    }
}
