package seedu.address.model.timeidentifiedclass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Some of the classes, such as BusinessDay and Transaction, are identified using their time of creation/
 * record.
 */
public abstract class TimeIdentifiedClass {
    private static final int NUMBER_OF_MONTHS_IN_YEAR = 12;
    private static final int MAXIMUM_NUMBER_OF_DAYS_IN_MONTH = 31;
    private static final int NUMBER_OF_HOURS_IN_DAY = 24;
    private static final int NUMBER_OF_MINUTES_IN_HOUR = 60;
    private static final int NUMBER_OF_SECONDS_IN_HOUR = 60;

    private static DateTimeFormatter dateAndTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static LocalDateTime time;

    /**
     * Gives the date and time in the format specified by {@code dateAndTimeFormatter}
     */
    public static String getCurrentDateAndTime() {
        String currentDateAndTime = dateAndTimeFormatter.format(time.now());
        return currentDateAndTime;
    }

    /**
     * Gives the current date in the format specified by {@code dayFormatter}
     */
    public static String getCurrentDate() {
        String currentDate = dayFormatter.format(time.now());
        return currentDate;
    }

    /**
     * Checks if a given month is valid
     * @param month
     * @return true if the month is valid
     */
    public static boolean isValidMonth(String month) {
        if (month.matches("\\d{2}")) {
            int monthValue = Integer.parseInt(month);
            if (monthValue <= NUMBER_OF_MONTHS_IN_YEAR && monthValue > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a given day is valid
     * @param day
     * @return true if the day is valid
     */
    public static boolean isValidDay(String day) {
        if (day.matches("\\d{2}")) {
            int dayValue = Integer.parseInt(day);
            if (dayValue <= MAXIMUM_NUMBER_OF_DAYS_IN_MONTH && dayValue > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a year is valid
     * @param year
     * @return true if the year is valid.
     */
    public static boolean isValidYear(String year) {
        if (year.matches("\\d{4}")) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the given hour is valid and follows the 24 hour format.
     * @param hour
     * @return true if the hour is valid
     */
    public static boolean isValidHour(String hour) {
        if (hour.matches("\\d{2}")) {
            int hourValue = Integer.parseInt(hour);
            if (hourValue < NUMBER_OF_HOURS_IN_DAY && hourValue >= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks if a given minute is valid
     * @param minute
     * @return true if the minute is valid
     */
    public static boolean isValidMinute(String minute) {
        if (minute.matches("\\d{2}")) {
            int minuteValue = Integer.parseInt(minute);
            if (minuteValue < NUMBER_OF_MINUTES_IN_HOUR && minuteValue >= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks if the given second is valid
     * @param second
     * @return true if the second is valid.
     */
    public static boolean isValidSecond(String second) {
        if (second.matches("\\d{2}")) {
            int secondValue = Integer.parseInt(second);
            if (secondValue < NUMBER_OF_SECONDS_IN_HOUR && secondValue >= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks if a given string is a valid date and time in the form dd/MM/yyyy HH:mm:ss
     * @param dateAndTime
     * @return true if the string is in valid day and time format
     */
    public static boolean isValidDateAndTime (String dateAndTime) {
        String[] times = dateAndTime.split("[/ \\s+ :]");

        for (int i = 0; i < 6; i++) {
            times[i].trim();
        }

        // checks on the different components of the transaction time.
        if (isValidYear(times[0])
                && isValidMonth(times[1])
                && isValidDay(times[2])
                && isValidHour(times[3])
                && isValidMinute(times[4])
                && isValidSecond(times[5])) {
            return true;
        }
        return false;
    }

    /**
     * The following method checks whether a string is in the correct date format of dd/MM/yyyy
     * @param date
     * @return true if valid format, false otherwise.
     */
    public static boolean isValidDate(String date) {
        String[] splitDate = date.split("/");

        // checking the individual components of the date
        if (isValidYear(splitDate[0])
                && isValidMonth(splitDate[1])
                && isValidDay(splitDate[2])) {
            return true;
        }
        return false;
    }
}
