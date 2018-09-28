package seedu.address.model.item;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Item {

    // Magic numbers
    final static public int STATUS_READY = 0;
    final static public int STATUS_ONLOAN = 1;
    final static public int STATUS_FAULTY = 2;

    // Identity fields
//    private final Name name;
//    private final Phone phone;
//    private final Email email;
    private final Name name;
    private final Integer quantity;
    private final Integer minQuantity;

    // Data fields
//    private final Address address;
    private final List<Integer> status = new ArrayList<>();
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
        status.set(STATUS_READY, quantity);
        status.set(STATUS_ONLOAN, 0);
        status.set(STATUS_FAULTY, 0);
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

    public List<Integer> getStatus() {
        return Collections.unmodifiableList(status);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both items of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two items.
     */
    public boolean isSameItem(Item otherItem) {
        if (otherItem == this) {
            return true;
        }

        return otherItem != null
                && otherItem.getName().equals((getName()))
                && (otherItem.getQuantity().equals(getQuantity()) || otherItem.getMinQuantity().equals(getMinQuantity())
                || otherItem.getStatus().equals((getStatus())));
    }

    /**
     * Returns true if both items have the same identity and data fields.
     * This defines a stronger notion of equality between two items.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Item)) {
            return false;
        }

        Item otherItem = (Item) other;
        return otherItem.getName().equals(getName())
                && otherItem.getQuantity().equals(getQuantity())
                && otherItem.getMinQuantity().equals(getMinQuantity())
                && otherItem.getStatus().equals(getStatus())
                && otherItem.getTags().equals(getTags());
    }

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
                .append(getStatus().get(STATUS_READY))
                .append(", On-Loan | ")
                .append(getStatus().get(STATUS_ONLOAN))
                .append(", Faulty | ")
                .append(getStatus().get(STATUS_FAULTY))
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

//    public void changeStatus(Integer ready, Integer onLoan, Integer faulty) {
//        status.set(STATUS_READY, ready);
//        status.set(STATUS_ONLOAN, onLoan);
//        status.set(STATUS_FAULTY, faulty);
//        return;
//    }
}
