package seedu.address.model.event;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateTime(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidDateTime = "31/2/2018 10:10";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DateTime(invalidDateTime));
    }

    @Test
    public void isValidDateTime() {
        //null date
        Assert.assertThrows(NullPointerException.class, () -> DateTime.isValidDateTime(null));

        //invalid date
        assertFalse(DateTime.isValidDateTime("31/2/2018 20:00"));
        assertFalse(DateTime.isValidDateTime("20/2/2018 25:00"));
        assertFalse(DateTime.isValidDateTime("31-2-2018 20|00"));

        //valid date
        assertTrue(DateTime.isValidDateTime("20/2/2018 10:10"));
        assertTrue(DateTime.isValidDateTime("1/1/2018 00:00"));
        assertTrue(DateTime.isValidDateTime("20/02/2018 10:10"));
        assertTrue(DateTime.isValidDateTime("31/12/2018 23:59"));
    }

    @Test
    public void equals() {

    }

}
