package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.model.appt.Appt;
import seedu.address.model.appt.ApptComparator;
import seedu.address.model.medhistory.MedHistory;
import seedu.address.model.medhistory.MedHistoryComparator;
import seedu.address.model.medicalreport.MedicalReport;
import seedu.address.model.medicalreport.ReportComparator;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.addinfo.BloodType;
import seedu.address.model.person.addinfo.DateOfBirth;
import seedu.address.model.person.addinfo.Gender;
import seedu.address.model.person.addinfo.Height;
import seedu.address.model.person.addinfo.MaritalStatus;
import seedu.address.model.person.addinfo.Nric;
import seedu.address.model.person.addinfo.Occupation;
import seedu.address.model.person.addinfo.Weight;
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
    public static final String DEFAULT_NRIC = "";
    public static final String DEFAULT_DOB = "";
    public static final String DEFAULT_HEIGHT = "";
    public static final String DEFAULT_WEIGHT = "";
    public static final String DEFAULT_GENDER = "";
    public static final String DEFAULT_BLOODTYPE = "";
    public static final String DEFAULT_OCCUPATION = "";
    public static final String DEFAULT_MARITAL_STATUS = "";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Nric nric;
    private DateOfBirth dateOfBirth;
    private Height height;
    private Weight weight;
    private Gender gender;
    private BloodType bloodType;
    private Occupation occupation;
    private MaritalStatus maritalStatus;
    private Set<MedicalReport> reports;
    private Set<MedHistory> medHistories;
    private Set<Appt> appts;
    private Set<Tag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        nric = new Nric(DEFAULT_NRIC);
        dateOfBirth = new DateOfBirth(DEFAULT_DOB);
        height = new Height(DEFAULT_HEIGHT);
        weight = new Weight(DEFAULT_WEIGHT);
        gender = new Gender(DEFAULT_GENDER);
        bloodType = new BloodType(DEFAULT_BLOODTYPE);
        occupation = new Occupation(DEFAULT_OCCUPATION);
        maritalStatus = new MaritalStatus(DEFAULT_MARITAL_STATUS);
        reports = new TreeSet<>(new ReportComparator());
        medHistories = new TreeSet<>(new MedHistoryComparator());
        appts = new TreeSet<>(new ApptComparator());
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
        nric = personToCopy.getNric();
        dateOfBirth = personToCopy.getDateOfBirth();
        height = personToCopy.getHeight();
        weight = personToCopy.getWeight();
        gender = personToCopy.getGender();
        bloodType = personToCopy.getBloodType();
        occupation = personToCopy.getOccupation();
        maritalStatus = personToCopy.getMaritalStatus();
        reports = personToCopy.getMedicalReports();
        medHistories = personToCopy.getMedHistory();
        appts = personToCopy.getAppts();
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

    //@@author xhxh96
    /**
     * Sets the {@code NRIC} of the {@code Person} that we are building.
     */
    public PersonBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Sets the {@code dateOfBirth} of the {@code Person} that we are building.
     */
    public PersonBuilder withDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = new DateOfBirth(dateOfBirth);
        return this;
    }

    /**
     * Sets the {@code weight} of the {@code Person} that we are building.
     */
    public PersonBuilder withHeight(String height) {
        this.height = new Height(height);
        return this;
    }

    /**
     * Sets the {@code weight} of the {@code Person} that we are building.
     */
    public PersonBuilder withWeight(String weight) {
        this.weight = new Weight(weight);
        return this;
    }

    /**
     * Sets the {@code gender} of the {@code Person} that we are building.
     */
    public PersonBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Sets the {@code bloodType} of the {@code Person} that we are building.
     */
    public PersonBuilder withBloodType(String bloodType) {
        this.bloodType = new BloodType(bloodType);
        return this;
    }

    /**
     * Sets the {@code occupation} of the {@code Person} that we are building.
     */
    public PersonBuilder withOccupation(String occupation) {
        this.occupation = new Occupation(occupation);
        return this;
    }

    /**
     * Sets the {@code maritalStatus} of the {@code Person} that we are building.
     */
    public PersonBuilder withMaritalStatus(String maritalStatus) {
        this.maritalStatus = new MaritalStatus(maritalStatus);
        return this;
    }

    //@@author
    /**
     * Sets the {@code MedicalReport} of the {@code Person} that we are building.
     */
    public PersonBuilder withMedicalReports(MedicalReport ... reports) {
        this.reports = SampleDataUtil.getReportSet(reports);
        return this;
    }

    /**
     * Sets the {@code MedHistory} of the {@code Person} that we are building
     */
    public PersonBuilder withMedHistories(MedHistory... medHistories) {
        this.medHistories = SampleDataUtil.getMedHistorySet(medHistories);
        return this;
    }

    //@@author brandonccm1996
    /**
     * Sets the {@code Appt} of the {@code Person} that we are building.
     */
    public PersonBuilder withAppts(Appt... appts) {
        this.appts = SampleDataUtil.getApptSet(appts);
        return this;
    }
    //@@author
    /**
     * Returns a {@code Person} that have been built
     */
    public Person build() {
        return new Person(name, phone, email, address, reports, medHistories, appts, nric, dateOfBirth, height, weight,
                gender, bloodType, occupation, maritalStatus, tags);
    }
}
