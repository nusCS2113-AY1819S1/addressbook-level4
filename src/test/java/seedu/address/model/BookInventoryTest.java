package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalBooks.ALICE;
import static seedu.address.testutil.TypicalBooks.getTypicalBookInventory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.testutil.BookBuilder;

public class BookInventoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final BookInventory bookInventory = new BookInventory();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), bookInventory.getBookList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        bookInventory.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        BookInventory newData = getTypicalBookInventory();
        bookInventory.resetData(newData);
        assertEquals(newData, bookInventory);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two books with the same identity fields
        Book editedAlice = new BookBuilder(ALICE).withQuantity(VALID_QUANTITY_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Book> newBooks = Arrays.asList(ALICE, editedAlice);
        BookInventoryStub newData = new BookInventoryStub(newBooks);

        thrown.expect(DuplicateBookException.class);
        bookInventory.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        bookInventory.hasBook(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(bookInventory.hasBook(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        bookInventory.addBook(ALICE);
        assertTrue(bookInventory.hasBook(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        bookInventory.addBook(ALICE);
        Book editedAlice = new BookBuilder(ALICE).withQuantity(VALID_QUANTITY_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(bookInventory.hasBook(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        bookInventory.getBookList().remove(0);
    }

    /**
     * A stub ReadOnlyBookInventory whose books list can violate interface constraints.
     */
    private static class BookInventoryStub implements ReadOnlyBookInventory {
        private final ObservableList<Book> books = FXCollections.observableArrayList();

        BookInventoryStub(Collection<Book> books) {
            this.books.setAll(books);
        }

        @Override
        public ObservableList<Book> getBookList() {
            return books;
        }
    }

}
