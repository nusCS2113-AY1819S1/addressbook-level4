package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;

import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.io.text.ICalReader;
import biweekly.property.DateEnd;
import biweekly.property.DateStart;
import biweekly.property.RecurrenceRule;
import biweekly.property.Summary;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
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
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<TimeTable> readIcsFile(Path filePath)
            throws DataConversionException {

        requireNonNull(filePath);

        try {
            if (!Files.exists(filePath)) {
                logger.info("Ics file " + filePath + " not found");
                return Optional.empty();
            }
        } catch (SecurityException e) {
            logger.warning("Read rights not available when trying to access ICS file " + filePath + ": " + e);
        }

        Optional<TimeTable> optionalTimeTable;
        try {
            optionalTimeTable = parseTimeTableFromString(FileUtil.readFromFile(filePath));
        } catch (IOException e) {
            logger.warning("Error reading from ICS file " + filePath + ": " + e);
            throw new DataConversionException(e);
        }

        return optionalTimeTable;
    }

    /**
     * Saves TimeTable object data to the .ics file specified
     *
     * @param filePath Points to a .ics file containing data {@code TimeTable}.
     *             Cannot be null.
     * @throws FileNotFoundException    Thrown if the file is missing.
     * @throws DataConversionException  Thrown if there is an error during converting the data
     *                                  into .ics and writing to the file.
     */

    public void saveIcsFile(TimeTable timeTable, Path filePath)
            throws DataConversionException, FileNotFoundException {
        requireNonNull(filePath);

        //TODO
    }


    /**
     * Parses the string (that was read from an .ics file) into a timetable object
     * @throws DataConversionException if the string contents are not expected.
     */
    private Optional<TimeTable> parseTimeTableFromString(String string) throws DataConversionException {

        ArrayList<ICalendar> iCalendarList = stringToICalendarList(string);
        ArrayList<VEvent> vEventList = iCalendarListToVEventList(iCalendarList);
        TimeTable timeTable = vEventListToTimeTable(vEventList);

        //finished using ICalReader
        return Optional.of(timeTable);
    }
    /**
     * Parses the list of VEvents into a single timetable object
     */
    private TimeTable vEventListToTimeTable(ArrayList<VEvent> vEventList) throws DataConversionException {
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
     * Parses a list of iCalendarList objects into a list of vevent objects
     */
    private ArrayList<VEvent> iCalendarListToVEventList(ArrayList<ICalendar> iCalendarList) {
        ArrayList < VEvent> vEventList = new ArrayList< VEvent>();
        for (ICalendar iCalendar : iCalendarList) {
            for (VEvent event : iCalendar.getEvents()) {
                vEventList.add(event);
            }
        }
        return vEventList;
    }

    /**
     * Parses the iCal string into a list of ICalendar Objects
     * @throws DataConversionException if the string contents are not expected.
     *
     */
    private ArrayList<ICalendar> stringToICalendarList (String string) throws DataConversionException {
        ArrayList<ICalendar> iCalendarList = new ArrayList<ICalendar>();
        ICalReader reader = null;
        try {
            reader = new ICalReader(string);
            ICalendar ical;
            while ((ical = reader.readNext()) != null) {
                //for each ICalendar found, add it to the list of ICalendars
                iCalendarList.add(ical);
            }
        } catch (IOException e) {
            throw new DataConversionException(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                // so many catches... :c
                throw new DataConversionException(e);
            }
        }
        return iCalendarList;
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
