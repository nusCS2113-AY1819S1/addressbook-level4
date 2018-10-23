package seedu.address.commons.util;

import static biweekly.util.DayOfWeek.valueOfAbbr;
import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.DateEnd;
import biweekly.property.DateStart;
import biweekly.property.RecurrenceRule;
import biweekly.property.Summary;
import biweekly.util.Frequency;
import biweekly.util.Recurrence;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.TimeSlot;
import seedu.address.model.person.TimeTable;

/**
 * Converts a TimeTable object instance to .ics and vice versa.
 *
 * Usage:
 * 1) Mainly used to read and write the TimeTable(s) of FreeTime into the disk for permanent storage.
 * 2) Also used during import and export commands (ie, read and write)
 *
 * NOTE: Only support ICS to-and-from TimeTable Objects(!!).
 * TODO: allow conversion to objects other than timetable
 * TODO: create a 'serialisable' class to serialise(?) the timetable objects to ics file formats
 */
public class IcsUtil {
    public static final String DEFAULT_ZONE_ID = "Asia/Shanghai";

    private static final Logger logger = LogsCenter.getLogger(IcsUtil.class);
    private static IcsUtil instance;

    private IcsUtil(){
        //any things to initialise?
    }

    public static IcsUtil getInstance() {
        if (instance == null) {
            instance = new IcsUtil();
        }
        return instance;
    }

    /**
     * Returns the TimeTable object from the .ics file.
     * Returns {@code Optional.empty()} object if the file is not found.
     * Missing or corrupted or incompatible entries in the .ics file will silently fail, for now.
     * @param filePath cannot be null.
     * @throws IOException if the file format is not as expected.
     */
    public Optional<TimeTable> readTimeTableFromFile(Path filePath)
            throws IOException {
        requireNonNull(filePath);

        ICalendar iCalendar = new ICalendar();
        try {
            iCalendar = readICalendarFromFile(filePath);
        } catch (IOException e) {
            throw new IOException(e);
        }

        Optional<TimeTable> optionalTimeTable = iCalendarToTimeTable(iCalendar);

        return optionalTimeTable;
    }

    /**
     * Converts icalendar to TimeTable
     * Returns {@code Optional.empty()} object if the file is not found.
     * Missing or corrupted or incompatible entries in the .ics file will silently fail, for now.

     * @throws IOException if the file format is not as expected.
     */
    private Optional<TimeTable> iCalendarToTimeTable(ICalendar iCalendar) {
        TimeTable timeTable = new TimeTable();
        /*
        In the for-loop, we go through all the VEvents (logical equivalent to TimeSlots)
        in the iCalendar (logical equivalent to TimeTable)

        Then we get all the properties of each VEvent, and Instantiate a TimeSlot using these properties as parameters.
        Then we add this TimeSlot to the TimeTable.
         */
        for (VEvent event : iCalendar.getEvents()) { //for-each TimeSlot in TimeTable
            //formatter
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            DateFormat timeFormat = new SimpleDateFormat("HHmmss");

            // this part extracts the vital information
            DateStart dateStart = event.getDateStart();
            String dateStartStr = (dateStart == null) ? null : dateFormat.format(dateStart.getValue());
            String timeStartStr = (dateStart == null) ? null : timeFormat.format(dateStart.getValue());

            DateEnd dateEnd = event.getDateEnd();
            //assume event ends on same day.
            String timeEndStr = (dateEnd == null) ? null : timeFormat.format(dateEnd.getValue());

            Summary summary = event.getSummary();
            String summaryStr = (summary == null) ? null : summary.getValue();
            //TODO: add this parameter to timetable object.

            RecurrenceRule recurrenceRule = event.getRecurrenceRule();
            String recurrenceRuleStr = (recurrenceRule == null) ? null : "hasrecurrance";
            if (recurrenceRule == null) {
                continue; //TODO: this is currently hacky. pls make proper.
            }

            //after the above information extraction, we instantiate a TimeSlot with these info.
            LocalTime timeSlotStartTime = timeStringToLocalTime(timeStartStr);
            LocalTime timeSlotEndTime = timeStringToLocalTime(timeEndStr);
            DayOfWeek timeSlotDay = dateStringToDayOfWeek(dateStartStr);

            System.out.println(timeSlotStartTime + " to " + timeSlotEndTime + " on " + timeSlotDay + ": " + summaryStr);

            //Add timeslot to timetable
            TimeSlot timeSlot = new TimeSlot(timeSlotDay, timeSlotStartTime, timeSlotEndTime);
            timeTable.addTimeSlot(timeSlot);

        }
        if (timeTable.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(timeTable);
        }
    }

    /**
     * Saves TimeTable object data to the .ics file specified
     *
     * @param filePath Points to a .ics file containing data {@code TimeTable}.
     *             Cannot be null.
     * @throws FileNotFoundException    Thrown if the file is missing.
     * @throws IOException  Thrown if there is an error during converting the data
     *                                  into .ics and writing to the file.
     */

    public void saveTimeTableToFile(TimeTable timeTable, Path filePath)
            throws IOException {
        requireNonNull(filePath);

        ICalendar iCalendar = timeTableToICalendar(timeTable);
        try {
            writeICalendarToFile(iCalendar, filePath);
        } catch (IOException e) {
            throw new IOException (e);
        }
        //TODO implement above functions.
    }

    /**
     * Converts TimeTable to ICalendar object.
     *
     */
    private ICalendar timeTableToICalendar(TimeTable timeTable) {
        Collection<TimeSlot> timeSlots = timeTable.getTimeSlots();

        ICalendar iCalendar = new ICalendar();
        for (TimeSlot timeSlot : timeSlots) {
            VEvent vEvent = toWeeklyVEvent(timeSlot, 14);
            iCalendar.addEvent(vEvent);
        }
        return iCalendar;

    }

    /**
     * Converts TimeSlot to a VEvent that has a {@code Recurrence} of weekly.
     * note that the exported data will only stretch this current week, for now.
     *
     * @throws FileNotFoundException    Thrown if the file is missing.
     * @throws IOException  Thrown if there is an error during converting the data
     *                                  into .ics and writing to the file.
     */
    private VEvent toWeeklyVEvent(TimeSlot timeSlot, int count) {
        Date date = new Date(); //date now
        LocalTime localTime = LocalTime.now(); //localtime now

        LocalTime startTime = timeSlot.getStartTime();
        LocalTime endTime = timeSlot.getEndTime();
        DayOfWeek dayOfWeek = timeSlot.getDayOfWeek();
        String label = timeSlot.getLabel();
        String abbreviation = timeSlot.getAbbreviationFromDayOfWeek();

        VEvent vEvent = new VEvent();

        //set the recurrence rule
        Recurrence recurrence =
                new Recurrence.Builder(Frequency.WEEKLY).count(14).byDay(valueOfAbbr(abbreviation)).build();
        RecurrenceRule recurrenceRule = new RecurrenceRule(recurrence);
        vEvent.setRecurrenceRule(recurrenceRule);

        //set the DateStart
        vEvent.setDateStart(getPreviousDateOfDay(startTime, dayOfWeek));

        //set the DateEnd
        vEvent.setDateEnd(getPreviousDateOfDay(endTime, dayOfWeek));

        //set summary (name of module)
        vEvent.setSummary(label);

        return vEvent;
    }

    /**
     * Get the Date of the previous dayOfWeek.
     * ie; if dayOfWeek is monday, then the return last monday's Date.
     */
    private Date getPreviousDateOfDay(LocalTime startTime, DayOfWeek dayOfWeek) {
        LocalDate previousDay =
                LocalDate.now(ZoneId.of(DEFAULT_ZONE_ID))
                        .with(TemporalAdjusters.previous(dayOfWeek));

        LocalDateTime localDateTime = startTime.atDate(previousDay);
        return localDateTimeToDate(localDateTime);
    }

    /**
     * Utility function to convert {@code LocalDateTime} to {@code Date}
     */
    private Date localDateTimeToDate(LocalDateTime localDateTime) {
        //Date in = new Date();
        //LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        Date out = Date.from(localDateTime.atZone(ZoneId.of(DEFAULT_ZONE_ID)).toInstant());
        return out;
    }

    /*
     * Utility function to convert {@code Date} to {@code LocalDateTime}
     */
    private LocalDateTime dateToLocalDateTime(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return localDateTime;
    }

    /**
     * Writes ICalendar object to file specified
     * @throws IOException if any error occurs during write.
     *
     */
    private void writeICalendarToFile(ICalendar iCalendar, Path filePath) throws IOException {
        File file = filePath.toFile();
        try {
            Biweekly.write(iCalendar).go(file);
        } catch (IOException e) {
            throw new IOException();
        }
    }

    /**
     * Reads {@code ICalendar} object from file specified
     * Will return an empty ICalendar if the .ics file is empty.
     *
     * NOTE: this function will silently fail if there are more than 1 VCalendar's in a .ics file.
     * other Vcalendar will not be read
     * Thankfully, NUSMODS export only has 1 VCalendar in an .ics file.
     *
     * @throws IOException if any error occurs during read.
     */
    private ICalendar readICalendarFromFile(Path filePath) throws IOException {
        ICalendar iCalendar;
        try {
            iCalendar = Biweekly.parse(filePath.toFile()).first();
        } catch (IOException e) {
            throw new IOException(e);
        }
        return iCalendar;
    }


    /**
     * Attempts to read the specified file and return its contents as a simple String.
     * @throws IOException if the file cannot be read for any reason.
     */
    private Optional<String> getStringFromPath(Path filePath) throws IOException {
        String fileString = "";

        try {
            fileString = FileUtil.readFromFile(filePath);
        } catch (IOException e) {
            logger.warning("Error reading from ICS file " + filePath + ": " + e);
            throw new IOException(e);
        }
        return Optional.of(fileString);
    }

    /**
     * Parses the list of VEvents into a single timetable object
     */
    private TimeTable vEventListToTimeTable(ArrayList<VEvent> vEventList) throws IOException {
        TimeTable timeTable = new TimeTable();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        DateFormat timeFormat = new SimpleDateFormat("HHmmss");

        //this for-loop iterates through the list of events.
        for (VEvent event : vEventList) {
            DateStart dateStart = event.getDateStart();

            String dateStartStr = (dateStart == null) ? null : dateFormat.format(dateStart.getValue());
            String timeStartStr = (dateStart == null) ? null : timeFormat.format(dateStart.getValue());

            DateEnd dateEnd = event.getDateEnd();
            //assume event ends on same day.
            String timeEndStr = (dateEnd == null) ? null : timeFormat.format(dateEnd.getValue());

            Summary summary = event.getSummary();
            String summaryStr = (summary == null) ? null : summary.getValue();
            //TODO: add this parameter to timetable object.

            RecurrenceRule recurrenceRule = event.getRecurrenceRule();
            String recurrenceRuleStr = (recurrenceRule == null) ? null : "hasrecurrance";
            if (recurrenceRule == null) {
                continue; //TODO: this is currently hacky. pls make proper.
            }

            //process the data we got from the chunk
            LocalTime timeSlotStartTime = timeStringToLocalTime(timeStartStr);
            LocalTime timeSlotEndTime = timeStringToLocalTime(timeEndStr);
            DayOfWeek timeSlotDay = dateStringToDayOfWeek(dateStartStr);

            System.out.println(timeSlotStartTime + " to " + timeSlotEndTime + " on " + timeSlotDay + ": " + summaryStr);

            //after getting all the data of this event, we add it to timetable.
            TimeSlot timeSlot = new TimeSlot(timeSlotDay, timeSlotStartTime, timeSlotEndTime);
            timeTable.addTimeSlot(timeSlot);
        }
        return timeTable;
    }

    /**
     * Converts the ics-format dateString into DayOfWeek object.
     * @param dateString    is in format yyyyMMdd
     */
    private DayOfWeek dateStringToDayOfWeek(String dateString) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate date = LocalDate.parse(dateString, fmt);
        DayOfWeek day = date.getDayOfWeek();

        return day;
    }

    /**
     * Converts the ics-formatted timeString into a LocalTime object.
     * @param timeString    is in format HHmmss
     */
    private LocalTime timeStringToLocalTime(String timeString) {
        int timeInt = Integer.parseInt(timeString);
        //timeInt += 80000; //need to add 8 hours to the ics-formatted version.
        String formattedTime = String.format("%06d", timeInt);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HHmmss");
        LocalTime time = LocalTime.parse(formattedTime, fmt);

        return time;
    }
}
