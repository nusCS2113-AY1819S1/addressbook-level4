package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
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

import seedu.address.model.BookInventory;
import seedu.address.model.book.Book;

/**
 * A utility class containing a list of {@code Book} objects to be used in tests.
 */
public class TypicalBooks {

    public static final Book ALICE = new BookBuilder().withName("Alice Pauline")
            .withQuantity("123").withPrice("8.88")
            .withIsbn("9435125333")
            .withTags("friends").build();
    public static final Book BENSON = new BookBuilder().withName("Benson Meier")
            .withQuantity("311")
            .withPrice("7.77").withIsbn("9876543222")
            .withTags("owesMoney", "friends").build();
    public static final Book CARL = new BookBuilder().withName("Carl Kurz").withIsbn("9535256333")
            .withPrice("6.66").withQuantity("111").build();
    public static final Book DANIEL = new BookBuilder().withName("Daniel Meier").withIsbn("8765253333")
            .withPrice("5,55").withQuantity("10").withTags("friends").build();
    public static final Book ELLE = new BookBuilder().withName("Elle Meyer").withIsbn("948222444")
            .withPrice("4.44").withQuantity("1").build();
    public static final Book FIONA = new BookBuilder().withName("Fiona Kunz").withIsbn("948242777")
            .withPrice("3.33").withQuantity("2").build();
    public static final Book GEORGE = new BookBuilder().withName("George Best").withIsbn("948244222")
            .withPrice("2.22").withQuantity("4").build();

    // Manually added
    public static final Book HOON = new BookBuilder().withName("Hoon Meier").withIsbn("8482424")
            .withPrice("1.11").withQuantity("2").build();
    public static final Book IDA = new BookBuilder().withName("Ida Mueller").withIsbn("8482131")
            .withPrice("0.11").withQuantity("1").build();

    // Manually added - Book's details found in {@code CommandTestUtil}
    public static final Book AMY = new BookBuilder().withName(VALID_NAME_AMY).withIsbn(VALID_PHONE_AMY)
            .withPrice(VALID_EMAIL_AMY).withQuantity(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Book BOB = new BookBuilder().withName(VALID_NAME_BOB).withIsbn(VALID_PHONE_BOB)
            .withPrice(VALID_EMAIL_BOB).withQuantity(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalBooks() {} // prevents instantiation

    /**
     * Returns an {@code BookInventory} with all the typical persons.
     */
    public static BookInventory getTypicalAddressBook() {
        BookInventory ab = new BookInventory();
        for (Book book : getTypicalPersons()) {
            ab.addPerson(book);
        }
        return ab;
    }

    public static List<Book> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
