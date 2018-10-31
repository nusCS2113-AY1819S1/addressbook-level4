package seedu.address.model.timeidentifiedclass;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;
import seedu.address.testutil.Assert;

public class ReminderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // null transaction time
        Assert.assertThrows(NullPointerException.class, () -> new Reminder(null, "Test"));
        // null transaction message
        Assert.assertThrows(NullPointerException.class, () -> new Reminder("time",
                null));
    }

    @Test
    public void isValidReminderTime_invalidTimes() {
        // null transaction time
        Assert.assertThrows(NullPointerException.class, () -> Reminder.isValidReminderTime(null));

        // empty transaction time
        assertFalse(Reminder.isValidReminderTime(""));
        // invalid time
        assertFalse(Reminder.isValidReminderTime("invalid time"));
        // no date
        assertFalse(Reminder.isValidReminderTime("12:00:00"));
        // no time
        assertFalse(Reminder.isValidReminderTime("2018/10/12"));
        // extra space (improper format)
        assertFalse(Reminder.isValidReminderTime("2018/10/12  12:00:00"));
        // invalid months
        assertFalse(Reminder.isValidReminderTime("2018/00/12 12:00:00"));
        assertFalse(Reminder.isValidReminderTime("2018/13/12 12:00:00"));
        // invalid days
        assertFalse(Reminder.isValidReminderTime("2018/01/00 12:00:00"));
        assertFalse(Reminder.isValidReminderTime("2018/01/32 12:00:00"));
        // invalid hour
        assertFalse(Reminder.isValidReminderTime("2018/01/20 24:00:00"));
        // invalid minute
        assertFalse(Reminder.isValidReminderTime("2018/01/20 23:60:00"));
        // invalid second
        assertFalse(Reminder.isValidReminderTime("2018/01/20 12:12:60"));

    }

    @Test
    public void isValidReminderTime_validTimes() {
        assertTrue(Reminder.isValidReminderTime("2018/12/31 23:59:59"));
        assertTrue(Reminder.isValidReminderTime("2018/12/31 00:00:00"));
    }

    @Test
    public void changeReminderTime_invalidTimes() {
        Reminder toTest = new Reminder();

        // null time
        Assert.assertThrows(NullPointerException.class, () -> toTest.changeTime(null));
        // invalid time
        Assert.assertThrows(InvalidTimeFormatException.class, () -> toTest.changeTime("invalid time"));
    }

    @Test
    public void changeReminderTime_validTime() {
        Reminder toTest = new Reminder();
        try {
            toTest.changeTime("2018/10/12 12:00:00");
        } catch (Exception e) {
            fail("Should be able to change reminder time with valid time");
        }
    }
}
