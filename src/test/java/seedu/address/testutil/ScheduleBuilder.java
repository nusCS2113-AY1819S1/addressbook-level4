package seedu.address.testutil;

import seedu.address.model.person.EventName;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.TheDate;
import seedu.address.model.person.Time;

/**
 * A utility class to help with building Schedule objects.
 */
public class ScheduleBuilder {

    public static final String DEFAULT_DATE = "09092018";
    public static final String DEFAULT_START_TIME = "1230";
    public static final String DEFAULT_END_TIME = "1400";
    public static final String DEFAULT_EVENT_NAME = "CS1231 Exam";


    private TheDate date;
    private Time startTime;
    private Time endTime;
    private EventName eventName;

    public ScheduleBuilder() {
        date = new TheDate(DEFAULT_DATE);
        startTime = new Time(DEFAULT_START_TIME);
        endTime = new Time(DEFAULT_END_TIME);
        eventName = new EventName(DEFAULT_EVENT_NAME);
    }

    /**
     * Sets the {@code TheDate} of the {@code Schedule} that we are building.
     */
    public ScheduleBuilder withTheDate(String date) {
        this.date = new TheDate(date);
        return this;
    }

    /**
     * Sets the {@code start Time} of the {@code Schedule} that we are building.
     */
    public ScheduleBuilder withStartTime(String startTime) {
        this.startTime = new Time(startTime);
        return this;
    }

    /**
     * Sets the {@code end Time} of the {@code Schedule} that we are building.
     */
    public ScheduleBuilder withEndTime(String endTime) {
        this.endTime = new Time(endTime);
        return this;
    }
    /**
     * Sets the {@code EventName} of the {@code Schedule} that we are building.
     */
    public ScheduleBuilder withEventName(String eventName) {
        this.eventName = new EventName(eventName);
        return this;
    }

    public Schedule build() {
        return new Schedule(date, startTime, endTime, eventName);
    }

}
