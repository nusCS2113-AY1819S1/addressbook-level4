package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.event.Event;
import seedu.address.testutil.PersonBuilder;

public class EventCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Event eventWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(eventWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, eventWithNoTags, 1);

        // with tags
        Event eventWithTags = new PersonBuilder().build();
        personCard = new PersonCard(eventWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, eventWithTags, 2);
    }

    @Test
    public void equals() {
        Event event = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(event, 0);

        // same event, same index -> returns true
        PersonCard copy = new PersonCard(event, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different event, same index -> returns false
        Event differentEvent = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentEvent, 0)));

        // same event, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(event, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedEvent} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, Event expectedEvent, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify event details are displayed correctly
        assertCardDisplaysPerson(expectedEvent, personCardHandle);
    }
}
