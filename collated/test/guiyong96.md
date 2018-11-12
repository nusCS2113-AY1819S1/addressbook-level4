# guiyong96
###### \java\seedu\address\logic\parser\DifferentiatingParserTest.java
``` java
package seedu.address.logic.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.CommandHistory;



public class DifferentiatingParserTest {

    private final DifferentiatingParser parser = new DifferentiatingParser();

    private String [] string = new String[]{"default"};

    private CommandHistory commandHistory;

    private String prev = "random";

    //User input is correct for RequestList.
    @Test
    public void parseInput_valid_toggleRequestCommand() {
        string[0] = "togglerequest";
        assertTrue(parser.parseInput(string, prev, commandHistory));
    }

    @Test
    public void parseInput_valid_deleteRequestCommand() {
        string[0] = "deleterequest";
        assertTrue(parser.parseInput(string, prev, commandHistory));
    }

    //User input is correct for RequestList, accepted by DiceCoefficient algo range.
    @Test
    public void parseInput_validSpellingError_requestCommand() {
        string[0] = "reques";
        assertTrue(parser.parseInput(string, prev, commandHistory));
    }

    @Test
    public void parseInput_validSpellingError_deleteRequestCommand() {
        string[0] = "deletereques";
        assertTrue(parser.parseInput(string, prev, commandHistory));
    }

    @Test
    public void parseInput_validSpellingError_toggleRequestCommand() {
        string[0] = "togglereques";
        assertTrue(parser.parseInput(string, prev, commandHistory));
    }

    //User input is independent of command history.
    @Test
    public void parseInput_validArgs_nullCommandHistory() {
        string[0] = "request";
        assertTrue(parser.parseInput(string, prev, null));
    }

    //User input is independent of prev input unless command is Undo.
    @Test
    public void parseInput_toggleRequestCommand_changeInPrev() {
        string[0] = "togglerequest";
        prev = "request";
        assertTrue(parser.parseInput(string, prev, commandHistory));
    }

    @Test
    public void parseInput_undoCommand_prevIsRequest() {
        string[0] = "undo";
        prev = "request";
        assertTrue(parser.parseInput(string, prev, commandHistory));
    }

    @Test
    public void parseInput_undoCommand_prevIsNotRequest() {
        string[0] = "undo";
        prev = "edit";
        assertFalse(parser.parseInput(string, prev, commandHistory));
    }

    //Too much spelling error. Not detected.
    @Test
    public void parseInput_invalidSpellingError_toggleRequestCommand() {
        string[0] = "trequoggllee";
        assertFalse(parser.parseInput(string, prev, commandHistory));
    }


    @Test
    public void parseInput_invalidArgs_deleteCommand() {
        string[0] = "delete";
        assertFalse(parser.parseInput(string, prev, commandHistory));
    }

    @Test
    public void parseInput_invalidSpellingError_deleteCommand() {
        string[0] = "dellletee";
        assertFalse(parser.parseInput(string, prev, commandHistory));
    }

    @Test
    public void parseInput_invalidArgs_randomCommand() {
        string[0] = "shout";
        assertFalse(parser.parseInput(string, prev, commandHistory));
    }

}
```
###### \java\seedu\address\logic\parser\SimilarityParserTest.java
``` java
package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.logic.commands.CommandList;

public class SimilarityParserTest {

    private final SimilarityParser parser = new SimilarityParser();
    private CommandList commandList = new CommandList();
    private ArrayList<String> commands = commandList.getCommandList();
    //Spelling Errors are accepted in DiceCoefficient algorithm.
    @Test
    public void performSimilarityCheck_validSpellingError_addCommand() {
        assertEquals(parser.performSimilarityCheck("ad", commands), "add");
    }

    @Test
    public void performSimilarityCheck_validSpellingError_editCommand() {
        assertEquals(parser.performSimilarityCheck("edi", commands), "edit");
    }

    //Applies for owners.
    @Test
    public void performSimilarityCheck_validSpellingError_toggleRequestsCommand() {
        assertEquals(parser.performSimilarityCheck("togglereq", commands), "togglerequests");
    }

    //Applies for accountants.
    @Test
    public void performSimilarityCheck_validSpellingError_viewStatisticsCommand() {
        assertEquals(parser.performSimilarityCheck("stat", commands), "stats");
    }

    //Invalid in such scenario is acceptable because the command will be accepted by the BookInventoryParser beforehand.
    @Test
    public void performSimilarityCheck_invalid_similarCommand() {
        assertNotEquals(parser.performSimilarityCheck("togglerequests", commands), "togglerequests");
    }

    //Too much spelling error. Not detected. Returns empty string.
    @Test
    public void performSimilarityCheck_invalid_toggleRequestsCommand() {
        assertEquals(parser.performSimilarityCheck("toggReq", commands), "");
    }

    //Random input. Returns empty string.
    @Test
    public void performSimilarityCheck_invalid_randomCommand() {
        assertEquals(parser.performSimilarityCheck("t141n", commands), "");
    }
}
```
###### \java\seedu\address\request\RequestBuilder.java
``` java
package seedu.address.request;

import seedu.address.model.book.Isbn;
import seedu.address.model.book.Quantity;

/**
 * A utility class to help with building Book objects.
 */
public class RequestBuilder {


    public static final String DEFAULT_ISBN = "978-3-16-148410-0";
    public static final String DEFAULT_QUANTITY = "1";
    public static final String DEFAULT_EMAIL = "test@yahoo.com.sg";

    private Email email;
    private Isbn isbn;
    private Quantity quantity;

    public RequestBuilder() {
        isbn = new Isbn(DEFAULT_ISBN);
        quantity = new Quantity(DEFAULT_QUANTITY);
        email = new Email(DEFAULT_EMAIL);
    }

    /**
     * Initializes the BookBuilder with the data of {@code bookToCopy}.
     */
    public RequestBuilder(Request requestToCopy) {
        isbn = requestToCopy.getIsbn();
        quantity = requestToCopy.getQuantity();
        email = requestToCopy.getEmail();
    }

    /**
     * Sets the {@code Name} of the {@code Book} that we are building.
     */
    public RequestBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code Book} that we are building.
     */
    public RequestBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    /**
     * Sets the {@code Isbn} of the {@code Book} that we are building.
     */
    public RequestBuilder withIsbn(String isbn) {
        this.isbn = new Isbn(isbn);
        return this;
    }

    public Request build() {
        return new Request(isbn, email, quantity);
    }

}
```
###### \java\seedu\address\request\RequestCommandTest.java
``` java
package seedu.address.request;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.request.requestcommands.RequestCommand;
import seedu.address.request.requestmodel.RequestModel;




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

    private void assertTrue(boolean equals) {
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
```
###### \java\seedu\address\request\RequestListBuilder.java
``` java

package seedu.address.request;

/**
 * A utility class to help with building RequestList objects.
 * Example usage: <br>
 *     {@code RequestList rl = new RequestListBuilder().withRequest(...).build();}
 */
public class RequestListBuilder {

    private RequestList requestList;

    public RequestListBuilder() {
        requestList = new RequestList();
    }

    public RequestListBuilder(RequestList requestList) {
        this.requestList = requestList;
    }

    /**
     * Adds a new {@code Request} to the {@code RequestList} that we are building.
     */
    public RequestListBuilder withRequest(Request request) {
        requestList.addRequest(request);
        return this;
    }

    public RequestList build() {
        return requestList;
    }
}
```
###### \java\seedu\address\request\RequestListTest.java
``` java
package seedu.address.request;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.request.TypicalRequests.REQUEST_STUDENT_A;
import static seedu.address.request.TypicalRequests.getTypicalRequestList;

import java.util.Collection;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
```
###### \java\seedu\address\request\TypicalRequests.java
``` java
package seedu.address.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A utility class containing a list of {@code Book} objects to be used in tests.
 */
public class TypicalRequests {

    public static final Request REQUEST_STUDENT_A = new RequestBuilder()
            .withIsbn("9780748137992")
            .withQuantity("5")
            .withEmail("testing3@gmail.com").build();
    public static final Request REQUEST_STUDENT_B = new RequestBuilder()
            .withIsbn("9781401309572")
            .withQuantity("10")
            .withEmail("testing1@gmail.com").build();

    private TypicalRequests() {} // prevents instantiation

    /**
     * Returns an {@code RequestList} with all the typical requests.
     */
    public static RequestList getTypicalRequestList() {
        RequestList rl = new RequestList();
        for (Request request : getTypicalRequests()) {
            rl.addRequest(request);
        }
        return rl;
    }

    public static List<Request> getTypicalRequests() {
        return new ArrayList<>(Arrays.asList(REQUEST_STUDENT_A, REQUEST_STUDENT_B));
    }
}
```
