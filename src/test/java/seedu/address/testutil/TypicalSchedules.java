package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.schedule.Schedule;

/**
 * A utility class containing a list of {@code Schedule} objects to be used in tests.
 */
public class TypicalSchedules {
    public static final Schedule CLEANING = new ScheduleBuilder().withTimeStart("0900")
            .withTimeEnd("1000").withVenue("Toilet").build();
    public static final Schedule USHER = new ScheduleBuilder().withTimeStart("1100")
            .withTimeEnd("1700").withVenue("Counter 1").build();
    public static final Schedule OFFICE = new ScheduleBuilder().withTimeStart("1000")
            .withTimeEnd("1800").withVenue("Level 3").build();
    public static final Schedule ACCOUTING = new ScheduleBuilder().withTimeStart("1000")
            .withTimeEnd("1800").withVenue("Finance Office").build();

    private TypicalSchedules() {}

    public static Set<Schedule> getTypicalSchedules() {
        return new HashSet<>(Arrays.asList(CLEANING, USHER, OFFICE, ACCOUTING));
    }
}
