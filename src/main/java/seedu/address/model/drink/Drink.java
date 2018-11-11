package seedu.address.model.drink;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.drink.exceptions.EmptyBatchListException;
import seedu.address.model.drink.exceptions.InsufficientQuantityException;
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
    private Set<Tag> tags = new HashSet<>();

    /**
     * Constructs a Drink for the use of adding new drink into inventory.
     * Hence, quantity is 0 and uniqueBatchList is empty
     */
    public Drink(Name name, Price costPrice, Price retailPrice, Set<Tag> tags) {
        requireAllNonNull(name, costPrice, retailPrice, tags);
        this.name = name;
        this.costPrice = costPrice;
        this.retailPrice = retailPrice;
        uniqueBatchList = new UniqueBatchList();
        quantity = new Quantity("0");
        this.tags.addAll(tags);
    }

    public Drink(Name name, Price costPrice, Price retailPrice) {
        requireAllNonNull(name, costPrice, retailPrice);
        this.name = name;
        this.costPrice = costPrice;
        this.retailPrice = retailPrice;
        uniqueBatchList = new UniqueBatchList();
        quantity = new Quantity("0");
    }

    /**
     * Every field must be present and not null.
     */
    public Drink(Name name, Price costPrice, Price retailPrice, UniqueBatchList batchList, Set<Tag> tags) {
        requireAllNonNull(name, costPrice, retailPrice, batchList, tags);
        this.name = name;
        this.costPrice = costPrice;
        this.retailPrice = retailPrice;
        this.uniqueBatchList = batchList;
        this.quantity = uniqueBatchList.getTotalQuantity();
        this.tags.addAll(tags);
    }

    /**
     * Constructs a Drink for the use of buying and selling.
     * Hence, fields other than name are initialised to default values.
     */
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

    public void updateQuantity() {
        this.quantity = uniqueBatchList.getTotalQuantity();
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

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
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
     * Gets the date of the earliest imported batch
     * @return a Batch Date object
     */
    public BatchDate getEarliestBatchDate() {
        try {
            return uniqueBatchList.getEarliestBatchDate();
        } catch (EmptyBatchListException e) {
            return null;
        }
    }

    /**
     * Gets the date of the earliest imported batch
     * @return a Batch Date object
     */
    public BatchDate getLatestBatchDate() {
        try {
            return uniqueBatchList.getLatestBatchDate();
        } catch (EmptyBatchListException e) {
            return null;
        }
    }

    /**
     * Gets the number of batches of the current drink
     * @return a quantity expressed as an integer
     */
    public int getNumberBatches() {
        return uniqueBatchList.getNumberBatches();
    }

    public ObservableList<Batch> getObservableBatchList() {
        try {
            return uniqueBatchList.asUnmodifiableObservableList();
        } catch (EmptyBatchListException e) {
            return FXCollections.observableArrayList();
        }
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
                && otherDrink.getRetailPrice().equals(getRetailPrice())
                && otherDrink.getCostPrice().equals(getCostPrice())
                && otherDrink.getQuantity().equals(getQuantity())
                // && otherDrink.getUniqueBatchList().equals(getUniqueBatchList())
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
        builder.append ("Drink name: ")
                .append (getName ())
                .append ("\nCost price: ")
                .append (getCostPrice ())
                .append (" Selling price: ")
                .append (getRetailPrice ())
                .append(", Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Decreases the quantity of the drink, using {@code quantity} as the value to decrease
     */
    public void decreaseQuantity(Quantity quantity) throws InsufficientQuantityException {
        this.uniqueBatchList.updateBatchTransaction(quantity);
        updateQuantity();
    }

    /**
     * Increases the quantity of the drink, using {@code quantity} as the value to increase
     */
    public void increaseQuantity(Quantity quantity) {
        BatchId tempId = new BatchId();
        BatchPrice tempPrice = new BatchPrice(this.getCostPrice().toString());
        BatchQuantity tempQuantity = new BatchQuantity(quantity.toString());
        Batch toAdd = new Batch(tempId, tempQuantity);
        this.uniqueBatchList.addBatch(toAdd);
        updateQuantity();
    }
}

