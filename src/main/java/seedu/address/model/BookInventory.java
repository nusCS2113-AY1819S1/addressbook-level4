package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.book.Book;
import seedu.address.model.book.UniqueBookList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameBook comparison)
 */
public class BookInventory implements ReadOnlyBookInventory {

    private final UniqueBookList books;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        books = new UniqueBookList();
    }

    public BookInventory() {}

    /**
     * Creates an BookInventory using the Books in the {@code toBeCopied}
     */
    public BookInventory(ReadOnlyBookInventory toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the book list with {@code books}.
     * {@code books} must not contain duplicate books.
     */
    public void setBooks(List<Book> books) {
        this.books.setBooks(books);
    }

    /**
     * Resets the existing data of this {@code BookInventory} with {@code newData}.
     */
    public void resetData(ReadOnlyBookInventory newData) {
        requireNonNull(newData);

        setBooks(newData.getBookList());
    }

    /**
     * Sorts the {@code BookInventory} according to quantity
     */
    public void sortBooks() {
        this.books.sortBooks();
    }

    //// book-level operations

    /**
     * Returns true if a book with the same identity as {@code book} exists in the BookInventory.
     */
    public boolean hasBook(Book book) {
        requireNonNull(book);
        return books.contains(book);
    }

    /**
     * Finds book in the list with the isbn provided
     * @param isbn the 10/13 digits assigned to books
     * @return the book with the corresponding {@param isbn}
     */
    public Book getBook(String isbn) {
        requireNonNull(isbn);
        return books.getBook(isbn);
    }

    /**
     * Adds a book to the BookInventory.
     * The book must not already exist in the BookInventory.
     */
    public void addBook(Book p) {
        books.add(p);
    }

    /**
     * Replaces the given book {@code target} in the list with {@code editedBook}.
     * {@code target} must exist in the BookInventory.
     * The book identity of {@code editedBook} must not be the same as another existing book in the BookInventory.
     */
    public void updateBook(Book target, Book editedBook) {
        requireNonNull(editedBook);

        books.setBook(target, editedBook);
    }

    /**
     * Removes {@code key} from this {@code BookInventory}.
     * {@code key} must exist in the BookInventory.
     */
    public void removeBook(Book key) {
        books.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return books.asUnmodifiableObservableList().size() + " books";
        // TODO: refine later
    }

    @Override
    public ObservableList<Book> getBookList() {
        return books.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookInventory // instanceof handles nulls
                && books.equals(((BookInventory) other).books));
    }

    @Override
    public int hashCode() {
        return books.hashCode();
    }
}
