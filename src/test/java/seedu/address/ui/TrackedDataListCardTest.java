package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysParameter;

import org.junit.Test;

import guitests.guihandles.TrackedDataListCardHandle;
import seedu.address.model.workout.Parameter;
import seedu.address.testutil.ParameterBuilder;

public class TrackedDataListCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Parameter parameter = new ParameterBuilder().build();
        TrackedDataListCard trackedDataListCard = new TrackedDataListCard(parameter, 1);
        uiPartRule.setUiPart(trackedDataListCard);
        assertCardDisplay(trackedDataListCard, parameter, 1);
    }

    @Test
    public void equals() {
        Parameter parameter = new ParameterBuilder().build();
        TrackedDataListCard trackedDataListCard = new TrackedDataListCard(parameter, 0);

        // same parameter, same index -> returns true
        TrackedDataListCard copy = new TrackedDataListCard(parameter, 0);
        assertTrue(trackedDataListCard.equals(copy));

        // same object -> returns true
        assertTrue(trackedDataListCard.equals(trackedDataListCard));

        // null -> returns false
        assertFalse(trackedDataListCard.equals(null));

        // different types -> returns false
        assertFalse(trackedDataListCard.equals(0));

        // different parameter, same index -> returns false
        Parameter differentParameter = new ParameterBuilder().withValue("test").build();
        assertFalse(trackedDataListCard.equals(new TrackedDataListCard(differentParameter, 0)));

        // same parameter, different index -> returns false
        assertFalse(trackedDataListCard.equals(new TrackedDataListCard(parameter, 1)));
    }

    /**
     * Asserts that {@code trackedDataListCard} displays the details of {@code expectedParameter} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(TrackedDataListCard trackedDataListCard, Parameter expectedParameter,
                                   int expectedId) {
        guiRobot.pauseForHuman();

        TrackedDataListCardHandle trackedDataListCardHandle = new TrackedDataListCardHandle(trackedDataListCard
                .getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", trackedDataListCardHandle.getId());

        // verify parameter details are displayed correctly
        assertCardDisplaysParameter(expectedParameter, trackedDataListCardHandle);
    }
}
