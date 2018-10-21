package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.person.TimeSlot;
import seedu.address.model.person.TimeTable;
import seedu.address.model.person.exceptions.TimeSlotOverlapException;

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
     * TODO: how to parse string in a OOP-way?
     */
    private Optional<TimeTable> parseTimeTableFromString(String string) throws DataConversionException {

        TimeTable timeTable = new TimeTable();

        String[] chunks = string.split("BEGIN:VEVENT");

        for (String chunk : chunks) { //each chunk contains the data of 1 timeslot
            Optional<TimeSlot> optionalTimeSlot = convertChunkToTimeSlot(timeTable, chunk);
            if (optionalTimeSlot.isPresent()) {
                try {
                    timeTable.addTimeSlot(optionalTimeSlot.get());
                } catch (TimeSlotOverlapException e) {
                    throw new DataConversionException(e);
                }
            }
        };
        return Optional.of(timeTable);
    }

    /**
     * Parses the chunk (a string) into the equivalent TimeSlot object
     */
    private Optional<TimeSlot> convertChunkToTimeSlot(TimeTable newTimeTable, String chunk)
            throws DataConversionException {
        String[] linesInChunk = chunk.split("\\r\\n|[\\n\\x0B\\x0C\\r\\u0085\\u2028\\u2029]");

        String startdate = "";
        String starttime = "";
        String enddate = "";
        String endtime = "";
        String name = "";
        boolean isTimeSlot = false;

        for (String line : linesInChunk) {
            String[] splitLine = line.split(":");
            if (splitLine.length != 2) {
                continue; //file format is wrong, just ignore the line
            }

            String dataName = splitLine[0];
            String dataString = splitLine[1];

            if (dataName.equals("DTSTART")) {
                //get start
                try {
                    startdate = dataString.substring(0, 8);
                    starttime = dataString.substring(9, 15);
                } catch (IndexOutOfBoundsException e) {
                    throw new DataConversionException(e);
                }
            } else if (dataName.equals("DTEND")) {
                //get end
                try {
                    enddate = dataString.substring(0, 8);
                    endtime = dataString.substring(9, 15);
                } catch (IndexOutOfBoundsException e) {
                    throw new DataConversionException(e);
                }
            } else if (dataName.equals("SUMMARY")) {
                //get mod name
                name = dataString;
            } else if (dataName.equals("RRULE")) {
                //is it a weekly time slot?
                isTimeSlot = dataString.contains("WEEKLY");
            }
        }

        //check data is all present
        if ((startdate.isEmpty()) || (enddate.isEmpty()) || (starttime.isEmpty())
                || (endtime.isEmpty()) || (name.isEmpty()) || (!isTimeSlot)) {
            return Optional.empty();
        }

        //process the data we got from the chunk
        LocalTime timeSlotStartTime;
        LocalTime timeSlotEndTime;
        DayOfWeek timeSlotDay;

        try {
            timeSlotStartTime = icsStringToTime(starttime);
            timeSlotEndTime = icsStringToTime(endtime);
            timeSlotDay = icsStringToDay(startdate);
        } catch (NumberFormatException e) {
            throw new DataConversionException(e);
        }

        TimeSlot timeSlot = new TimeSlot(timeSlotDay, timeSlotStartTime, timeSlotEndTime);
        return Optional.of(timeSlot);
    }

    /**
     * Converts the date in the ics file into day (1-7).
     */
    private DayOfWeek icsStringToDay(String dateString) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate date = LocalDate.parse(dateString, fmt);
        DayOfWeek day = date.getDayOfWeek();

        return day;
    }

    /**
     * Converts the ics-formatted string into a LocalTime object.
     */
    private LocalTime icsStringToTime(String icsTime) {
        int timeInt = Integer.parseInt(icsTime);
        timeInt += 80000; //need to add 8 hours to the ics-formatted version.
        String formattedTime = String.format("%06d", timeInt);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HHmmss");
        LocalTime time = LocalTime.parse(formattedTime, fmt);

        return time;
    }
}
