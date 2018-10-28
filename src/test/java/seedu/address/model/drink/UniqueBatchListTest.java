//@@author Lunastryke
package seedu.address.model.drink;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.testutil.TypicalBatches.COKE2;
import static seedu.address.model.testutil.TypicalBatches.getTypicalBatches;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.drink.exceptions.BatchNotFoundException;
import seedu.address.model.drink.exceptions.DuplicateBatchException;

class UniqueBatchListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueBatchList uniqueBatchList = new UniqueBatchList();

    @Test
    void contains_nullBatch_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBatchList.contains(null);
    }

    @Test
    void contains_batchNotInList_returnsFalse() {
        assertFalse(uniqueBatchList.contains(COKE2));
    }

    @Test
    void contains_batchInList_returnsTrue() {
        uniqueBatchList.add(COKE2);
        assertTrue(uniqueBatchList.contains(COKE2));
    }

    @Test
    void add_nullBatch_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBatchList.add(null);
    }

    @Test
    void add_duplicateBatch_throwsDuplicateBatchException() {
        uniqueBatchList.add(COKE2);
        thrown.expect(DuplicateBatchException.class);
        uniqueBatchList.add(COKE2);
    }

    @Test
    public void setBatch_nullTargetBatch_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBatchList.setBatch(null, COKE2);
    }

    @Test
    public void setBatch_nullEditedBatch_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBatchList.setBatch(COKE2, null);
    }

    @Test
    public void setBatch_targetBatchNotInList_throwsPersonNotFoundException() {
        thrown.expect(BatchNotFoundException.class);
        uniqueBatchList.setBatch(COKE2, COKE2);
    }

    @Test
    public void setBatch_editedBatchIsSameBatch_success() {
        uniqueBatchList.add(COKE2);
        uniqueBatchList.setBatch(COKE2, COKE2);
        UniqueBatchList expectedUniqueBatchList = new UniqueBatchList();
        expectedUniqueBatchList.add(COKE2);
        assertEquals(expectedUniqueBatchList, uniqueBatchList);
    }

    @Test
    void sortBatches() {
        // Sets uniqueBatchList
        uniqueBatchList.setBatches(getTypicalBatches());
    }
}
