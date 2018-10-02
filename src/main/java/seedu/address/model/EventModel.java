package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;


/**
 * The API of the Model component.
 */
public interface EventModel {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

    /** Returns the AddressBook */
    ReadOnlyEventList getEventList();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasEvent(Event person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deleteEvent(Event target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addEvent(Event person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updateEvent(Event target, Event editedEvent);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Event> getFilteredEventList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);
}
