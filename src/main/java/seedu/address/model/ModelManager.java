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

    private final VersionedBookInventory versionedBookInventory;
    private final FilteredList<Book> filteredBooks;

    /**
     * Initializes a ModelManager with the given bookInventory and userPrefs.
     */
    public ModelManager(ReadOnlyBookInventory bookInventory, UserPrefs userPrefs) {
        super();
        requireAllNonNull(bookInventory, userPrefs);

        logger.fine("Initializing with address book: " + bookInventory + " and user prefs " + userPrefs);

        versionedBookInventory = new VersionedBookInventory(bookInventory);
        filteredBooks = new FilteredList<>(versionedBookInventory.getBookList());
    }

    public ModelManager() {
        this(new BookInventory(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyBookInventory newData) {
        versionedBookInventory.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyBookInventory getBookInventory() {
        return versionedBookInventory;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new BookInventoryChangedEvent(versionedBookInventory));
    }

    @Override
    public boolean hasBook(Book book) {
        requireNonNull(book);
        return versionedBookInventory.hasBook(book);
    }

    @Override
    public Book getBook(String isbn) {
        requireNonNull(isbn);
        return versionedBookInventory.getBook(isbn);
    }

    @Override
    public void deleteBook(Book target) {
        versionedBookInventory.removeBook(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addBook(Book book) {
        versionedBookInventory.addBook(book);
        updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        indicateAddressBookChanged();
    }

    @Override
    public void updateBook(Book target, Book editedBook) {
        requireAllNonNull(target, editedBook);

        versionedBookInventory.updateBook(target, editedBook);
        indicateAddressBookChanged();
    }

    @Override
    public void sortBooksUsingQuantity() {
        versionedBookInventory.sortBooks();
    }
    //=========== Filtered Book List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Book} backed by the internal list of
     * {@code versionedBookInventory}
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
        return versionedBookInventory.getCompleteIsbn(isbnText);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedBookInventory.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedBookInventory.canRedo();
    }

    @Override
    public void undoBookInventory() {
        versionedBookInventory.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoBookInventory() {
        versionedBookInventory.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitBookInventory() {
        versionedBookInventory.commit();
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
        return versionedBookInventory.equals(other.versionedBookInventory)
                && filteredBooks.equals(other.filteredBooks);
    }

}
