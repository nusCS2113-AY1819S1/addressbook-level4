package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Contact;
import seedu.address.model.event.Email;
import seedu.address.model.event.Name;
import seedu.address.model.event.Phone;
import seedu.address.model.event.Venue;
import seedu.address.testutil.Assert;

public class XmlAdaptedEventTest {
    private static final String INVALID_ATTENDEE = "AL@K@Z@M";
    private static final String INVALID_NAME = "D@NC^N& ^N TH3 D@RK";
    private static final String INVALID_CONTACT = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_VENUE = " ";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_CONTACT = BENSON.getContact().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_VENUE = BENSON.getVenue().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<XmlAdaptedAttendee> VALID_ATTENDEES = BENSON.getAttendees().stream()
            .map(XmlAdaptedAttendee::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedEvent person = new XmlAdaptedEvent(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedEvent person = new XmlAdaptedEvent(INVALID_NAME, VALID_CONTACT, VALID_PHONE, VALID_EMAIL,
                VALID_VENUE, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedEvent person = new XmlAdaptedEvent(null, VALID_CONTACT, VALID_PHONE, VALID_EMAIL,
                VALID_VENUE, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidContact_throwsIllegalValueException() {
        XmlAdaptedEvent person = new XmlAdaptedEvent(VALID_NAME, INVALID_CONTACT, VALID_PHONE, VALID_EMAIL,
                VALID_VENUE, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = Contact.MESSAGE_CONTACT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullContact_throwsIllegalValueException() {
        XmlAdaptedEvent person = new XmlAdaptedEvent(VALID_NAME, null, VALID_PHONE, VALID_EMAIL,
                VALID_VENUE, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Contact.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedEvent person = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, INVALID_PHONE, VALID_EMAIL,
                VALID_VENUE, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedEvent person = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, null, VALID_EMAIL,
                VALID_VENUE, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedEvent person = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, VALID_PHONE, INVALID_EMAIL,
                VALID_VENUE, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedEvent person = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, VALID_PHONE, null,
                VALID_VENUE, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidVenue_throwsIllegalValueException() {
        XmlAdaptedEvent person = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, VALID_PHONE, VALID_EMAIL,
                INVALID_VENUE, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = Venue.MESSAGE_VENUE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullVenue_throwsIllegalValueException() {
        XmlAdaptedEvent person = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, VALID_PHONE, VALID_EMAIL,
                null, VALID_TAGS, VALID_ATTENDEES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Venue.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedEvent person = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, VALID_PHONE, VALID_EMAIL,
                VALID_VENUE, invalidTags, VALID_ATTENDEES);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

}
