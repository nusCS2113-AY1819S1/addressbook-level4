package seedu.address.model.addressbook;

import java.util.Calendar;

/**
 * DayHourGreeting is used to get the greeting message based on current time.
 * E.g Good Morning, afternoon, evening, night
 */
public class DayHourGreeting {

    public static final String AFTERNOON = "Good Morning";
    public static final String EVENING = "Good Evening";
    public static final String MORNING = "Good Morning";
    public static final String NIGHT = "Good Night";

    private static String dayHourGreeting;

    /**
     * Time of the day based on Operating system clock, sets the greeting message based on current time.
     */
    public DayHourGreeting () {
        Calendar c = Calendar.getInstance();
        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        setGreeting(hourOfDay);
    }

    /**
     * Sets greeting based on hour of the day.
     */
    public static void setGreeting(int hourOfDay) {
        if (hourOfDay >= 0 && hourOfDay < 12) {
            dayHourGreeting = MORNING;
        } else if (hourOfDay >= 12 && hourOfDay < 16) {
            dayHourGreeting = AFTERNOON;
        } else if (hourOfDay >= 16 && hourOfDay < 21) {
            dayHourGreeting = EVENING;
        } else if (hourOfDay >= 21 && hourOfDay < 24) {
            dayHourGreeting = NIGHT;
        }
    }


    /**
     * Returns greeting to user.
     */
    public static String getGreeting() {
        return dayHourGreeting;
    }
}
