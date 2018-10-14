package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;


/**
 * The API of the Model component.
 */
public interface EventModel {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

    /**
     * Returns the EventList
     */
    ReadOnlyEventList getEventList();

    /**
     * Returns true if a person with the same identity as {@code event} exists in the event list.
     */
    boolean hasEvent(Event event);

    /**
     * Deletes the given event.
     * The event must exist in the address book.
     */
    void deleteEvent(Event target);

    /**
     * Adds the given event.
     * {@code event} must not already exist in the eventList.
     */
    void addEvent(Event event);

    /**
     * Replaces the given event {@code target} with {@code editedEvent}.
     * {@code target} must exist in the event list.
     * The event identity of {@code editedPerson} must not be the same as another existing event in the event list.
     */
    void updateEvent(Event target, Event editedEvent);

    /**
     * Returns an unmodifiable view of the filtered event list
     */
    ObservableList<Event> getFilteredEventList();

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);

    /**
     * Returns true if the model has previous event list states to restore.
     */
    boolean canUndoEventList();

    /**
     * Returns true if the model has undone event list states to restore.
     */
    boolean canRedoEventList();

    /**
     * Restores the model's event list to its previous state.
     */
    void undoEventList();

    /**
     * Restores the model's event list to its previously undone state.
     */
    void redoEventList();

    /**
     * Saves the current event list state for undo/redo.
     */
    void commitEventList();

    void sortByName();

    void sortByStartTime();

    void sortByEndTime();

    void sortByDate();
}
