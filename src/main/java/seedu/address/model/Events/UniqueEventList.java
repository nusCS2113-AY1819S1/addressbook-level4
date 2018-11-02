package seedu.address.model.Events;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.Events.exceptions.DuplicateEventException;
import seedu.address.model.Events.exceptions.EventNotFoundException;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
/**
 * A list of events that enforces uniqueness between its elements and does not allow nulls.
 * An event is considered unique by comparing using {@code Event#isSameEvent(Event)}. As such, adding and updating of
 * event uses Event#isSameEvent(Event) for equality so as to ensure that the event being added or updated is
 * unique in terms of name in the UniqueEventList. However, the removal of a event uses Event#equals(Object) so
 * as to ensure that the event with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Event#isSameEvent(Event)
 */
public class UniqueEventList implements Iterable<Event> {
    private final ObservableList<Event> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent event as the given argument.
     */
    public boolean contains(Event toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEvent);
    }

    /**
     * Adds an event to the list.
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
     * The event name of {@code editedEvent} must not be the same as another existing event in the list.
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


    public void setEvents(UniqueEventList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void setEvents(List<Event> events) {
        requireAllNonNull(events);
        if (!EventAreUnique(events)) {
            throw new DuplicateEventException();
        }

        internalList.setAll(events);
    }

    /**
     * Removes the event in the list.
     */
    public void remove(Event toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EventNotFoundException();
        }

    }

    /**
     * Returns true if {@code events} contains only unique events.
     */
    private boolean EventAreUnique(List<Event> Events) {
        for (int i = 0; i < Events.size() - 1; i++) {
            for (int j = i + 1; j < Events.size(); j++) {
                if (Events.get(i).isSameEvent(Events.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

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



}
