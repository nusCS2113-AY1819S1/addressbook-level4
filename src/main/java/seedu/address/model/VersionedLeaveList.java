package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

//@@author Hafizuddin-NUS
/**
 * {@code LeaveList} that keeps track of its own history.
 */
public class VersionedLeaveList extends LeaveList {


    private final List<ReadOnlyLeaveList> leaveListStateList;
    private int currentStatePointer;

    public VersionedLeaveList(ReadOnlyLeaveList initialState) {
        super(initialState);

        leaveListStateList = new ArrayList<>();
        leaveListStateList.add(new LeaveList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code LeaveList} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        leaveListStateList.add(new LeaveList(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        leaveListStateList.subList(currentStatePointer + 1, leaveListStateList.size()).clear();
    }
}

