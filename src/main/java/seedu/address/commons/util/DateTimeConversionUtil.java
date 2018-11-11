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
 */
public class DateTimeConversionUtil {
    private static DateTimeConversionUtil instance;
    private static final String DEFAULT_ZONE_ID = "Asia/Shanghai";

    private DateTimeConversionUtil() {

    }

    public static DateTimeConversionUtil getInstance() {
        if (instance == null) {
            instance = new DateTimeConversionUtil();
        }
        return instance;
    }

    /**
     * Utility function to convert {@code LocalDateTime} to {@code Date}
     */
    public Date localDateTimeToDate(LocalDateTime localDateTime) throws IllegalArgumentException {
        requireNonNull(localDateTime);
        Date out;
        try {
            out = Date.from(localDateTime.atZone(ZoneId.of(DEFAULT_ZONE_ID)).toInstant());
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
        localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
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
    public Date getNextDateOfDay(LocalTime time, DayOfWeek dayOfWeek) {
        LocalDate nextDay =
                LocalDate.now(ZoneId.of(DEFAULT_ZONE_ID))
                        .with(TemporalAdjusters.nextOrSame(dayOfWeek));

        LocalDateTime localDateTime = time.atDate(nextDay);
        return localDateTimeToDate(localDateTime);
    }

    /**
     * Gets the the next {@code LocalDateTime} of the {@code DayOfWeek} requested, with reference to now.
     *
     * ie: if {@code DayOfWeek} requested is Monday, then the LocalDateTime returned is the next monday from now.
     * if today is monday, then return is today's date.
     */
    public LocalDateTime getNextLocalDateTime(LocalTime time, DayOfWeek dayOfWeek) {
        LocalDate nextDay =
                LocalDate.now(ZoneId.of(DEFAULT_ZONE_ID))
                        .with(TemporalAdjusters.nextOrSame(dayOfWeek));

        LocalDateTime localDateTime = time.atDate(nextDay);
        return localDateTime;
    }

}
