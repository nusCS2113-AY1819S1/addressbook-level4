package seedu.address.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.EventList;
import seedu.address.model.ReadOnlyEventList;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code EventList} with sample data.
 */
public class SampleEventUtil {
    public static Event[] getSampleEvents() {
        return new Event[]{
                new Event(new EventName("CS2113 Lecture"), new Description("Very Hard"), LocalDate.parse("2018-9-28"),
                        LocalDate.parse("2018-11-9"), new Location("LT 100"))

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
