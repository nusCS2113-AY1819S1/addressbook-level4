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
        requireAllNonNull(quantity, price, date);
        this.batchQuantity = quantity;
        this.batchPrice = price;
        this.batchDate = date;
    }
}
