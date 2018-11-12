package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.person.Person;

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
     * Returns true if an event with the same identity as {@code event} exists in the address book.
     */
    public boolean hasEventAfterEdit(Event eventToEdit, Event editedEvent) {
        requireNonNull(eventToEdit);
        requireNonNull(editedEvent);
        return events.containsAfterEdit(eventToEdit, editedEvent);
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

    /**
     * Removes {@code Person} from the Attendee of {@code EventList}.
     */
    public void removePersonFromAllEvents(Person person) {
        requireNonNull(person);
        String personEmail = person.getEmail().toString();
        events.removeAttendee(personEmail);
    }

    /**
     * Returns true if {@code personEmail}'s schedule clashes with {@code event}.
     */
    public boolean hasClash(Event event, String personEmail) {
        requireNonNull(event);
        requireNonNull(personEmail);
        return events.hasClash(event, personEmail);
    }

    /**
     * Returns true if {@code personEmail}'s schedule clashes with an edited{@code event}.
     */
    public boolean hasClashAfterEdit(Event eventBeforeEdit, Event eventAfterEdit, String personEmail) {
        requireNonNull(eventBeforeEdit);
        requireNonNull(eventAfterEdit);
        requireNonNull(personEmail);
        return events.hasClashAfterEdit(eventBeforeEdit, eventAfterEdit, personEmail);
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


