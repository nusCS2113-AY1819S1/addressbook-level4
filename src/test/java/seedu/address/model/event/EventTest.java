package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDEE_HAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_BOB;
import static seedu.address.testutil.TypicalEvents.ALICE;
import static seedu.address.testutil.TypicalEvents.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.EventBuilder;

public class EventTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Event event = new EventBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        event.getTags().remove(0);
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSameEvent(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameEvent(null));

        // different contact, phone, email amd datetime -> returns false
        Event editedAlice = new EventBuilder(ALICE).withContact(VALID_CONTACT_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameEvent(editedAlice));

        // different name -> returns false
        editedAlice = new EventBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameEvent(editedAlice));

        // same name, same contact, different attributes -> returns true
        editedAlice = new EventBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withVenue(VALID_VENUE_BOB).withDateTime(VALID_DATETIME_BOB).withTags(VALID_TAG_HUSBAND)
                .withAttendees(VALID_ATTENDEE_HAN).build();
        assertTrue(ALICE.isSameEvent(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new EventBuilder(ALICE).withContact(VALID_CONTACT_BOB).withEmail(VALID_EMAIL_BOB)
                .withVenue(VALID_VENUE_BOB).withDateTime(VALID_DATETIME_BOB).withTags(VALID_TAG_HUSBAND)
                .withAttendees(VALID_ATTENDEE_HAN).build();
        assertTrue(ALICE.isSameEvent(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new EventBuilder(ALICE).withContact(VALID_CONTACT_BOB).withPhone(VALID_PHONE_BOB)
                .withVenue(VALID_VENUE_BOB).withDateTime(VALID_DATETIME_BOB).withTags(VALID_TAG_HUSBAND)
                .withAttendees(VALID_ATTENDEE_HAN).build();
        assertTrue(ALICE.isSameEvent(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new EventBuilder(ALICE).withVenue(VALID_VENUE_BOB).withDateTime(VALID_DATETIME_BOB)
                .withTags(VALID_TAG_HUSBAND).withAttendees(VALID_ATTENDEE_HAN).build();
        assertTrue(ALICE.isSameEvent(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Event aliceCopy = new EventBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different event -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Event editedAlice = new EventBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different contact -> returns false
        editedAlice = new EventBuilder(ALICE).withContact(VALID_CONTACT_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new EventBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new EventBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different venue -> returns false
        editedAlice = new EventBuilder(ALICE).withVenue(VALID_VENUE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different datetime -> returns false
        editedAlice = new EventBuilder(ALICE).withDateTime(VALID_DATETIME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new EventBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));

        // different attendees -> returns false
        editedAlice = new EventBuilder(ALICE).withAttendees(VALID_ATTENDEE_HAN).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
