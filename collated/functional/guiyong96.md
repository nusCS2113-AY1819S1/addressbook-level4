# guiyong96
###### \java\seedu\address\logic\DiceCoefficient.java
``` java
package seedu.address.logic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Sets the degree of comparisons between words
 */
public class DiceCoefficient {

    /**
     * Adapted from https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Dice%27s_coefficient
     */
    //Note that this implementation is case-sensitive!
    public static double diceCoefficient(String s1, String s2) {
        Set<String> nx = new HashSet<String>();
        Set<String> ny = new HashSet<String>();

        for (int i = 0; i < s1.length() - 1; i++) {
            char x1 = s1.charAt(i);
            char x2 = s1.charAt(i + 1);
            String tmp = "" + x1 + x2;
            nx.add(tmp);
        }
        for (int j = 0; j < s2.length() - 1; j++) {
            char y1 = s2.charAt(j);
            char y2 = s2.charAt(j + 1);
            String tmp = "" + y1 + y2;
            ny.add(tmp);
        }

        Set<String> intersection = new HashSet<String>(nx);
        intersection.retainAll(ny);
        double totcombigrams = intersection.size();

        return (2 * totcombigrams) / (nx.size() + ny.size());
    }

    /**
     * Here's an optimized version of the dice coefficient calculation. It takes
     * advantage of the fact that a bigram of 2 chars can be stored in 1 int, and
     * applies a matching algorithm of O(n*log(n)) instead of O(n*n).
     *
     * <p>Note that, at the time of writing, this implementation differs from the
     * other implementations on this page. Where the other algorithms incorrectly
     * store the generated bigrams in a set (discarding duplicates), this
     * implementation actually treats multiple occurrences of a bigram as unique.
     * The correctness of this behavior is most easily seen when getting the
     * similarity between "GG" and "GGGGGGGG", which should obviously not be 1.
     *
     * @author Jelle Fresen
     * @param s The first string
     * @param t The second String
     * @return The dice coefficient between the two input strings. Returns 0 if one
     *         or both of the strings are {@code null}. Also returns 0 if one or both
     *         of the strings contain less than 2 characters and are not equal.
     */
    public static double diceCoefficientOptimized(String s, String t) {
        // Verifying the input:
        if (s == null || t == null) {
            return 0;
        }
        // Quick check to catch identical objects:
        if (s.equals(t)) {
            return 1;
        }
        // avoid exception for single character searches
        if (s.length() < 2 || t.length() < 2) {
            return 0;
        }

        // Create the bigrams for string s:
        final int n = s.length() - 1;
        final int[] sPairs = new int[n];
        for (int i = 0; i <= n; i++) {
            if (i == 0) {
                sPairs[i] = s.charAt(i) << 16;
            } else if (i == n) {
                sPairs[i - 1] |= s.charAt(i);
            } else {
                sPairs[i] = (sPairs[i - 1] |= s.charAt(i)) << 16;
            }
        }

        // Create the bigrams for string t:
        final int m = t.length() - 1;
        final int[] tPairs = new int[m];
        for (int i = 0; i <= m; i++) {
            if (i == 0) {
                tPairs[i] = t.charAt(i) << 16;
            } else if (i == m) {
                tPairs[i - 1] |= t.charAt(i);
            } else {
                tPairs[i] = (tPairs[i - 1] |= t.charAt(i)) << 16;
            }
        }

        // Sort the bigram lists:
        Arrays.sort(sPairs);
        Arrays.sort(tPairs);

        // Count the matches:
        int matches = 0;
        int i = 0;
        int j = 0;
        while (i < n && j < m) {
            if (sPairs[i] == tPairs[j]) {
                matches += 2;
                i++;
                j++;
            } else if (sPairs[i] < tPairs[j]) {
                i++;
            } else {
                j++;
            }
        }
        return (double) matches / (n + m);
    }
}
```
###### \java\seedu\address\logic\parser\DifferentiatingParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.logic.DiceCoefficient.diceCoefficient;

import seedu.address.logic.CommandHistory;
import seedu.address.request.requestcommands.DeleteRequestCommand;
import seedu.address.request.requestcommands.RequestCommand;
import seedu.address.request.requestcommands.ToggleRequestCommand;
import seedu.address.request.requestcommands.UndoRequestCommand;

/**
 * Differentiating Parser separates commands going to the BookInventoryParser and RequestListParser.
 */
public class DifferentiatingParser {

    //Different threshold tested for higher accuracy.
    private static final double DICE_COEFFICIENT_THRESHOLD = 0.5;
    private static final double DICE_COEFFICIENT_ADJUSTED_THRESHOLD = 0.7;
    private static final String requestCommand = "request";

    /**
     *  parseInput takes in three args, the trimmed commands, the prev command (To check if undo/redo belongs in
     *  requestlist or in bookinventory), and the command history.
     *  It returns true when the input is meant for requestlistparser.
     */
    public boolean parseInput (String[] string, String prev, CommandHistory history) {
        if (diceCoefficient(string[0], RequestCommand.COMMAND_WORD) > DICE_COEFFICIENT_ADJUSTED_THRESHOLD
                || diceCoefficient(string[0], DeleteRequestCommand.COMMAND_WORD) > DICE_COEFFICIENT_ADJUSTED_THRESHOLD
                || diceCoefficient(string[0], ToggleRequestCommand.COMMAND_WORD) > DICE_COEFFICIENT_ADJUSTED_THRESHOLD
                || ((diceCoefficient(string[0], UndoRequestCommand.COMMAND_WORD) > DICE_COEFFICIENT_THRESHOLD)
                && prev.equals(requestCommand))) {
            return true;
        } else {
            return false;
        }
    }

    public String getPreviousCommand (CommandHistory commandHistory) {
        if (!commandHistory.getHistory().isEmpty()) {
            return commandHistory.getHistory().get(commandHistory.getHistory().size() - 1);
        } else {
            return "";
        }
    }
}
```
###### \java\seedu\address\logic\parser\SimilarityParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.logic.DiceCoefficient.diceCoefficient;

import java.util.ArrayList;

/**
 * Similarity parser takes in the UserInput and compares it with the commandList
 * to detect how similar the input is to the command, given that the input is NOT the command.
 */
public class SimilarityParser {
    private static final double DICE_COEFFICIENT_THRESHOLD = 0.5;
    /**
     * performSimilarityCheck takes in two args, the userInput, and the commandList in arrays.
     * If a similarity is found, returns the command most similar to the userInput.
     */
    public String performSimilarityCheck (String userInput, ArrayList<String> commandList) {
        for (String command : commandList) {
            if (diceCoefficient(command, userInput) > DICE_COEFFICIENT_THRESHOLD) {
                return command;
            }
        }
        return "";
    }
}
```
###### \java\seedu\address\request\Request.java
``` java
package seedu.address.request;

import java.util.Objects;

import seedu.address.model.book.Isbn;
import seedu.address.model.book.Quantity;

/**
 * Represents a Request in the BookInventory.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Request {

    // Identity fields
    private final Isbn isbn;
    private final Email email;
    private final Quantity quantity;

    public Request(Isbn isbn, Email email, Quantity quantity) {
        this.isbn = isbn;
        this.email = email;
        this.quantity = quantity;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    public Email getEmail() {
        return email;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    /**
     * compares request made with existing request
     * @param otherRequest request made by the user
     * @return boolean by comparing results
     */
    public boolean isSameRequest(Request otherRequest) {
        if (otherRequest == this) {
            return true;
        }

        return otherRequest != null
                && otherRequest.getEmail().equals(getEmail())
                && (otherRequest.getIsbn().equals(getIsbn()) || otherRequest.getQuantity().equals(getQuantity()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Request request = (Request) o;
        return Objects.equals(isbn, request.isbn)
                && Objects.equals(email, request.email)
                && Objects.equals(quantity, request.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, email, quantity);
    }

    @Override
    public String toString() {
        return "Request{"
                + "isbn=" + isbn
                + ", email=" + email
                + ", quantity=" + quantity
                + '}';
    }
}
```
###### \java\seedu\address\request\requestcommands\DeleteRequestCommand.java
``` java
package seedu.address.request.requestcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.request.Request;
import seedu.address.request.requestmodel.RequestModel;

/**
 * Deletes a request identified using its displayed index from the RequestList.
 */
public class DeleteRequestCommand extends CommandSecondary {

    public static final String COMMAND_WORD = "deleterequest";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the request identified by the index number used in the displayed request list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_REQUEST_SUCCESS = "Request deleted successfully!";

    private final Index targetIndex;

    public DeleteRequestCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(RequestModel requestModel, CommandHistory history) throws CommandException {
        requireNonNull(requestModel);
        List<Request> lastShownList = requestModel.getFilteredRequestList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }

        Request requestToDelete = lastShownList.get(targetIndex.getZeroBased());
        requestModel.deleteRequest(requestToDelete);
        requestModel.commitRequests();
        return new CommandResult(String.format(MESSAGE_DELETE_REQUEST_SUCCESS, requestToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteRequestCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteRequestCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\request\requestcommands\RedoRequestCommand.java
``` java
package seedu.address.request.requestcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.request.requestmodel.RequestModel.PREDICATE_SHOW_ALL_REQUESTS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.request.requestmodel.RequestModel;

/**
 * Reverts the {@code model}'s RequestList to its previously undone state.
 */
public class RedoRequestCommand extends CommandSecondary {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(RequestModel requestModel, CommandHistory history) throws CommandException {
        requireNonNull(requestModel);

        if (!requestModel.canRedoRequests()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        requestModel.redoRequests();
        requestModel.updateFilteredRequestList(PREDICATE_SHOW_ALL_REQUESTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\request\requestcommands\RequestCommand.java
``` java
package seedu.address.request.requestcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.request.Request;
import seedu.address.request.requestmodel.RequestModel;

/**
 * Adds a request to the BookInventory.
 */
public class RequestCommand extends CommandSecondary {

    public static final String COMMAND_WORD = "request";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Request a book. "
            + "Parameters: "
            + PREFIX_ISBN + "ISBN "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_EMAIL + "EMAIL "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ISBN + "9783161484100 "
            + PREFIX_QUANTITY + "42 "
            + PREFIX_EMAIL + "johnd@example.com ";

    public static final String MESSAGE_SUCCESS = "New request added: %1$s";
    public static final String COMMAND_SYNTAX = COMMAND_WORD
            + " "
            + PREFIX_ISBN + " "
            + PREFIX_QUANTITY + " "
            + PREFIX_EMAIL + " ";

    private final Request toAdd;

    /**
     * Creates an RequestCommand to add the specified {@code Request}
     */
    public RequestCommand(Request request) {
        requireNonNull(request);
        toAdd = request;
    }

    @Override
    public CommandResult execute(RequestModel requestModel, CommandHistory history) throws CommandException {
        requireNonNull(requestModel);

        //if (requestModel.hasRequest(toAdd)) {
        //    throw new CommandException(MESSAGE_DUPLICATE_BOOK);
        //}

        requestModel.addRequest(toAdd);
        requestModel.commitRequests();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RequestCommand // instanceof handles nulls
                && toAdd.equals(((RequestCommand) other).toAdd));
    }
}
```
###### \java\seedu\address\request\requestcommands\ToggleRequestCommand.java
``` java
package seedu.address.request.requestcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.events.model.RequestPanelChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.request.requestmodel.RequestModel;

/**
 * Toggles the request panel.
 * If request panel initially shows data, data will be hidden afterwards, and vice versa.
 */
public class ToggleRequestCommand extends CommandSecondary {

    public static final String COMMAND_WORD = "togglerequests";

    public static final String MESSAGE_SUCCESS = "Request panel toggled.";

    @Override
    public CommandResult execute(RequestModel requestModel, CommandHistory history) {
        requireNonNull(requestModel);
        raise(new RequestPanelChangedEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\request\requestcommands\UndoRequestCommand.java
``` java
package seedu.address.request.requestcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.request.requestmodel.RequestModel.PREDICATE_SHOW_ALL_REQUESTS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.request.requestmodel.RequestModel;

/**
 * Reverts the {@code model}'s RequestList to its previous state.
 */
public class UndoRequestCommand extends CommandSecondary {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(RequestModel requestModel, CommandHistory history) throws CommandException {
        requireNonNull(requestModel);

        if (!requestModel.canUndoRequests()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        requestModel.undoRequests();
        requestModel.updateFilteredRequestList(PREDICATE_SHOW_ALL_REQUESTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\request\RequestList.java
``` java
package seedu.address.request;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameRequest comparison)
 */
public class RequestList implements ReadOnlyRequests {

    private final UniqueRequestList requestList;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        requestList = new UniqueRequestList();
    }

    public RequestList() {}

    /**
     * Creates an RequestList using the Persons in the {@code toBeCopied}
     */
    public RequestList(ReadOnlyRequests toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the request list with {@code requestList}.
     * {@code requestList} must not contain duplicate requestList.
     */
    public void setRequestList(List<Request> requestList) {
        this.requestList.setRequests(requestList);
    }

    /**
     * Resets the existing data of this {@code RequestList} with {@code newData}.
     */
    public void resetData(ReadOnlyRequests newData) {
        requireNonNull(newData);

        setRequestList(newData.getRequestList());
    }

    //// request-level operations

    /**
     * Returns true if a request with the same identity as {@code request} exists in the BookInventory.
     */
    public boolean hasRequest(Request request) {
        requireNonNull(request);
        return requestList.contains(request);
    }

    /**
     * Adds a request to the BookInventory.
     * The request must not already exist in the BookInventory.
     */
    public void addRequest(Request p) {
        requestList.add(p);
    }

    /**
     * Replaces the given request {@code target} in the list with {@code editedRequest}.
     * {@code target} must exist in the BookInventory.
     * The request identity of {@code editedRequest} must not be the same as
     * another existing request in the BookInventory.
     */
    public void updateRequest(Request target, Request editedRequest) {
        requireNonNull(editedRequest);

        requestList.setRequest(target, editedRequest);
    }

    /**
     * Removes {@code key} from this {@code RequestList}.
     * {@code key} must exist in the BookInventory.
     */
    public void removeRequest(Request key) {
        requestList.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return requestList.asUnmodifiableObservableList().size() + " requestList";
        // TODO: refine later
    }

    @Override
    public ObservableList<Request> getRequestList() {
        return requestList.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RequestList // instanceof handles nulls
                && requestList.equals(((RequestList) other).requestList));
    }

    @Override
    public int hashCode() {
        return requestList.hashCode();
    }
}
```
