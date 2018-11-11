package seedu.address.testutil.batch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.drink.Batch;
import seedu.address.model.drink.UniqueBatchList;

/**
 * A utility class containing a list of {@code Batch} objects to be used in tests.
 */
public class TypicalBatches {
    // In inventory list
    public static final Batch COKE1 = new BatchBuilder().withId("100000002").withQuantity("40")
            .withDate("01/10/2018").build();
    public static final Batch COKE2 = new BatchBuilder().withId("100000003").withQuantity("20")
            .withDate("15/10/2018").build();
    public static final Batch COKE3 = new BatchBuilder().withId("100000004").withQuantity("30")
            .withDate("01/11/2018").build();
    public static final Batch COKE4 = new BatchBuilder().withId("100000005").withQuantity("50")
            .withDate("04/11/2018").build();
    public static final Batch COKE5 = new BatchBuilder().withId("100000006").withQuantity("80")
            .withDate("11/11/2018").build();
    public static final Batch COKE6 = new BatchBuilder().withId("100000007").withQuantity("90")
            .withDate("30/09/2018").build();

    // Manually added
    // Same id as COKE4 to test for duplicates;
    public static final Batch COKE7 = new BatchBuilder().withId("100000005").withQuantity("70")
            .withDate("29/10/2018").build();
    public static final Batch COKE8 = new BatchBuilder().withId("100000010").withQuantity("80")
            .withDate("28/10/2018").build();
    // Same date as COKE4 to test for compiling of batches
    public static final Batch COKE9 = new BatchBuilder().withId("100000008").withQuantity("70")
            .withDate("04/11/2018").build();
    // Batch with no quantity
    public static final Batch COKE10 = new BatchBuilder().withId("100000009").withQuantity("0")
            .withDate("05/11/2018").build();

    private TypicalBatches() {
    } // prevents instantiation

    /**
     * Returns an {@code } with all the typical persons.
     */
    public static UniqueBatchList getTypicalUniqueBatchList() {
        UniqueBatchList ubl = new UniqueBatchList();
        for (Batch batch :getTypicalBatches()) {
            ubl.addBatch(batch);
        }
        return ubl;
    }

    public static List<Batch> getTypicalBatches() {
        return new ArrayList<>(Arrays.asList(COKE1, COKE2, COKE3, COKE4, COKE5, COKE6));
    }
}
