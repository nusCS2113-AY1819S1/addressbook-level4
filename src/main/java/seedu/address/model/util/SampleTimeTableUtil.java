package seedu.address.model.util;

import java.time.DayOfWeek;
import java.time.LocalTime;

import seedu.address.model.person.TimeSlot;
import seedu.address.model.person.TimeTable;

/**
 * Contains utility methods for populating {@code AddressBook} with sample {@code TimeTable}s
 */
public class SampleTimeTableUtil {
    public static TimeTable getTimeTableJohnDoe() {
        TimeTable toReturn = new TimeTable();

        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.MONDAY, LocalTime.parse("10:00"), LocalTime.parse("12:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.MONDAY, LocalTime.parse("14:00"), LocalTime.parse("16:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.TUESDAY, LocalTime.parse("12:00"), LocalTime.parse("14:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.TUESDAY, LocalTime.parse("14:00"), LocalTime.parse("16:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.WEDNESDAY, LocalTime.parse("09:00"), LocalTime.parse("12:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.WEDNESDAY, LocalTime.parse("14:00"), LocalTime.parse("17:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.THURSDAY, LocalTime.parse("14:00"), LocalTime.parse("16:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.THURSDAY, LocalTime.parse("18:00"), LocalTime.parse("20:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.FRIDAY, LocalTime.parse("10:00"), LocalTime.parse("11:00")));

        return toReturn;
    }

    public static TimeTable getTimeTableJohnRoe() {
        TimeTable toReturn = new TimeTable();

        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.MONDAY, LocalTime.parse("10:00"), LocalTime.parse("12:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.MONDAY, LocalTime.parse("14:00"), LocalTime.parse("16:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.MONDAY, LocalTime.parse("16:00"), LocalTime.parse("18:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.TUESDAY, LocalTime.parse("09:00"), LocalTime.parse("12:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.TUESDAY, LocalTime.parse("12:00"), LocalTime.parse("14:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.TUESDAY, LocalTime.parse("14:00"), LocalTime.parse("16:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.TUESDAY, LocalTime.parse("18:00"), LocalTime.parse("20:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.WEDNESDAY, LocalTime.parse("12:00"), LocalTime.parse("13:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.THURSDAY, LocalTime.parse("14:00"), LocalTime.parse("16:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.THURSDAY, LocalTime.parse("18:00"), LocalTime.parse("20:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.FRIDAY, LocalTime.parse("12:00"), LocalTime.parse("14:00")));

        return toReturn;
    }

    public static TimeTable getTimeTableJohnnyDoe() {
        TimeTable toReturn = new TimeTable();

        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.MONDAY, LocalTime.parse("16:00"), LocalTime.parse("18:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.WEDNESDAY, LocalTime.parse("16:00"), LocalTime.parse("18:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.FRIDAY, LocalTime.parse("14:00"), LocalTime.parse("16:00")));

        return toReturn;
    }

    public static TimeTable getTimeTableBensonMeier() {
        TimeTable toReturn = new TimeTable();

        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.TUESDAY, LocalTime.parse("10:00"), LocalTime.parse("12:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.TUESDAY, LocalTime.parse("14:00"), LocalTime.parse("15:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.TUESDAY, LocalTime.parse("16:00"), LocalTime.parse("18:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.WEDNESDAY, LocalTime.parse("09:00"), LocalTime.parse("12:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.WEDNESDAY, LocalTime.parse("14:00"), LocalTime.parse("16:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.WEDNESDAY, LocalTime.parse("17:00"), LocalTime.parse("18:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.THURSDAY, LocalTime.parse("10:00"), LocalTime.parse("11:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.THURSDAY, LocalTime.parse("14:00"), LocalTime.parse("16:00")));
        toReturn.addTimeSlot(new TimeSlot(DayOfWeek.THURSDAY, LocalTime.parse("18:00"), LocalTime.parse("20:00")));

        return toReturn;
    }
}
