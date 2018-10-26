package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.collections.ObservableList;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;
import seedu.address.model.event.AttendanceContainsUserPredicate;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Event;
import seedu.address.model.user.User;

/**
 * This class is utility class for method related to @DateTime field
 * and system clock
 */
public class DateTimeUtil {
    public static final DateFormat PAGE_DATE_FORMAT = new SimpleDateFormat("EEEEE dd-MMMMM-yyyy 'at' HH:mm a");
    private static final String CALENDAR_FILE_PATH = "data/";

    private static AttendanceContainsUserPredicate predicate;

    //Utility method to access DateTime data
    //Use to get computer current datetime,assuming computer date is set correctly
    public static Date getCurrentDateTime() {
        return new Date();
    }

    /**
     * Compare to know how many TimeUnit until or past the date of comparision
     * TimeUnit: NANOSECONDS, MICROSECONDS, MILLISECONDS, SECONDS, MINUTES, HOURS, DAYS
     */
    public static long daysDiff(DateTime eventDate, Date currentDate, TimeUnit timeUnit) {
        requireAllNonNull(eventDate, currentDate, timeUnit);
        return timeUnit.convert(
                eventDate.dateTime.getTime() - currentDate.getTime(), timeUnit);
    }

    //*****************************Method related to the new export calendar command********************************
    //TODO: Write test case for new command in test/logic/command
    /**
     * Get the current list of event that the current user
     * @param  model current Event Manager model
     * @param  currentUser current User
     * @return an user registered event list
     */
    public static ObservableList<Event> getAttendingEventList(Model model, User currentUser) {
        requireAllNonNull(model, currentUser);
        //currentUser.getUsername();
        model.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);
        return model.getFilteredEventList();
    }

    /**
     * Convert the Event in Event Manager to VEvent type in ical4j to add to iCalendar file
     * @param  registeredEventList current user registered event
     * @return a list of VEvent after conversion
     * NOTE: Currently, due to the limit of java.util.Date so event are default to last from start time to end of the
     * day
     */
    public static List<VEvent> convertEventListToVEventList(ObservableList<Event> registeredEventList) {
        List<VEvent> calendarEvents =  new ArrayList<>();
        for (Event event : registeredEventList) {
            VEvent toAddEvent = convertEventToVEvent(event);
            calendarEvents.add(toAddEvent);
        }

        return calendarEvents;
    }

    /**
     * Convert Event to VEvent
     * @param  event an Event in Event Manager
     * @return a VEvent with given properties in event
     */
    public static VEvent convertEventToVEvent(Event event) {
        VEvent vEvent = new VEvent(new net.fortuna.ical4j.model.DateTime(event.getDateTime().dateTime),
                new net.fortuna.ical4j.model.DateTime(new DateTime("3/1/2019 11:20").dateTime),
                event.getName().toString());

        Location eventLocation = new Location(event.getAddress().value);
        UidGenerator eventUid = Uid::new;
        Attendee eventHost = new Attendee(URI.create("mailto:" + event.getEmail().value));
        eventHost.getParameters().add(Role.CHAIR);
        eventHost.getParameters().add(new Cn(event.getContact().fullContactName));
        //TODO: add description when include, add current user as an attendee

        vEvent.getProperties().add(eventLocation);
        vEvent.getProperties().add(eventUid.generateUid());
        vEvent.getProperties().add(eventHost);
        return vEvent;
    }

    /**
     * Stream and write data from event to an iCalendar file will user specify file name
     * @param  eventsList user registered event list
     * @param  filename user preferences file name
     * @return userCalendar
     */
    private static Calendar writeToUserCalendar(ObservableList<Event> eventsList, String filename) {
        Calendar userCalendar = buildCalendar(filename);
        List<VEvent> userRegisteredEvents = convertEventListToVEventList(eventsList);

        for(VEvent userRegisteredEvent : userRegisteredEvents) {
            userCalendar.getComponents().add(userRegisteredEvent);
        }
        return userCalendar;
    }

    /**
     * Build an Calendar with user preferences file name
     * @param filename user preferences file name
     * @return Calendar
     */
    private static Calendar buildCalendar(String filename) {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId(String.format("-//%1$s//iCal4j 1.0//EN",filename)));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        return calendar;
    }

    /**
     * Export an iCalendar from user registered event list
     * @param  model current Event Manager model
     * @param  currentUser current logged in user
     * @param  fileName user preferences file name
     * @throws IOException
     */
    public static void exportICalenderFile(Model model, User currentUser, String fileName) throws IOException {
        FileOutputStream fileOut = new FileOutputStream( CALENDAR_FILE_PATH +
                String.format("%1$s.ics", fileName), false);
        CalendarOutputter outPutter = new CalendarOutputter();

        Calendar calendar = writeToUserCalendar(getAttendingEventList(model, currentUser),fileName);

        outPutter.output(calendar, fileOut);
    }
}
