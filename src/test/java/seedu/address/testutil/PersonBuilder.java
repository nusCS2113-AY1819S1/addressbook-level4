package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.course.CourseCode;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.MatricNo;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_MATRIC_NO = "A0168347B";
    public static final String DEFAULT_COURSE_CODE = "CS";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private CourseCode courseCode;
    private MatricNo matricNo;
    private Set<Tag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        courseCode = new CourseCode(DEFAULT_COURSE_CODE);
        matricNo = new MatricNo(DEFAULT_MATRIC_NO);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        courseCode = personToCopy.getCourseCode();
        matricNo = personToCopy.getMatricNo();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code CourseCocde} of the {@code Person} that we are building.
     */
    public PersonBuilder withCourseCode(String courseCode) {

        this.courseCode = new CourseCode(courseCode);


        return this;
    }
    /**
     * Sets the {@code MatricNo} of the {@code Person} that we are building.
     */
    public PersonBuilder withMatricNo(String matricNo) {
        this.matricNo = new MatricNo(matricNo);
        return this;
    }
    public Person build() {
        return new Person(name, phone, email, address, tags, courseCode, matricNo);
    }

}
