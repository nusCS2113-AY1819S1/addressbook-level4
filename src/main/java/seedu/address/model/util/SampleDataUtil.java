package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.ReadOnlyTrackedData;
import seedu.address.model.ReadOnlyTrackedDataList;
import seedu.address.model.ReadOnlyWorkoutBook;
import seedu.address.model.TrackedData;
import seedu.address.model.TrackedDataList;
import seedu.address.model.WorkoutBook;
import seedu.address.model.tag.Tag;
import seedu.address.model.workout.Calories;
import seedu.address.model.workout.Difficulty;
import seedu.address.model.workout.Duration;
import seedu.address.model.workout.Equipment;
import seedu.address.model.workout.Instruction;
import seedu.address.model.workout.Muscle;
import seedu.address.model.workout.Name;
import seedu.address.model.workout.Parameter;
import seedu.address.model.workout.Remark;
import seedu.address.model.workout.Type;
import seedu.address.model.workout.Workout;

/**
 * Contains utility methods for populating {@code WorkoutBook} with sample data.
 */
public class SampleDataUtil {
    public static Workout[] getSampleWorkouts() {
        return new Workout[] {
            new Workout(new Name("alex yeoh's workout"), new Type("strength"), new Duration("20m"),
                    new Difficulty("beginner"), new Equipment("dumbbell"), new Muscle("tricep"),
                    new Calories("150"), new Instruction("set1: tricep extension reps: 4-6"),
                    getTagSet("future", "heavy", "morning"),
                    new Remark("This is a great upper body workout, targeting the muscles in arms,"
                            + " the shoulders, the center and your back.")
            ),
            new Workout(new Name("commando workout"), new Type("strength, cardio"), new Duration("20m"),
                    new Difficulty("advanced"), new Equipment("dumbbell, bench"), new Muscle("triceps"),
                    new Calories("150"),
                    new Instruction("set 1: bicep curl reps: 4-6 set 2: tricep extension reps: 4-6"),
                    getTagSet("future", "heavy"), new Remark("")
            ),
            new Workout(new Name("5BX warm up"), new Type("warm up"), new Duration("10m"),
                    new Difficulty("beginner"), new Equipment("NIL"), new Muscle("everything"),
                    new Calories("100"), new Instruction("Jumping Jack 5 Counts of 4, Squat Bender 5 Counts of 4"
                    + "High Jumper 5 Counts of 4, Crunches 5 Counts of 4 and Push ups 5 Counts of 4"),
                    getTagSet("future", "army", "NSdailylife"), new Remark("")
            ),
            new Workout(new Name("long distance run"), new Type("endurance, aerobic"), new Duration("30m"),
                    new Difficulty("beginner"), new Equipment("NIL"), new Muscle("leg muscle"),
                    new Calories("225"),
                    new Instruction("run for 30 minutes without walking at your natural pace."),
                    getTagSet("future", "heavy"), new Remark("")
            ),
            new Workout(new Name("brisk walk"), new Type("endurance"), new Duration("20m"),
                    new Difficulty("beginner"), new Equipment("NIL"), new Muscle("leg muscle"),
                    new Calories("100"), new Instruction("20 minutes brisk walk"),
                    getTagSet("future", "heavy"), new Remark("")
            ),
            new Workout(new Name("interval training"), new Type("endurance, agility"), new Duration("20m"),
                    new Difficulty("advanced"), new Equipment("NIL"), new Muscle("leg muscle"),
                    new Calories("250"), new Instruction("Run around the track(400m) for about 1m 40s and rest for 1m, "
                    + "repeat 8 times"),
                    getTagSet("future", "heavy"), new Remark("")
            ),
            new Workout(new Name("circuit training"), new Type("endurance"), new Duration("15m"),
                    new Difficulty("advanced"), new Equipment("NIL"), new Muscle("everything"),
                    new Calories("250"), new Instruction("Each set consists of 10 of the following exercise: burpees,"
                    + " push ups, squats and high jump. Do this set 3 times with minimal rest."),
                    getTagSet("future", "tiring"), new Remark("")
            ),
            new Workout(new Name("one punch man training"), new Type("endurance, strength, agility"),
                new Duration("5m"),
                new Difficulty("advanced"), new Equipment("NIL"), new Muscle("everything"),
                new Calories("500"), new Instruction("100 push-ups, 100 sit-ups, 100 squats and run 10km"),
                getTagSet("future", "insane"), new Remark("")
            ),
            new Workout(new Name("cloud strife training"), new Type("strength, agility"),
                    new Duration("50m"),
                    new Difficulty("advanced"), new Equipment("NIL"), new Muscle("everything"),
                    new Calories("500"), new Instruction("Running across the open world"),
                    getTagSet("future", "ff7"), new Remark("")
            ),
            new Workout(new Name("High Intensity Interval Training 1"), new Type("strength, endurance"),
                    new Duration("15m"),
                    new Difficulty("beginner"), new Equipment("NIL"), new Muscle("everything"),
                    new Calories("150"), new Instruction("Each set consists of 10 of the following exercise: flutter"
                    + "kicks, push ups, half squats and burpees. Do this set 3 times with minimal rest."),
                    getTagSet("future", "HIIT"), new Remark("")
            ),
            new Workout(new Name("Ballet beautiful"), new Type("cardio, stretch"),
                    new Duration("15m"),
                    new Difficulty("beginner"), new Equipment("NIL"), new Muscle("arms"),
                    new Calories("100"), new Instruction("set1: Extends the arms straight side,"
                    + "drop the elbows and lift for 8 times" + "repeats for 4 sets"),
                    getTagSet("future", "ballet"), new Remark("")
            ),
            new Workout(new Name("Zumba dancer workout"), new Type("cardio, endurance"),
                    new Duration("20m"),
                    new Difficulty("beginner"), new Equipment("NIL"), new Muscle("everything"),
                    new Calories("300"), new Instruction("Step to the right. Bend your knees."
                    + "Return to the middle, Step to the left. Bend your knees slightly."
                    + "Return to the middle."),
                    getTagSet("zumba"), new Remark("")
            ),
            new Workout(new Name("Alice Pauline's workout"), new Type("balance"),
                    new Duration("5m"),
                    new Difficulty("advanced"), new Equipment("NIL"), new Muscle("bicep"),
                    new Calories("20"), new Instruction("Set1: chin-ups as many as you can"),
                    getTagSet("rigorous", "future"), new Remark("")
            ),
            new Workout(new Name("Benson Meier's workout"), new Type("strength"),
                    new Duration("10m"),
                    new Difficulty("intermediate"), new Equipment("bar"), new Muscle("deltoids"),
                    new Calories("100"), new Instruction("set1: shoulder press reps: 5-7"),
                    getTagSet("heavy", "future"), new Remark("")
            ),
            new Workout(new Name("Carl Kurz's workout"), new Type("cardio"),
                    new Duration("25m"),
                    new Difficulty("intermediate"), new Equipment("NIL"), new Muscle("legs"),
                    new Calories("135"), new Instruction("set1: leg raises reps: 15-20"),
                    getTagSet("future"), new Remark("")
            ),
            new Workout(new Name("Daniel Meier's workout"), new Type("cardio"),
                    new Duration("30m"),
                    new Difficulty("intermediate"), new Equipment("NIL"), new Muscle("legs"),
                    new Calories("200"), new Instruction("30 minutes slow jog"),
                    getTagSet("favourite", "future"), new Remark("")
            ),
            new Workout(new Name("Elle Meyer's workout"), new Type("endurance"),
                    new Duration("20m"),
                    new Difficulty("beginner"), new Equipment("NIL"), new Muscle("legs"),
                    new Calories("100"), new Instruction("20 minutes brisk walk"),
                    getTagSet("future"), new Remark("")
            ),
            new Workout(new Name("Fiona Kunz's workout"), new Type("strength"),
                    new Duration("40m"),
                    new Difficulty("advanced"), new Equipment("medicine ball"), new Muscle("arms"),
                    new Calories("225"), new Instruction("Set1: medicine ball throw reps: 25-30"),
                    getTagSet("future"), new Remark("")
            ),
            new Workout(new Name("George Best's workout"), new Type("strength"),
                    new Duration("30m"),
                    new Difficulty("intermediate"), new Equipment("free weights"), new Muscle("chest"),
                    new Calories("175"), new Instruction("Set1: chin-ups as many as you can"),
                    getTagSet("rigorous", "future"), new Remark("")
            ),
            new Workout(new Name("Hoon Meier's workout"), new Type("endurance"),
                    new Duration("10m"),
                    new Difficulty("beginner"), new Equipment("yoga mat"), new Muscle("core"),
                    new Calories("90"), new Instruction("set1: planks reps: 5 x 1 minute"),
                    getTagSet("future"), new Remark("")
            ),



        };
    }

    public static ReadOnlyWorkoutBook getSampleWorkoutBook() {
        WorkoutBook sampleWb = new WorkoutBook();
        for (Workout sampleWorkout : getSampleWorkouts()) {
            sampleWb.addWorkout(sampleWorkout);
        }
        return sampleWb;
    }

    public static Parameter[] getSampleParameters() {
        return new Parameter[] {
            new Parameter(new Prefix("name/"), "commando"),
            new Parameter(new Prefix("duration/"), "20m"),
            new Parameter(new Prefix("difficulty/"), "advanced"),
            new Parameter(new Prefix("equipment/"), "dumbbell"),
            new Parameter(new Prefix("calories/"), "150")
        };
    }

    public static ReadOnlyTrackedDataList getSampleTrackedDataList() {
        TrackedDataList sampleTdl = new TrackedDataList();
        for (Parameter sampleParameter : getSampleParameters()) {
            sampleTdl.addParameter(sampleParameter);
        }
        return sampleTdl;
    }

    public static ReadOnlyTrackedData getEmptyTrackedData() {
        TrackedData emptyTrackedData = new TrackedData();
        return emptyTrackedData;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
