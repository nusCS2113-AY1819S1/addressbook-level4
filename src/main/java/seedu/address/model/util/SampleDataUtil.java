package seedu.address.model.util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.enrolledClass.EnrolledClass;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.person.TimeSlots;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"),
                        getTagSet("friends"), getEnrolledClassMap("CS2101", "CS2113T"),
                        TimeSlots.sampleTimeSlots()),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        getTagSet("colleagues", "friends"), getEnrolledClassMap("CS2105", "CS2106"),
                        TimeSlots.sampleTimeSlots()),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        getTagSet("neighbours"), getEnrolledClassMap("CS2101", "CS2113T"),
                        TimeSlots.sampleTimeSlots()),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        getTagSet("family"), getEnrolledClassMap("CS3235", "CS3236"),
                        TimeSlots.sampleTimeSlots()),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"),
                        getTagSet("classmates"), getEnrolledClassMap("CS1010", "CS1231"),
                        TimeSlots.sampleTimeSlots()),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"),
                        getTagSet("colleagues"), getEnrolledClassMap("CS2101", "CS2113T"),
                        TimeSlots.sampleTimeSlots())
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
     * Returns a enrolled class map containing the list of strings given.
     */
    public static Map<String, EnrolledClass> getEnrolledClassMap(String... strings) {
        Map<String, EnrolledClass> enrolledClassMap = new TreeMap<>();
        EnrolledClass tempEnrolledClass;
        for(String enrolledClassName : strings){
            tempEnrolledClass = new EnrolledClass(enrolledClassName);
            enrolledClassMap.put(tempEnrolledClass.enrolledClassName, tempEnrolledClass);
        }
        return enrolledClassMap;
    }

}
