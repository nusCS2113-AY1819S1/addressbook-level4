package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("John Doe"), new Phone("87438807"), new Email("john_doe@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), SampleTimeTableUtil.getTimeTableJohnDoe(), new HashSet<>()),
            new Person(new Name("John Roe"), new Phone("99272758"), new Email("rowjohn@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"), SampleTimeTableUtil.getTimeTableJohnRoe(), new HashSet<>()),
            new Person(new Name("Johnny Doe"), new Phone("93210283"), new Email("johnny@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), SampleTimeTableUtil.getTimeTableJohnnyDoe(), new HashSet<>()),
            new Person(new Name("Benson Meier"), new Phone("91031282"), new Email("meierbenson@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), SampleTimeTableUtil.getTimeTableBensonMeier(), new HashSet<>()),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
