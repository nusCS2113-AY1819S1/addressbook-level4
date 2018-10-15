package seedu.address.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.EventList;
import seedu.address.model.ReadOnlyEventList;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.StartTime;
import seedu.address.model.tag.Tag;


/**
 * Contains utility methods for populating {@code EventList} with sample data.
 */
public class SampleEventUtil {
    public static Event[] getSampleEvents() {

        return new Event[]{
            new Event(new EventName("CS2113 Lecture"), new Description("Very Hard"), LocalDate.parse("2018-09-28"),
                      new StartTime("16:00"), new EndTime("18:00"), new Location("LT 100")),
            new Event(new EventName("CS2113 Tutorial"), new Description("Can i skip this?"),
                      LocalDate.parse("2018-10-28"), new StartTime("16:00"),
                      new EndTime("17:00"), new Location("Tut room 1000")),
            new Event(new EventName("CS2113 Java session"), new Description("Java Camp help!"),
                      LocalDate.parse("2018-09-28"), new StartTime("16:00"),
                      new EndTime("23:00"), new Location("Utown")),
        };
    }

    public static ReadOnlyEventList getSampleEventList() {
        EventList sampleAb = new EventList();
        for (Event sampleEvent : getSampleEvents()) {
            sampleAb.addEvent(sampleEvent);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
