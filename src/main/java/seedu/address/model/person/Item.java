package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Item {

    // Identity fields
//    private final Name name;
//    private final Phone phone;
//    private final Email email;
    private final Name name;
    private final Integer quantity;
    private final Integer minQuantity;

    // Data fields
//    private final Address address;
    private final ArrayList<Integer> status = new ArrayList<>();
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
//    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
//        requireAllNonNull(name, phone, email, address, tags);
//        this.name = name;
//        this.phone = phone;
//        this.email = email;
//        this.address = address;
//        this.tags.addAll(tags);
//    }
    public Item(Name name, Integer quantity, Integer minQuantity, Set<Tag> tags) {
        requireAllNonNull(name, quantity, minQuantity, tags);
        this.name = name;
        this.quantity = quantity;
        this.minQuantity = minQuantity;
        this.status.add(quantity);
        this.status.add(0);
        this.status.add(0);
        this.tags.addAll(tags);
    }

//    public Name getName() {
//        return name;
//    }
//
//    public Phone getPhone() {
//        return phone;
//    }
//
//    public Email getEmail() {
//        return email;
//    }
//
//    public Address getAddress() {
//        return address;
//    }
    public Name getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getMinQuantity() { return minQuantity; }

    public ArrayList<Integer> getStatus() {
        return status;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

//    /**
//     * Returns true if both persons of the same name have at least one other identity field that is the same.
//     * This defines a weaker notion of equality between two persons.
//     */
//    public boolean isSamePerson(Person otherPerson) {
//        if (otherPerson == this) {
//            return true;
//        }
//
//        return otherPerson != null
//                && otherPerson.getName().equals(getName())
//                && (otherPerson.getPhone().equals(getPhone()) || otherPerson.getEmail().equals(getEmail()));
//    }

    public boolean isSameItem(Item otherItem) {
        if (otherItem == this) {
            return true;
        }

        return otherItem != null
                && otherItem.getName().equals((getName()));
    }

//    /**
//     * Returns true if both persons have the same identity and data fields.
//     * This defines a stronger notion of equality between two persons.
//     */
//    @Override
//    public boolean equals(Object other) {
//        if (other == this) {
//            return true;
//        }
//
//        if (!(other instanceof Person)) {
//            return false;
//        }
//
//        Person otherPerson = (Person) other;
//        return otherPerson.getName().equals(getName())
//                && otherPerson.getPhone().equals(getPhone())
//                && otherPerson.getEmail().equals(getEmail())
//                && otherPerson.getAddress().equals(getAddress())
//                && otherPerson.getTags().equals(getTags());
//    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, quantity, minQuantity, status, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Name: ")
                .append(getName())
                .append(" Quantity: ")
                .append(getQuantity())
                .append(" Minimum Quantity Required In Stocks: ")
                .append(getMinQuantity())
                .append(" Status: Ready | ")
                .append(getStatus().get(0))
                .append(", On-Loan | ")
                .append(getStatus().get(1))
                .append(", Faulty | ")
                .append(getStatus().get(2))
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
