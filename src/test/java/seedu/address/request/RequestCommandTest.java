package seedu.address.request;

import javafx.collections.ObservableList;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.request.requestcommands.RequestCommand;
import seedu.address.request.requestmodel.RequestModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.*;

public class RequestCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullRequest_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RequestCommand(null);
    }

    @Test
    public void execute_requestAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingRequestAdded modelStub = new ModelStubAcceptingRequestAdded();
        Request validRequest = new RequestBuilder().build();

        CommandResult commandResult = new RequestCommand(validRequest).execute(modelStub, commandHistory);

        assertEquals(String.format(RequestCommand.MESSAGE_SUCCESS, validRequest), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validRequest), modelStub.RequestsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        Request Student_A = new RequestBuilder().withIsbn("978-1-86197-876-9").build();
        Request Student_B = new RequestBuilder().withIsbn("978-3-16-148410-0").build();
        RequestCommand addAliceCommand = new RequestCommand(Student_A);
        RequestCommand addBobCommand = new RequestCommand(Student_B);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        RequestCommand addAliceCommandCopy = new RequestCommand(Student_A);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different Request -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class RequestModelStub implements RequestModel {
        @Override
        public void addRequest(Request Request) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyRequests newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyRequests getRequestList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasRequest(Request Request) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRequest(Request target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateRequest(Request target, Request editedRequest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Request> getFilteredRequestList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRequestList(Predicate<Request> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoRequests() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoRequests() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoRequests() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoRequests() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitRequests() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single Request.
     */
    private class ModelStubWithRequest extends RequestModelStub {
        private final Request Request;

        ModelStubWithRequest(Request Request) {
            requireNonNull(Request);
            this.Request = Request;
        }

        @Override
        public boolean hasRequest(Request Request) {
            requireNonNull(Request);
            return this.Request.isSameRequest(Request);
        }
    }

    /**
     * A Model stub that always accept the Request being added.
     */
    private class ModelStubAcceptingRequestAdded extends RequestModelStub {
        final ArrayList<Request> RequestsAdded = new ArrayList<>();

        @Override
        public boolean hasRequest(Request Request) {
            requireNonNull(Request);
            return RequestsAdded.stream().anyMatch(Request::isSameRequest);
        }

        @Override
        public void addRequest(Request Request) {
            requireNonNull(Request);
            RequestsAdded.add(Request);
        }

        @Override
        public void commitRequests() {
            // called by {@code RequestCommand#execute()}
        }

        @Override
        public ReadOnlyRequests getRequestList() {
            return new RequestList();
        }
    }

}
