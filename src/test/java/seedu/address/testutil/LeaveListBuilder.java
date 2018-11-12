package seedu.address.testutil;

import seedu.address.model.LeaveList;
import seedu.address.model.leave.Leave;

//@@author Hafizuddin-NUS
/**
 * A utility class to help with building LeaveList objects.
 * Example usage: <br>
 *     {@code LeaveList ll = new LeaveListBuilder().withLeave("REQUEST_1", "REQUEST_1").build();}
 */
public class LeaveListBuilder {
    private LeaveList leaveList;

    public LeaveListBuilder() {
        leaveList = new LeaveList();
    }

    public LeaveListBuilder(LeaveList leaveList) {
        this.leaveList = leaveList;
    }

    /**
     * Adds a new {@code Leave} to the {@code LeaveList} that we are building.
     */
    public LeaveListBuilder withLeave(Leave leave) {
        leaveList.addRequest(leave);
        return this;
    }

    public LeaveList build() {
        return leaveList;
    }
}
