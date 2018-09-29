package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import seedu.address.model.EventList;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;

public class TypicalEvents {

    public static final Event EVENT_1 = new Event(
            new EventName("Test Event 1"),
            new Description("Test Description 1"),
            new Date(100000),
            new Date(100000),
            new Location("Test Location 1")
    );

    public static final Event EVENT_2 = new Event(
            new EventName("Test Event 2"),
            new Description("Test Description 2"),
            new Date(110000),
            new Date(110000),
            new Location("Test Location 2")
    );

    public static final Event EVENT_3 = new Event(
            new EventName("Test Event 3"),
            new Description("Test Description 3"),
            new Date(120000),
            new Date(120000),
            new Location("Test Location 3")
    );

    public static EventList getTypicalEventList() {
        EventList eventList = new EventList();

        eventList.addEvent(EVENT_1);
        eventList.addEvent(EVENT_2);
        eventList.addEvent(EVENT_3);

        return eventList;
    }
}
