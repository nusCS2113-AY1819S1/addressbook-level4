package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.LeaveList;
import seedu.address.model.leave.Leave;

//@@author Hafizuddin-NUS
/**
 * A utility class containing a list of {@code Leave} objects to be used in tests.
 */
public class TypicalLeave {

    public static final Leave REQUEST_1 = new LeaveBuilder().withNric("S1234567A").withDate("01/02/2020")
            .withApproval("PENDING").withPriorityLevel(1).build();
    public static final Leave REQUEST_2 = new LeaveBuilder().withNric("S1234597A").withDate("01/10/2020")
            .withApproval("PENDING").withPriorityLevel(2).build();
    public static final Leave REQUEST_5 = new LeaveBuilder().withNric("T2457846E").withDate("02/02/2020")
            .withApproval("APPROVED").withPriorityLevel(0).build();
    public static final Leave REQUEST_6 = new LeaveBuilder().withNric("T2457846E").withDate("03/02/2020")
            .withApproval("REJECTED").withPriorityLevel(0).build();
    public static final Leave REQUEST_7 = new LeaveBuilder().withNric("T2457888E").withDate("03/02/2020")
            .withApproval("REJECTED").withPriorityLevel(0).build();

    //Manually add
    public static final Leave REQUEST_3 = new LeaveBuilder().withNric("S1234591A").withDate("01/10/2020")
            .withApproval("PENDING").withPriorityLevel(3).build();
    public static final Leave REQUEST_4 = new LeaveBuilder().withNric("S1234591Z").withDate("01/10/2020")
            .withApproval("PENDING").withPriorityLevel(3).build();


    private TypicalLeave() {} // prevents instantiation

    /**
     * Returns an {@code LeaveList} with all the typical leaves.
     */
    public static LeaveList getTypicalLeaveList() {
        LeaveList leaveList = new LeaveList();
        for (Leave leave : getTypicalLeaves()) {
            leaveList.addRequest(leave);
        }
        return leaveList;
    }

    public static List<Leave> getTypicalLeaves() {
        return new ArrayList<>(Arrays.asList(REQUEST_1, REQUEST_2, REQUEST_5, REQUEST_6, REQUEST_7));
    }

}
