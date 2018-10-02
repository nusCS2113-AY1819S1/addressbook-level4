package seedu.address.model.book;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Book in the quantity book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Book {

    // Identity fields
    private final Name name;
    private final Isbn isbn;
    private final Price price;

    // Data fields
    private final Quantity quantity;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Book(Name name, Isbn isbn, Price price, Quantity quantity, Set<Tag> tags) {
        requireAllNonNull(name, isbn, price, quantity, tags);
        this.name = name;
        this.isbn = isbn;
        this.price = price;
        this.quantity = quantity;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    public Price getPrice() {
        return price;
    }

    public Quantity getQuantity() {
        return quantity;
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
    public boolean isSamePerson(Book otherBook) {
        if (otherBook == this) {
            return true;
        }

        return otherBook != null
                && otherBook.getName().equals(getName())
                && (otherBook.getIsbn().equals(getIsbn()) || otherBook.getPrice().equals(getPrice()));
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

        if (!(other instanceof Book)) {
            return false;
        }

        Book otherBook = (Book) other;
        return otherBook.getName().equals(getName())
                && otherBook.getIsbn().equals(getIsbn())
                && otherBook.getPrice().equals(getPrice())
                && otherBook.getQuantity().equals(getQuantity())
                && otherBook.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, isbn, price, quantity, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Isbn: ")
                .append(getIsbn())
                .append(" Price: ")
                .append(getPrice())
                .append(" Quantity: ")
                .append(getQuantity())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
