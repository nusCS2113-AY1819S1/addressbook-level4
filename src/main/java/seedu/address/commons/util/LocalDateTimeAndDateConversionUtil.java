package seedu.address.commons.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Class containing utility functions to convert from {@code LocalDateTime} to {@code Date} (and vice versa)
 *
 */
public class LocalDateTimeAndDateConversionUtil {
    private static LocalDateTimeAndDateConversionUtil instance;

    private LocalDateTimeAndDateConversionUtil() {

    }

    public static LocalDateTimeAndDateConversionUtil getInstance() {
        if (instance == null) {
            instance = new LocalDateTimeAndDateConversionUtil();
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
}
