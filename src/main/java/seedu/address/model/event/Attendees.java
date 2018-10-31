package seedu.address.model.event;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//@@author: jieliangang
/**
 * Represents a the attendees in a event.
 */
public class Attendees {
    public final Set<String> attendeesSet;

    public Attendees() {
        this.attendeesSet = new HashSet<>();
    }

    public Attendees(Set<String> attendeesSet) {
        Objects.requireNonNull(attendeesSet);
        this.attendeesSet = attendeesSet;
    }

    public Attendees(Set<String>... attendeesSet) {
        this();
        for (Set<String> names: attendeesSet) {
            Objects.requireNonNull(names);
            this.attendeesSet.addAll(names);
        }
    }

    /**
     * Add names to attendees list
     * @param name The new name to be added.
     * @return A new copy of updated Attendees.
     */
    public Attendees addName(String name) {
        Set<String> updatedAttendees = new HashSet<>(this.attendeesSet);
        updatedAttendees.add(name);
        return new Attendees(updatedAttendees);
    }

    /**
     * Remove names to attendees list
     * @param name The name to be removed.
     * @return A new copy of updated Attendees.
     */
    public Attendees removeName(String name) {
        Set<String> updatedAttendees = new HashSet<>(this.attendeesSet);
        updatedAttendees.remove(name);
        return new Attendees(updatedAttendees);
    }

    /**
     * Returns whether set contains name.
     * @param name The name to be checked.
     */
    public boolean hasName(String name) {
        return attendeesSet.contains(name);
    }

    /**
     * Returns whether set is empty.
     */
    public boolean isSetEmpty() {
        return attendeesSet.isEmpty();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Attendees// instanceof handles nulls
                && attendeesSet.equals(((Attendees) other).attendeesSet)); // state check
    }

    @Override
    public int hashCode() {
        return attendeesSet.hashCode();
    }
}
