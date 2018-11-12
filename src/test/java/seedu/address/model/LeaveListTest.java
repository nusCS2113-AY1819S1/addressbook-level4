package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalLeave.REQUEST_1;
import static seedu.address.testutil.TypicalLeave.getTypicalLeaveList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.exceptions.DuplicateLeaveException;
import seedu.address.testutil.LeaveBuilder;

//@@author Hafizuddin-NUS
public class LeaveListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final LeaveList leaveList = new LeaveList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), leaveList.getRequestList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        leaveList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyLeaveList_replacesData() {
        LeaveList newData = getTypicalLeaveList();
        leaveList.resetData(newData);
        assertEquals(newData, leaveList);
    }

    @Test
    public void getLeaveList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        leaveList.getRequestList().remove(0);
    }

    @Test
    public void resetData_withDuplicateLeaves_throwsDuplicateLeaveException() {
        // Two leave with the same date and identity fields
        Leave editedLeave = new LeaveBuilder(REQUEST_1).withNric("S1234567A").withDate("01/02/2020")
                .build();
        List<Leave> newLeaves = Arrays.asList(REQUEST_1, editedLeave);
        LeaveListStub newData = new LeaveListStub(newLeaves);

        thrown.expect(DuplicateLeaveException.class);
        leaveList.resetData(newData);
    }

    /**
     * A stub ReadOnlyLeaveList whose leaves list can violate interface constraints.
     */
    private static class LeaveListStub implements ReadOnlyLeaveList {
        private final ObservableList<Leave> leaves = FXCollections.observableArrayList();

        LeaveListStub(Collection<Leave> leaves) {
            this.leaves.setAll(leaves);
        }

        @Override
        public ObservableList<Leave> getRequestList() {
            return leaves;
        }
    }




}
