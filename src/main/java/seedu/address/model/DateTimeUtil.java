package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javafx.collections.ObservableList;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
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
    private static final Path CALENDAR_FILE_PATH = Paths.get("src", "data", "");


    private static AttendanceContainsUserPredicate predicate;

    //Use to get computer current datetime,assuming computer date is set correctly
    public static Date getCurrentDateTime() {
        return new Date();
    }

    /**Compare to know how many TimeUnit until or past the date of comparision
     *TimeUnit: NANOSECONDS, MICROSECONDS, MILLISECONDS, SECONDS, MINUTES, HOURS, DAYS
     */
    public static long daysDiff(DateTime eventDate, Date currentDate, TimeUnit timeUnit) {
        requireAllNonNull(eventDate, currentDate, timeUnit);
        return timeUnit.convert(
                eventDate.dateTime.getTime() - currentDate.getTime(), timeUnit);
    }

    //Require the current model to get the list
    public static ObservableList<Event> getAttendingEventList(Model model, User currentUser) {
        requireAllNonNull(model, currentUser);
        currentUser.getUsername();
        model.updateFilteredEventList(predicate);
        return model.getFilteredEventList();
    }


    /**
     * Stream and write data from event to an iCalendar file will user specify file name
     * @param eventsList
     * @param filename
     * @return userCalendar
     */
    private static Calendar writeToICalenderFile(ObservableList<Event> eventsList, String filename) {
        Calendar userCalendar = buildICalendarFile(filename);

        return userCalendar;
    }

    /**
     * Build an Calendar with user preferences file name
     * @param filename
     * @return Calendar
     */
    private static Calendar buildICalendarFile(String filename) {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId(String.format("-//%1$s//iCal4j 3.0//EN",filename)));
        calendar.getProperties().add(CalScale.GREGORIAN);

        return calendar;
    }

    /**
     * Export an iCalendar from user registered event list
     * @param model
     * @param currentUser
     * @param fileName
     * @throws IOException
     */
    public static void exportICalenderFile(Model model, User currentUser, String fileName) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(String.format("%1$s.ics",fileName),false);
        CalendarOutputter outPutter = new CalendarOutputter();

        Calendar calendar = writeToICalenderFile(getAttendingEventList(model, currentUser),fileName);

        outPutter.output(calendar, fileOut);
    }
}
