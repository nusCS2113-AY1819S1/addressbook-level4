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

    private final UniqueBookList persons;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniqueBookList();
    }

    public BookInventory() {}

    /**
     * Creates an BookInventory using the Persons in the {@code toBeCopied}
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
    public void setPersons(List<Book> books) {
        this.persons.setPersons(books);
    }

    /**
     * Resets the existing data of this {@code BookInventory} with {@code newData}.
     */
    public void resetData(ReadOnlyBookInventory newData) {
        requireNonNull(newData);

        setPersons(newData.getBookList());
    }

    //// book-level operations

    /**
     * Returns true if a book with the same identity as {@code book} exists in the address book.
     */
    public boolean hasPerson(Book book) {
        requireNonNull(book);
        return persons.contains(book);
    }

    /**
     * Adds a book to the address book.
     * The book must not already exist in the address book.
     */
    public void addPerson(Book p) {
        persons.add(p);
    }

    /**
     * Replaces the given book {@code target} in the list with {@code editedBook}.
     * {@code target} must exist in the address book.
     * The book identity of {@code editedBook} must not be the same as another existing book in the address book.
     */
    public void updatePerson(Book target, Book editedBook) {
        requireNonNull(editedBook);

        persons.setPerson(target, editedBook);
    }

    /**
     * Removes {@code key} from this {@code BookInventory}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Book key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Book> getBookList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookInventory // instanceof handles nulls
                && persons.equals(((BookInventory) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
