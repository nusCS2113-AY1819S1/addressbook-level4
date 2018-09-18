package seedu.address.model.person;

/**
 * Represents a timetable that is associated with a person
 *
 */
public class TimeTable {

    //Is this even correct syntax, i cries java is hard
    private TimeSlot[][] weeklyslots = new TimeSlot[7][24];

    public TimeTable() {
        //FIX THIS?
        //this.weeklyslots = new TimeSlot weeklyslots;
    }

    public void fillTimeSlot(int day, int hour) {
        weeklyslots[day][hour].setIsFilled();
    }

    public void removeTimeSlot(int day, int hour) {
        weeklyslots[day][hour].removeIsFilled();
    }

    public boolean checkTimeSlot(int day, int hour) {
        return false;
    }
}
