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
    public static final String EMAIL_EDRIC = "edricg@example.com";
    public static final String EMAIL_FARHAN = "farhanish@example.com";
    public static final String EMAIL_LEE = "dlwlrma@example.com";
    public static final String EMAIL_ELON = "elont@example.com";
    public static final String EMAIL_EDWARD = "whitebeard@example.com";
    public static final String EMAIL_PAUL = "reddevil@example.com";

    public static Event[] getSampleEvents() {

        Attendees attendeesOne = new Attendees(EMAIL_ALEX, EMAIL_BERNICE, EMAIL_CHARLOTTE, EMAIL_PAUL);
        Attendees attendeesTwo = new Attendees(EMAIL_ALEX, EMAIL_BERNICE, EMAIL_CHARLOTTE, EMAIL_DAVID, EMAIL_IRFAN);
        Attendees attendeesThree = new Attendees(EMAIL_ALEX, EMAIL_ROY, EMAIL_BERNICE, EMAIL_PAUL);
        Attendees attendeesFour = new Attendees(EMAIL_CHARLOTTE, EMAIL_DAVID, EMAIL_IRFAN, EMAIL_ROY, EMAIL_ALEX);
        Attendees attendeesFive = new Attendees(EMAIL_EDRIC, EMAIL_FARHAN, EMAIL_EDWARD, EMAIL_PAUL, EMAIL_LEE);

        Attendees adminDep = new Attendees(EMAIL_ROY, EMAIL_EDRIC, EMAIL_ALEX);
        Attendees financeDep = new Attendees(EMAIL_CHARLOTTE, EMAIL_FARHAN, EMAIL_IRFAN, EMAIL_EDWARD);
        Attendees all = new Attendees(EMAIL_ALEX, EMAIL_BERNICE, EMAIL_CHARLOTTE, EMAIL_DAVID, EMAIL_IRFAN,
                EMAIL_ROY, EMAIL_EDRIC, EMAIL_FARHAN, EMAIL_LEE, EMAIL_ELON, EMAIL_EDWARD, EMAIL_PAUL);
        Attendees manager = new Attendees(EMAIL_DAVID, EMAIL_IRFAN, EMAIL_LEE, EMAIL_ELON, EMAIL_EDWARD);

        return new Event[]{
            new Event(new EventName("Monthly Team Meeting"), new Description("Review monthly progress [Finance Team]"),
                    new EventDate("2018-11-06"), new StartTime("10:00"), new EndTime("12:00"),
                    new Location("Meeting Room 2"), financeDep),
            new Event(new EventName("Monthly Team Meeting"), new Description("Review monthly progress [Admin Team]"),
                    new EventDate("2018-11-06"), new StartTime("10:00"), new EndTime("12:00"),
                    new Location("Meeting Room 5"), adminDep),
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
                    new EndTime("23:00"), new Location("Bugis+"), attendeesOne),
            new Event(new EventName("Team Annual Outing"), new Description("Do come if you are free."),
                    new EventDate("2018-12-24"), new StartTime("12:00"),
                    new EndTime("18:00"), new Location("Sentosa"), attendeesFive),
            new Event(new EventName("BetaQ Project Meeting"), new Description("Finalize project final report"),
                    new EventDate("2018-11-10"), new StartTime("20:00"),
                    new EndTime("23:00"), new Location("Alex's House"), attendeesFive),
            new Event(new EventName("Practical Exam 2"), new Description("good luck have fun. have mercy"),
                    new EventDate("2018-11-16"), new StartTime("16:00"),
                    new EndTime("18:00"), new Location("LT15"), all),
            new Event(new EventName("Manager Discussion"), new Description("annual team review"),
                    new EventDate("2018-12-30"), new StartTime("10:00"),
                    new EndTime("18:00"), new Location("Skyfall"), manager)

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
