package seedu.address.model.task;

/**
 * Represents a Task's start & end date time in the task list.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime {
    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Date & Time should only contain numbers, "
                    +
                    "and it should in DD/MM_HHmm format\";should be at least 4 digits long";
    public static final String DATETIME_VALIDATION_REGEX = "\\d+\\/\\d+(\\w\\d+)?";

    private String dateTimeString;

    public DateTime(String dateTimeString) {
        this.dateTimeString = dateTimeString;
    }

    /**
     * Returns true if a given string is a valid date & time.
     */
    public static boolean isValidDateTime(String test) {
        return test.matches(DATETIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return dateTimeString;
    }
}
