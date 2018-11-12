package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_CHRISTMAS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CHRISTMAS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_ROOM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_NIGHT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_NOON;

import org.junit.Test;

import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.testutil.EditEventDescriptorBuilder;

public class EditEventDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditEventDescriptor descriptorWithSameValues = new EditEventDescriptor(DESC_BIRTHDAY);
        assertTrue(DESC_BIRTHDAY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_BIRTHDAY.equals(DESC_BIRTHDAY));

        // null -> returns false
        assertFalse(DESC_BIRTHDAY.equals(null));

        // different types -> returns false
        assertFalse(DESC_BIRTHDAY.equals(5));

        // different values -> returns false
        assertFalse(DESC_BIRTHDAY.equals(DESC_MEETING));

        // different name -> returns false
        EditEventDescriptor editEvent = new EditEventDescriptorBuilder(DESC_BIRTHDAY)
                .withEventName(VALID_EVENT_NAME_MEETING).build();
        assertFalse(DESC_BIRTHDAY.equals(editEvent));

        // different location -> returns false
        editEvent = new EditEventDescriptorBuilder(DESC_BIRTHDAY).withLocation(VALID_LOCATION_ROOM).build();
        assertFalse(DESC_BIRTHDAY.equals(editEvent));

        // different description -> returns false
        editEvent = new EditEventDescriptorBuilder(DESC_BIRTHDAY).withDescription(VALID_DESCRIPTION_CHRISTMAS).build();
        assertFalse(DESC_BIRTHDAY.equals(editEvent));

        // different date -> returns false
        editEvent = new EditEventDescriptorBuilder(DESC_BIRTHDAY).withDate(VALID_DATE_CHRISTMAS).build();
        assertFalse(DESC_BIRTHDAY.equals(editEvent));

        // different start time -> returns false
        editEvent = new EditEventDescriptorBuilder(DESC_BIRTHDAY).withStartTime(VALID_TIME_NOON).build();
        assertFalse(DESC_BIRTHDAY.equals(editEvent));

        // different end time -> returns false
        editEvent = new EditEventDescriptorBuilder(DESC_BIRTHDAY).withEndTime(VALID_TIME_NIGHT).build();
        assertFalse(DESC_BIRTHDAY.equals(editEvent));
    }
}
