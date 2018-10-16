//@@author emobeany
package seedu.address.testutil;

import seedu.address.model.task.Deadline;

/**
 * A utility class to help with building Deadline objects.
 */

public class DeadlineBuilder {
    public static final String DEFAULT_DAY = "01";
    public static final String DEFAULT_MONTH = "01";
    public static final String DEFAULT_YEAR = "2018";

    private String day;
    private String month;
    private String year;

    public DeadlineBuilder() {
        this.day = DEFAULT_DAY;
        this.month = DEFAULT_MONTH;
        this.year = DEFAULT_YEAR;
    }

    /**
     * Initializes the DeadlineBuilder with the data of {@code deadlineToCopy}.
     */
    public DeadlineBuilder(Deadline deadlineToCopy) {
        this.day = deadlineToCopy.getDay();
        this.month = deadlineToCopy.getMonth();
        this.year = deadlineToCopy.getYear();
    }
    /**
     * Sets the {@code day} of the {@code Deadline} that we are building.
     */
    public DeadlineBuilder withDay(String day) {
        this.day = day;
        return this;
    }

    /**
     * Sets the {@code month} of the {@code Deadline} that we are building.
     */
    public DeadlineBuilder withMonth(String month) {
        this.month = month;
        return this;
    }

    /**
     * Sets the {@code year} of the {@code Deadline} that we are building.
     */
    public DeadlineBuilder withYear(String year) {
        this.year = year;
        return this;
    }

    public Deadline build() {
        return new Deadline(day, month, year);
    }
}
