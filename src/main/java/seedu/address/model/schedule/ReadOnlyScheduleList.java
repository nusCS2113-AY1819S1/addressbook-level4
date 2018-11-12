package seedu.address.model.schedule;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an schedule list
 */
public interface ReadOnlyScheduleList {

    /**
     * Returns an unmodifiable view of the schedules list.
     * This list will not contain any duplicate schedules.
     */
    ObservableList<Schedule> getScheduleList();

}
