package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;

/**
 * Wraps all data at the event-list level
 * Duplicates are not allowed (by .isSameEvent comparison)
 */
public class EventList implements ReadOnlyEventList {

    private final UniqueEventList events;

    public EventList() {
        events = new UniqueEventList();
    }

    /**
     * @param toBeCopied
     */
    public EventList(ReadOnlyEventList toBeCopied) {
        events = new UniqueEventList();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the event list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyEventList newData) {
        requireNonNull(newData);
        this.setEvents(newData.getEventList());
    }

    //// event-level operations

    /**
     * Returns true if an event with the same identity as {@code event} exists in the address book.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Adds an event to the address book.
     * The event must not already exist in the event list.
     */
    public void addEvent(Event e) {
        events.add(e);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in the event list.
     * The identity of {@code editedEvent} must not be the same as another existing event in the event list.
     */
    public void updateEvent(Event target, Event editedEvent) {
        requireNonNull(editedEvent);

        events.setEvent(target, editedEvent);
    }

    /**
     * Removes {@code key} from this {@code EventList}.
     * {@code key} must exist in the event list.
     */
    public void removeEvent(Event key) {
        events.remove(key);
    }

    public List<Event> getSortedEventList() {
        return events.getSortedEventList();
    }

    //// util methods

    @Override
    public String toString() {
        return events.asUnmodifiableObservableList().size() + " events";
        // TODO: refine later
    }


    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventList// instanceof handles nulls
                && events.equals(((EventList) other).events));
    }

    @Override
    public int hashCode() {
        return events.hashCode();
    }

    @Override
    public void sortByName() {
        events.sortByName();
    }

    @Override
    public void sortByStartTime() {
        events.sortByStartTime();
    }

    @Override
    public void sortByEndTime() {
        events.sortByEndTime();
    }

    @Override
    public void sortByDate() {
        events.sortByDate();
    }
}


