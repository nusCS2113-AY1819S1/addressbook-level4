package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.record.Record;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withRecord("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Record} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withRecord(Record record) {
        addressBook.addRecord(record);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
