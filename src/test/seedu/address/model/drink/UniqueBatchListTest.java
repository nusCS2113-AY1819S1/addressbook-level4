//@@author Lunastryke
package seedu.address.model.drink;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.batch.TypicalBatches.*;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.drink.exceptions.BatchNotFoundException;
import seedu.address.model.drink.exceptions.DuplicateBatchException;
import seedu.address.model.drink.exceptions.EmptyBatchListException;
import seedu.address.model.drink.exceptions.InsufficientQuantityException;
import seedu.address.testutil.Assert;
import seedu.address.testutil.batch.TypicalBatches;

class UniqueBatchListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueBatchList uniqueBatchList = new UniqueBatchList();

    @Test
    void contains_nullBatch_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueBatchList.contains(null));
    }


    @Test
    void contains_batchNotInList_returnsFalse() {
        UniqueBatchList uniqueBatchList = new UniqueBatchList();
        assertFalse(uniqueBatchList.contains(COKE2));
    }

    @Test
    void contains_batchInList_returnsTrue() {
        uniqueBatchList.addBatch(COKE2);
        assertTrue(uniqueBatchList.contains(COKE2));
    }

    @Test
    void add_nullBatch_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueBatchList.addBatch(null));
    }


    @Test
    void add_duplicateBatch_throwsDuplicateBatchException() {
        uniqueBatchList.addBatch(COKE4);
        Assert.assertThrows(DuplicateBatchException.class, () -> uniqueBatchList.addBatch(COKE7));
    }

    @Test
    void addSameDateBatch() {
        uniqueBatchList.addBatch(COKE4);
        uniqueBatchList.addBatch(COKE9);
        assertEquals(uniqueBatchList.getNumberBatches(), 1);
    }

    @Test
    public void setBatch_nullTargetBatch_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueBatchList.setBatch(null, COKE2));
    }

    @Test
    public void setBatch_nullEditedBatch_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueBatchList.setBatch(COKE2, null));
    }


    @Test
    public void setBatch_targetBatchNotInList_throwsPersonNotFoundException() {
        Assert.assertThrows(BatchNotFoundException.class, () -> uniqueBatchList.setBatch(COKE2, COKE2));
    }


    @Test
    public void setBatch_editedBatchIsSameBatch_success() {
        uniqueBatchList.addBatch(COKE2);
        uniqueBatchList.setBatch(COKE2, COKE2);
        UniqueBatchList expectedUniqueBatchList = new UniqueBatchList();
        expectedUniqueBatchList.addBatch(COKE2);
        assertEquals(expectedUniqueBatchList, uniqueBatchList);
    }

    @Test
    void sortBatches() {
        // Sets uniqueBatchList
        uniqueBatchList.setBatches(TypicalBatches.getTypicalUniqueBatchList());
        // Calls sort method to sort batch list
        uniqueBatchList.sortBatches();
        // Checks for order of batches
        assertEquals(uniqueBatchList.getBatch(0), COKE6);
        assertEquals(uniqueBatchList.getBatch(1), COKE1);
        assertEquals(uniqueBatchList.getBatch(2), COKE2);
        assertEquals(uniqueBatchList.getBatch(3), COKE3);
        assertEquals(uniqueBatchList.getBatch(4), COKE4);
        assertEquals(uniqueBatchList.getBatch(5), COKE5);
    }

    @Test
    void updateTotalQuantity() {
        uniqueBatchList.setBatches(TypicalBatches.getTypicalUniqueBatchList());
        uniqueBatchList.updateTotalQuantity();
        assertEquals(uniqueBatchList.getTotalQuantity(), new Quantity("310"));
    }

    @Test
    void clearEmptyBatches() {
        uniqueBatchList.setBatches(TypicalBatches.getTypicalUniqueBatchList());
        uniqueBatchList.addBatch(COKE10);
        assertEquals(uniqueBatchList.getNumberBatches(), 7);
        uniqueBatchList.clearEmptyBatches();
        assertEquals(uniqueBatchList.getNumberBatches(), 6);
    }

    @Test
    void getEarliestBatch() {
        uniqueBatchList.setBatches(TypicalBatches.getTypicalUniqueBatchList());
        assertEquals(uniqueBatchList.getEarliestBatch(), COKE6);
    }

    @Test
    void getLatestBatch() {
        uniqueBatchList.setBatches(TypicalBatches.getTypicalUniqueBatchList());
        assertEquals(uniqueBatchList.getLatestBatch(), COKE5);
    }

    @Test
    void getEarliestBatchDate() {
        uniqueBatchList.setBatches(TypicalBatches.getTypicalUniqueBatchList());
        assertEquals(uniqueBatchList.getEarliestBatchDate(), COKE6.getBatchDate());
    }

    @Test
    void getLatestBatchDate() {
        uniqueBatchList.setBatches(TypicalBatches.getTypicalUniqueBatchList());
        assertEquals(uniqueBatchList.getLatestBatchDate(), COKE5.getBatchDate());
    }

    @Test
    void batchSameDate() {
        uniqueBatchList.setBatches(TypicalBatches.getTypicalUniqueBatchList());
        assertTrue(uniqueBatchList.batchSameDate(COKE9));
    }

    @Test
    void updateBatchTransaction_throwsInsufficientQuantityException() {
        uniqueBatchList.setBatches(TypicalBatches.getTypicalUniqueBatchList());
        Assert.assertThrows(InsufficientQuantityException.class,
                () -> uniqueBatchList.updateBatchTransaction(new Quantity("400")));
    }

    @Test
    void updateBatchTransaction_throwsEmptyBatchListException() {
        Assert.assertThrows(EmptyBatchListException.class,
                () -> uniqueBatchList.updateBatchTransaction(new Quantity("400")));
    }
}
