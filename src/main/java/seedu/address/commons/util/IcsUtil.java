package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

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
 * Helps with reading from and writing to ICS files.
 * Only support ICS to-and-from TimeTable Objects.
 * TODO: create a 'serialisable' class to serialise(?) the timetable objects to ics file formats,
 * just like in the jsonUtil class
 */
public class IcsUtil {
    private static final Logger logger = LogsCenter.getLogger(IcsUtil.class);

    /**
     * Returns the data in the ICS file as a TimeTableObject
     */
    public static Optional<TimeTable> getTimeTableFromFile(Path file)
            throws DataConversionException {

        requireNonNull(file);

        if (!Files.exists(file)) {
            logger.info("Ics file " + file + " not found");
            return Optional.empty();
        }

        String importstring = "";
        try {
            importstring = FileUtil.readFromFile(file);
        } catch (IOException e) {
            logger.warning("Error reading from ICS file " + file + ": " + e);
            throw new DataConversionException(e);
        }

        //parse the string into timetable object
        Optional<TimeTable> optionalTimeTable = stringToTimeTableParser(importstring);

        return optionalTimeTable;
    }

    /**
     * Parses the string from an ics file into a timetable object
     */
    private static Optional<TimeTable> stringToTimeTableParser(String string) throws DataConversionException {

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
    private static Optional<TimeSlot> convertChunkToTimeSlot(TimeTable newTimeTable, String chunk)
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
    private static DayOfWeek icsStringToDay(String dateString) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate date = LocalDate.parse(dateString, fmt);
        DayOfWeek day = date.getDayOfWeek();

        return day;
    }

    /**
     * Converts the ics-formatted string into a LocalTime object.
     */
    private static LocalTime icsStringToTime(String icsTime) {
        int timeInt = Integer.parseInt(icsTime);
        timeInt += 80000; //need to add 8 hours to the ics-formatted version.
        String formattedTime = String.format("%06d", timeInt);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HHmmss");
        LocalTime time = LocalTime.parse(formattedTime, fmt);

        return time;
    }
}
