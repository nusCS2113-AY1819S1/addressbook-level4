package seedu.address.model.util;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateTimeUtilTest {
    private static final String INCORRECT_DATE = "01-01-1899";
    private static final String INVALID_DATE = "01/30/1970";

    private static final String INCORRECT_DATE_TIME = "01-01-1899 00:00";
    private static final String INVALID_DATE_TIME = "01-01-1970: 80:20";


    @Test
    public void parse_nullLocalDate_returnNull() {
        assertEquals(DateTimeUtil.format((LocalDate) null), null);
    }

    @Test
    public void parse_nullLocalDateTime_returnNull() {
        assertEquals(DateTimeUtil.format((LocalDateTime) null),
                null);
    }

    @Test
    public void parse_incorrectDate_throwsDateTimeParseException() {
        Assert.assertThrows(DateTimeParseException.class, () -> DateTimeUtil.isCorrectDate(INCORRECT_DATE));
    }

    @Test
    public void parse_incorrectDateTime_throwsDateTimeParseException() {
        Assert.assertThrows(DateTimeParseException.class, () -> DateTimeUtil.isCorrectDateTime(INCORRECT_DATE_TIME));
    }

    @Test
    public void parse_invalidDate_throwsDateTimeParseException() {
        Assert.assertThrows(DateTimeParseException.class, () -> DateTimeUtil.isCorrectDate(INVALID_DATE));
    }

    @Test
    public void parse_invalidDateTime_throwsDateTimeParseException() {
        Assert.assertThrows(DateTimeParseException.class, () -> DateTimeUtil.isCorrectDateTime(INVALID_DATE_TIME));
    }

}
