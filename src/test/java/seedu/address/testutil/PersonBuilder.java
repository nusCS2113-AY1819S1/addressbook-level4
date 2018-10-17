package seedu.address.testutil;

import static seedu.address.testutil.TypicalAppts.EMPTY_APPT;
import static seedu.address.testutil.TypicalMedHistory.EMPTY_MEDHISTORY;
import static seedu.address.testutil.TypicalReports.EMPTY_MEDICAL_REPORT;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.medhistory.MedHistory;
import seedu.address.model.medicalreport.MedicalReport;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.timetable.Appt;
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
    public static final MedHistory DEFAULT_MEDHISTORY = EMPTY_MEDHISTORY;
    public static final MedicalReport DEFAULT_MEDICAL_REPORT = EMPTY_MEDICAL_REPORT;
    public static final Appt DEFAULT_APPT = EMPTY_APPT;

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Nric nric;
    private MedicalReport report;
    private MedHistory medhistory;
    private Appt appt;
    private Set<Tag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        nric = new Nric(DEFAULT_NRIC);
        medhistory = DEFAULT_MEDHISTORY;
        report = DEFAULT_MEDICAL_REPORT;
        appt = DEFAULT_APPT;
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
        report = personToCopy.getMedicalReport();
        medhistory = personToCopy.getMedHistory();
        appt = personToCopy.getAppt();
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
     * Sets the {@code NRIC} of the {@code Person} that we are building.
     */
    public PersonBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Sets the {@code MedicalReport} of the {@code Person} that we are building.
     */
    public PersonBuilder withMedicalReport(MedicalReport report) {
        this.report = report;
        return this;
    }

    /**
     * Sets the {@code MedHistory} of the {@code Person} that we are building
     */
    public PersonBuilder withMedHistory(MedHistory medhistory) {
        this.medhistory = medhistory;
        return this;
    }

    /**
     * Sets the {@code Appt} of the {@code Person} that we are building.
     */
    public PersonBuilder withAppt(Appt appt) {
        this.appt = appt;
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, report, medhistory, appt, nric, tags);
    }

}
