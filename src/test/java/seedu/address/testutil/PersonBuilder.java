package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.CheckedInTime;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.Mode;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.WorkingRate;
import seedu.address.model.person.password.Password;
import seedu.address.model.prioritylevel.PriorityLevel;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.TimeEnd;
import seedu.address.model.schedule.TimeStart;
import seedu.address.model.schedule.Venue;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_DEPARTMENT = "Junior Management";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_NRIC = "S1458574G";
    public static final String DEFAULT_PASSWORD = "PQWOei23";
    public static final String DEFAULT_MODE = "out";
    public static final String DEFAULT_WORKINGRATE = "7.5";
    public static final String DEFAULT_CHECKEDINTIME = "";
    public static final int DEFAULT_PRIORITYLEVEL = 3;
    public static final String DEFAULT_TIME_START = "0900";
    public static final String DEFAULT_TIME_END = "1800";
    public static final String DEFAULT_VENUE = "Counter 2";

    private Name name;
    private Nric nric;
    private Password password;
    private Phone phone;
    private Email email;
    private Department department;
    private PriorityLevel priorityLevel;
    private Address address;
    private Mode mode;
    private WorkingRate workingRate;
    private CheckedInTime checkedInTime;
    private Set<Tag> tags;
    private Schedule schedule;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        nric = new Nric(DEFAULT_NRIC);
        password = new Password(DEFAULT_PASSWORD);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        department = new Department(DEFAULT_DEPARTMENT);
        priorityLevel = new PriorityLevel(DEFAULT_PRIORITYLEVEL);
        address = new Address(DEFAULT_ADDRESS);
        mode = new Mode(DEFAULT_MODE);
        workingRate = new WorkingRate(DEFAULT_WORKINGRATE);
        checkedInTime = new CheckedInTime(DEFAULT_CHECKEDINTIME);
        tags = new HashSet<>();
        try {
            schedule = new Schedule(new TimeStart(DEFAULT_TIME_START), new TimeEnd(DEFAULT_TIME_END),
                    new Venue(DEFAULT_VENUE));
        } catch (Exception e) {
            schedule = null;
        }
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        nric = personToCopy.getNric();
        password = personToCopy.getPassword();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        department = personToCopy.getDepartment();
        priorityLevel = personToCopy.getPriorityLevel();
        address = personToCopy.getAddress();
        mode = personToCopy.getMode();
        workingRate = personToCopy.getWorkingRate();
        checkedInTime = personToCopy.getCheckedInTime();
        tags = new HashSet<>(personToCopy.getTags());
        schedule = personToCopy.getSchedule();
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
     * Sets the {@code Department} of the {@code Person} that we are building.
     */
    public PersonBuilder withDepartment(String department) {
        this.department = new Department(department);
        return this;
    }

    /**
     * Sets the {@code Department} of the {@code Person} that we are building.
     */
    public PersonBuilder withPriorityLevel(int plvl) {
        this.priorityLevel = new PriorityLevel(plvl);
        return this;
    }

    /**
     * Sets the {@code EmployeeId} of the {@code Person} that we are building.
     */
    public PersonBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code Person} that we are building.
     */
    public PersonBuilder withPassword(String password) {
        this.password = new Password(password);
        return this;
    }

    /**
     * Sets the {@code Mode} of the {@code Person} that we are building.
     */
    public PersonBuilder withMode(String mode) {
        this.mode = new Mode(mode);
        return this;
    }

    /**
     * Sets the {@code WorkingRate} of the {@code Person} that we are building.
     */
    public PersonBuilder withWorkingRate(String workingRate) {
        this.workingRate = new WorkingRate(workingRate);
        return this;
    }

    /**
     * Sets the {@code CheckedInTime} of the {@code Person} that we are building.
     */
    public PersonBuilder withCheckedInTime(String checkedInTime) {
        this.checkedInTime = new CheckedInTime(checkedInTime);
        return this;
    }

    /**
     * Sets the {@code Schedule} of the {@code Person} that we are building.
     */
    public PersonBuilder withSchedule(Schedule scheduleInput) {
        this.schedule = scheduleInput;
        return this;
    }

    /**
     * Removes the {@code Schedule} of the {@code Person} that we are building.
     * Used for testing add command
     */
    public PersonBuilder withoutSchedule() {
        this.schedule = null;
        return this;
    }

    /**
     * Building Person objects.
     */
    public Person build() {
        return new Person(name, nric, password, phone, email,
            department, priorityLevel, address, mode, workingRate, checkedInTime, tags, schedule);
    }

}
