package seedu.address.model.drink;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

class UniqueBatchListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueBatchList uniqueBatchList = new UniqueBatchList();

    @Test
    public void contains_nullBatch_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
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
