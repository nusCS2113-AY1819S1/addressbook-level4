package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_COST_ADD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COST_BIOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_ADD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_BIOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ADD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BIOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_ADD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BIOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_ADD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BIOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SCIENCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_STUDIES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.BookInventory;
import seedu.address.model.book.Book;

/**
 * A utility class containing a list of {@code Book} objects to be used in tests.
 */
public class TypicalBooks {

    public static final Book ART = new BookBuilder()
            .withName("Art")
            .withIsbn("9780748137992")
            .withPrice("73.76")
            .withCost("19.99")
            .withQuantity("5")
            .withTags("studies").build();
    public static final Book BIOLOGY = new BookBuilder()
            .withName("Biology")
            .withIsbn("9781401309572")
            .withPrice("58.98")
            .withCost("19.99")
            .withQuantity("10")
            .withTags("science", "studies").build();
    public static final Book CHEMISTRY = new BookBuilder()
            .withName("Chemistry")
            .withIsbn("9780062294432")
            .withPrice("6.66")
            .withCost("6.66")
            .withQuantity("15").build();
    public static final Book DARWIN = new BookBuilder()
            .withName("Digital Darwinism")
            .withIsbn("9780062472601")
            .withPrice("5.55")
            .withCost("6.66")
            .withQuantity("20")
            .withTags("friends").build();
    public static final Book ELEMENTARY = new BookBuilder()
            .withName("Elementary Statistics: Picturing the World")
            .withIsbn("9780767905923")
            .withPrice("4.44")
            .withCost("6.66")
            .withQuantity("25").build();
    public static final Book FINANCIAL = new BookBuilder()
            .withName("Financial Accounting")
            .withIsbn("9781401310462")
            .withPrice("3.33")
            .withCost("6.66")
            .withQuantity("30").build();
    public static final Book GEOGRAPHY = new BookBuilder()
            .withName("Geography")
            .withIsbn("9781401312855")
            .withPrice("2.22")
            .withCost("6.66")
            .withQuantity("35").build();

    // Manually added
    public static final Book HISTORY = new BookBuilder()
            .withName("History")
            .withIsbn("9781401308582")
            .withPrice("1.11")
            .withCost("6.66")
            .withQuantity("40").build();
    public static final Book IT = new BookBuilder()
            .withName("Introduction to IT")
            .withIsbn("9780062294449")
            .withPrice("0.11")
            .withCost("6.66")
            .withQuantity("45").build();

    // Manually added - Book's details found in {@code CommandTestUtil}
    public static final Book ADD = new BookBuilder()
            .withName(VALID_NAME_ADD)
            .withIsbn(VALID_ISBN_ADD)
            .withPrice(VALID_PRICE_ADD)
            .withCost(VALID_COST_ADD)
            .withQuantity(VALID_QUANTITY_ADD)
            .withTags(VALID_TAG_STUDIES).build();
    public static final Book BOB = new BookBuilder()
            .withName(VALID_NAME_BIOLOGY)
            .withIsbn(VALID_ISBN_BIOLOGY)
            .withPrice(VALID_PRICE_BIOLOGY)
            .withCost(VALID_COST_BIOLOGY)
            .withQuantity(VALID_QUANTITY_BIOLOGY)
            .withTags(VALID_TAG_SCIENCE, VALID_TAG_STUDIES).build();

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
        return new ArrayList<>(Arrays.asList(ART, BIOLOGY, CHEMISTRY, DARWIN, ELEMENTARY, FINANCIAL, GEOGRAPHY));
    }
}
