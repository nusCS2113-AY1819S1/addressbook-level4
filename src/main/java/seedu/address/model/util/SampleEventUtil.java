package seedu.address.model.util;

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


/**
 * Contains utility methods for populating {@code EventList} with sample data.
 */
public class SampleEventUtil {

    public static final String EMAIL_ALEX = "alexyeoh@example.com";
    public static final String EMAIL_BERNICE = "berniceyu@example.com";
    public static final String EMAIL_CHARLOTTE = "charlotte@example.com";
    public static final String EMAIL_DAVID = "lidavid@example.com";
    public static final String EMAIL_IRFAN = "irfan@example.com";
    public static final String EMAIL_ROY = "royb@example.com";

    public static Event[] getSampleEvents() {

        Attendees attendeesOne = new Attendees(EMAIL_ALEX, EMAIL_BERNICE, EMAIL_CHARLOTTE);
        Attendees attendeesTwo = new Attendees(EMAIL_ALEX, EMAIL_BERNICE, EMAIL_CHARLOTTE, EMAIL_DAVID, EMAIL_IRFAN);
        Attendees attendeesThree = new Attendees(EMAIL_ALEX, EMAIL_ROY, EMAIL_BERNICE);
        Attendees attendeesFour = new Attendees(EMAIL_CHARLOTTE, EMAIL_DAVID, EMAIL_IRFAN, EMAIL_ROY, EMAIL_ALEX);

        return new Event[]{
            new Event(new EventName("Monthly Team Meeting"), new Description("Review monthly progress"),
                    new EventDate("2018-11-06"), new StartTime("10:00"), new EndTime("12:00"),
                    new Location("Meeting Room 5"), attendeesOne),
            new Event(new EventName("Dinner with ABC Client"), new Description("Attire: Smart Casual. No Sandles"),
                    new EventDate("2018-11-14"), new StartTime("18:00"),
                    new EndTime("20:00"), new Location("Marina Bay Sands"), attendeesTwo),
            new Event(new EventName("Contract Negotiation Meeting"), new Description("For CX-2143 Project"),
                    new EventDate("2018-11-07"), new StartTime("14:00"),
                    new EndTime("16:00"), new Location("Seminar Room 3"), attendeesThree),
            new Event(new EventName("Tender Discussion with Supplier"), new Description("Get ready tender draft"),
                    new EventDate("2018-12-15"), new StartTime("16:00"),
                    new EndTime("18:00"), new Location("Jurong East"), attendeesFour),
            new Event(new EventName("Farewell Party for Roy"), new Description("Goodbye Roy!!! Have fun in US"),
                    new EventDate("2019-01-20"), new StartTime("18:00"),
                    new EndTime("23:00"), new Location("Bugis+"), attendeesFour),
            new Event(new EventName("Team Annual Outing"), new Description("Do come if you are free."),
                    new EventDate("2018-12-24"), new StartTime("12:00"),
                    new EndTime("18:00"), new Location("Sentosa")),
            new Event(new EventName("AlphaQ Project Meeting"), new Description("Finalize project final report"),
                    new EventDate("2018-11-10"), new StartTime("20:00"),
                    new EndTime("23:00"), new Location("Alex's House"), attendeesOne),
            new Event(new EventName("Practical Exam 2"), new Description("good luck have fun. have mercy"),
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
}
