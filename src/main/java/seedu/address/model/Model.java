package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.book.Book;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Book> PREDICATE_SHOW_ALL_BOOKS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyBookInventory newData);

    /** Returns the BookInventory */
    ReadOnlyBookInventory getBookInventory();

    /**
     * Returns true if a book with the same identity as {@code book} exists in the BookInventory.
     */
    boolean hasBook(Book book);

    /**
     * Deletes the given book.
     * The book must exist in the BookInventory.
     */
    void deleteBook(Book target);

    /**
     * Adds the given book.
     * {@code book} must not already exist in the BookInventory.
     */
    void addBook(Book book);

    /**
     * Replaces the given book {@code target} with {@code editedBook}.
     * {@code target} must exist in the BookInventory.
     * The book identity of {@code editedBook} must not be the same as another existing book in the BookInventory.
     */
    void updateBook(Book target, Book editedBook);

    /** Returns an unmodifiable view of the filtered book list */
    ObservableList<Book> getFilteredBookList();

    /**
     * Updates the filter of the filtered book list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBookList(Predicate<Book> predicate);

    /**
     * Sorts the books according the their quantity
     */
    void sortBooksUsingQuantity();

    /**
     * Returns true if the model has previous BookInventory states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone BookInventory states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's BookInventory to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's BookInventory to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current BookInventory state for undo/redo.
     */
    void commitBookInventory();
}
