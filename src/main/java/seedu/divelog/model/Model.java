package seedu.divelog.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.divelog.commons.enums.SortingMethod;
import seedu.divelog.logic.pressuregroup.exceptions.LimitExceededException;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.exceptions.DiveNotFoundException;
import seedu.divelog.model.dive.exceptions.DiveOverlapsException;
import seedu.divelog.model.dive.exceptions.InvalidTimeException;


/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<DiveSession> PREDICATE_SHOW_ALL_DIVES = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyDiveLog newData);

    /** Returns the DiveLog */
    ReadOnlyDiveLog getDiveLog();

    /**
     * Deletes the given dive.
     * The dive session must exist in the divelog book.
     * @param target
     * @throws DiveNotFoundException if the dive to be deleted does not exist
     */
    void deleteDiveSession(DiveSession target) throws DiveNotFoundException;

    /**
     * Adds the given dive.
     * {@code DiveSession} must not already exist in the divelog book.
     * @param diveSession
     * @throws DiveOverlapsException when the dive you want to insert overlaps
     * @throws LimitExceededException if the dive to be added pushes the diver over the limit
     * @throws InvalidTimeException if something goes wrong in time conversions
     */
    void addDiveSession(DiveSession diveSession) throws DiveOverlapsException, LimitExceededException,
            InvalidTimeException;

    /**
     * Replaces the given dive {@code target} with {@code editedDiveSession}.
     * {@code target} must exist in the divelog book.
     * The dive of {@code DiveSession} must not be the same as another existing dive session in the divelog book.
     * @param target
     * @param editedDiveSession
     * @throws DiveNotFoundException if the dive to be deleted does not exist
     * @throws DiveOverlapsException when the dive you want to insert overlaps
     * @throws LimitExceededException if the dive to be added pushes the diver over the limit
     * @throws InvalidTimeException if something goes wrong in time conversions
     */
    void updateDiveSession(DiveSession target, DiveSession editedDiveSession) throws DiveOverlapsException,
            DiveNotFoundException, InvalidTimeException, LimitExceededException;

    /** Returns an unmodifiable view of the filtered dive session list */
    ObservableList<DiveSession> getFilteredDiveList();

    /**
     * Updates the filter of the filtered dive session list to filter by the given {@code predicate}.
     * @param predicate
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDiveList(Predicate<DiveSession> predicate);

    /**
     * Sorts the InternalList based on Time
     * Can be scaled to sort based on other things
     */
    void sortDiveSession(SortingMethod sortByCategory);

    /**
     * Gets the most recent dive
     */
    DiveSession getMostRecent();

    /**
     * Recalculate all pressure groups
     */
    void recalculatePressureGroups() throws LimitExceededException;

    /**
     * Returns true if the model has previous divelog book states to restore.
     */
    boolean canUndoDiveLog();

    /**
     * Returns true if the model has undone divelog book states to restore.
     */
    boolean canRedoDiveLog();

    /**
     * Restores the model's divelog book to its previous state.
     */
    void undoDiveLog();

    /**
     * Restores the model's divelog book to its previously undone state.
     */
    void redoDiveLog();
    /**
     * zeroes the planner count
     */
    void zeroPlannerCount();

    /**
     * Saves the current divelog book state for undo/redo.
     */
    void commitDiveLog();
    /**
     * Returns true if model is in planning mode
     */
    boolean getPlanningMode();
    /**
     * Toggles Planning Mode
     */
    void setPlanningMode();
    /**
     * Adds one to planner count
     */
    void plannerCountPlus();
    /**
     * Removes one from planner count
     */
    void plannerCountMinus();
    /**
     * Returns Planner Count to be undone
     */
    int getPlannerCount();
}
