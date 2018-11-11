package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * Class containing utility functions to:
 * 1) convert from {@code LocalDateTime} to {@code Date} (and vice versa)
 * 2) convert from ics-formatted-date-String to {@code DayOfWeek}.
 * 3) convert from ics-formatted-time-String to a {@code LocalTime}.
 *
 * Currently timezone for all conversions is assumed to be at +8GMT (Singapore) for simple implementation;
 */
public class DateTimeConversionUtil {
    private static DateTimeConversionUtil instance;
    private static ZoneId localZoneId;

    private DateTimeConversionUtil() {
        localZoneId = ZoneId.of("Asia/Shanghai");
    }

    public static DateTimeConversionUtil getInstance() {
        if (instance == null) {
            instance = new DateTimeConversionUtil();
        }
        return instance;
    }

    //should only be used during testing.
    //TODO: make this non-accessible outside of JUnit tests.
    public void setLocalZoneId(ZoneId setTo) {
        localZoneId = setTo;
    }

    /**
     * Utility function to convert {@code LocalDateTime} to {@code Date}
     * Date will be in the timezone of what the system reports.
     */
    public Date localDateTimeToDate(LocalDateTime localDateTime) throws IllegalArgumentException {
        requireNonNull(localDateTime);
        Date out;
        try {
            //using '.systemDefault()' allows us to return the Date that has the local-timezone.
            out = Date.from(localDateTime.atZone(localZoneId).toInstant());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException (e);
        }
        return out;
    }

    /**
     *  Utility function to convert {@code Date} to {@code LocalDateTime}
     */
    public LocalDateTime dateToLocalDateTime(Date date) throws DateTimeException {
        requireNonNull(date);
        LocalDateTime localDateTime;
        localDateTime = LocalDateTime.ofInstant(date.toInstant(), localZoneId);
        return localDateTime;
    }

    /**
     * Converts the ics-formatted dateString into DayOfWeek object.
     * @param dateString    is in format yyyyMMdd (as in .ics)
     */
    public DayOfWeek dateStringToDayOfWeek(String dateString) throws DateTimeParseException {
        requireNonNull(dateString);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate date = LocalDate.parse(dateString, fmt);
        DayOfWeek day = date.getDayOfWeek();

        return day;
    }

    /**
     * Converts the ics-formatted timeString into a LocalTime object.
     * @param timeString    is in format HHmmss (as in .ics)
     */
    public LocalTime timeStringToLocalTime(String timeString) throws DateTimeParseException, IllegalArgumentException {
        requireNonNull(timeString);
        //TODO: defensive coding
        int timeInt = Integer.parseInt(timeString);
        String formattedTime = String.format("%06d", timeInt);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HHmmss");
        LocalTime time = LocalTime.parse(formattedTime, fmt);

        return time;
    }

    /**
     * @return the next {@code Date} of the {@code DayOfWeek} requested
     *
     * ie: if {@code DayOfWeek} requested is monday, then the return the Date of the next monday from now.
     * if today is monday, then return today's date.
     */
    public Date getNextDateOfDay(LocalTime localTime, DayOfWeek dayOfWeek) {
        LocalDate nextDay =
                LocalDate.now(localZoneId)
                        .with(TemporalAdjusters.nextOrSame(dayOfWeek));

        LocalDateTime localDateTime = localTime.atDate(nextDay);
        return getInstance().localDateTimeToDate(localDateTime);
    }

}
