package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;

/**
 * Class containing utility functions to deal with date, time, datetime, timewhatever stuffs
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
     * Gets the the next {@code LocalDateTime} of the {@code DayOfWeek} requested, with reference to now.
     *
     * ie: if {@code DayOfWeek} requested is Monday, then the LocalDateTime returned is the next monday from now.
     * if today is monday, then return is today's date.
     */
    public LocalDateTime getNextLocalDateTime(LocalTime time, DayOfWeek dayOfWeek) {
        requireNonNull(time);
        requireNonNull(dayOfWeek);
        LocalDate nextDay =
                LocalDate.now(ZoneId.of(DEFAULT_ZONE_ID))
                        .with(TemporalAdjusters.nextOrSame(dayOfWeek));

        LocalDateTime localDateTime = time.atDate(nextDay);
        return localDateTime;
    }

}
