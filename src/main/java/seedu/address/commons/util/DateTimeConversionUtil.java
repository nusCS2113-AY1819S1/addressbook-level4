package seedu.address.commons.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Class containing utility functions to:
 * 1) convert from {@code LocalDateTime} to {@code Date} (and vice versa)
 * 2) convert from ics-formatted-date-String to {@code DayOfWeek}.
 * 3) convert from ics-formatted-time-String to a {@code LocalTime}.
 */
public class DateTimeConversionUtil {
    private static DateTimeConversionUtil instance;

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
    Date localDateTimeToDate(LocalDateTime localDateTime) {
        Date out = Date.from(localDateTime.atZone(ZoneId.of(IcsUtil.DEFAULT_ZONE_ID)).toInstant());
        return out;
    }

    /**
     *  Utility function to convert {@code Date} to {@code LocalDateTime}
     */
    LocalDateTime dateToLocalDateTime(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return localDateTime;
    }
    
    /**
     * Converts the ics-formatted dateString into DayOfWeek object.
     * @param dateString    is in format yyyyMMdd (as in .ics)
     */
    public DayOfWeek dateStringToDayOfWeek(String dateString) {
        //TODO: defensive coding
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate date = LocalDate.parse(dateString, fmt);
        DayOfWeek day = date.getDayOfWeek();

        return day;
    }

    /**
     * Converts the ics-formatted timeString into a LocalTime object.
     * @param timeString    is in format HHmmss (as in .ics)
     */
    public LocalTime timeStringToLocalTime(String timeString) {
        //TODO: defensive coding
        int timeInt = Integer.parseInt(timeString);
        String formattedTime = String.format("%06d", timeInt);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HHmmss");
        LocalTime time = LocalTime.parse(formattedTime, fmt);

        return time;
    }
}
