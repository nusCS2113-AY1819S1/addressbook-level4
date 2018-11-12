package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    public static final String MESSAGE_PHONE = " Phone: ";
    public static final String MESSAGE_EMAIL = " Email: ";
    public static final String MESSAGE_ADDRESS = " Address: ";
    public static final String MESSAGE_TAG = " Tags: ";
    public static final String MESSAGE_DEPARTMENT = " Department: ";
    public static final String MESSAGE_DESIGNATION = " Designation: ";

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Department department;
    private final Designation designation;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Department department,
                  Designation designation, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, department, designation, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.department = department;
        this.designation = designation;
        this.tags.addAll(tags);
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

    public Department getDepartment() {
        return department;
    }

    public Designation getDesignation() {
        return designation;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        // person is identified by their work email, which must be unique
        return otherPerson != null
                && otherPerson.getEmail().equals(getEmail());
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
                && otherPerson.getDepartment().equals(getDepartment())
                && otherPerson.getDesignation().equals(getDesignation());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, department, designation, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(MESSAGE_PHONE)
                .append(getPhone())
                .append(MESSAGE_EMAIL)
                .append(getEmail())
                .append(MESSAGE_ADDRESS)
                .append(getAddress())
                .append(MESSAGE_DEPARTMENT)
                .append(getDepartment())
                .append(MESSAGE_DESIGNATION)
                .append(getDesignation())
                .append(MESSAGE_TAG);
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
