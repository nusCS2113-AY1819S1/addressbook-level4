package seedu.address.commons.util;

import static biweekly.util.DayOfWeek.valueOfAbbr;
import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collection;
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
 * Utility functions for the reading and writing of {@code TimeTable} objects to disk as .ics file. (and vice versa)
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
     * Returns {@code Optional.empty()} object if the file is not found, or it did not contain iCalendar data.
     *
     * @param filePath cannot be null.
     * @throws IOException if the file format is not as expected.
     *
     */
    public Optional<TimeTable> readTimeTableFromFile(Path filePath)
            throws IOException {
        requireNonNull(filePath);

        ICalendar iCalendar = new ICalendar();
        try {
            iCalendar = readICalendarFromFile(filePath); //does not return null.
        } catch (IOException e) {
            throw new IOException(e);
        }

        Optional<TimeTable> optionalTimeTable = iCalendarToTimeTable(iCalendar);

        return optionalTimeTable;
    }

    /**
     * Converts {@code ICalendar} to {@code TimeTable}
     *
     */
    private Optional<TimeTable> iCalendarToTimeTable(ICalendar iCalendar) {
        TimeTable timeTable = new TimeTable();
        /*
        In the forloop, we go through all the VEvents (logically equivalent to TimeSlots)
        in the iCalendar (logically equivalent to TimeTable)

        Then we get all the properties of each VEvent, and Instantiate a TimeSlot using these properties as parameters.
        Then we add this TimeSlot to the TimeTable.
         */
        for (VEvent event : iCalendar.getEvents()) { //foreach TimeSlot in TimeTable
            //formatter
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            DateFormat timeFormat = new SimpleDateFormat("HHmmss");

            // this part extracts the vital information
            DateStart dateStart = event.getDateStart();
            String dateStartStr = (dateStart == null) ? null : dateFormat.format(dateStart.getValue());
            String timeStartStr = (dateStart == null) ? null : timeFormat.format(dateStart.getValue());

            DateEnd dateEnd = event.getDateEnd();
            //dateEndStr omitted; we assume event ends on the same day.
            String timeEndStr = (dateEnd == null) ? null : timeFormat.format(dateEnd.getValue());

            Summary summary = event.getSummary();
            String summaryStr = (summary == null) ? null : summary.getValue();

            RecurrenceRule recurrenceRule = event.getRecurrenceRule(); //is the event recurring every X-weeks/X-days?
            if (recurrenceRule == null) {
                continue; //TODO: (optional) add "recurrence" variable into the TimeSlot object.
            }

            //after the above information extraction, we instantiate a TimeSlot with these info.
            LocalTime timeSlotStartTime = DateTimeConversionUtil.getInstance().timeStringToLocalTime(timeStartStr);
            LocalTime timeSlotEndTime = DateTimeConversionUtil.getInstance().timeStringToLocalTime(timeEndStr);
            DayOfWeek timeSlotDay = DateTimeConversionUtil.getInstance().dateStringToDayOfWeek(dateStartStr);

            logger.info("TimeSlot read:" + timeSlotStartTime + " to " + timeSlotEndTime + " on " + timeSlotDay + ": " + summaryStr);

            //Add timeslot to timetable
            //TODO: Add (summary/label) to timetable object.
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
     * Saves {@code TimeTable} data to the .ics file specified.
     *
     * @param filePath Location to save the file to. Cannot be null.
     * @throws IOException  Thrown if there is an error during converting the data
     *                                  into .ics or writing to the file.
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
    }

    /**
     * Converts {@code TimeTable} to {@code ICalendar}.
     */
    private ICalendar timeTableToICalendar(TimeTable timeTable) {
        Collection<TimeSlot> timeSlots = timeTable.getTimeSlots();

        ICalendar iCalendar = new ICalendar();
        for (TimeSlot timeSlot : timeSlots) {
            VEvent vEvent = timeSlotToWeeklyVEvent(timeSlot, 14);
            iCalendar.addEvent(vEvent);
        }
        return iCalendar;

    }

    /**
     * Converts {@code TimeSlot} to a {@code VEvent} that has a {@code Recurrence} of {@code Frequency.WEEKLY}.
     * Note that the exported data will only stretch for 1 week, the current week. (as of now)
     */
    private VEvent timeSlotToWeeklyVEvent(TimeSlot timeSlot, int count) {
        //extract data from {@code TimeSlot}
        LocalTime startTime = timeSlot.getStartTime();
        LocalTime endTime = timeSlot.getEndTime();
        DayOfWeek dayOfWeek = timeSlot.getDayOfWeek();
        String label = timeSlot.getLabel();
        String abbreviation = timeSlot.getAbbreviationFromDayOfWeek();

        //write data to {@code VEvent}
        VEvent vEvent = new VEvent();

        //write data to {@code VEvent}: set the recurrence rule
        Recurrence recurrence =
                new Recurrence.Builder(Frequency.WEEKLY).count(14).byDay(valueOfAbbr(abbreviation)).build();
        RecurrenceRule recurrenceRule = new RecurrenceRule(recurrence);
        vEvent.setRecurrenceRule(recurrenceRule);

        //write data to {@code VEvent}: set the DateStart
        vEvent.setDateStart(DateTimeConversionUtil.getInstance().getPreviousDateOfDay(startTime, dayOfWeek));

        //write data to {@code VEvent}: set the DateEnd
        vEvent.setDateEnd(DateTimeConversionUtil.getInstance().getPreviousDateOfDay(endTime, dayOfWeek));

        //write data to {@code VEvent}: set summary (Module Name)
        vEvent.setSummary(label);

        return vEvent;
    }


    /**
     * Writes {@code ICalendar} object to file specified
     * @throws IOException if any error occurs during write.
     */
    private void writeICalendarToFile(ICalendar iCalendar, Path filePath) throws IOException {
        requireNonNull(filePath);
        requireNonNull(iCalendar);

        File file = filePath.toFile();
        try {
            Biweekly.write(iCalendar).go(file);
        } catch (IOException e) {
            throw new IOException();
        }
    }

    /**
     * Reads {@code ICalendar} object from {@code Path} specified
     *
     * Will return an empty {@code ICalendar} if the .ics file is empty or has no related information.
     *
     * @throws IOException if any IO error occurs during read.
     *
     * TODO: This function currently only reads the 1st {@code ICalendar} in an .ics file
     * the other {@code ICalendar} are simply not read! (silent failure)
     * NUSMODS export only has 1 VCalendar in an .ics file; all good for now.
     */
    private ICalendar readICalendarFromFile(Path filePath) throws IOException {
        requireNonNull(filePath);
        ICalendar iCalendar;
        try {
            iCalendar = Biweekly.parse(filePath.toFile()).first();
        } catch (IOException e) {
            throw new IOException(e);
        }

        if (iCalendar == null){
            return new ICalendar();
        } else {
            return iCalendar;
        }
    }
}
