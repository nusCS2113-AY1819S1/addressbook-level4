package seedu.address.testutil;

import seedu.address.model.BookInventoryInventory;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code BookInventoryInventory ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private BookInventoryInventory bookInventory;

    public AddressBookBuilder() {
        bookInventory = new BookInventoryInventory();
    }

    public AddressBookBuilder(BookInventoryInventory bookInventory) {
        this.bookInventory = bookInventory;
    }

    /**
     * Adds a new {@code Person} to the {@code BookInventoryInventory} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        bookInventory.addPerson(person);
        return this;
    }

    public BookInventoryInventory build() {
        return bookInventory;
    }
}
