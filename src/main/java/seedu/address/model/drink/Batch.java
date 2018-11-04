package seedu.address.model.drink;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Batch of drink in DRINKio
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

    /**
     * Alternative constructor for the batch class, with the date attribute filled by the current date
     */
    public Batch(BatchId id, BatchQuantity quantity, BatchPrice price) {
        requireAllNonNull(id, quantity, price);
        this.batchId = id;
        this.batchQuantity = quantity;
        this.batchPrice = price;
        this.batchDate = new BatchDate();
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

    public void decreaseBatchQuantity(int value) {
        batchQuantity.decreaseValue(value);
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

    public void setBatchQuantity(int value) {
        batchQuantity.setValue(value);
    }

    public int compareDateTo(Batch otherBatch) {
        return this.batchDate.compareTo(otherBatch.getBatchDate());
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
                .append(" Batch Price: $")
                .append(getBatchPrice().toString())
                .append(" Batch Date: ")
                .append(getBatchDate().toString());
        return builder.toString();
    }
}
