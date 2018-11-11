package seedu.address.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.request.RequestList;
import seedu.address.testutil.BookBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BIOLOGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SCIENCE;
import static seedu.address.testutil.TypicalBooks.ART;
import static seedu.address.testutil.TypicalBooks.getTypicalBookInventory;

public class RequestListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final RequestList requestList = new RequestList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), requestList.getRequestList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        requestList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        BookInventory newData = getTypicalBookInventory();
        requestList.resetData(newData);
        assertEquals(newData, requestList);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two books with the same identity fields
        Book editedAlice = new BookBuilder(ART).withQuantity(VALID_QUANTITY_BIOLOGY).withTags(VALID_TAG_SCIENCE)
                .build();
        List<Book> newBooks = Arrays.asList(ART, editedAlice);
        BookInventoryStub newData = new BookInventoryStub(newBooks);

        thrown.expect(DuplicateBookException.class);
        requestList.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        requestList.hasBook(null);
    }

    @Test
    public void hasBook_bookNotInBookInventory_returnsFalse() {
        assertFalse(bookInventory.hasBook(ART));
    }

    @Test
    public void hasBook_bookInBookInventory_returnsTrue() {
        requestList.addBook(ART);
        assertTrue(bookInventory.hasBook(ART));
    }

    @Test
    public void hasBook_bookWithSameIdentityFieldsInBookInventory_returnsTrue() {
        requestList.addBook(ART);
        Book editedAlice = new BookBuilder(ART).withQuantity(VALID_QUANTITY_BIOLOGY).withTags(VALID_TAG_SCIENCE)
                .build();
        assertTrue(bookInventory.hasBook(editedAlice));
    }

    @Test
    public void getBookList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        requestList.getBookList().remove(0);
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
