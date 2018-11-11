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
                getTagSet("CS2113T-F16-1"), SampleTimeTableUtil.getTimeTableJohnDoe(), new HashSet<>()),
            new Person(new Name("John Roe"), new Phone("99272758"), new Email("rowjohn@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("CS2113T-F16-1", "CS2101-A"), SampleTimeTableUtil.getTimeTableJohnRoe(), new HashSet<>()),
            new Person(new Name("Johnny Doe"), new Phone("93210283"), new Email("johnny@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("CS2113T-F16-1"), SampleTimeTableUtil.getTimeTableJohnnyDoe(), new HashSet<>()),
            new Person(new Name("Benson Meier"), new Phone("91031282"), new Email("meierbenson@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("CS2113T-F16-1", "CS2101-B"), SampleTimeTableUtil.getTimeTableBensonMeier(), new HashSet<>()),
            new Person(new Name("NF"), new Phone("12345678"), new Email("nianfei97@example.com"),
                new Address("NUS"),
                getTagSet("CS2113T-W13-1", "CS2101-A"), SampleTimeTableUtil.getTimeTableNf(), new HashSet<>()),
            new Person(new Name("Ben"), new Phone("98765432"), new Email("bennchong@example.com"),
                new Address("NUS"),
                getTagSet("CS2113T-W13-1", "CS2101-B"), SampleTimeTableUtil.getTimeTableBen(), new HashSet<>()),
            new Person(new Name("Jasper"), new Phone("23456789"), new Email("cjinting-nus@example.com"),
                new Address("NUS"),
                getTagSet("CS2113T-W13-1", "CS2101-C"), SampleTimeTableUtil.getTimeTableJasper(), new HashSet<>()),
            new Person(new Name("Alexis"), new Phone("87654321"), new Email("alexiscatnip@example.com"),
                new Address("NUS"),
                getTagSet("CS2113T-W13-1", "CS2101-D"), SampleTimeTableUtil.getTimeTableAlexis(), new HashSet<>()),
            new Person(new Name("test"), new Phone("88888888"), new Email("test@test.com"),
                new Address("Testy Road"),
                getTagSet("TestGroup"), SampleTimeTableUtil.getTimeTableBensonMeier(), new HashSet<>()),
        };
    }

    public static Person getSamplePerson() {
        return new Person(new Name("John Doe"), new Phone("87438807"), new Email("john_doe@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"),
                        getTagSet("friends"), SampleTimeTableUtil.getTimeTableJohnDoe(), new HashSet<>());
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
