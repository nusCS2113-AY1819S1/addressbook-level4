package seedu.address.model.item;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Item in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Item {

    // Identity
    // fields
    private final Name name;
    private final Quantity quantity;
    private final Quantity minQuantity;

    // Data fields
    private final Status status = new Status();
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Item(Name name, Quantity quantity, Quantity minQuantity, Set<Tag> tags) {
        requireAllNonNull(name, quantity, minQuantity, tags);
        this.name = name;
        this.quantity = quantity;
        this.minQuantity = minQuantity;
        this.status.setDefaultValues(quantity.toInteger());
        this.tags.addAll(tags);
    }

    public Item(Name name, Quantity quantity, Quantity minQuantity, Status status, Set<Tag> tags) {
        requireAllNonNull(name, quantity, minQuantity, tags);
        this.name = name;
        this.quantity = quantity;
        this.minQuantity = minQuantity;
        this.status.setStatusReady(status.getStatusReady());
        this.status.setStatusOnLoan(status.getStatusOnLoan());
        this.status.setStatusFaulty(status.getStatusFaulty());
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Quantity getMinQuantity() {
        return minQuantity;
    }

    public Status getStatus() {
        return status;
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
                && (otherItem.getName().fullName.toLowerCase()).equals(getName().fullName.toLowerCase());
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
        return (otherItem.getName().fullName.toLowerCase()).equals(getName().fullName.toLowerCase());
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
                .append(getStatus().getStatusReady())
                .append(", On-Loan | ")
                .append(getStatus().getStatusOnLoan())
                .append(", Faulty | ")
                .append(getStatus().getStatusFaulty())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
