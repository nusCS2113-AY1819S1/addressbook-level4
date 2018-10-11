package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.BookInventory;
import seedu.address.model.ReadOnlyBookInventory;
import seedu.address.model.book.Book;

/**
 * An Immutable BookInventory that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Book Inventory list contains duplicate book(s).";

    @XmlElement
    private List<XmlAdaptedBook> persons;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        persons = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyBookInventory src) {
        this();
        persons.addAll(src.getBookList().stream().map(XmlAdaptedBook::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code BookInventory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedBook}.
     */
    public BookInventory toModelType() throws IllegalValueException {
        BookInventory bookInventory = new BookInventory();
        for (XmlAdaptedBook p : persons) {
            Book book = p.toModelType();
            if (bookInventory.hasPerson(book)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            bookInventory.addBook(book);
        }
        return bookInventory;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableAddressBook)) {
            return false;
        }
        return persons.equals(((XmlSerializableAddressBook) other).persons);
    }
}
