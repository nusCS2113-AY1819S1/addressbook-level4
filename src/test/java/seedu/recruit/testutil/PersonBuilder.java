package seedu.recruit.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.recruit.model.candidate.Age;
import seedu.recruit.model.candidate.Candidate;
import seedu.recruit.model.candidate.Education;
import seedu.recruit.model.candidate.Gender;
import seedu.recruit.model.candidate.Name;
import seedu.recruit.model.commons.Address;
import seedu.recruit.model.commons.Email;
import seedu.recruit.model.commons.Phone;
import seedu.recruit.model.joboffer.Job;
import seedu.recruit.model.joboffer.Salary;
import seedu.recruit.model.tag.Tag;
import seedu.recruit.model.util.SampleDataUtil;

/**
 * A utility class to help with building Candidate objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_GENDER = "F";
    public static final String DEFAULT_AGE = "21";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_JOB = "Waiter";
    public static final String DEFAULT_EDUCATION = "O level";
    public static final String DEFAULT_SALARY = "1000";

    private Name name;
    private Gender gender;
    private Age age;
    private Phone phone;
    private Email email;
    private Address address;
    private Job job;
    private Education education;
    private Salary salary;
    private Set<Tag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        gender = new Gender(DEFAULT_GENDER);
        age = new Age(DEFAULT_AGE);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        job = new Job(DEFAULT_JOB);
        education = new Education(DEFAULT_EDUCATION);
        salary = new Salary(DEFAULT_SALARY);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code candidateToCopy}.
     */
    public PersonBuilder(Candidate candidateToCopy) {
        name = candidateToCopy.getName();
        gender = candidateToCopy.getGender();
        age = candidateToCopy.getAge();
        phone = candidateToCopy.getPhone();
        email = candidateToCopy.getEmail();
        address = candidateToCopy.getAddress();
        job = candidateToCopy.getJob();
        education = candidateToCopy.getEducation();
        salary = candidateToCopy.getSalary();
        tags = new HashSet<>(candidateToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Candidate} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Candidate} that we are building.
     */
    public PersonBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Sets the {@code Age} of the {@code Candidate} that we are building.
     */
    public PersonBuilder withAge(String age) {
        this.age = new Age(age);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Candidate} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Candidate} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Job} of the {@code Candidate} that we are building.
     */
    public PersonBuilder withJob(String job) {
        this.job = new Job(job);
        return this;
    }

    /**
     * Sets the {@code Education} of the {@code Candidate} that we are building.
     */
    public PersonBuilder withEducation(String education) {
        this.education = new Education(education);
        return this;
    }

    /**
     * Sets the {@code Salary} of the {@code Candidate} that we are building.
     */
    public PersonBuilder withSalary(String salary) {
        this.salary = new Salary(salary);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Candidate} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Candidate} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Candidate build() {
        return new Candidate(name, gender, age, phone, email, address, job, education, salary, tags);
    }

}
