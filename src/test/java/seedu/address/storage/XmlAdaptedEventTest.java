package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalEvents.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Address;
import seedu.address.model.event.Contact;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Email;
import seedu.address.model.event.Name;
import seedu.address.model.event.Phone;
import seedu.address.testutil.Assert;

public class XmlAdaptedEventTest {
    private static final String INVALID_NAME = "D@NC^N& ^N TH3 D@RK";
    private static final String INVALID_CONTACT = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_DATETIME = "31-2-2018 10:10";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_CONTACT = BENSON.getContact().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_DATETIME = BENSON.getDateTime().toString();

    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_valideventDetails_returnsevent() throws Exception {
        XmlAdaptedEvent event = new XmlAdaptedEvent(BENSON);
        assertEquals(BENSON, event.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(INVALID_NAME, VALID_CONTACT, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_DATETIME, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(null, VALID_CONTACT, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DATETIME, VALID_TAGS);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidContact_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, INVALID_CONTACT, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_DATETIME, VALID_TAGS);
        String expectedMessage = Contact.MESSAGE_CONTACT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullContact_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DATETIME, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Contact.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_DATETIME, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_DATETIME, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_DATETIME, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, VALID_PHONE, null,
                VALID_ADDRESS, VALID_DATETIME, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_DATETIME, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, VALID_PHONE, VALID_EMAIL,
                null, VALID_DATETIME, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidDateTime_throwIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        INVALID_DATETIME, VALID_TAGS);
        String expectedMessage = DateTime.MESSAGE_DATETIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullDateTime_throwIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null,
                        VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_NAME, VALID_CONTACT, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATETIME,
                        invalidTags);
        Assert.assertThrows(IllegalValueException.class, event::toModelType);
    }
}
