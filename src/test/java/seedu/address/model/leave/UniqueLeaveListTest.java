package seedu.address.model.leave;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalLeave.REQUEST_1;
import static seedu.address.testutil.TypicalLeave.REQUEST_2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.leave.exceptions.DuplicateLeaveException;
import seedu.address.model.leave.exceptions.LeaveNotFoundException;
import seedu.address.testutil.LeaveBuilder;

//@@author Hafizuddin-NUS
public class UniqueLeaveListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueLeaveList uniqueLeaveList = new UniqueLeaveList();

    @Test
    public void contains_nullLeave_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLeaveList.contains(null);
    }

    @Test
    public void contains_leaveNotInList_returnsFalse() {
        assertFalse(uniqueLeaveList.contains(REQUEST_1));
    }

    @Test
    public void contains_leaveInList_returnsTrue() {
        uniqueLeaveList.add(REQUEST_1);
        assertTrue(uniqueLeaveList.contains(REQUEST_1));
    }

    @Test
    public void contains_leaveWithSameIdentityFieldsInList_returnsTrue() {
        uniqueLeaveList.add(REQUEST_1);
        Leave editedLeave = new LeaveBuilder(REQUEST_1).withApproval("APPROVED")
                .build();
        assertTrue(uniqueLeaveList.contains(editedLeave));
    }

    @Test
    public void add_nullLeave_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLeaveList.add(null);
    }

    @Test
    public void add_duplicateLeave_throwsDuplicatePersonException() {
        uniqueLeaveList.add(REQUEST_1);
        thrown.expect(DuplicateLeaveException.class);
        uniqueLeaveList.add(REQUEST_1);
    }

    @Test
    public void setLeave_nullTargetLeave_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLeaveList.setRequest(null, REQUEST_1);
    }

    @Test
    public void setLeave_nullEditedLeave_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLeaveList.setRequest(REQUEST_1, null);
    }

    @Test
    public void setLeave_targetLeaveNotInList_throwsLeaveNotFoundException() {
        thrown.expect(LeaveNotFoundException.class);
        uniqueLeaveList.setRequest(REQUEST_1, REQUEST_1);
    }

    @Test
    public void setPerson_editedLeaveIsSameLeave_success() {
        uniqueLeaveList.add(REQUEST_1);
        uniqueLeaveList.setRequest(REQUEST_1, REQUEST_1);
        UniqueLeaveList expectedUniqueLeaveList = new UniqueLeaveList();
        expectedUniqueLeaveList.add(REQUEST_1);
        assertEquals(expectedUniqueLeaveList, uniqueLeaveList);
    }

    @Test
    public void setLeave_editedLeaveHasSameIdentity_success() {
        uniqueLeaveList.add(REQUEST_1);
        Leave editedLeave = new LeaveBuilder(REQUEST_1).withApproval("REJECTED")
                .build();
        uniqueLeaveList.setRequest(REQUEST_1, editedLeave);
        UniqueLeaveList expectedUniqueLeaveList = new UniqueLeaveList();
        expectedUniqueLeaveList.add(editedLeave);
        assertEquals(expectedUniqueLeaveList, uniqueLeaveList);
    }

    @Test
    public void setLeave_editedLeaveHasDifferentIdentity_success() {
        uniqueLeaveList.add(REQUEST_1);
        uniqueLeaveList.setRequest(REQUEST_1, REQUEST_2);
        UniqueLeaveList expectedUniqueLeaveList = new UniqueLeaveList();
        expectedUniqueLeaveList.add(REQUEST_2);
        assertEquals(expectedUniqueLeaveList, uniqueLeaveList);
    }

    @Test
    public void setLeave_editedLeaveHasNonUniqueIdentity_throwsDuplicateLeaveException() {
        uniqueLeaveList.add(REQUEST_1);
        uniqueLeaveList.add(REQUEST_2);
        thrown.expect(DuplicateLeaveException.class);
        uniqueLeaveList.setRequest(REQUEST_1, REQUEST_2);
    }

    @Test
    public void remove_nullLeave_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLeaveList.remove(null);
    }

    @Test
    public void remove_leaveDoesNotExist_throwsLeaveNotFoundException() {
        thrown.expect(LeaveNotFoundException.class);
        uniqueLeaveList.remove(REQUEST_1);
    }

    @Test
    public void remove_existingLeave_removesLeave() {
        uniqueLeaveList.add(REQUEST_1);
        uniqueLeaveList.remove(REQUEST_1);
        UniqueLeaveList expectedUniqueLeaveList = new UniqueLeaveList();
        assertEquals(expectedUniqueLeaveList, uniqueLeaveList);
    }

    @Test
    public void setLeaves_nullUniqueLeaveList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLeaveList.setRequests((UniqueLeaveList) null);
    }

    @Test
    public void setLeaves_uniqueLeaveList_replacesOwnListWithProvidedUniqueLeaveList() {
        uniqueLeaveList.add(REQUEST_1);
        UniqueLeaveList expectedUniqueLeaveList = new UniqueLeaveList();
        expectedUniqueLeaveList.add(REQUEST_2);
        uniqueLeaveList.setRequests(expectedUniqueLeaveList);
        assertEquals(expectedUniqueLeaveList, uniqueLeaveList);
    }

    @Test
    public void setLeaves_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLeaveList.setRequests((List<Leave>) null);
    }

    @Test
    public void setLeaves_list_replacesOwnListWithProvidedList() {
        uniqueLeaveList.add(REQUEST_1);
        List<Leave> leaveList = Collections.singletonList(REQUEST_2);
        uniqueLeaveList.setRequests(leaveList);
        UniqueLeaveList expectedUniqueLeaveList = new UniqueLeaveList();
        expectedUniqueLeaveList.add(REQUEST_2);
        assertEquals(expectedUniqueLeaveList, uniqueLeaveList);
    }

    @Test
    public void setLeaves_listWithDuplicateLeaves_throwsDuplicateLeaveException() {
        List<Leave> listWithDuplicateLeaves = Arrays.asList(REQUEST_1, REQUEST_1);
        thrown.expect(DuplicateLeaveException.class);
        uniqueLeaveList.setRequests(listWithDuplicateLeaves);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueLeaveList.asUnmodifiableObservableList().remove(0);
    }
}
