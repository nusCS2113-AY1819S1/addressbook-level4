package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.BookCardHandle;
import guitests.guihandles.BookListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.book.Book;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(BookCardHandle expectedCard, BookCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getQuantity(), actualCard.getQuantity());
        assertEquals(expectedCard.getPrice(), actualCard.getPrice());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedBook}.
     */
    public static void assertCardDisplaysBook(Book expectedBook, BookCardHandle actualCard) {
        assertEquals(expectedBook.getName().fullName, actualCard.getName());
        assertEquals(expectedBook.getIsbn().value, actualCard.getPhone());
        assertEquals(expectedBook.getPrice().value, actualCard.getPrice());
        assertEquals(expectedBook.getQuantity().getValue(), actualCard.getQuantity());
        assertEquals(expectedBook.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code bookListPanelHandle} displays the details of {@code books} correctly and
     * in the correct order.
     */
    public static void assertListMatching(BookListPanelHandle bookListPanelHandle, Book... books) {
        for (int i = 0; i < books.length; i++) {
            bookListPanelHandle.navigateToCard(i);
            assertCardDisplaysBook(books[i], bookListPanelHandle.getBookCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code bookListPanelHandle} displays the details of {@code books} correctly and
     * in the correct order.
     */
    public static void assertListMatching(BookListPanelHandle bookListPanelHandle, List<Book> books) {
        assertListMatching(bookListPanelHandle, books.toArray(new Book[0]));
    }

    /**
     * Asserts the size of the list in {@code bookListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(BookListPanelHandle bookListPanelHandle, int size) {
        int numberOfPeople = bookListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
