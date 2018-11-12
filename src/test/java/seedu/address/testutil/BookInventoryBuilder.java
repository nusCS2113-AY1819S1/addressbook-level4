package seedu.address.testutil;

import seedu.address.model.BookInventory;
import seedu.address.model.book.Book;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code BookInventory ab = new BookInventoryBuilder().withBook("John", "Doe").build();}
 */
public class BookInventoryBuilder {

    private BookInventory bookInventory;

    public BookInventoryBuilder() {
        bookInventory = new BookInventory();
    }

    public BookInventoryBuilder(BookInventory bookInventory) {
        this.bookInventory = bookInventory;
    }

    /**
     * Adds a new {@code Book} to the {@code BookInventory} that we are building.
     */
    public BookInventoryBuilder withBook(Book book) {
        bookInventory.addBook(book);
        return this;
    }

    public BookInventory build() {
        return bookInventory;
    }
}
