package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_BIOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BIOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BIOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BIOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SCIENCE;
import static seedu.address.testutil.TypicalBooks.ART;
import static seedu.address.testutil.TypicalBooks.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.BookBuilder;

public class BookTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Book book = new BookBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        book.getTags().remove(0);
    }

    @Test
    public void isSameBook() {
        // same object -> returns true
        assertTrue(ART.isSameBook(ART));

        // null -> returns false
        assertFalse(ART.isSameBook(null));

        // different phone and email -> returns false
        Book editedAlice = new BookBuilder(ART).withIsbn(VALID_ISBN_BIOLOGY).withPrice(VALID_PRICE_BIOLOGY).build();
        assertFalse(ART.isSameBook(editedAlice));

        // different name -> returns false
        editedAlice = new BookBuilder(ART).withName(VALID_NAME_BIOLOGY).build();
        assertTrue(ART.isSameBook(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new BookBuilder(ART).withPrice(VALID_PRICE_BIOLOGY).withQuantity(VALID_QUANTITY_BIOLOGY)
                .withTags(VALID_TAG_SCIENCE).build();
        assertTrue(ART.isSameBook(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new BookBuilder(ART).withIsbn(VALID_ISBN_BIOLOGY).withQuantity(VALID_QUANTITY_BIOLOGY)
                .withTags(VALID_TAG_SCIENCE).build();
        assertFalse(ART.isSameBook(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new BookBuilder(ART).withQuantity(VALID_QUANTITY_BIOLOGY).withTags(VALID_TAG_SCIENCE).build();
        assertTrue(ART.isSameBook(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Book aliceCopy = new BookBuilder(ART).build();
        assertTrue(ART.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ART.equals(ART));

        // null -> returns false
        assertFalse(ART.equals(null));

        // different type -> returns false
        assertFalse(ART.equals(5));

        // different book -> returns false
        assertFalse(ART.equals(BOB));

        // different name -> returns false
        Book editedAlice = new BookBuilder(ART).withName(VALID_NAME_BIOLOGY).build();
        assertFalse(ART.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new BookBuilder(ART).withIsbn(VALID_ISBN_BIOLOGY).build();
        assertFalse(ART.equals(editedAlice));

        // different email -> returns false
        editedAlice = new BookBuilder(ART).withPrice(VALID_PRICE_BIOLOGY).build();
        assertFalse(ART.equals(editedAlice));

        // different address -> returns false
        editedAlice = new BookBuilder(ART).withQuantity(VALID_QUANTITY_BIOLOGY).build();
        assertFalse(ART.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new BookBuilder(ART).withTags(VALID_TAG_SCIENCE).build();
        assertFalse(ART.equals(editedAlice));
    }
}
