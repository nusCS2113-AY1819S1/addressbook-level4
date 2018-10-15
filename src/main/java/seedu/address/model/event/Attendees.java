package seedu.address.model.event;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a the attendees in a event.
 */
public class Attendees {
    private Set<String> attendeesSet;

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int i = 1;
        for (String name : attendeesSet) {
            builder.append(String.format("%i: ", i++));
            builder.append(name);
            builder.append("\n");
        }
        return builder.toString();
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
