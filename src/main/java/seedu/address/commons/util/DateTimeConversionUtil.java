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
     * Get the {@code Date} of the previous {@code DayOfWeek} from the given {@code LocalTime}.
     * ie: if dayOfWeek is monday, then the return last monday's Date.
     */
    public Date getPreviousDateOfDay(LocalTime startTime, DayOfWeek dayOfWeek) {
        LocalDate previousDay =
                LocalDate.now(ZoneId.of(DEFAULT_ZONE_ID))
                        .with(TemporalAdjusters.previous(dayOfWeek));

        LocalDateTime localDateTime = startTime.atDate(previousDay);
        return DateTimeConversionUtil.getInstance().localDateTimeToDate(localDateTime);
    }

}
