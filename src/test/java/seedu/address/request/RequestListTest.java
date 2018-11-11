package seedu.address.request;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.request.TypicalRequests.REQUEST_STUDENT_A;
import static seedu.address.request.TypicalRequests.getTypicalRequestList;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.request.ReadOnlyRequests;
import seedu.address.request.Request;
import seedu.address.request.RequestList;

import java.util.Collection;
import java.util.Collections;



/**
 * RequestListTest is similar to BookInventoryTest.
 * However, no duplicate request test is made because requests can be duplicated.
 */
public class RequestListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final RequestList requestList = new RequestList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), requestList.getRequestList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        requestList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyRequestList_replacesData() {
        RequestList newData = getTypicalRequestList();
        requestList.resetData(newData);
        assertEquals(newData, requestList);
    }


    @Test
    public void hasRequest_nullRequest_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        requestList.hasRequest(null);
    }

    @Test
    public void hasRequest_requestNotInRequestList_returnsFalse() {
        assertFalse(requestList.hasRequest(REQUEST_STUDENT_A));
    }

    @Test
    public void hasRequest_requestInRequestList_returnsTrue() {
        requestList.addRequest(REQUEST_STUDENT_A);
        assertTrue(requestList.hasRequest(REQUEST_STUDENT_A));
    }


    @Test
    public void getBookList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        requestList.getRequestList().remove(0);
    }

    /**
     * A stub ReadOnlyBookInventory whose books list can violate interface constraints.
     */
    private static class RequestListStub implements ReadOnlyRequests {
        private final ObservableList<Request> requests = FXCollections.observableArrayList();

        RequestListStub(Collection<Request> requests) {
            this.requests.setAll(requests);
        }

        @Override
        public ObservableList<Request> getRequestList() {
            return requests;
        }
    }

}
