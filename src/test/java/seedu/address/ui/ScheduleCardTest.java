package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysSchedule;

import org.junit.Test;

import guitests.guihandles.ScheduleCardHandle;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.schedule.ScheduleBuilder;

public class ScheduleCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Schedule schedule = new ScheduleBuilder().build();
        ScheduleCard scheduleCard = new ScheduleCard(schedule, 1);
        uiPartRule.setUiPart(scheduleCard);
        assertCardDisplay(scheduleCard, schedule, 1);

    }

    @Test
    public void equals() {
        Schedule schedule = new ScheduleBuilder().build();
        ScheduleCard scheduleCard = new ScheduleCard(schedule, 0);

        // same schedule, same index -> returns true
        ScheduleCard copy = new ScheduleCard(schedule, 0);
        assertTrue(scheduleCard.equals(copy));

        // same object -> returns true
        assertTrue(scheduleCard.equals(scheduleCard));

        // null -> returns false
        assertFalse(scheduleCard.equals(null));

        // different types -> returns false
        assertFalse(scheduleCard.equals(0));

        // different employee id, same index -> returns false
        Schedule differentSchedule = new ScheduleBuilder().withEmployeeId("000007").build();
        assertFalse(scheduleCard.equals(new ScheduleCard(differentSchedule, 0)));

        // same person, different index -> returns false
        assertFalse(schedule.equals(new ScheduleCard(schedule, 1)));
    }

    /**
     * Asserts that {@code scheduleCard} displays the details of {@code expectedSchedule} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ScheduleCard scheduleCard, Schedule expectedSchedule, int expectedId) {
        guiRobot.pauseForHuman();

        ScheduleCardHandle scheduleCardHandle = new ScheduleCardHandle(scheduleCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", scheduleCardHandle.getId());

        // verify schedule details are displayed correctly
        assertCardDisplaysSchedule(expectedSchedule, scheduleCardHandle);
    }

}
