package com.t13g2.forum.testutil;

import com.t13g2.forum.model.ForumBook;
import com.t13g2.forum.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ForumBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private ForumBook addressBook;

    public AddressBookBuilder() {
        addressBook = new ForumBook();
    }

    public AddressBookBuilder(ForumBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code ForumBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        addressBook.addPerson(person);
        return this;
    }

    public ForumBook build() {
        return addressBook;
    }
}
