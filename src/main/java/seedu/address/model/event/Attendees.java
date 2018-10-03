package seedu.address.model.event;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.model.person.Person;

/**
 * Represents a the attendees in a event.
 */
public class Attendees {
    private ArrayList<Person> attendeesList;

    public Attendees(Person... persons) {
        this.attendeesList = new ArrayList<Person>(Arrays.asList(persons));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int i = 1;
        for (Person person : attendeesList) {
            builder.append(String.format("%i: ", i++));
            builder.append(person.getName());
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Attendees// instanceof handles nulls
                && attendeesList.equals(((Attendees) other).attendeesList)); // state check
    }

    public ArrayList<Person> getAttendeesList() {
        return attendeesList;
    }

    @Override
    public int hashCode() {
        return attendeesList.hashCode();
    }
}
