package seedu.address.model.drinks;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Batch of drinks in DRINKio
 * Guarantees: TODO
 */
public class Batch {
    private final BatchId batchId;
    private BatchQuantity batchQuantity;
    private final BatchPrice batchPrice;
    private final BatchDate batchDate;

    public Batch(BatchId id, BatchQuantity quantity, BatchPrice price, BatchDate date) {
        requireAllNonNull(id, quantity, price, date);
        this.batchId = id;
        this.batchQuantity = quantity;
        this.batchPrice = price;
        this.batchDate = date;
    }

    public BatchId getBatchId() {
        return batchId;
    }

    public BatchQuantity getBatchQuantity() {
        return batchQuantity;
    }

    public BatchPrice getBatchPrice() {
        return batchPrice;
    }

    public BatchDate getBatchDate() {
        return batchDate;
    }

    /**
     * Returns true if both batches have the same BatchId
     * This defines a weaker notion of equality between two batches.
     */
    public boolean isSameBatch(Batch otherBatch) {
        if (otherBatch == this) {
            return true;
        }

        return otherBatch != null
                && otherBatch.getBatchId().equals(getBatchId());
    }

    /**
     * Returns true if both batches have the same identity and data fields.
     * This defines a stronger notion of equality between two batches.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Batch)) {
            return false;
        }

        Batch otherBatch = (Batch) other;
        return otherBatch.getBatchId().equals(getBatchId())
                && otherBatch.getBatchDate().equals(getBatchDate())
                && otherBatch.getBatchPrice().equals(getBatchPrice())
                && otherBatch.getBatchQuantity().equals(getBatchQuantity());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Batch ID: ")
                .append(getBatchId().toString())
                .append(" Batch Quantity: ")
                .append(getBatchQuantity().toString())
                .append(" Batch Date: ")
                .append(getBatchDate().toString())
                .append(" Batch Price: $")
                .append(getBatchPrice().toString());
        return builder.toString();
    }
}
