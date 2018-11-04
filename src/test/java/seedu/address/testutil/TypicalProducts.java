package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DIST_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIST_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFO_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFO_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SN_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SN_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HEALTHY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SWEET;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ProductDatabase;
import seedu.address.model.product.Product;

/**
 * A utility class containing a list of {@code Product} objects to be used in tests.
 */
public class TypicalProducts {

    public static final Product ORANGE = new ProductBuilder().withName("Orange")
            .withInfo("fruit").withDistributor("Ah Huat")
            .withSerialNumber("001")
            .withTags("healthy").build();
    public static final Product GRAPE = new ProductBuilder().withName("Grape")
            .withInfo("fruit")
            .withDistributor("Ah Beng").withSerialNumber("002")
            .withTags("healthy").build();
    public static final Product CHOCOLATE = new ProductBuilder().withName("Chocolate").withSerialNumber("003")
            .withDistributor("Ahmad").withInfo("snack").build();
    public static final Product DORITOS = new ProductBuilder().withName("Doritos").withSerialNumber("004")
            .withDistributor("Snack Shack").withInfo("snack").withTags("unhealthy").build();

    // Manually added
    public static final Product MILO = new ProductBuilder().withName("Milo").withSerialNumber("005")
            .withDistributor("Ali").withInfo("morning").build();
    public static final Product KOPI = new ProductBuilder().withName("Kopi").withSerialNumber("006")
            .withDistributor("Ah Seng").withInfo("morning").build();

    // Manually added - Product's details found in {@code CommandTestUtil}
    public static final Product APPLE =
            new ProductBuilder().withName(VALID_NAME_APPLE).withSerialNumber(VALID_SN_APPLE)
            .withDistributor(VALID_DIST_APPLE).withInfo(VALID_INFO_APPLE).withTags(VALID_TAG_HEALTHY).build();
    public static final Product BANANA =
            new ProductBuilder().withName(VALID_NAME_BANANA).withSerialNumber(VALID_SN_BANANA)
            .withDistributor(VALID_DIST_BANANA).withInfo(VALID_INFO_BANANA).withTags(VALID_TAG_HEALTHY, VALID_TAG_SWEET)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalProducts() {} // prevents instantiation

    /**
     * Returns an {@code ProductDatabase} with all the typical persons.
     */
    public static ProductDatabase getTypicalAddressBook() {
        ProductDatabase ab = new ProductDatabase();
        for (Product product : getTypicalPersons()) {
            ab.addPerson(product);
        }
        return ab;
    }

    public static List<Product> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ORANGE, GRAPE, CHOCOLATE, DORITOS));
    }
}
