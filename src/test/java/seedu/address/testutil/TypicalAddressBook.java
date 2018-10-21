package seedu.address.testutil;

import static seedu.address.testutil.TypicalGroups.getTypicalGroups;
import static seedu.address.testutil.TypicalPersons.getMultipleValidTypicalPerson;
import static seedu.address.testutil.TypicalPersons.getSingleValidTypicalPerson;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import seedu.address.model.AddressBook;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Group}{@code Person} objects to be used in tests.
 */
public class TypicalAddressBook {
    private TypicalAddressBook() {} // prevents instantiation

    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        for (Group group : getTypicalGroups()) {
            ab.createGroup(group);
        }
        return ab;
    }

    public static AddressBook getSingleTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getSingleValidTypicalPerson()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static AddressBook getMultipleTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getMultipleValidTypicalPerson()) {
            ab.addPerson(person);
        }
        return ab;
    }
}
