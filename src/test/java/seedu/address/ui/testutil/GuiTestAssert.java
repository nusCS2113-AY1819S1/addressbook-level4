package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.WorkoutCardHandle;
import guitests.guihandles.WorkoutListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.workout.Workout;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(WorkoutCardHandle expectedCard, WorkoutCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getType(), actualCard.getType());
        assertEquals(expectedCard.getDuration(), actualCard.getDuration());
        assertEquals(expectedCard.getDifficulty(), actualCard.getDifficulty());
        assertEquals(expectedCard.getEquipment(), actualCard.getEquipment());
        assertEquals(expectedCard.getMuscle(), actualCard.getMuscle());
        assertEquals(expectedCard.getCalories(), actualCard.getCalories());
        assertEquals(expectedCard.getInstruction(), actualCard.getInstruction());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedWorkout}.
     */
    public static void assertCardDisplaysWorkout(Workout expectedWorkout, WorkoutCardHandle actualCard) {
        assertEquals(expectedWorkout.getName(), actualCard.getName());
        assertEquals(expectedWorkout.getType(), actualCard.getType());
        assertEquals(expectedWorkout.getDuration(), actualCard.getDuration());
        assertEquals(expectedWorkout.getDifficulty(), actualCard.getDifficulty());
        assertEquals(expectedWorkout.getEquipment(), actualCard.getEquipment());
        assertEquals(expectedWorkout.getMuscle(), actualCard.getMuscle());
        assertEquals(expectedWorkout.getCalories(), actualCard.getCalories());
        assertEquals(expectedWorkout.getInstruction(), actualCard.getInstruction());
        assertEquals(expectedWorkout.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }


    /**
     * Asserts that the list in {@code workoutListPanelHandle} displays the details of {@code workouts} correctly and
     * in the correct order.
     */

    public static void assertListMatching(WorkoutListPanelHandle workoutListPanelHandle, Workout... workouts) {
        for (int i = 0; i < workouts.length; i++) {
            workoutListPanelHandle.navigateToCard(i);
            assertCardDisplaysWorkout(workouts[i], workoutListPanelHandle.getWorkoutCardHandle(i));
        }
    }


    /**
     * Asserts that the list in {@code workoutListPanelHandle} displays the details of {@code workouts} correctly and
     * in the correct order.
     */

    public static void assertListMatching(WorkoutListPanelHandle workoutListPanelHandle, List<Workout> workouts) {
        assertListMatching(workoutListPanelHandle, workouts.toArray(new Workout[0]));
    }

    /**
     * Asserts the size of the list in {@code workoutListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(WorkoutListPanelHandle workoutListPanelHandle, int size) {
        int numberOfWorkout = workoutListPanelHandle.getListSize();
        assertEquals(size, numberOfWorkout);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
