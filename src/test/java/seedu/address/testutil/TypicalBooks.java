package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_COST_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COST_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.BookInventory;
import seedu.address.model.book.Book;

/**
 * A utility class containing a list of {@code Book} objects to be used in tests.
 */
public class TypicalBooks {

    public static final Book ALICE = new BookBuilder()
            .withName("Alice Pauline")
            .withIsbn("978-3-16-148410-0")
            .withPrice("8.88")
            .withCost("6.66")
            .withQuantity("123")
            .withTags("friends").build();
    public static final Book BENSON = new BookBuilder()
            .withName("Benson Meier")
            .withIsbn("978-3-16-148410-0")
            .withPrice("7.77")
            .withCost("6.66")
            .withQuantity("311")
            .withTags("owesMoney", "friends").build();
    public static final Book CARL = new BookBuilder()
            .withName("Carl Kurz")
            .withIsbn("978-3-16-148410-0")
            .withPrice("6.66")
            .withCost("6.66")
            .withQuantity("111").build();
    public static final Book DANIEL = new BookBuilder()
            .withName("Daniel Meier")
            .withIsbn("978-3-16-148410-0")
            .withPrice("5.55")
            .withCost("6.66")
            .withQuantity("10")
            .withTags("friends").build();
    public static final Book ELLE = new BookBuilder()
            .withName("Elle Meyer")
            .withIsbn("978-3-16-148410-0")
            .withPrice("4.44")
            .withCost("6.66")
            .withQuantity("1").build();
    public static final Book FIONA = new BookBuilder()
            .withName("Fiona Kunz")
            .withIsbn("978-3-16-148410-0")
            .withPrice("3.33")
            .withCost("6.66")
            .withQuantity("2").build();
    public static final Book GEORGE = new BookBuilder()
            .withName("George Best")
            .withIsbn("978-3-16-148410-0")
            .withPrice("2.22")
            .withCost("6.66")
            .withQuantity("4").build();

    // Manually added
    public static final Book HOON = new BookBuilder()
            .withName("Hoon Meier")
            .withIsbn("978-3-16-148410-0")
            .withPrice("1.11")
            .withCost("6.66")
            .withQuantity("2").build();
    public static final Book IDA = new BookBuilder()
            .withName("Ida Mueller")
            .withIsbn("978-3-16-148410-0")
            .withPrice("0.11")
            .withCost("6.66")
            .withQuantity("1").build();

    // Manually added - Book's details found in {@code CommandTestUtil}
    public static final Book AMY = new BookBuilder()
            .withName(VALID_NAME_AMY)
            .withIsbn(VALID_ISBN_AMY)
            .withPrice(VALID_PRICE_AMY)
            .withCost(VALID_COST_AMY)
            .withQuantity(VALID_QUANTITY_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Book BOB = new BookBuilder()
            .withName(VALID_NAME_BOB)
            .withIsbn(VALID_ISBN_BOB)
            .withPrice(VALID_PRICE_BOB)
            .withCost(VALID_COST_BOB)
            .withQuantity(VALID_QUANTITY_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalBooks() {} // prevents instantiation

    /**
     * Returns an {@code BookInventory} with all the typical persons.
     */
    public static BookInventory getTypicalBookInventory() {
        BookInventory bi = new BookInventory();
        for (Book book : getTypicalBooks()) {
            bi.addBook(book);
        }
        return bi;
    }

    public static List<Book> getTypicalBooks() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
