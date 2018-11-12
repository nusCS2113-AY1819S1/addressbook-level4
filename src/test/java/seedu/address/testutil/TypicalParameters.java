package seedu.address.testutil;

import static seedu.address.testutil.WorkoutBuilder.DEFAULT_CALORIES;
import static seedu.address.testutil.WorkoutBuilder.DEFAULT_DIFFICULTY;
import static seedu.address.testutil.WorkoutBuilder.DEFAULT_DURATION;
import static seedu.address.testutil.WorkoutBuilder.DEFAULT_EQUIPMENT;
import static seedu.address.testutil.WorkoutBuilder.DEFAULT_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TrackedDataList;
import seedu.address.model.workout.Parameter;

/**
 * A utility class containing a list of {@code Parameter} objects to be used in tests.
 */
public class TypicalParameters {

    public static final Parameter NAME_PARAMETER = new ParameterBuilder().withPrefix("name/").withValue(DEFAULT_NAME)
            .build();
    public static final Parameter TYPE_PARAMETER = new ParameterBuilder().withPrefix("type/").withValue("strength")
            .build();
    public static final Parameter DURATION_PARAMETER = new ParameterBuilder().withPrefix("duration/")
            .withValue(DEFAULT_DURATION).build();
    public static final Parameter DIFFICULTY_PARAMETER = new ParameterBuilder().withPrefix("difficulty/")
            .withValue(DEFAULT_DIFFICULTY).build();
    public static final Parameter EQUIPMENT_PARAMETER = new ParameterBuilder().withPrefix("equipment/")
            .withValue(DEFAULT_EQUIPMENT).build();
    public static final Parameter MUSCLE_PARAMETER = new ParameterBuilder().withPrefix("muscle/")
            .withValue("bicep").build();
    public static final Parameter CALORIES_PARAMETER = new ParameterBuilder().withPrefix("calories/")
            .withValue(DEFAULT_CALORIES).build();
    public static final Parameter INSTRUCTION_PARAMETER = new ParameterBuilder().withPrefix("instruction/")
            .withValue("extension").build();

    // Manually added
    public static final Parameter NAME_PARAMETER_MANUAL = new ParameterBuilder().withPrefix("name/")
            .withValue("test").build();
    public static final Parameter TYPE_PARAMETER_MANUAL = new ParameterBuilder().withPrefix("type/")
            .withValue("test").build();

    private TypicalParameters() {} // prevents instantiation

    /**
     * Returns an {@code TrackedDataList} with all the typical parameters.
     */
    public static TrackedDataList getTypicalTrackedDataList() {
        TrackedDataList tdl = new TrackedDataList();
        for (Parameter parameter : getTypicalParameters()) {
            tdl.addParameter(parameter);
        }
        return tdl;
    }

    public static List<Parameter> getTypicalParameters() {
        return new ArrayList<>(Arrays.asList(NAME_PARAMETER, TYPE_PARAMETER, DURATION_PARAMETER, DIFFICULTY_PARAMETER,
                EQUIPMENT_PARAMETER, MUSCLE_PARAMETER, CALORIES_PARAMETER, INSTRUCTION_PARAMETER));
    }
}
