package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.EventList;
import seedu.address.model.ReadOnlyEventList;
import seedu.address.model.event.Attendees;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.StartTime;
import seedu.address.model.tag.Tag;


/**
 * Contains utility methods for populating {@code EventList} with sample data.
 */
public class SampleEventUtil {
    public static Event[] getSampleEvents() {

        Set<String> attendeesOne = new HashSet<>();
        Set<String> attendeesTwo = new HashSet<>();
        Set<String> attendeesThree = new HashSet<>();
        Set<String> attendeesFour = new HashSet<>();
        attendeesOne.add("Alex Yeoh");
        attendeesOne.add("Bernice Yu");
        attendeesOne.add("Charlotte Oliveiro");
        attendeesTwo.add("Alex Yeoh");
        attendeesTwo.add("David Li");
        attendeesTwo.add("Irfan Ibrahim");
        attendeesTwo.add("Roy Balakrishnan");
        attendeesTwo.add("Bernice Yu");
        attendeesThree.add("Alex Yeoh");
        attendeesThree.add("Roy Balakrishnan");
        attendeesThree.add("Bernice Yu");
        attendeesFour.add("Charlotte Oliveiro");
        attendeesFour.add("David Li");
        attendeesFour.add("Irfan Ibrahim");
        attendeesFour.add("Alex Yeoh");
        attendeesFour.add("Roy Balakrishnan");

        return new Event[]{
            new Event(new EventName("Monthly Team Meeting"), new Description("Review monthly progress"),
                    new EventDate("2018-11-06"), new StartTime("10:00"), new EndTime("12:00"),
                    new Location("Meeting Room 5"), new Attendees(attendeesOne)),
            new Event(new EventName("Dinner with ABC Client"), new Description("Attire: Smart Casual. No Sandles"),
                    new EventDate("2018-11-14"), new StartTime("18:00"),
                    new EndTime("20:00"), new Location("Marina Bay Sands"), new Attendees(attendeesThree)),
            new Event(new EventName("Contract Negotiation Meeting"), new Description("For CX-2143 Project"),
                    new EventDate("2018-11-07"), new StartTime("14:00"),
                    new EndTime("16:00"), new Location("Seminar Room 3"), new Attendees(attendeesThree)),
            new Event(new EventName("Tender Discussion with Supplier"), new Description("Get ready tender draft"),
                    new EventDate("2018-12-15"), new StartTime("16:00"),
                    new EndTime("18:00"), new Location("Jurong East"), new Attendees(attendeesTwo)),
            new Event(new EventName("Farewell Party for Roy"), new Description("Goodbye Roy!!! Have fun in US"),
                    new EventDate("2019-01-20"), new StartTime("18:00"),
                    new EndTime("23:00"), new Location("Bugis+"), new Attendees(attendeesFour)),
            new Event(new EventName("Team Annual Outing"), new Description("Do come if you are free."),
                    new EventDate("2018-12-24"), new StartTime("12:00"),
                    new EndTime("18:00"), new Location("Sentosa")),
            new Event(new EventName("AlphaQ Project Meeting"), new Description("Finalize project final report"),
                    new EventDate("2018-11-10"), new StartTime("20:00"),
                    new EndTime("23:00"), new Location("Alex's House"), new Attendees(attendeesOne)),
            new Event(new EventName("Practical Exam 1"), new Description("good luck have fun. have mercy"),
                    new EventDate("2018-11-03"), new StartTime("16:00"),
                    new EndTime("18:00"), new Location("LT15"))
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
