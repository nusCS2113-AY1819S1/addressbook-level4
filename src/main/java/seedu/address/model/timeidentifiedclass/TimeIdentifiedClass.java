package seedu.address.model.timeidentifiedclass;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This is a template class for classes which are identified using time.
 * Some of the classes, such as {@link Reminder} and {@link Transaction}, are identified using time.
 */
public abstract class TimeIdentifiedClass {
    private static final int NUMBER_OF_MONTHS_IN_YEAR = 12;
    private static final int MAXIMUM_NUMBER_OF_DAYS_IN_MONTH = 31;
    private static final int NUMBER_OF_HOURS_IN_DAY = 24;
    private static final int NUMBER_OF_MINUTES_IN_HOUR = 60;
    private static final int NUMBER_OF_SECONDS_IN_HOUR = 60;
    private static final int FEBRUARY_MONTH_NUMBER = 2;
    private static final int[] DAYS_IN_MONTH_NON_LEAP_YEAR = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int FEBRUARY_LEAP_YEAR_DAYS = 29;

    private static DateTimeFormatter dateAndTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
     * @param dateAndTime
     * @return true if the string is in valid day and time format
     */
    public static boolean isValidDateAndTime(String dateAndTime) {
        String[] splitDateAndTime = dateAndTime.split("[/ \\s+ :]");
        if (splitDateAndTime.length != 6) {
            return false;
        }
        for (int i = 0; i < splitDateAndTime.length; i++) {
            splitDateAndTime[i].trim();
        }
        // checks on the different components of the given date and time.
        if (isValidDate(splitDateAndTime[0], splitDateAndTime[1], splitDateAndTime[2])
                && isValidTime(splitDateAndTime[3], splitDateAndTime[4], splitDateAndTime[5])) {
            return true;
        }
        return false;
    }

    /**
     * The following method checks if a given year is a leap year
     * @param year
     * @return true if and only if the year is in the correct format and is a leap year
     */
    private static boolean isLeapYear(String year) {
        if (!isValidYear(year)) {
            return false;
        }
        int yearNumber = Integer.parseInt(year);
        if (yearNumber % 4 == 0) {
            return true;
        }
        return false;
    }

    /**
     * The following method checks if the given time is valid.
     * @param hour
     * @param minute
     * @param second
     * @return true if and only if time is valid
     */
    private static boolean isValidTime(String hour, String minute, String second) {
        if (isValidHour(hour)
                && isValidMinute(minute)
                && isValidSecond(second)) {
            return true;
        }
        return false;
    }

    /**
     * The following method checks whether a string is in the correct date format of dd/MM/yyyy
     * @param year
     * @param month
     * @param day
     * @return true if valid format, false otherwise.
     */
    private static boolean isValidDate(String year, String month, String day) {
        requireAllNonNull(year, month, day);

        // Checking the individual components of the date
        if (!isValidYear(year)
                || !isValidMonth(month)
                || !isValidDay(day)) {
            return false;
        }

        int monthNumber = Integer.parseInt(month);
        int dayNumber = Integer.parseInt(day);

        // Checking for feb 29th case on a leap year.
        if (isLeapYear(year)
                && monthNumber == FEBRUARY_MONTH_NUMBER
                && dayNumber <= FEBRUARY_LEAP_YEAR_DAYS) {
            return true;
        }

        // For a non leap year.
        if (dayNumber > DAYS_IN_MONTH_NON_LEAP_YEAR[monthNumber - 1]) {
            return false;
        }
        return true;
    }

    /**
     * The following method splits a given date and checks if it is valid.
     * @param date
     * @return true if and only if the date is valid.
     */
    public static boolean isValidDate(String date) {
        String[] splitDate = date.split("/");
        if (splitDate.length != 3) {
            return false;
        }
        return isValidDate(splitDate[0], splitDate[1], splitDate[2]);
    }
}
