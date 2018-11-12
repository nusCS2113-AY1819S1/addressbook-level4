package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CALORIES_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALORIES_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALORIES_JOHN_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_JOHN_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_JOHN_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EQUIPMENT_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EQUIPMENT_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EQUIPMENT_JOHN_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTRUCTION_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTRUCTION_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTRUCTION_JOHN_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MUSCLE_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MUSCLE_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MUSCLE_JOHN_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JOHN_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FUTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NIGHT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_JOHN_WORKOUT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TrackedData;
import seedu.address.model.WorkoutBook;
import seedu.address.model.workout.Workout;

/**
 * A utility class containing a list of {@code Workouts} objects to be used in tests.
 */
public class TypicalWorkouts {

    public static final Workout ALICE_WORKOUT = new WorkoutBuilder().withName("Alice Pauline's workout")
            .withType("balance").withDuration("5m")
            .withDifficulty("advanced").withEquipment("NIL")
            .withMuscle("bicep").withCalories("20")
            .withInstruction("Set1: chin-ups as many as you can").withTags("rigorous", "future").build();
    public static final Workout BENSON_WORKOUT = new WorkoutBuilder().withName("Benson Meier's workout")
            .withType("strength").withDuration("10m")
            .withDifficulty("intermediate").withEquipment("bar")
            .withMuscle("deltoids").withCalories("100")
            .withInstruction("set1: shoulder press reps: 5-7").withTags("heavy", "future").build();
    public static final Workout CARL_WORKOUT = new WorkoutBuilder().withName("Carl Kurz's workout")
            .withType("cardio").withDuration("25m")
            .withDifficulty("intermediate").withEquipment("NIL")
            .withMuscle("legs").withCalories("135")
            .withInstruction("set1: leg raises reps: 15-20").withTags("future").build();
    public static final Workout CARL_WORKOUT_CURRENT = new WorkoutBuilder().withName("Carl Kurz's workout")
            .withType("cardio").withDuration("25m")
            .withDifficulty("intermediate").withEquipment("NIL")
            .withMuscle("legs").withCalories("135")
            .withInstruction("set1: leg raises reps: 15-20").withTags("current").build();
    public static final Workout DANIEL_WORKOUT = new WorkoutBuilder().withName("Daniel Meier's workout")
            .withType("cardio").withDuration("30m")
            .withDifficulty("intermediate").withEquipment("NIL")
            .withMuscle("legs").withCalories("200")
            .withInstruction("30 minutes slow jog").withTags("favourite", "future").build();
    public static final Workout ELLE_WORKOUT = new WorkoutBuilder().withName("Elle Meyer's workout")
            .withType("endurance").withDuration("20m")
            .withDifficulty("beginner").withEquipment("NIL")
            .withMuscle("legs").withCalories("100")
            .withInstruction("20 minutes brisk walk").withTags("future").build();
    public static final Workout FIONA_WORKOUT = new WorkoutBuilder().withName("Fiona Kunz's workout")
            .withType("strength").withDuration("40m")
            .withDifficulty("advanced").withEquipment("medicine ball")
            .withMuscle("arms").withCalories("225")
            .withInstruction("set1: medicine ball throw reps: 25-30").withTags("future").build();
    public static final Workout GEORGE_WORKOUT = new WorkoutBuilder().withName("George Best's workout")
            .withType("strength").withDuration("30m")
            .withDifficulty("intermediate").withEquipment("free weights")
            .withMuscle("chest").withCalories("175")
            .withInstruction("6 sets of bench press, 6 reps per set of 70% max").withTags("future").build();

    // Manually added
    public static final Workout HOON_WORKOUT = new WorkoutBuilder().withName("Hoon Meier's workout")
            .withType("endurance").withDuration("10m")
            .withDifficulty("beginner").withEquipment("yoga mat")
            .withMuscle("core").withCalories("90")
            .withInstruction("set1: planks reps: 5 x 1 minute").withTags("future").build();
    public static final Workout IDA_WORKOUT = new WorkoutBuilder().withName("Ida Mueller's workout")
            .withType("strength").withDuration("35m")
            .withDifficulty("intermediate").withEquipment("dumbbells")
            .withMuscle("chest").withCalories("160")
            .withInstruction("set1: chest fly reps: 5 - 10").withTags("future").build();

    // Manually added - Workout details found in {@code CommandTestUtil}
    public static final Workout AMY_WORKOUT = new WorkoutBuilder().withName(VALID_NAME_AMY_WORKOUT)
            .withType(VALID_TYPE_AMY_WORKOUT).withDuration(VALID_DURATION_AMY_WORKOUT)
            .withDifficulty(VALID_DIFFICULTY_AMY_WORKOUT).withEquipment(VALID_EQUIPMENT_AMY_WORKOUT)
            .withMuscle(VALID_MUSCLE_AMY_WORKOUT).withCalories(VALID_CALORIES_AMY_WORKOUT)
            .withInstruction(VALID_INSTRUCTION_AMY_WORKOUT).withTags(VALID_TAG_FUTURE).build();
    public static final Workout BOB_WORKOUT = new WorkoutBuilder().withName(VALID_NAME_BOB_WORKOUT)
            .withType(VALID_TYPE_BOB_WORKOUT).withDuration(VALID_DURATION_BOB_WORKOUT)
            .withDifficulty(VALID_DIFFICULTY_BOB_WORKOUT).withEquipment(VALID_EQUIPMENT_BOB_WORKOUT)
            .withMuscle(VALID_MUSCLE_BOB_WORKOUT).withCalories(VALID_CALORIES_BOB_WORKOUT)
            .withInstruction(VALID_INSTRUCTION_BOB_WORKOUT).withTags(VALID_TAG_NIGHT, VALID_TAG_FUTURE).build();
    public static final Workout JOHN_WORKOUT = new WorkoutBuilder().withName(VALID_NAME_JOHN_WORKOUT)
            .withType(VALID_TYPE_JOHN_WORKOUT).withDuration(VALID_DURATION_JOHN_WORKOUT)
            .withDifficulty(VALID_DIFFICULTY_JOHN_WORKOUT).withEquipment(VALID_EQUIPMENT_JOHN_WORKOUT)
            .withMuscle(VALID_MUSCLE_JOHN_WORKOUT).withCalories(VALID_CALORIES_JOHN_WORKOUT)
            .withInstruction(VALID_INSTRUCTION_JOHN_WORKOUT).withTags(VALID_TAG_FUTURE).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalWorkouts() {} // prevents instantiation

    /**
     * Returns an {@code WorkoutBook} with all the typical workouts.
     */
    public static WorkoutBook getTypicalWorkoutBook() {
        WorkoutBook wb = new WorkoutBook();
        for (Workout workout : getTypicalWorkouts()) {
            wb.addWorkout(workout);
        }
        return wb;
    }

    public static WorkoutBook getTypicalWorkoutBookForCompleteCommand() {
        WorkoutBook wb = new WorkoutBook();
        for (Workout workout : getTypicalWorkoutsForCompleteCommand()) {
            wb.addWorkout(workout);
        }
        return wb;
    }

    /**
     * Returns an {@code TrackedData} with all the typical workouts.
     */
    public static TrackedData getTypicalTrackedData() {
        TrackedData td = new TrackedData();
        for (Workout workout : getTypicalWorkouts()) {
            td.addWorkout(workout);
        }
        return td;
    }

    public static List<Workout> getTypicalWorkouts() {
        return new ArrayList<>(Arrays.asList(ALICE_WORKOUT, BENSON_WORKOUT, CARL_WORKOUT, DANIEL_WORKOUT, ELLE_WORKOUT,
                FIONA_WORKOUT, GEORGE_WORKOUT, JOHN_WORKOUT));
    }

    public static List<Workout> getTypicalWorkoutsForCompleteCommand() {
        return new ArrayList<>(Arrays.asList(ALICE_WORKOUT, BENSON_WORKOUT, CARL_WORKOUT_CURRENT, DANIEL_WORKOUT,
                ELLE_WORKOUT, FIONA_WORKOUT, GEORGE_WORKOUT, JOHN_WORKOUT));
    }
}
