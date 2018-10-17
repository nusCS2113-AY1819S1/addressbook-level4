package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medhistory.Allergy;
import seedu.address.model.medhistory.MedHistDate;
import seedu.address.model.medhistory.MedHistory;
import seedu.address.model.medhistory.PrevCountry;
import seedu.address.model.medicalreport.Date;
import seedu.address.model.medicalreport.Information;
import seedu.address.model.medicalreport.MedicalReport;
import seedu.address.model.medicalreport.Title;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.timetable.Appt;
import seedu.address.model.timetable.ApptDateTime;
import seedu.address.model.timetable.ApptDrName;
import seedu.address.model.timetable.ApptInfo;
import seedu.address.model.timetable.ApptVenue;

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

    // Med History
    @XmlElement(required = true)
    private String medHistDate;
    @XmlElement(required = true)
    private String allergy;
    @XmlElement(required = true)
    private String prevCountry;

    // Medical Report
    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String information;

    // Appt
    @XmlElement(required = true)
    private String apptStart;
    @XmlElement(required = true)
    private String apptEnd;
    @XmlElement(required = true)
    private String apptVenue;
    @XmlElement(required = true)
    private String apptInfo;
    @XmlElement(required = true)
    private String apptDrName;


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
    public XmlAdaptedPerson(String name, String phone, String email, String address, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
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

        // Med History
        medHistDate = source.getMedHistory().getMedHistDate().value;
        allergy = source.getMedHistory().getAllergy().value;
        prevCountry = source.getMedHistory().getPrevCountry().value;

        // Medical Report
        title = source.getMedicalReport().getTitle().toString();
        date = source.getMedicalReport().getDate().toString();
        information = source.getMedicalReport().getInformation().toString();

        // Appt
        apptStart = source.getAppt().getStart().toString();
        apptEnd = source.getAppt().getEnd().toString();
        apptVenue = source.getAppt().getVenue().toString();
        apptInfo = source.getAppt().getInfo().toString();
        apptDrName = source.getAppt().getDrName().toString();

        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
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

        /**
         * ==================================================
         * MEDICAL REPORT SUBFIELDS
         * ==================================================
         */

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        final Title modelTitle = new Title(title);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        final Date modelDate = new Date(date);

        if (information == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Information.class.getSimpleName()));
        }
        final Information modelInformation = new Information(information);

        final MedicalReport modelReport = new MedicalReport(modelTitle, modelDate, modelInformation);

        /**
         * ==================================================
         * MED HISTORY SUBFIELDS
         * ==================================================
         */

        if (medHistDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MedHistory.class.getSimpleName()));
        }
        final MedHistDate modelMedHistDate = new MedHistDate(medHistDate);

        if (allergy == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MedHistory.class.getSimpleName()));
        }
        final Allergy modelAllergy = new Allergy(allergy);

        if (prevCountry == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MedHistory.class.getSimpleName()));
        }
        final PrevCountry modelPrevCountry = new PrevCountry(prevCountry);

        final MedHistory modelMedHistory = new MedHistory(modelMedHistDate, modelAllergy, modelPrevCountry);

        /**
         * ==================================================
         * APPT SUBFIELDS
         * ==================================================
         */

        if (apptStart == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ApptDateTime.class.getSimpleName()));
        }
        final ApptDateTime modelApptStart = new ApptDateTime(apptStart);

        if (apptEnd == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ApptDateTime.class.getSimpleName()));
        }
        final ApptDateTime modelApptEnd = new ApptDateTime(apptEnd);

        if (apptVenue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ApptVenue.class.getSimpleName()));
        }
        final ApptVenue modelApptVenue = new ApptVenue(apptVenue);

        if (apptInfo == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ApptInfo.class.getSimpleName()));
        }
        final ApptInfo modelApptInfo = new ApptInfo(apptInfo);

        if (apptDrName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ApptDrName.class.getSimpleName()));
        }
        final ApptDrName modelApptDrName = new ApptDrName(apptDrName);

        final Appt modelAppt = new Appt(modelApptStart, modelApptEnd, modelApptVenue, modelApptInfo, modelApptDrName);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelReport,
                          modelMedHistory, modelAppt, modelNric, modelTags);
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
                // Medical History
                && Objects.equals(date, otherPerson.date)
                && Objects.equals(allergy, otherPerson.allergy)
                && Objects.equals(prevCountry, otherPerson.prevCountry)
                // Medical Report
                && Objects.equals(title, otherPerson.title)
                && Objects.equals(date, otherPerson.date)
                && Objects.equals(information, otherPerson.information)
                // Appt
                && Objects.equals(apptStart, otherPerson.apptStart)
                && Objects.equals(apptEnd, otherPerson.apptEnd)
                && Objects.equals(apptVenue, otherPerson.apptVenue)
                && Objects.equals(apptInfo, otherPerson.apptInfo)
                && Objects.equals(apptDrName, otherPerson.apptDrName)
                // Tags
                && tagged.equals(otherPerson.tagged);
    }
}
