package seedu.address.model.util;

import seedu.address.model.drink.Batch;
import seedu.address.model.drink.UniqueBatchList;

/**
 * A utility class to help with building Batch objects.
 */
public class SampleBatchListBuilder {
    private UniqueBatchList batchList;

    SampleBatchListBuilder() {
        batchList = new UniqueBatchList();
    }

    /**
     * Builds a batch list with a variable number of batches
     * @param batch a valid Batch object
     * @return a valid UniqueBatchList object
     */
    public UniqueBatchList buildBatchList(Batch ...batch) {
        for (Batch b: batch) {
            batchList.addBatch(b);
        }
        return batchList;
    }

}
