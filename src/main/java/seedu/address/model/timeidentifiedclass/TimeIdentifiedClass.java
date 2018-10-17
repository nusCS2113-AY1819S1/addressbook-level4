package seedu.address.model.timeidentifiedclass;

/**
 * Some of the classes, such as ShopDay and Transaction, are identified using their time of creation/
 * record.
 */
public abstract class TimeIdentifiedClass {
    private static final int NUMBER_OF_MONTHS_IN_YEAR = 12;
    private static final int MAXIMUM_NUMBER_OF_DAYS_IN_MONTH = 31;
    private static final int NUMBER_OF_HOURS_IN_DAY = 24;
    private static final int NUMBER_OF_MINUTES_IN_HOUR = 60;
    private static final int NUMBER_OF_SECONDS_IN_HOUR = 60;

    /**
     *
     * @param month
     * @return
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
     *
     * @param day
     * @return
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
     *
     * @param year
     * @return
     */
    public static boolean isValidYear(String year) {
        if (year.matches("\\d{4}")) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param hour
     * @return
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
     *
     * @param minute
     * @return
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
     *
     * @param second
     * @return
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
}
