package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Department;
import seedu.address.model.person.Designation;
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
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Department("Admin"), new Designation("Manager"),
                getTagSet("overseas", "threeweeks", "mountainview")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Department("Publicity"),
                new Designation("Employee"), getTagSet("senior", "photoshop", "camera")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Department("Finance"),
                new Designation("Employee"), getTagSet("leave", "sick")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Department("Marketing"),
                new Designation("Manager"), getTagSet("exchange", "UK")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Department("Finance"),
                new Designation("Manager"), getTagSet("tenure")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Department("Admin"), new Designation("Employee"),
                getTagSet("intern", "NUS", "business")),
            new Person(new Name("Edric Grishman"), new Phone("92743301"), new Email("edricg@example.com"),
                new Address("Blk 2F Kent Ridge Tier 2, #13-16"), new Department("Admin"),
                new Designation("Employee"), getTagSet("intern", "new")),
            new Person(new Name("Farhan Ishmak"), new Phone("82390124"), new Email("farhanish@example.com"),
                new Address("Blk 17 Changi Village, #01-11"), new Department("Finance"),
                new Designation("Employee"), getTagSet("gettingmarried")),
            new Person(new Name("Lee Ji Eun"), new Phone("91328341"), new Email("dlwlrma@example.com"),
                new Address("Blk 26 Fave Street, #05-16"), new Department("Management"),
                new Designation("Manager"), getTagSet("onleave", "personalreasons")),
            new Person(new Name("Elon Tusk"), new Phone("69420420"), new Email("elont@example.com"),
                new Address("Blk X Space, #99-01"), new Department("Management"),
                new Designation("Manager"), getTagSet("CEO", "Founder")),
            new Person(new Name("Edward Newgate"), new Phone("92348610"), new Email("whitebeard@example.com"),
                new Address("Blk 0 New World, #01-24"), new Department("Finance"),
                new Designation("Manager"), getTagSet("emperor")),
            new Person(new Name("Paul Drogba"), new Phone("86239134"), new Email("reddevil@example.com"),
                new Address("Blk 06 Old Trafford, #06-06"), new Department("Publicity"),
                new Designation("Employee"), getTagSet("editing"))
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

    /**
     * Returns an attendee set containing the list of strings given.
     */
    public static Set<String> getAttendeeSet(String... strings) {
        return Arrays.stream(strings)
                .collect(Collectors.toSet());
    }

}
