package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalBooks.ALICE;
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
        assertTrue(ALICE.isSameBook(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameBook(null));

        // different phone and email -> returns false
        Book editedAlice = new BookBuilder(ALICE).withIsbn(VALID_PHONE_BOB).withPrice(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameBook(editedAlice));

        // different name -> returns false
        editedAlice = new BookBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameBook(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new BookBuilder(ALICE).withPrice(VALID_EMAIL_BOB).withQuantity(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameBook(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new BookBuilder(ALICE).withIsbn(VALID_PHONE_BOB).withQuantity(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameBook(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new BookBuilder(ALICE).withQuantity(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameBook(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Book aliceCopy = new BookBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different book -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Book editedAlice = new BookBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new BookBuilder(ALICE).withIsbn(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new BookBuilder(ALICE).withPrice(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new BookBuilder(ALICE).withQuantity(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new BookBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
