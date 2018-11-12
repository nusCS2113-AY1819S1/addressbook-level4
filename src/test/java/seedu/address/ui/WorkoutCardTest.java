package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysWorkout;

import org.junit.Test;

import guitests.guihandles.WorkoutCardHandle;
import seedu.address.model.workout.Workout;
import seedu.address.testutil.WorkoutBuilder;

public class WorkoutCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Workout workoutWithNoTags = new WorkoutBuilder().withTags().build();
        WorkoutCard workoutCard = new WorkoutCard(workoutWithNoTags, 1);
        uiPartRule.setUiPart(workoutCard);
        //assertCardDisplay(workoutCard, workoutWithNoTags, 1);

        // with tags
        Workout workoutWithTags = new WorkoutBuilder().build();
        workoutCard = new WorkoutCard(workoutWithTags, 2);
        uiPartRule.setUiPart(workoutCard);
        //assertCardDisplay(workoutCard, workoutWithTags, 2);
    }

    @Test
    public void equals() {
        Workout workout = new WorkoutBuilder().build();
        WorkoutCard workoutCard = new WorkoutCard(workout, 0);

        // same workout, same index -> returns true
        WorkoutCard copy = new WorkoutCard(workout, 0);
        assertTrue(workoutCard.equals(copy));

        // same object -> returns true
        assertTrue(workoutCard.equals(workoutCard));

        // null -> returns false
        assertFalse(workoutCard.equals(null));

        // different types -> returns false
        assertFalse(workoutCard.equals(0));

        // different workout, same index -> returns false
        Workout differentWorkout = new WorkoutBuilder().withName("differentName").build();
        assertFalse(workoutCard.equals(new WorkoutCard(differentWorkout, 0)));

        // same workout, different index -> returns false
        assertFalse(workoutCard.equals(new WorkoutCard(workout, 1)));
    }

    /**
     * Asserts that {@code workoutCard} displays the details of {@code expectedWorkout} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(WorkoutCard workoutCard, Workout expectedWorkout, int expectedId) {
        guiRobot.pauseForHuman();

        WorkoutCardHandle workoutCardHandle = new WorkoutCardHandle(workoutCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", workoutCardHandle.getId());

        // verify workout details are displayed correctly
        assertCardDisplaysWorkout(expectedWorkout, workoutCardHandle);
    }
}
