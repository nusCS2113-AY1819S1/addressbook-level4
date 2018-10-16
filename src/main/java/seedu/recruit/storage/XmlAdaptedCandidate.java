package seedu.recruit.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.recruit.commons.exceptions.IllegalValueException;
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


/**
 * JAXB-friendly version of the Candidate.
 */
public class XmlAdaptedCandidate {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Candidate's %s field is missing!";


    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String gender;
    @XmlElement(required = true)
    private String age;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String job;
    @XmlElement(required = true)
    private String education;
    @XmlElement(required = true)
    private String salary;
    @XmlElement(required = true)
    private String address;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedCandidate.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCandidate() {}

    /**
     * Constructs an {@code XmlAdaptedCandidate} with the given candidate details.
     */

    public XmlAdaptedCandidate(String name, String gender, String age, String phone, String email, String address,
                               String job, String education, String salary, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.job = job;
        this.education = education;
        this.salary = salary;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Candidate into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedCandidate
     */

    public XmlAdaptedCandidate(Candidate source) {
        name = source.getName().fullName;
        gender = source.getGender().value;
        age = source.getAge().value;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        job = source.getJob().value;
        education = source.getEducation().value;
        salary = source.getSalary().value;

        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted candidate object into the model's Candidate object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted candidate
     */

    public Candidate toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }

        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (gender == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName()));
        }

        if (!Gender.isValidGender(gender)) {
            throw new IllegalValueException(Gender.MESSAGE_GENDER_CONSTRAINTS);
        }

        final Gender modelGender = new Gender(gender);

        if (age == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Age.class.getSimpleName()));
        }

        if (!Age.isValidAge(age)) {
            throw new IllegalValueException(Age.MESSAGE_AGE_CONSTRAINTS);
        }

        final Age modelAge = new Age(age);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (job == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Job.class.getSimpleName()));
        }

        if (!Job.isValidJob(job)) {
            throw new IllegalValueException(Job.MESSAGE_JOB_CONSTRAINTS);
        }

        final Job modelJob = new Job(job);

        if (education == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Education.class.getSimpleName()));
        }

        if (!Education.isValidEducation(education)) {
            throw new IllegalValueException(Education.MESSAGE_EDUCATION_CONSTRAINTS);
        }

        final Education modelEducation = new Education(education);

        if (salary == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Salary.class.getSimpleName()));
        }

        if (!Salary.isValidSalary(salary)) {
            throw new IllegalValueException(Salary.MESSAGE_SALARY_CONSTRAINTS);
        }

        final Salary modelSalary = new Salary(salary);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Candidate(modelName, modelGender, modelAge, modelPhone, modelEmail, modelAddress, modelJob,
            modelEducation, modelSalary, modelTags);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCandidate)) {
            return false;
        }

        XmlAdaptedCandidate otherPerson = (XmlAdaptedCandidate) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(gender, otherPerson.gender)
                && Objects.equals(age, otherPerson.age)
                && Objects.equals(phone, otherPerson.phone)
                && Objects.equals(email, otherPerson.email)
                && Objects.equals(address, otherPerson.address)
                && Objects.equals(job, otherPerson.job)
                && Objects.equals(education, otherPerson.education)
                && Objects.equals(salary, otherPerson.salary)
                && tagged.equals(otherPerson.tagged);
    }
}
