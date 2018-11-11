package seedu.address.testutil.batch;

import seedu.address.model.drink.Batch;
import seedu.address.model.drink.BatchDate;
import seedu.address.model.drink.BatchId;
import seedu.address.model.drink.BatchPrice;
import seedu.address.model.drink.BatchQuantity;

/**
 * A utility class to help with building Batch objects.
 */
public class BatchBuilder {
    public static final String DEFAULT_BATCH_ID = "1000000001";
    public static final String DEFAULT_BATCH_QUANTITY = "30";
    public static final String DEFAULT_BATCH_DATE = "1/11/2018";

    private BatchId batchId;
    private BatchQuantity batchQuantity;
    private BatchDate batchDate;

    public BatchBuilder() {
        batchId = new BatchId(DEFAULT_BATCH_ID);
        batchQuantity = new BatchQuantity(DEFAULT_BATCH_QUANTITY);
        batchDate = new BatchDate(DEFAULT_BATCH_DATE);
    }

    /**
     * Initializes the BatchBuilder with the data of {@code batchToCopy}.
     */
    public BatchBuilder(Batch batchToCopy) {
        batchId = batchToCopy.getBatchId();
        batchQuantity = batchToCopy.getBatchQuantity();
        batchDate = batchToCopy.getBatchDate();
    }

    /**
     * Sets the {@code BatchId} of the {@code Batch} that we are building.
     */
    public BatchBuilder withId(String input) {
        this.batchId = new BatchId(input);
        return this;
    }

    /**
     * Sets the {@code BatchId} of the {@code Batch} that we are building with the automatically generated batch Id.
     */
    public BatchBuilder withId() {
        this.batchId = new BatchId();
        return this;
    }

    /**
     * Sets the {@code BatchQuantity} of the {@code Batch} that we are building.
     */
    public BatchBuilder withQuantity(String input) {
        this.batchQuantity = new BatchQuantity(input);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public BatchBuilder withDate(String input) {
        this.batchDate = new BatchDate(input);
        return this;
    }

    public Batch build() {
        return new Batch(batchId, batchQuantity, batchDate);
    }
}
