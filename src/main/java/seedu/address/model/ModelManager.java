package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Queue;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.BookInventoryChangedEvent;
import seedu.address.model.book.Book;

/**
 * Represents the in-memory model of the BookInventory data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedBookInventory versionedAddressBook;
    private final FilteredList<Book> filteredBooks;

    /**
     * Initializes a ModelManager with the given bookInventory and userPrefs.
     */
    public ModelManager(ReadOnlyBookInventory bookInventory, UserPrefs userPrefs) {
        super();
        requireAllNonNull(bookInventory, userPrefs);

        logger.fine("Initializing with address book: " + bookInventory + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedBookInventory(bookInventory);
        filteredBooks = new FilteredList<>(versionedAddressBook.getBookList());
    }

    public ModelManager() {
        this(new BookInventory(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyBookInventory newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyBookInventory getBookInventory() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new BookInventoryChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasBook(Book book) {
        requireNonNull(book);
        return versionedAddressBook.hasBook(book);
    }

    @Override
    public Book getBook(String isbn) {
        requireNonNull(isbn);
        return versionedAddressBook.getBook(isbn);
    }

    @Override
    public void deleteBook(Book target) {
        versionedAddressBook.removeBook(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addBook(Book book) {
        versionedAddressBook.addBook(book);
        updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        indicateAddressBookChanged();
    }

    @Override
    public void updateBook(Book target, Book editedBook) {
        requireAllNonNull(target, editedBook);

        versionedAddressBook.updateBook(target, editedBook);
        indicateAddressBookChanged();
    }

    @Override
    public void sortBooksUsingQuantity() {
        versionedAddressBook.sortBooks();
    }
    //=========== Filtered Book List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Book} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Book> getFilteredBookList() {
        return FXCollections.unmodifiableObservableList(filteredBooks);
    }

    @Override
    public void updateFilteredBookList(Predicate<Book> predicate) {
        requireNonNull(predicate);
        filteredBooks.setPredicate(predicate);
    }
    //=========== Tab Pressed ===============================================================================

    @Override
    public Queue<String> getCompleteIsbn(String isbnText) {
        return versionedAddressBook.getCompleteIsbn(isbnText);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitBookInventory() {
        versionedAddressBook.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredBooks.equals(other.filteredBooks);
    }

}
