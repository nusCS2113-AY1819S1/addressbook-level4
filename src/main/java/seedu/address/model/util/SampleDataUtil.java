package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.addressbook.AddressBook;
import seedu.address.model.addressbook.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Bonus;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Salary;
import seedu.address.model.person.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Email SAMPLE_EMAIL = new Email ("bobbyyeoh@example.com");
    public static final Name SAMPLE_NAME = new Name("Bobby");
    public static final Phone SAMPLE_PHONE = new Phone ("81234567");
    public static final Address SAMPLE_ADDRESS = new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18");
    public static final DateOfBirth SAMPLE_DATEOFBIRTH = new DateOfBirth("12/12/1995");
    public static final Department SAMPLE_DEPARTMENT = new Department("Human Resource");
    public static final Position SAMPLE_POSITION = new Position("Intern");
    public static final Salary SAMPLE_SALARY = new Salary("1000.00");
    public static final Bonus SAMPLE_BONUS = new Bonus("00.00");

    public static Person[] getSamplePersons() {

        return new Person[] {
            new Person(new EmployeeId("000001"), new Name("Alex Yeoh"), new DateOfBirth("31/10/1975"),
                new Phone("87438807"), new Email("alexyeoh@example.com"), new Department("Finance"),
                new Position("Director"), new Address("Blk 30 Geylang Street 29, #06-40"), new Salary("8000.00"),
                SAMPLE_BONUS, getTagSet("Fishing")),
            new Person(new EmployeeId("000002"), new Name("Bernice Yu"), new DateOfBirth("26/03/2000"),
                new Phone("99272758"), new Email("berniceyu@example.com"), new Department("IT"), new Position("Intern"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Salary("1000.00"), SAMPLE_BONUS,
                    getTagSet("Cycling")),
            new Person(new EmployeeId("000003"), new Name("Charlotte Oliveiro"), new DateOfBirth("13/06/1990"),
                new Phone("93210283"), new Email("charlotte@example.com"), new Department("Finance"),
                new Position("Manager"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Salary("4000.00"),
                SAMPLE_BONUS, getTagSet("Cooking")),
            new Person(new EmployeeId("000004"), new Name("David Li"), new DateOfBirth("18/08/1999"),
                new Phone("91031282"), new Email("lidavid@example.com"), new Department("Human Resource"),
                new Position("Intern"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                new Salary("800.00"), SAMPLE_BONUS, getTagSet("FlyKite")),
            new Person(new EmployeeId("000005"), new Name("Irfan Ibrahim"), new DateOfBirth("01/03/1965"),
                new Phone("92492021"), new Email("irfan@example.com"), new Department("IT"), new Position("Director"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Salary("10000.00"), SAMPLE_BONUS,
                getTagSet("Pool")),
            new Person(new EmployeeId("000006"), new Name("Roy Balakrishnan"), new DateOfBirth("16/06/1987"),
                new Phone("92624417"), new Email("royb@example.com"), new Department("Human Resource"),
                new Position("Manager"), new Address("Blk 45 Aljunied Street 85, #11-31"), new Salary("5000.00"),
                SAMPLE_BONUS, getTagSet("Soccer"))
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
