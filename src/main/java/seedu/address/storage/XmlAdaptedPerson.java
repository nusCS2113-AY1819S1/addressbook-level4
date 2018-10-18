package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medhistory.MedHistory;
import seedu.address.model.medicalreport.MedicalReport;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.timetable.Appt;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String nric;
    @XmlElement(required = true)
    private String dateOfBirth;
    @XmlElement
    private List<XmlAdaptedReport> reports = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedMedHistory> medHistories = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedAppt> appts = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address, List<XmlAdaptedReport> reports,
                            List<XmlAdaptedMedHistory> medHistories, List<XmlAdaptedAppt> appts,
                            List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (reports != null) {
            this.reports = new ArrayList<>(reports);
        }
        if (medHistories != null) {
            this.medHistories = new ArrayList<>(medHistories);
        }
        if (appts != null) {
            this.appts = new ArrayList<>(appts);
        }
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        nric = source.getNric().value;
        dateOfBirth = source.getDateOfBirth().toString();
        reports = source.getMedicalReports().stream().map(XmlAdaptedReport::new).collect(Collectors.toList());
        medHistories = source.getMedHistory().stream().map(XmlAdaptedMedHistory::new).collect(Collectors.toList());
        appts = source.getAppts().stream().map(XmlAdaptedAppt::new).collect(Collectors.toList());
        tagged = source.getTags().stream().map(XmlAdaptedTag::new).collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

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

        /**
         * ==================================================
         * ADDITIONAL INFO SUBFIELDS
         * ==================================================
         */

        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }
        final Nric modelNric = new Nric(nric);

        if (dateOfBirth == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateOfBirth.class.getSimpleName()));
        }
        final DateOfBirth modelDateOfBirth = new DateOfBirth(dateOfBirth);

        /**
         * ==================================================
         * MEDICAL REPORT SUBFIELDS
         * ==================================================
         */
        final List<MedicalReport> personMedicalReports = new ArrayList<>();
        for (XmlAdaptedReport report : reports) {
            personMedicalReports.add(report.toModelType());
        }
        final Set<MedicalReport> modelReports = new HashSet<>(personMedicalReports);

        /**
         * ==================================================
         * MED HISTORY SUBFIELDS
         * ==================================================
         */

        final List<MedHistory> personMedHistories = new ArrayList<>();
        for (XmlAdaptedMedHistory medHistory : medHistories) {
            personMedHistories.add(medHistory.toModelType());
        }

        final Set<MedHistory> modelMedHistory = new HashSet<>(personMedHistories);

        /**
         * ==================================================
         * APPT SUBFIELDS
         * ==================================================
         */

        final List<Appt> personAppts = new ArrayList<>();
        for (XmlAdaptedAppt appt : appts) {
            personAppts.add(appt.toModelType());
        }
        final Set<Appt> modelAppts = new HashSet<>(personAppts);

        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Set<Tag> modelTags = new HashSet<>(personTags);

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelReports,
                          modelMedHistory, modelAppts, modelNric, modelDateOfBirth, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedPerson otherPerson = (XmlAdaptedPerson) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(phone, otherPerson.phone)
                && Objects.equals(email, otherPerson.email)
                && Objects.equals(address, otherPerson.address)
                && Objects.equals(nric, otherPerson.nric)
                && Objects.equals(dateOfBirth, otherPerson.dateOfBirth)
                && medHistories.equals(otherPerson.medHistories)
                && reports.equals(otherPerson.reports)
                && appts.equals(otherPerson.appts)
                && tagged.equals(otherPerson.tagged);
    }
}
