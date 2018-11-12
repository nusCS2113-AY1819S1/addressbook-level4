package seedu.divelog.logic;

import javafx.collections.ObservableList;
import seedu.divelog.logic.commands.CommandResult;
import seedu.divelog.logic.commands.exceptions.CommandException;
import seedu.divelog.logic.parser.exceptions.ParseException;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.exceptions.InvalidTimeException;

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
    CommandResult execute(String commandText) throws CommandException, ParseException,
            java.text.ParseException, InvalidTimeException;

    /** Returns an unmodifiable view of the filtered list of dives */
    ObservableList<DiveSession> getFilteredDiveList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
