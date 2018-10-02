package seedu.address.model.person;

/**
 * Represents a timetable that is associated with a person
 *
 */
public class TimeTable {


    private TimeSlot[][] weeklyslots;

    public TimeTable() {
        this.weeklyslots = new TimeSlot[7][24];
    }

    /**
     *  Sets the timeslot as filled
     * @param day
     * @param hour
     */
    public void fillTimeSlot(int day, int hour) {
        weeklyslots[day][hour] = new TimeSlot(day, hour);
        weeklyslots[day][hour].setIsFilled();
    }

    public void removeTimeSlot(int day, int hour) {
        weeklyslots[day][hour].removeIsFilled();
    }

    public boolean checkTimeSlot(int day, int hour) {
        return false;
    }
}
