package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.EventList;
import seedu.address.model.event.Attendees;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.StartTime;


/**
 * Returns test event list.
 */
public class TypicalEvents {

    public static final Event EVENT_1 = new Event(
            new EventName("Test Event 1"),
            new Description("Test Description 1"),
            new EventDate("2018-09-18"),
            new StartTime("12:00"),
            new EndTime("16:00"),
            new Location("Test Location 1")
    );

    public static final Event EVENT_2 = new Event(
            new EventName("Test Event 2"),
            new Description("Test Description 2"),
            new EventDate("2018-09-18"),
            new StartTime("12:00"),
            new EndTime("21:00"),
            new Location("Test Location 2")
    );

    public static final Event EVENT_3 = new Event(
            new EventName("Test Event 3"),
            new Description("Test Description 3"),
            new EventDate("2018-09-18"),
            new StartTime("09:00"),
            new EndTime("21:00"),
            new Location("Test Location 3")
    );

    public static final Event EVENT_4 = new Event(
            new EventName("Test Event 4"),
            new Description("Test Description 4"),
            new EventDate("2018-09-18"),
            new StartTime("06:00"),
            new EndTime("08:00"),
            new Location("Test Location 4")
    );

    /**
     * Returns test event with attendees.
     */
    public static final Event eventwithAttendee() {
        Set<String> attendee = new HashSet<>();
        attendee.add("Alice Pauline");
        return new Event(
                new EventName("Test Event 3"),
                new Description("Test Description 3"),
                new EventDate("2018-09-18"),
                new StartTime("09:00"),
                new EndTime("21:00"),
                new Location("Test Location 3"),
                new Attendees(attendee));
    }

    /**
     * Returns test event list.
     */
    public static EventList getTypicalEventList() {
        EventList eventList = new EventList();
        eventList.addEvent(EVENT_1);
        eventList.addEvent(EVENT_2);
        eventList.addEvent(eventwithAttendee());

        return eventList;
    }
}
