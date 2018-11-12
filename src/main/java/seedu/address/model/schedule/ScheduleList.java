package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the schedule-list level
 * Duplicates are not allowed (by .isSameSchedule comparison)
 */
public class ScheduleList implements ReadOnlyScheduleList {

    private final UniqueScheduleList schedules;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        schedules = new UniqueScheduleList();
    }

    public ScheduleList() {}

    /**
     * Creates an ScheduleList using the Schedules in the {@code toBeCopied}
     */
    public ScheduleList(ReadOnlyScheduleList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the schedule list with {@code schedules}.
     * {@code schedules} must not contain duplicate schedules.
     */
    public void setSchedules(List<Schedule> schedules) {
        this.schedules.setSchedules(schedules);
    }

    /**
     * Resets the existing data of this {@code ScheduleList} with {@code newData}.
     */
    public void resetData(ReadOnlyScheduleList newData) {
        requireNonNull(newData);
        setSchedules(newData.getScheduleList());
    }

    //// schedule-level operations

    /**
     * Returns true if a schedule with the same identity as {@code schedule} exists in the schedule list.
     */
    public boolean hasSchedule(Schedule schedule) {
        requireNonNull(schedule);
        return schedules.contains(schedule);
    }

    /**
     * Adds a schedule to the schedule list.
     * The schedule must not already exist in the schedule list.
     */
    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    /**
     * Replaces the given schedule {@code schedule} in the list with {@code editedSchedule}.
     * {@code schedule} must exist in the schedule list.
     */
    public void updateSchedule(Schedule schedule, Schedule editedSchedule) {
        requireNonNull(editedSchedule);
        schedules.setSchedule(schedule, editedSchedule);
    }

    /**
     * Removes {@code key} from this {@code ScheduleList}.
     * {@code key} must exist in the schedule list.
     */
    public void removeSchedule(Schedule key) {
        schedules.remove(key);
    }

    /**
     * Sort Schedules within CHRS by employeeId
     */
    public void sortSchedulesBy() {
        schedules.sortByEmployeeId();
    }

    //// util methods

    @Override
    public String toString() {
        return schedules.asUnmodifiableObservableList().size() + " schedules";
        // TODO: refine later
    }

    @Override
    public ObservableList<Schedule> getScheduleList() {
        return schedules.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleList // instanceof handles nulls
                && schedules.equals(((ScheduleList) other).schedules));
    }

    @Override
    public int hashCode() {
        return schedules.hashCode();
    }
}
