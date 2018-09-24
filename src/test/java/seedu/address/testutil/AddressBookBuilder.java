package seedu.address.testutil;

import seedu.address.model.BookInventory;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code BookInventory ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private BookInventory bookInventory;

    public AddressBookBuilder() {
        bookInventory = new BookInventory();
    }

    public AddressBookBuilder(BookInventory bookInventory) {
        this.bookInventory = bookInventory;
    }

    /**
     * Adds a new {@code Person} to the {@code BookInventory} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        bookInventory.addPerson(person);
        return this;
    }

    public BookInventory build() {
        return bookInventory;
    }
}
