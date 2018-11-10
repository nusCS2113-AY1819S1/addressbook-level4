package seedu.address.model.util;

import seedu.address.model.drink.Batch;
import seedu.address.model.drink.BatchDate;
import seedu.address.model.drink.BatchId;
import seedu.address.model.drink.BatchQuantity;

/**
 * A utility class to help with building Batch objects.
 */
public class SampleBatchBuilder {

    public static final String DEFAULT_BATCH_ID = "0001";
    public static final String DEFAULT_BATCH_QUANTITY = "10";
    public static final String DEFAULT_BATCH_DATE = "1/11/1996";

    private BatchId batchId;
    private BatchQuantity batchQuantity;
    private BatchDate batchDate;

    public SampleBatchBuilder() {
        batchId = new BatchId(DEFAULT_BATCH_ID);
        batchQuantity = new BatchQuantity(DEFAULT_BATCH_QUANTITY);
        batchDate = new BatchDate(DEFAULT_BATCH_DATE);
    }

    /**
     * Initializes the BatchBuilder with the data of {@code batchToCopy}.
     */
    public SampleBatchBuilder(Batch batchToCopy) {
        batchId = batchToCopy.getBatchId();
        batchQuantity = batchToCopy.getBatchQuantity();
        batchDate = batchToCopy.getBatchDate();
    }

    /**
     * Sets the {@code BatchId} of the {@code Batch} that we are building.
     */
    public SampleBatchBuilder withId(String input) {
        this.batchId = new BatchId(input);
        return this;
    }

    /**
     * Sets the {@code BatchQuantity} of the {@code Batch} that we are building.
     */
    public SampleBatchBuilder withQuantity(String input) {
        this.batchQuantity = new BatchQuantity(input);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public SampleBatchBuilder withDate(String input) {
        this.batchDate = new BatchDate(input);
        return this;
    }

    public Batch build() {
        return new Batch(batchId, batchQuantity, batchDate);
    }
}
