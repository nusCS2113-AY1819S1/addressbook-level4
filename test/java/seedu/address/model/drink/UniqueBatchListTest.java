package seedu.address.model.drink;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

class UniqueBatchListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueBatchList uniqueBatchList = new UniqueBatchList();

    @Test
    void contains_nullBatch_throwsNullPointerException() {
        thrown.expect(NullPointerException.none());
        uniqueBatchList.contains(null);
    }

    @Test
    void add() {
    }

    @Test
    void remove() {
    }

    @Test
    void sortBatches() {
    }

    @Test
    void setBatch() {
    }

    @Test
    void setBatches() {
    }

    @Test
    void setBatches1() {
    }

    @Test
    void asUnmodifiableObservableList() {
    }
}
