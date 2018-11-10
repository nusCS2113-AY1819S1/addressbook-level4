package seedu.address.model.task;

//@@author luhan02
/**
 * Represents a Task's start & end date time in the task list.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartDateTime(String)}
 * and {@link #isValidEndDateTime(String)}
 */
public class DateTime {
    public static final String MESSAGE_START_DATETIME_CONSTRAINTS =
            "Start Date & Time should only contain numbers, "
                    +
                    "and it should in DD/MM(_HH:mm) format";
    /*
    public static final String DATETIME_VALIDATION_REGEX = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])"
        + "(\\_([01]?[0-9]|2[0-3]):([0-5][0-9]))?";
        */
    public static final String START_DATETIME_VALIDATION_REGEX = "(((0?[1-9]|1[0-9]|2[0-8])/(0?2))"
            + "|((0?[1-9]|[12][0-9]|3[01])/(0?[13578]|10|12))|((0?[1-9]|[12][0-9]|30)/(0?[469]|11)))"
            + "(\\_([01]?[0-9]|2[0-3]):([0-5][0-9]))?";

    public static final String MESSAGE_END_DATETIME_CONSTRAINTS =
            "End Date & Time should only contain numbers, "
                    +
                    "and it should in DD/MM_HH:mm format";

    public static final String END_DATETIME_VALIDATION_REGEX = "(((0?[1-9]|1[0-9]|2[0-8])/(0?2))"
            + "|((0?[1-9]|[12][0-9]|3[01])/(0?[13578]|10|12))|((0?[1-9]|[12][0-9]|30)/(0?[469]|11)))"
            + "(\\_([01]?[0-9]|2[0-3]):([0-5][0-9]))";

    public final String dateTimeString;

    public DateTime(String dateTimeString) {
        this.dateTimeString = dateTimeString;
    }

    /**
     * Returns true if a given string is a valid start date & time.
     */
    public static boolean isValidStartDateTime(String test) {
        return test.matches(START_DATETIME_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid end date & time.
     */
    public static boolean isValidEndDateTime(String test) {
        return test.matches(END_DATETIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return dateTimeString;
    }
}
