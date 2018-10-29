package seedu.address.model.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.drink.Batch;
import seedu.address.model.drink.UniqueBatchList;

/**
 * A utility class containing a list of {@code Batch} objects to be used in tests.
 */
public class TypicalBatches {

    public static final Batch COKE1 = new BatchBuilder().withId("0001").withQuantity("10").withPrice("1.20")
            .withDate("01/11/2018").build();
    public static final Batch COKE2 = new BatchBuilder().withId("0002").withQuantity("20").withPrice("1.20")
            .withDate("02/11/2018").build();
    public static final Batch COKE3 = new BatchBuilder().withId("0003").withQuantity("30").withPrice("1.20")
            .withDate("03/11/2018").build();
    public static final Batch COKE4 = new BatchBuilder().withId("0004").withQuantity("40").withPrice("1.20")
            .withDate("04/11/2018").build();
    public static final Batch COKE5 = new BatchBuilder().withId("0005").withQuantity("50").withPrice("1.20")
            .withDate("05/11/2018").build();
    public static final Batch COKE6 = new BatchBuilder().withId("0006").withQuantity("60").withPrice("1.20")
            .withDate("30/10/2018").build();

    // Manually added
    public static final Batch COKE7 = new BatchBuilder().withId("0007").withQuantity("70").withPrice("1.20")
            .withDate("29/10/2018").build();
    public static final Batch COKE8 = new BatchBuilder().withId("0008").withQuantity("80").withPrice("1.20")
            .withDate("28/10/2018").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    /*
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER
    */
    private TypicalBatches() {
    } // prevents instantiation

    /**
     * Returns an {@code } with all the typical persons.
     */
    public static UniqueBatchList getTypicalUniqueBatchList() {
        UniqueBatchList ubl = new UniqueBatchList();
        for (Batch batch :getTypicalBatches()) {
            ubl.add(batch);
        }
        return ubl;
    }

    public static List<Batch> getTypicalBatches() {
        return new ArrayList<>(Arrays.asList(COKE1, COKE2, COKE3, COKE4, COKE5, COKE6));
    }
}
