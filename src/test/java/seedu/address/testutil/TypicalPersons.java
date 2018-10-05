package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ARDUINO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.StockList;
import seedu.address.model.item.Item;

/**
 * A utility class containing a list of {@code Item} objects to be used in tests.
 */
public class TypicalItems {

    public static final Item ALICE = new ItemBuilder().withName("Alice Pauline")
            .withStatus("123, Jurong West Ave 6, #08-111").withMinQuantity("alice@example.com")
            .withQuantity("94351253")
            .withTags("friends").build();
    public static final Item BENSON = new ItemBuilder().withName("Benson Meier")
            .withStatus("311, Clementi Ave 2, #02-25")
            .withMinQuantity("johnd@example.com").withQuantity("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Item CARL = new ItemBuilder().withName("Carl Kurz").withQuantity("95352563")
            .withMinQuantity("heinz@example.com").withStatus("wall street").build();
    public static final Item DANIEL = new ItemBuilder().withName("Daniel Meier").withQuantity("87652533")
            .withMinQuantity("cornelia@example.com").withStatus("10th street").withTags("friends").build();
    public static final Item ELLE = new ItemBuilder().withName("Elle Meyer").withQuantity("9482224")
            .withMinQuantity("werner@example.com").withStatus("michegan ave").build();
    public static final Item FIONA = new ItemBuilder().withName("Fiona Kunz").withQuantity("9482427")
            .withMinQuantity("lydia@example.com").withStatus("little tokyo").build();
    public static final Item GEORGE = new ItemBuilder().withName("George Best").withQuantity("9482442")
            .withMinQuantity("anna@example.com").withStatus("4th street").build();

    // Manually added
    public static final Item HOON = new ItemBuilder().withName("Hoon Meier").withQuantity("8482424")
            .withMinQuantity("stefan@example.com").withStatus("little india").build();
    public static final Item IDA = new ItemBuilder().withName("Ida Mueller").withQuantity("8482131")
            .withMinQuantity("hans@example.com").withStatus("chicago ave").build();

    // Manually added - Item's details found in {@code CommandTestUtil}
    public static final Item AMY = new ItemBuilder().withName(VALID_NAME_AMY).withQuantity(VALID_PHONE_AMY)
            .withMinQuantity(VALID_EMAIL_AMY).withStatus(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Item BOB = new ItemBuilder().withName(VALID_NAME_BOB).withQuantity(VALID_PHONE_BOB)
            .withMinQuantity(VALID_EMAIL_BOB).withStatus(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalItems() {} // prevents instantiation

    /**
     * Returns an {@code StockList} with all the typical items.
     */
    public static StockList getTypicalStockList() {
        StockList ab = new StockList();
        for (Item item : getTypicalItems()) {
            ab.addItem(item);
        }
        return ab;
    }

    public static List<Item> getTypicalItems() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
