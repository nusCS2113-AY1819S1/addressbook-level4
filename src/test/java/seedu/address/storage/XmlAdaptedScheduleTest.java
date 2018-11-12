package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalSchedules.CLEANING;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.schedule.TimeEnd;
import seedu.address.model.schedule.TimeStart;
import seedu.address.model.schedule.Venue;
import seedu.address.testutil.Assert;

public class XmlAdaptedScheduleTest {
    private static final String INVALID_TIME_START = "R@chel";
    private static final String INVALID_TIME_END = "651234";
    private static final String INVALID_VENUE = " ";

    private static final String VALID_TIME_START = CLEANING.getTimeStart().toString();
    private static final String VALID_TIME_END = CLEANING.getTimeEnd().toString();
    private static final String VALID_VENUE = CLEANING.getVenue().toString();

    @Test
    public void toModelType_validScheduleDetails_returnsSchedule() throws Exception {
        XmlAdaptedSchedule schedule = new XmlAdaptedSchedule(CLEANING);
        assertEquals(CLEANING, schedule.toModelType());
    }

    @Test
    public void toModelType_invalidTimeStart_throwsIllegalValueException() {
        XmlAdaptedSchedule schedule = new XmlAdaptedSchedule(INVALID_TIME_START, VALID_TIME_END, VALID_VENUE);
        String expectedMessage = TimeStart.MESSAGE_TIME_START_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }

    @Test
    public void toModelType_invalidTimeEnd_throwsIllegalValueException() {
        XmlAdaptedSchedule schedule = new XmlAdaptedSchedule(VALID_TIME_START, INVALID_TIME_END, VALID_VENUE);
        String expectedMessage = TimeEnd.MESSAGE_TIME_END_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }

    @Test
    public void toModelType_invalidVenue_throwsIllegalValueException() {
        XmlAdaptedSchedule schedule = new XmlAdaptedSchedule(VALID_TIME_START, VALID_TIME_END, INVALID_VENUE);
        String expectedMessage = Venue.MESSAGE_VENUE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, schedule::toModelType);
    }
}
