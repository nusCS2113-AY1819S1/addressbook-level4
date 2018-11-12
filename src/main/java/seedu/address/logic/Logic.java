package seedu.address.logic;

import java.util.List;
import java.util.Queue;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.Book;
import seedu.address.request.Request;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Compares substring isbn and completes the line
     * @return the list of the books
     */
    Queue<String> getCompleteIsbn(String isbnText);

    /** Returns an unmodifiable view of the filtered list of books */
    ObservableList<Book> getFilteredBookList();

    ObservableList<Request> getFilteredRequestList();
    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();

    List<String> getHistoryList ();

}
