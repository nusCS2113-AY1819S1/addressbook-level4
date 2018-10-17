package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupLocation;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nationality;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {

                new Person(new Name("Alex Yeoh"), new Gender("Male"), new Nationality("SG"),
                        new Phone("87438807"), new Email("alexyeoh@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"),
                        new Grade("100"), getTagSet("friends"),null),

                new Person(new Name("Bernice Yu"), new Gender("Female"), new Nationality("SG"),
                        new Phone("99272758"), new Email("berniceyu@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        new Grade("100"), getTagSet("colleagues", "friends"),null),

                new Person(new Name("Charlotte Oliveiro"), new Gender("F"), new Nationality("AU"),
                        new Phone("93210283"), new Email("charlotte@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        new Grade("100"), getTagSet("neighbours"),null),
                new Person(new Name("David Li"), new Gender("M"), new Nationality("CN"),
                        new Phone("91031282"), new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        new Grade("100"), getTagSet("family"),null),
                new Person(new Name("Irfan Ibrahim"), new Gender("Male"), new Nationality("MY"),
                        new Phone("92492021"), new Email("irfan@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"),
                        new Grade("100"), getTagSet("classmates"),null),
                new Person(new Name("Roy Balakrishnan"), new Gender("M"), new Nationality("IN"),
                        new Phone("92624417"), new Email("royb@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"),
                        new Grade("100"), getTagSet("colleagues"),null)
        };
    }

    public static Group[] getSampleGroups() {
        return new Group[] {

            new Group(new GroupName("CS2113"), new GroupLocation("LT15"),
                    getTagSet("java", "friday", "4pm", "afternoon")),

            new Group(new GroupName("CS2040C"), new GroupLocation("LT15"), getTagSet("cpp", "tuesday", "5pm")),

            new Group(new GroupName("MA1508"), new GroupLocation("E1-01-01"),
                    getTagSet("maths", "linear", "wednesday", "2pm")),

            new Group(new GroupName("CG2027"), new GroupLocation("E2-02-02"),
                    getTagSet("circuits", "monday", "12pm")),

            new Group(new GroupName("TUT[01]"), new GroupLocation("E3-03-03"),
                    getTagSet("MA1501", "monday", "6pm", "night")),

            new Group(new GroupName("TUT[02]"), new GroupLocation("LT12"),
                    getTagSet("physics", "thursday", "8am", "morning")),

            new Group(new GroupName("TUT[03]"), new GroupLocation("LT12"),
                    getTagSet("physics", "thursday", "10am")),

            new Group(new GroupName("TUT[04]"), new GroupLocation("LT12"),
                    getTagSet("physics", "thursday", "12pm")),

            new Group(new GroupName("TUT[05]"), new GroupLocation("LT12"),
                    getTagSet("physics", "thursday", "4pm")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Group sampleGroup : getSampleGroups()) {
            sampleAb.createGroup(sampleGroup);
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
     * Returns a person set containing the list of person given.
     */
    public static Set<Person> getPersonSet(Person... persons) {
        Set<Person> personSet = new HashSet<>();
        for (Person p : persons) {
            personSet.add(p);
        }
        return personSet;
    }

}
