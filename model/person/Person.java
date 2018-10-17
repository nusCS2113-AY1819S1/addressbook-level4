package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.*;

import seedu.address.model.enrolledClass.EnrolledClass;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Map<String, EnrolledClass> enrolledClasses = new TreeMap<>();
    private final Map<String, List<TimeSlots>> timeslots;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  Set<Tag> tags, Map<String, EnrolledClass> enrolledClasses,Map<String ,List<TimeSlots> > timeslots) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        EnrolledClass tempClass;
        for(String tempClassName: enrolledClasses.keySet()){
            tempClass = enrolledClasses.get(tempClassName);
            this.enrolledClasses.put(tempClassName, tempClass);
        }

        if (timeslots!=null) {
            this.timeslots = new HashMap<>(timeslots);
        }
        else{
            this.timeslots = TimeSlots.sampleTimeSlots();
        }

    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable enrolled class map, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Map<String, EnrolledClass> getEnrolledClasses() { return Collections.unmodifiableMap(enrolledClasses); }

    public Map<String, List<TimeSlots>> getTimeSlots() {
        return timeslots;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags())
                && otherPerson.getEnrolledClasses().equals(getEnrolledClasses());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, enrolledClasses, timeslots);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Enrolled Classes: ");
        for(String temp: getEnrolledClasses().keySet()){
            temp = temp + " ";
            builder.append(temp);
        }
        return builder.toString();
    }

}
