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

import java.time.LocalTime;
import java.util.Collection;
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

/**
 * Utility functions for the reading and writing of {@code TimeTable} objects to disk as .ics file. (and vice versa)
 * Classes available for public access:
 * 1) readTimeTableFromFile (Path filePath)
 * 2) saveTimeTableToFile (TimeTable timeTable, Path filePath)
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
     * Obtains the {@code Optional<TimeTable>} from the .ics file specified.
     * @returns {@code Optional.empty()} object if the file did not contain any {@code TimeTable} data.
     *
     * @param filePath cannot be null.
     * @throws IOException if any IO error occurs, or file is not found.
     *
     */
    public Optional<TimeTable> readTimeTableFromFile(Path filePath)
            throws IOException {
        requireNonNull(filePath);

        ICalendar iCalendar = new ICalendar();
        try {
            iCalendar = readICalendarFromFile(filePath); //does not return null.
        } catch (IOException e) {
            logger.info("Failed to read: " + filePath.toString());
            throw new IOException(e);
        }

        Optional<TimeTable> optionalTimeTable = iCalendarToTimeTable(iCalendar);

        return optionalTimeTable;
    }

    /**
     * Saves {@code TimeTable} data to the .ics file specified.
     *
     * @param filePath Location to save the file to. Cannot be null.
     * @throws IOException  Thrown if there is an error writing to the file.
     */
    public void saveTimeTableToFile(TimeTable timeTable, Path filePath)
            throws IOException {
        requireNonNull(filePath);

        ICalendar iCalendar = timeTableToICalendar(timeTable);
        try {
            writeICalendarToFile(iCalendar, filePath);
        } catch (IOException e) {
            logger.info("Failed to write to: " + filePath.toString());
            throw new IOException (e);
        }
    }

    /**
     * Converts {@code ICalendar} to {@code TimeTable}
     * @param iCalendar {@code ICalendar} to convert. Cannot be null.
     */
    private Optional<TimeTable> iCalendarToTimeTable(ICalendar iCalendar) {
        requireNonNull(iCalendar);
        TimeTable timeTable = new TimeTable();
        /*
        In the forloop, we go through all the VEvents (logically equivalent to TimeSlots)
        in the iCalendar (logically equivalent to TimeTable)

        Then we get all the properties of each VEvent, and Instantiate a TimeSlot using these properties as parameters.
        Then we add this TimeSlot to the TimeTable.
         */
        for (VEvent vEvent : iCalendar.getEvents()) { //foreach TimeSlot in TimeTable
            Optional<TimeSlot> optionalTimeSlot = vEventToTimeSlot(vEvent);
            if (optionalTimeSlot.isPresent()) {
                timeTable.addTimeSlot(optionalTimeSlot.get());
            }
        }
        if (timeTable.isEmpty()) {
            logger.info("No timeslots found in file.");
            return Optional.empty();
        } else {
            logger.info("At least 1 timeslot has been read from file.");
            return Optional.of(timeTable);
        }
    }

    /**
     * Converts {@code VEvent} to {@code Optional<TimeSlot>}.
     * Only allow VEvents that are recurring to be added.
     * @param vEvent {@code VEvent} to convert. Cannot be null.
     */
    private Optional<TimeSlot> vEventToTimeSlot(VEvent vEvent) {
        requireNonNull(vEvent);
        //formatter
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        DateFormat timeFormat = new SimpleDateFormat("HHmmss");

        // this part extracts the vital information
        DateStart dateStart = vEvent.getDateStart();
        String dateStartStr = (dateStart == null) ? null : dateFormat.format(dateStart.getValue());
        String timeStartStr = (dateStart == null) ? null : timeFormat.format(dateStart.getValue());

        DateEnd dateEnd = vEvent.getDateEnd();
        //dateEndStr omitted; we assume event ends on the same day.
        String timeEndStr = (dateEnd == null) ? null : timeFormat.format(dateEnd.getValue());

        Summary summary = vEvent.getSummary();
        String summaryStr = (summary == null) ? null : summary.getValue();

        RecurrenceRule recurrenceRule = vEvent.getRecurrenceRule(); //is the event recurring every X-weeks/X-days?
        if (recurrenceRule == null) {
            return Optional.empty();
        }

        //if any of out essential TimeSlot variables are missing, ignore the VEvent and do not add it.
        if ((dateStartStr == null) || (timeStartStr == null) || (timeEndStr == null)) {
            return Optional.empty();
        }

        //after the above information extraction, we instantiate a TimeSlot with these info.
        LocalTime timeSlotStartTime = DateTimeConversionUtil.getInstance().timeStringToLocalTime(timeStartStr);
        LocalTime timeSlotEndTime = DateTimeConversionUtil.getInstance().timeStringToLocalTime(timeEndStr);
        DayOfWeek timeSlotDay = DateTimeConversionUtil.getInstance().dateStringToDayOfWeek(dateStartStr);

        //Add timeslot to timetable
        //TODO: Add (summary/label) to timetable object.
        TimeSlot timeSlot = new TimeSlot(timeSlotDay, timeSlotStartTime, timeSlotEndTime);
        return Optional.of(timeSlot);
    }

    /**
     * Converts {@code TimeTable} to {@code ICalendar}.
     * @param timeTable {@code TimeTable} to convert. Cannot be null.
     */
    private ICalendar timeTableToICalendar(TimeTable timeTable) {
        requireNonNull(timeTable);
        Collection<TimeSlot> timeSlots = timeTable.getTimeSlots();

        ICalendar iCalendar = new ICalendar();
        for (TimeSlot timeSlot : timeSlots) {
            VEvent vEvent = timeSlotToWeeklyVEvent(timeSlot, 1);
            iCalendar.addEvent(vEvent);
        }
        return iCalendar;
    }

    /**
     * Converts a {@code TimeSlot} to a {@code VEvent} that recur weekly, for {@code count} times.
     *
     * @param timeSlot {@code TimeSlot} to convert. Cannot be null.
     * @param count {@code int} number of recurrences.
     */
    private VEvent timeSlotToWeeklyVEvent(TimeSlot timeSlot, int count) {
        //TODO: protect against people who pass count < 0

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
                new Recurrence.Builder(Frequency.WEEKLY).count(count).byDay(valueOfAbbr(abbreviation)).build();
        RecurrenceRule recurrenceRule = new RecurrenceRule(recurrence);
        vEvent.setRecurrenceRule(recurrenceRule);

        //write data to {@code VEvent}: set the DateStart
        vEvent.setDateStart(DateTimeConversionUtil.getInstance().getNextDateOfDay(startTime, dayOfWeek));

        //write data to {@code VEvent}: set the DateEnd
        vEvent.setDateEnd(DateTimeConversionUtil.getInstance().getNextDateOfDay(endTime, dayOfWeek));

        //write data to {@code VEvent}: set summary (Module Name)
        vEvent.setSummary(label);

        return vEvent;
    }


    /**
     * Writes {@code ICalendar} object to {@code Path} specified
     * @throws IOException if any error occurs during write.
     */
    private void writeICalendarToFile(ICalendar iCalendar, Path filePath) throws IOException {
        requireNonNull(filePath);
        requireNonNull(iCalendar);

        File file = filePath.toFile();
        try {
            file.getParentFile().mkdirs(); //create parent folder if it does not exist.
            file.createNewFile(); //biweekly will throw IOException if the file does not exist already
            Biweekly.write(iCalendar).go(file);
        } catch (IOException e) { //catch IOException thrown by .createNewFile() or .go()
            throw new IOException();
        }
    }

    /**
     * @return {@code ICalendar} object from reading the {@code Path} specified
     * Will return an empty {@code ICalendar} if the file has no iCalendar information.
     * @throws IOException if any IO error occurs during read.
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
