package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalEvents.EVENT_1;

import java.time.format.DateTimeParseException;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.StartTime;
import seedu.address.testutil.Assert;

public class XmlAdaptedEventTest {
    private static final String INVALID_EVENT_NAME = "CS@3$%?/>21";
    private static final String INVALID_DESCRIPTION = "LOL $$$$ ";
    private static final String INVALID_DATE = "2018-9-28";
    private static final String INVALID_STARTTIME = "25:00";
    private static final String INVALID_ENDTIME = "25:00";
    private static final String INVALID_LOCATION = "G!0 home sleep";


    private static final String VALID_EVENT_NAME = "CS3244";
    private static final String VALID_DESCRIPTION = "Can i sleep ";
    private static final String VALID_DATE = "2018-09-28";
    private static final String VALID_STARTTIME = "09:00";
    private static final String VALID_ENDTIME = "12:00";
    private static final String VALID_LOCATION = "Go home sleep";


    @Test
    public void toModelType_validEventDetails_returnsPerson() throws Exception {
        XmlAdaptedEvent event = new XmlAdaptedEvent(EVENT_1);
        assertEquals(EVENT_1, event.toModelType());
    }


    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(null, VALID_DESCRIPTION, VALID_DATE, VALID_STARTTIME, VALID_ENDTIME,
                VALID_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EventName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }


    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_EVENT_NAME, null, VALID_DATE, VALID_STARTTIME, VALID_ENDTIME,
                VALID_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_EVENT_NAME, VALID_DESCRIPTION, INVALID_DATE, VALID_STARTTIME, VALID_ENDTIME,
                        VALID_LOCATION);
        Assert.assertThrows(DateTimeParseException.class, event::toModelType);
    }


    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_EVENT_NAME, VALID_DESCRIPTION, null, VALID_STARTTIME, VALID_ENDTIME,
                        VALID_LOCATION);
        Assert.assertThrows(IllegalValueException.class, event::toModelType);
    }


    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_EVENT_NAME, VALID_DESCRIPTION, VALID_DATE, INVALID_STARTTIME, VALID_ENDTIME,
                        VALID_LOCATION);
        String expectedMessage = StartTime.MESSAGE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_EVENT_NAME, VALID_DESCRIPTION, VALID_DATE, null, VALID_ENDTIME,
                        VALID_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StartTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }



    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_EVENT_NAME, VALID_DESCRIPTION, VALID_DATE, VALID_STARTTIME, INVALID_ENDTIME,
                        VALID_LOCATION);
        String expectedMessage = EndTime.MESSAGE_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_EVENT_NAME, VALID_DESCRIPTION, VALID_DATE, VALID_STARTTIME, null,
                        VALID_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EndTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }


    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        XmlAdaptedEvent event =
                new XmlAdaptedEvent(VALID_EVENT_NAME, VALID_DESCRIPTION, VALID_DATE, VALID_STARTTIME, VALID_ENDTIME,
                        null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }


}
