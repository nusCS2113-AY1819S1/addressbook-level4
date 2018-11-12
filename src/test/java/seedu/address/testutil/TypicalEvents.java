package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            new Location("Test Location 3"),
            new Attendees("alice@example.com")
    );

    public static final Event EVENT_4 = new Event(
            new EventName("Test Event 4"),
            new Description("Test Description 4"),
            new EventDate("2018-08-18"),
            new StartTime("06:00"),
            new EndTime("08:00"),
            new Location("Test Location 4"),
            new Attendees("lydia@example.com")
    );


    public static final Event EVENT_5 = new Event(
            new EventName("Test Event 5"),
            new Description("Test Description 5"),
            new EventDate("2018-11-18"),
            new StartTime("06:00"),
            new EndTime("12:00"),
            new Location("Test Location 5"),
            new Attendees("johnd@example.com", "alice@example.com")
    );

    public static final Event EVENT_6 = new Event(
            new EventName("Test Event 6"),
            new Description("Test Description 6"),
            new EventDate("2018-11-10"),
            new StartTime("09:00"),
            new EndTime("13:00"),
            new Location("Test Location 6")
    );

    /**
     * Returns test event list.
     */
    public static EventList getTypicalEventList() {
        EventList eventList = new EventList();
        eventList.addEvent(EVENT_1);
        eventList.addEvent(EVENT_2);
        eventList.addEvent(EVENT_3);
        eventList.addEvent(EVENT_4);
        eventList.addEvent(EVENT_5);

        return eventList;
    }

    public static EventList getAllTypicalEventList() {
        EventList eventList = new EventList();
        eventList.addEvent(EVENT_1);
        eventList.addEvent(EVENT_2);
        eventList.addEvent(EVENT_3);
        eventList.addEvent(EVENT_4);
        eventList.addEvent(EVENT_5);

        return eventList;
    }

    public static EventList getSortByNameEventList() {
        EventList eventList = new EventList();
        eventList.addEvent(EVENT_1);
        eventList.addEvent(EVENT_2);
        eventList.addEvent(EVENT_3);
        eventList.addEvent(EVENT_4);
        eventList.addEvent(EVENT_5);

        return eventList;
    }

    public static EventList getSortByDateEventList() {
        EventList eventList = new EventList();
        eventList.addEvent(EVENT_4);
        eventList.addEvent(EVENT_3);
        eventList.addEvent(EVENT_1);
        eventList.addEvent(EVENT_2);
        eventList.addEvent(EVENT_5);

        return eventList;
    }

    public static EventList getSortByStartTimeEventList() {
        EventList eventList = new EventList();
        eventList.addEvent(EVENT_4);
        eventList.addEvent(EVENT_5);
        eventList.addEvent(EVENT_3);
        eventList.addEvent(EVENT_1);
        eventList.addEvent(EVENT_2);

        return eventList;
    }

    public static EventList getSortByEndTimeEventList() {
        EventList eventList = new EventList();
        eventList.addEvent(EVENT_4);
        eventList.addEvent(EVENT_5);
        eventList.addEvent(EVENT_1);
        eventList.addEvent(EVENT_2);
        eventList.addEvent(EVENT_3);

        return eventList;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(EVENT_1, EVENT_2, EVENT_3, EVENT_4, EVENT_5));
    }

}
