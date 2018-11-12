package seedu.address.logic;

import java.io.IOException;

import javafx.collections.ObservableList;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.workout.Parameter;
import seedu.address.model.workout.Workout;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws IOException If an error occurs when opening a file.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, IOException, ParseException;

    /** Returns an unmodifiable view of the filtered list of workouts */
    ObservableList<Workout> getFilteredWorkoutList();

    /** Returns an unmodifiable view of the filtered list of parameters */
    ObservableList<Parameter> getFilteredTrackedDataList();

    /** Returns an unmodifiable view of the filtered list of tracked data */
    ObservableList<Workout> getFilteredTrackedData();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
