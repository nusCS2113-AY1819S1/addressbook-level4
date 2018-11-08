package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.grade.Grade;
import seedu.address.model.grade.Marks;
import seedu.address.model.grade.Test;
import seedu.address.model.grade.TestName;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupLocation;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
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
        Set<Test> test = new HashSet<>();
        test.add(new Test(new TestName("CS2113 MockExam"), new Marks("88"), new Grade("A")));

        return new Person[] {

            new Person(new Name("Alex Yeoh"), new Gender("Male"), new Nationality("SG"),
                    new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends"), test),

            new Person(new Name("Bernice Yu"), new Gender("Female"), new Nationality("SG"),
                    new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                   getTagSet("colleagues", "friends"), test),

            new Person(new Name("Charlotte Oliveiro"), new Gender("F"), new Nationality("AU"),
                    new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours"), test),

            new Person(new Name("David Li"), new Gender("M"), new Nationality("CN"),
                    new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Seran"
                            + "on Gardens Street 26, #16-43"),
                     getTagSet("family"), test),

            new Person(new Name("Irfan Ibrahim"), new Gender("M"), new Nationality("MY"),
                    new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("classmates"), test),

            new Person(new Name("Roy Balakrishnan"), new Gender("M"), new Nationality("IN"),
                    new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"), test),

            new Person(new Name("Khadijah Saine"), new Gender("F"), new Nationality("IN"),
                    new Phone("92142978"), new Email("KhadijahSaine@example.com"),
                    new Address("Blk 112, Simei Street 1"),
                    getTagSet("classmate"), test),

            new Person(new Name("Jeni Eisenhower"), new Gender("F"), new Nationality("US"),
                    new Phone("91077545"), new Email("JeniEisenhower@example.com"),
                    new Address("NUS Utown"),
                    getTagSet("classmate"), test),

            new Person(new Name("Tien Agan"), new Gender("F"), new Nationality("US"),
                    new Phone("96175570"), new Email("TienAgan@example.com"),
                    new Address("NUS Utown"),
                    getTagSet("classmate"), test),

            new Person(new Name("Brendan"), new Gender("M"), new Nationality("SG"),
                    new Phone("95704308"), new Email("Brendan@example.com"),
                    new Address("NUS Utown"),
                    getTagSet("classmate"), test),

            new Person(new Name("Fay"), new Gender("M"), new Nationality("SG"),
                    new Phone("97894720"), new Email("Fay@example.com"),
                    new Address("NUS Utown"),
                    getTagSet("classmate"), test),

            new Person(new Name("Tracey"), new Gender("F"), new Nationality("SG"),
                    new Phone("99944817"), new Email("Tracey@example.com"),
                    new Address("NUS Utown"),
                    getTagSet("classmate"), test),

            new Person(new Name("Christy"), new Gender("F"), new Nationality("SG"),
                    new Phone("98496742"), new Email("Christy@example.com"),
                    new Address("NUS Utown"),
                    getTagSet("classmate"), test),

            new Person(new Name("Heidi"), new Gender("F"), new Nationality("SG"),
                    new Phone("95067553"), new Email("Heidi@example.com"),
                    new Address("NUS Utown"),
                    getTagSet("classmate"), test),

            new Person(new Name("Christene"), new Gender("F"), new Nationality("SG"),
                    new Phone("96573233"), new Email("Christene@example.com"),
                    new Address("NUS Utown"),
                    getTagSet("classmate"), test),

            new Person(new Name("Les"), new Gender("F"), new Nationality("SG"),
                    new Phone("94615325"), new Email("Les@example.com"),
                    new Address("NUS Utown"),
                    getTagSet("classmate"), test),

            new Person(new Name("Luigi"), new Gender("M"), new Nationality("SG"),
                    new Phone("96259361"), new Email("Luigi@example.com"),
                    new Address("NUS Utown"),
                    getTagSet("classmate"), test),

            new Person(new Name("Reatha"), new Gender("F"), new Nationality("IN"),
                    new Phone("96775760"), new Email("Reatha@example.com"),
                    new Address("NUS Utown"),
                    getTagSet("classmate"), test),

            new Person(new Name("Dorethea"), new Gender("F"), new Nationality("CN"),
                    new Phone("94680649"), new Email("Dorethea@example.com"),
                    new Address("NUS Utown"),
                    getTagSet("classmate"), test),

            new Person(new Name("Deon"), new Gender("M"), new Nationality("SG"),
                    new Phone("96200344"), new Email("Deon@example.com"),
                    new Address("NUS Utown"),
                    getTagSet("classmate"), test),

            new Person(new Name("Joleen"), new Gender("F"), new Nationality("SG"),
                    new Phone("98481923"), new Email("Joleen@example.com"),
                    new Address("NUS Utown"),
                    getTagSet("classmate"), test),

            new Person(new Name("Glynis"), new Gender("F"), new Nationality("SG"),
                    new Phone("91174790"), new Email("Glynis@example.com"),
                    new Address("NUS Utown"),
                    getTagSet("classmate"), test),

            new Person(new Name("Corinna"), new Gender("F"), new Nationality("SG"),
                    new Phone("91082076"), new Email("Corinna@example.com"),
                    new Address("NUS Utown"),
                    getTagSet("classmate"), test),

            new Person(new Name("Lianne"), new Gender("F"), new Nationality("SG"),
                    new Phone("97181574"), new Email("Lianne@example.com"),
                    new Address("NUS Utown"),
                    getTagSet("classmate"), test),

            new Person(new Name("Deanne"), new Gender("F"), new Nationality("SG"),
                    new Phone("96849130"), new Email("Deanne@example.com"),
                    new Address("NUS Utown"),
                    getTagSet("classmate"), test),

            new Person(new Name("Donna"), new Gender("F"), new Nationality("SG"),
                    new Phone("95435362"), new Email("Donna@example.com"),
                    new Address("NUS Utown"),
                    getTagSet("classmate"), test),

            new Person(new Name("Chandra"), new Gender("M"), new Nationality("IN"),
                    new Phone("91410083"), new Email("Chandra@example.com"),
                    new Address("NUS Utown"),
                    getTagSet("classmate"), test),

            new Person(new Name("Tsu Wei Quan"), new Gender("M"), new Nationality("SG"),
                    new Phone("96259561"), new Email("tsuweiquan@gmail.com"),
                    new Address("Blk 111, Simei Street 1"),
                    getTagSet("teammate"), test),

            new Person(new Name("Novin Tong Yong Kang"), new Gender("M"), new Nationality("SG"),
                    new Phone("99999999"), new Email("E0176909@u.nus.edu"),
                    new Address("Pasir Ris street 99,#02-25"),
                    getTagSet("friends"), test),

            new Person(new Name("TsuraJovin"), new Gender("M"), new Nationality("SG"),
                    new Phone("99999999"), new Email("tsurajovin@gmail.com"),
                    new Address("Bedok street 99,#02-25"),
                    getTagSet("GithubBot"), test),

            new Person(new Name("Joel"), new Gender("M"), new Nationality("MY"),
                    new Phone("9784230"), new Email("joeltan98@gmail.com"),
                    new Address("Jurong West Street 52"),
                    getTagSet("GithubBot"), test),

            new Person(new Name("JoelTan"), new Gender("M"), new Nationality("MY"),
                    new Phone("9784230"), new Email("joel.twh@u.nus.edu"),
                    new Address("Jurong West Street 52"),
                    getTagSet("CSGOHacker"), test),

            new Person(new Name("Rajdeep"), new Gender("M"), new Nationality("SG"),
                    new Phone("9784230"), new Email("rajdeepsh@outlook.com"),
                    new Address("Pasir ris street 2"),
                    getTagSet("Dota2Hacker"), test),

            new Person(new Name("Clara"), new Gender("F"), new Nationality("SG"),
                    new Phone("9784230"), new Email("Clara@example.com"),
                    new Address("UTown"),
                    getTagSet("Developer"), test)
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

        return setPersonsInGroups(sampleAb);
    }

    /**
     * Adds persons from sampleAb to groups in sampleAb
     * @param sampleAb
     * @return
     */
    public static AddressBook setPersonsInGroups (AddressBook sampleAb) {
        int count = 0;
        for (Group abGroup : sampleAb.getGroupList()) {
            for (Person abPerson : sampleAb.getPersonList()) {
                if (++count == 4) {
                    count = 0;
                    continue;
                }
                abGroup.addPersons(abPerson);
            }
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
    /**
     * Returns a test set containing the list of test given.
     */
    public static Set<Test> getTestsSet(Test... tests) {
        Set<Test> testSet = new HashSet<>();
        for (Test t : tests) {
            testSet.add(t);
        }
        return testSet;
    }
}
