package seedu.address.commons.util;

import static biweekly.util.DayOfWeek.valueOfAbbr;
import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.time.DayOfWeek;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.io.text.ICalReader;
import biweekly.property.DateEnd;
import biweekly.property.DateStart;
import biweekly.property.RecurrenceRule;
import biweekly.property.Summary;
import biweekly.util.Frequency;
import biweekly.util.Recurrence;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.TimeSlot;
import seedu.address.model.person.TimeTable;
import seedu.address.model.person.exceptions.TimeSlotOverlapException;

/**
 * Utility functions for the reading and writing of {@code TimeTable} objects from/to disk as .ics file.
 */
public class IcsUtil {
    private static final Logger logger = LogsCenter.getLogger(IcsUtil.class);
    private static IcsUtil instance;

    private IcsUtil(){

    }

    public static IcsUtil getInstance() {
        if (instance == null) {
            instance = new IcsUtil();
        }
        return instance;
    }

    /**
     * Returns the {@code TimeTable} from the .ics file specified.
     * The data in the .ics file should not have timezone-related settings; unsupported for now.
     *
     * @param filePath                  Location of the .ics file.
     *                                  Cannot be null.
     * @param zoneId                    The timezone of the timetable.
     *                                  Cannot be null.
     * @throws IOException              if any IO error occurs, or file is not found.
     * @throws TimeSlotOverlapException if the file to be imported has overlapping {@code TimeSlot}.
     *
     */
    public TimeTable readTimeTableFromFile(Path filePath, ZoneId zoneId)
            throws IOException, TimeSlotOverlapException, IllegalArgumentException {
        requireNonNull(filePath);
        requireNonNull(zoneId);

        ICalendar iCalendar;
        try {
            iCalendar = readICalendarFromFile(filePath);
        } catch (IOException e) {
            logger.info("Failed to read: " + filePath.toString());
            throw new IOException(e);
        }

        return iCalendarToTimeTable(iCalendar, zoneId);
    }

    /**
     * Saves {@code TimeTable} data to the .ics file specified.
     * The timestamps in the saved .ics file will be in UTC.
     *
     * @param timeTable     The timetable to save.
     *                      Cannot be null.
     * @param filePath      Location to save the file to.
     *                      Cannot be null.
     * @param zoneId        timezone of the Timetable.
     *                      Cannot be null.
     * @throws IOException  Thrown if there is an error writing to the file.
     */
    public void saveTimeTableToFile(TimeTable timeTable, ZoneId zoneId, Path filePath)
            throws IOException {
        requireNonNull(filePath);
        requireNonNull(timeTable);
        requireNonNull(zoneId);

        ICalendar iCalendar = timeTableToICalendar(timeTable, zoneId);
        try {
            writeICalendarToFile(iCalendar, filePath);
        } catch (IOException e) {
            logger.info("Failed to write to: " + filePath.toString());
            throw new IOException (e);
        }
    }

    /**
     * Converts {@code ICalendar} to {@code TimeTable}.
     * Non-recurring {@code VEvent} are ignored.
     *
     * @param iCalendar The {@code ICalendar} to convert.
     *                  Cannot be null.
     * @param zoneId    The TimeZone of the {@code TimeTable}.
     *                  Cannot be null.
     */
    private TimeTable iCalendarToTimeTable(ICalendar iCalendar, ZoneId zoneId)
            throws TimeSlotOverlapException, IllegalArgumentException {
        requireNonNull(iCalendar);
        requireNonNull(zoneId);
        TimeTable timeTable = new TimeTable();

        for (VEvent vEvent : iCalendar.getEvents()) {
            //convert VEvent to TimeSlot
            Optional<TimeSlot> optionalTimeSlot = vEventToTimeSlot(vEvent, zoneId);
            if (optionalTimeSlot.isPresent()) {
                timeTable.addTimeSlot(optionalTimeSlot.get());
            }
        }
        if (timeTable.isEmpty()) {
            logger.info("No timeslots found in file.");
        } else {
            logger.info("At least 1 timeslot has been read from file.");
        }
        return timeTable;
    }

    /**
     * Converts {@code VEvent} to {@code Optional<TimeSlot>}.
     * Non-recurring VEvents are ignored.
     *
     * @param vEvent        The {@code VEvent} to convert.
     *                      Cannot be null.
     * @param zoneId        The TimeZone of the {@code Optional<TimeSlot>}.
     */
    private Optional<TimeSlot> vEventToTimeSlot(VEvent vEvent, ZoneId zoneId) {
        requireNonNull(vEvent);
        requireNonNull(zoneId);

        //each of these chunks extract/filter the vital information:

        //Event's Start:
        //get the starting date and time (UTC)
        DateStart dtStart = vEvent.getDateStart();
        //convert to simple Date (UTC)
        Date startDate = dtStart.getValue();
        //use the simple Date to create an Instant. Instant objects are independent of timezone (UTC).
        Instant startInstant = startDate.toInstant();
        //derive the LocalDateTime (+8GMT) from the Instant and ZoneId
        LocalDateTime startLdt = LocalDateTime.ofInstant(startInstant, zoneId);

        //Event's End:
        //get the ending date and time (UTC)
        DateEnd dtEnd = vEvent.getDateEnd();
        //convert to simple Date (UTC)
        Date endDate = dtEnd.getValue();
        //use the simple Date to create an {@code Instant}. Instant objects are independent of timezone (UTC).
        Instant endInstant = endDate.toInstant();
        //derive the LocalDateTime (+8GMT) from the Instant and ZoneId
        LocalDateTime endLdt = LocalDateTime.ofInstant(endInstant, zoneId);


        //get summary (TimeSlot's Label)
        Summary summary = vEvent.getSummary();
        String summaryStr = (summary == null) ? null : summary.getValue();

        //ignore non-recurring events
        RecurrenceRule recurrenceRule = vEvent.getRecurrenceRule();
        if (recurrenceRule == null) {
            return Optional.empty();
        }

        //ignore events that are missing essential information.
        if ((dtStart == null) || (dtEnd == null)) {
            return Optional.empty();
        }

        //after the above information extraction, we instantiate a TimeSlot with these info.
        LocalTime timeSlotStartTime = startLdt.toLocalTime();
        LocalTime timeSlotEndTime = endLdt.toLocalTime();
        DayOfWeek timeSlotDay = startLdt.getDayOfWeek();
        //TODO: Add (summary/label) to timetable object.

        //Add timeslot to timetable
        TimeSlot timeSlot = new TimeSlot(timeSlotDay, timeSlotStartTime, timeSlotEndTime);
        return Optional.of(timeSlot);
    }

    /**
     * Converts {@code TimeTable} to {@code ICalendar}.
     *
     * @param timeTable     The {@code TimeTable} to convert.
     *                      Cannot be null.
     * @param zoneId        the timezone of the timetable
     *                      Cannot be null.
     *
     */
    private ICalendar timeTableToICalendar(TimeTable timeTable, ZoneId zoneId) {
        requireNonNull(timeTable);
        requireNonNull(zoneId);
        Collection<TimeSlot> timeSlots = timeTable.getTimeSlots();

        ICalendar iCalendar = new ICalendar();
        for (TimeSlot timeSlot : timeSlots) {
            VEvent vEvent = timeSlotToWeeklyVEvent(timeSlot, zoneId, 1);
            iCalendar.addEvent(vEvent);
        }
        return iCalendar;
    }

    /**
     * Converts a {@code TimeSlot} to a {@code VEvent} that recurs weekly, for {@code count} times.
     * {@code zoneId} is required, because {@code VEvent} are in UTC,
     *  but {@code TimeSlot} are in the user's timezone,
     *
     * @param timeSlot  The {@code TimeSlot} to convert.
     *                  Cannot be null.
     * @param count     The number of recurrences.
     * @param zoneId    The timezone of the {@code TimeSlot}
     */
    private VEvent timeSlotToWeeklyVEvent(TimeSlot timeSlot, ZoneId zoneId, int count) {
        //TODO: protect against people who pass count <= 0
        //TODO: make comments/code more human readable.
        requireNonNull(timeSlot);
        requireNonNull(zoneId);
        requireNonNull(count);

        //extract data from {@code TimeSlot}
        LocalTime startTime = timeSlot.getStartTime();
        LocalTime endTime = timeSlot.getEndTime();
        DayOfWeek dayOfWeek = timeSlot.getDayOfWeek();
        String label = timeSlot.getLabel();
        //TODO: remove getAbbreviationFromDayOfWeek() from TimeSlot
        String abbreviation = timeSlot.getAbbreviationFromDayOfWeek();

        VEvent vEvent = new VEvent();

        //write data to vEvent: set the recurrence rule
        Recurrence recurrence =
                new Recurrence.Builder(Frequency.WEEKLY).count(count).byDay(valueOfAbbr(abbreviation)).build();
        RecurrenceRule recurrenceRule = new RecurrenceRule(recurrence);
        vEvent.setRecurrenceRule(recurrenceRule);

        //write data to vEvent: set start-date
        //Get the local-datetime of the next time that the TimeSlot starts.
        // > If my timeslot is Friday 2pm-4pm, the local-datetime I want is the the next Friday's date, at 2pm.
        LocalDateTime startLdt = DateTimeConversionUtil.getInstance().getNextLocalDateTime(startTime, dayOfWeek);
        //get zone-offset
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(Instant.now());
        //startInstant is an Instant ; Instant is independent of timezones.
        Instant startInstant = startLdt.toInstant(zoneOffset);
        //dateStart (UTC)
        Date dateStart = Date.from(startInstant);
        vEvent.setDateStart(dateStart);

        //write data to vEvent: set end-date
        //Get the local-datetime of the next time that the TimeSlot ends.
        // > If my timeslot is Friday 2pm-4pm, the local-datetime I want is the the next Friday's date, at 4pm.
        LocalDateTime endLdt = DateTimeConversionUtil.getInstance().getNextLocalDateTime(endTime, dayOfWeek);
        //endInstant is an Instant; Instant is independent of timezones.
        Instant endInstant = endLdt.toInstant(zoneOffset);
        //get dateEnd (UTC)
        Date dateEnd = Date.from(endInstant);
        vEvent.setDateEnd(dateEnd);

        //write data to {@code VEvent}: set summary (Module Name)
        vEvent.setSummary(label);

        return vEvent;
    }


    /**
     * Writes {@code ICalendar} object to {@code Path} specified
     *
     * @param filePath      The location to write to.
     *                      Cannot be null.
     * @param iCalendar     The ICalendar object to write.
     *                      Cannot be null.
     * @throws IOException  If any error occurs during write.
     */
    private void writeICalendarToFile(ICalendar iCalendar, Path filePath) throws IOException {
        requireNonNull(filePath);
        requireNonNull(iCalendar);

        File file = filePath.toFile();
        try {
            //create parent folder if it does not exist.
            file.getParentFile().mkdirs();
            //create file if it does not exist.
            file.createNewFile();
            //Write using biweekly.
            Biweekly.write(iCalendar).go(file);
        } catch (IOException e) { //catch IOException thrown by .createNewFile() or .go()
            throw new IOException();
        }
    }

    /**
     * Returns the {@code ICalendar} object obtained from reading the .ics file at the {@code Path} specified
     *
     * @param filePath      the location of the file to read from.
     * @throws IOException  if any IO error occurs during read.
     */
    private ICalendar readICalendarFromFile(Path filePath) throws IOException {
        requireNonNull(filePath);
        ICalendar iCalendar = new ICalendar();

        File file = filePath.toFile();
        ICalReader reader;
        try {
            reader = new ICalReader(file);
        } catch (FileNotFoundException e) {
            throw new IOException(); //throw IOException to indicate file-not-found.
        }

        try {
            //for each event found, add them to the iCalendar
            ICalendar tempICalendar;
            while ((tempICalendar = reader.readNext()) != null) {
                for (VEvent event : tempICalendar.getEvents()) {
                    iCalendar.addEvent(event);
                }
            }
        } catch (IOException e) {
            //IOException thrown by readNext() - not able to read from stream
            throw new IOException();
        } finally {
            reader.close();
        }
        return iCalendar;
    }
}
