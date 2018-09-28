package seedu.address.model.person;

/**
 * Represents a single hour in TimeTable Class
 *
 */
public class TimeSlot {

    public static final String MESSAGE_TIMESLOT_CONSTRAINTS =
            "TIMESLOT HAVE TO BE IN THIS FORMAT ...";
    private static final String TIMESLOT_VALIDATION_REGEX = "[0-6][:]\\d{4,}";


    private boolean isFilled;
    private String modName;

    //For the parser to label which timeslot it is
    //0-23 hour of the day, 0-6 day of the week
    private int hour;
    private int day;


    public TimeSlot(int hour, int day) {
        this.isFilled = false;
        this.modName = "";
        this.hour = hour;
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public int getDay() {
        return day;
    }

    public void setIsFilled() {
        this.isFilled = true;
    }

    public boolean getIsFilled() {
        return isFilled;
    }

    public void removeIsFilled() {
        this.isFilled = false;
    }

    public void setModName(String name) {
        modName = name;
    }

    public String getModName() {
        return modName;
    }

    /**
     * Returns true if a given string is a valid timeslot.
     */
    public static boolean isValidTimeSlot(String test) {
        return test.matches(TIMESLOT_VALIDATION_REGEX);
    }
}
