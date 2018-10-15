package seedu.address.commons.util;

import static java.lang.Integer.parseInt;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_IO_ERROR;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Calendar;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.TimeTable;

/**
 * Helps with reading from and writing to ICS files.
 * Only support ICS to-and-from TimeTable Objects.
 * TODO: create a 'serialisable' class to serialise(?) the timetable objects to ics file formats,
 * just like in the jsonUtil class
 */
public class IcsUtil {
    /**
     * Returns the data in the ICS file as a TimeTableObject
     */
    public static TimeTable getTimeTableFromFile(Path file)
            throws CommandException {

        requireNonNull(file);

        if (!FileUtil.isFileExists(file)) {
            throw new CommandException(MESSAGE_IO_ERROR);
        }

        String importstring = "";
        try {
            importstring = FileUtil.readFromFile(file);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_IO_ERROR);
        }

        stringToTimeTableParser(importstring);

        return new TimeTable();
    }

    /**
     * Parses raw string (from ics file) into a timetable object
     */
    private static TimeTable stringToTimeTableParser(String string) throws CommandException {

        TimeTable newTimeTable = new TimeTable();

        String[] lineArray = string.split("\\r\\n|[\\n\\x0B\\x0C\\r\\u0085\\u2028\\u2029]"); //REGEX

        String startdate = "";
        String starttime = "";
        String enddate = "";
        String endtime = "";
        String name = "";

        // this chunky thing goes through the strings from start to end, adding timeslots that it detects.
        for (String line : lineArray) { //must be in sequence, or else will bug
            //TODO: make robust. current implementation can fail explosively.

            String[] splitLine = line.split(":");

            if (splitLine[0].equals("DTSTART")) {
                //start time!
                try {
                    startdate = splitLine[1].substring(0, 8);
                    starttime = splitLine[1].substring(9, 15);
                } catch (IndexOutOfBoundsException e) {
                    throw new CommandException(MESSAGE_IO_ERROR);
                }
            } else if (splitLine[0].equals("DTEND")) {
                //end time!
                try {
                    enddate = splitLine[1].substring(0, 8);
                    endtime = splitLine[1].substring(9, 15);
                } catch (IndexOutOfBoundsException e) {
                    throw new CommandException(MESSAGE_IO_ERROR);
                }
            } else if (splitLine[0].equals("SUMMARY")) {
                //mod name
                name = splitLine[1];
            } else if ((splitLine[0].equals("END")) && (splitLine[1].equals("VEVENT"))) {
                //single event reading over
                int icsStartTime = 0;
                try {
                    icsStartTime = icsTimeToHour(parseInt(starttime));
                } catch (NumberFormatException e) {
                    throw new CommandException(MESSAGE_IO_ERROR);
                }
                int icsDay = 0;

                try {
                    icsDay = icsDateToDay(startdate);
                } catch (CommandException e) {
                    throw new CommandException(MESSAGE_IO_ERROR);
                }
            }

            //newTimeTable.fillTimeSlot(icsDay-1,icsStartTime);
        };
        //TODO: output an actual timetable.
        return new TimeTable();
    }

    /**
     * Converts the date in the ics file into day (1-7).
     */
    private static int icsDateToDay(String dateString) throws CommandException {
        Calendar cal = Calendar.getInstance();
        try {
            cal.set(parseInt(dateString.substring(0, 3)), //year
                    parseInt(dateString.substring(4, 5)), //month
                    parseInt(dateString.substring(6, 7))); //day
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            throw new CommandException(MESSAGE_IO_ERROR);
        }
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return day;
    }

    /**
     * Converts the weird time in the ics file into human hours (0-23).
     */
    private static int icsTimeToHour(int icsTime) throws CommandException {
        int hour = icsTime / 10000 + 8;
        if ((hour > 24) || (hour < 0)) {
            throw new CommandException(MESSAGE_IO_ERROR);
        }
        return hour;
    }
}
