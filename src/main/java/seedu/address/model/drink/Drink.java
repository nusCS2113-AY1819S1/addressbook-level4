package seedu.address.model.drink;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Drink in the inventory.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Drink {
    // Identity fields
    private Name name;

    // Data fields
    private Price costPrice;
    private Price retailPrice;
    private UniqueBatchList uniqueBatchList;
    private Quantity quantity;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Drink(Name name, Price costPrice, Price retailPrice, Set<Tag> tags) {
        requireAllNonNull(name, costPrice, retailPrice, tags);
        this.name = name;
        this.costPrice = costPrice;
        this.retailPrice = retailPrice;
        quantity = new Quantity("0");
        this.tags.addAll(tags);
    }

    public Drink(Name name, Price costPrice, Price retailPrice, Quantity quantity, Set<Tag> tags) {
        requireAllNonNull(name, costPrice, retailPrice, quantity, tags);
        this.name = name;
        this.costPrice = costPrice;
        this.retailPrice = retailPrice;
        this.quantity = quantity;
        this.tags.addAll(tags);
    }

    public Drink(Name name) {
        this.name = name;
        this.costPrice = new Price("0");
        this.retailPrice = new Price("0");
        this.quantity = new Quantity("0");
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Price getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Price costPrice) {
        this.costPrice = costPrice;
    }

    public Price getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Price retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    public UniqueBatchList getUniqueBatchList() {
        return uniqueBatchList;
    }

    public void setUniqueBatchList(UniqueBatchList uniqueBatchList) {
        this.uniqueBatchList = uniqueBatchList;
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
    public boolean isSameDrink(Drink otherDrink) {
        if (otherDrink == this) {
            return true;
        }

        return otherDrink != null
                && otherDrink.getName().equals(getName());
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

        if (!(other instanceof Drink)) {
            return false;
        }

        Drink otherDrink = (Drink) other;
        return otherDrink.getName().equals(getName())
                && otherDrink.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Selling price: ")
                .append(getRetailPrice())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}

