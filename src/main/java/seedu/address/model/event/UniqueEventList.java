package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;


/**
 * A list of events that enforces uniqueness between its elements and does not allow nulls.
 * A event is considered unique by comparing using {@code Person#isSameEvent(Event)}. As such, adding and updating of
 * events uses Event#isSameEvent(Event) for equality so as to ensure that the event being added or updated is
 * unique in terms of identity in the UniqueEventList. However, the removal of a event uses Event#equals(Object) so
 * as to ensure that the Event with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Event#isSameEvent(Event)
 */

public class UniqueEventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Event toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEvent);
    }

    /**
     * Adds a Event to the list.
     * The event must not already exist in the list.
     */
    public void add(Event toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEventException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EventNotFoundException();
        }

        if (!target.isSameEvent(editedEvent) && contains(editedEvent)) {
            throw new DuplicateEventException();
        }
        internalList.set(index, editedEvent);
    }

    /**
     * Removes the equivalent event from the list.
     * The event must exist in the list.
     */
    public void remove(Event toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EventNotFoundException();
        }
    }

    public void setEvents(UniqueEventList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setEvents(List<Event> events) {
        requireAllNonNull(events);
        if (!eventsAreUnique(events)) {
            throw new DuplicateEventException();
        }

        internalList.setAll(events);
    }

    /**
     * Update events which Attendee contains personName
     */
    public void removeAttendee(String personName) {
        requireNonNull(personName);
        for (Event event: internalList) {
            if (event.hasAttendee(personName)) {
                int index = internalList.indexOf(event);
                Event updatedEvent = event.removePersonFromAttendee(personName);
                internalList.set(index, updatedEvent);
            }
        }
    }

    /**
     * Returns true if the selected event clashes with the person's event list.
     */
    public boolean hasClash(Event toCheck, String personName) {
        requireNonNull(toCheck);
        EventClashPredicate predicate = new EventClashPredicate(toCheck, personName);
        return internalList.stream().anyMatch(predicate);
    }


    public List<Event> getSortedEventList() {
        List<Event> result = this.internalList;
        Collections.sort(result);
        return result;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Event> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Event> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEventList // instanceof handles nulls
                && internalList.equals(((UniqueEventList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean eventsAreUnique(List<Event> events) {
        for (int i = 0; i < events.size() - 1; i++) {
            for (int j = i + 1; j < events.size(); j++) {
                if (events.get(i).isSameEvent(events.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void sortByName() {
        internalList.sort((Event e1, Event e2) -> e1.compareTo(e2));
    }

    public void sortByStartTime() {
        internalList.sort((Event e1, Event e2) -> e1.compareStartTimeTo(e2));
    }

    public void sortByEndTime() {
        internalList.sort((Event e1, Event e2) -> e1.compareEndTimeTo(e2));
    }

    public void sortByDate() {
        internalList.sort((Event e1, Event e2) -> e1.compareDateTo(e2));
    }

}
