package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appt.Appt;
import seedu.address.model.appt.ApptComparator;
import seedu.address.model.appt.ApptDateTime;
import seedu.address.model.appt.ApptDrName;
import seedu.address.model.appt.ApptInfo;
import seedu.address.model.appt.ApptVenue;
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

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Nric EMPTY_NRIC = new Nric("");
    public static final DateOfBirth EMPTY_DATE_OF_BIRTH = new DateOfBirth("");
    public static final Height EMPTY_HEIGHT = new Height("");
    public static final Weight EMPTY_WEIGHT = new Weight("");
    public static final Gender EMPTY_GENDER = new Gender("");
    public static final BloodType EMPTY_BLOODTYPE = new BloodType("");
    public static final Occupation EMPTY_OCCUPATION = new Occupation("");
    public static final MaritalStatus EMPTY_MARITAL_STATUS = new MaritalStatus("");

    // Appts
    public static final String VALID_START_APPT1 = "01-01-2018 14:00";
    public static final String VALID_START_APPT2 = "02-02-2018 15:00";
    public static final String VALID_START_APPT3 = "03-03-2018 16:00";
    public static final String VALID_START_APPT4 = "17-03-2017 15:30";
    public static final String VALID_START_APPT5 = "11-04-2017 15:30";
    public static final String VALID_START_APPT6 = "11-04-2014 11:30";
    public static final String VALID_END_APPT1 = "01-01-2018 15:00";
    public static final String VALID_END_APPT2 = "02-02-2018 16:00";
    public static final String VALID_END_APPT3 = "03-03-2018 17:00";
    public static final String VALID_END_APPT4 = "17-03-2017 16:30";
    public static final String VALID_END_APPT5 = "11-04-2017 16:30";
    public static final String VALID_END_APPT6 = "11-04-2014 12:30";
    public static final String VALID_VENUE_APPT1 = "Consultation Room 1";
    public static final String VALID_VENUE_APPT2 = "Consultation Room 2";
    public static final String VALID_VENUE_APPT3 = "Consultation Room 3";
    public static final String VALID_VENUE_APPT4 = "Consultation Room 4";
    public static final String VALID_VENUE_APPT5 = "Consultation Room 5";
    public static final String VALID_VENUE_APPT6 = "Consultation Room 6";
    public static final String VALID_INFO_APPT1 = "Diabetes Checkup";
    public static final String VALID_INFO_APPT2 = "Asthma Checkup";
    public static final String VALID_INFO_APPT3 = "Eye Checkup";
    public static final String VALID_DRNAME_APPT1 = "Dr Tan";
    public static final String VALID_DRNAME_APPT2 = "Dr Lim";
    public static final String VALID_DRNAME_APPT3 = "Dr Chan";
    public static final String VALID_DRNAME_APPT4 = "Dr Wong";
    public static final String VALID_DRNAME_APPT5 = "Dr Ali";
    public static final String VALID_DRNAME_APPT6 = "Dr Bala";

    public static final Appt APPT_EXAMPLE1 = new Appt(new ApptDateTime(VALID_START_APPT1),
            new ApptDateTime(VALID_END_APPT1), new ApptVenue(VALID_VENUE_APPT1), new ApptInfo(VALID_INFO_APPT1),
            new ApptDrName(VALID_DRNAME_APPT1));
    public static final Appt APPT_EXAMPLE2 = new Appt(new ApptDateTime(VALID_START_APPT2),
            new ApptDateTime(VALID_END_APPT2), new ApptVenue(VALID_VENUE_APPT2), new ApptInfo(VALID_INFO_APPT2),
            new ApptDrName(VALID_DRNAME_APPT2));
    public static final Appt APPT_EXAMPLE3 = new Appt(new ApptDateTime(VALID_START_APPT3),
            new ApptDateTime(VALID_END_APPT3), new ApptVenue(VALID_VENUE_APPT3), new ApptInfo(VALID_INFO_APPT3),
            new ApptDrName(VALID_DRNAME_APPT3));
    public static final Appt APPT_EXAMPLE4 = new Appt(new ApptDateTime(VALID_START_APPT4),
            new ApptDateTime(VALID_END_APPT4), new ApptVenue(VALID_VENUE_APPT4), new ApptInfo(VALID_INFO_APPT3),
            new ApptDrName(VALID_DRNAME_APPT4));
    public static final Appt APPT_EXAMPLE5 = new Appt(new ApptDateTime(VALID_START_APPT5),
            new ApptDateTime(VALID_END_APPT5), new ApptVenue(VALID_VENUE_APPT5), new ApptInfo(VALID_INFO_APPT2),
            new ApptDrName(VALID_DRNAME_APPT5));
    public static final Appt APPT_EXAMPLE6 = new Appt(new ApptDateTime(VALID_START_APPT6),
            new ApptDateTime(VALID_END_APPT6), new ApptVenue(VALID_VENUE_APPT6), new ApptInfo(VALID_INFO_APPT1),
            new ApptDrName(VALID_DRNAME_APPT6));

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), getReportSet(), getMedHistorySet(),
                    getApptSet(APPT_EXAMPLE1, APPT_EXAMPLE4), EMPTY_NRIC, EMPTY_DATE_OF_BIRTH, new Height("180"),
                    EMPTY_WEIGHT, EMPTY_GENDER, EMPTY_BLOODTYPE, EMPTY_OCCUPATION, EMPTY_MARITAL_STATUS,
                    getTagSet("asthma")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), getReportSet(),
                    getMedHistorySet(), getApptSet(APPT_EXAMPLE2, APPT_EXAMPLE5),
                    EMPTY_NRIC, EMPTY_DATE_OF_BIRTH, EMPTY_HEIGHT, EMPTY_WEIGHT, EMPTY_GENDER, EMPTY_BLOODTYPE,
                    EMPTY_OCCUPATION, EMPTY_MARITAL_STATUS, getTagSet("asthma", "diabetes")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), getReportSet(), getMedHistorySet(),
                    getApptSet(APPT_EXAMPLE3, APPT_EXAMPLE6), EMPTY_NRIC, EMPTY_DATE_OF_BIRTH, EMPTY_HEIGHT,
                    EMPTY_WEIGHT, EMPTY_GENDER, EMPTY_BLOODTYPE, EMPTY_OCCUPATION, EMPTY_MARITAL_STATUS,
                    getTagSet("cancer")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), getReportSet(),
                    getMedHistorySet(), getApptSet(), EMPTY_NRIC, EMPTY_DATE_OF_BIRTH, EMPTY_HEIGHT, EMPTY_WEIGHT,
                    EMPTY_GENDER, EMPTY_BLOODTYPE, EMPTY_OCCUPATION, EMPTY_MARITAL_STATUS,
                    getTagSet("stroke", "cancer")),
            new Person(new Name("Elizabeth Tan"), new Phone("95430492"), new Email("elitan@example.com"),
                    new Address("Blk 123 West Coast Street 20, #05-192"), getReportSet(), getMedHistorySet(),
                    getApptSet(), EMPTY_NRIC, EMPTY_DATE_OF_BIRTH, EMPTY_HEIGHT, EMPTY_WEIGHT, EMPTY_GENDER,
                    EMPTY_BLOODTYPE, EMPTY_OCCUPATION, EMPTY_MARITAL_STATUS,
                    getTagSet("dengue", "asthma")),
            new Person(new Name("Frank Lee"), new Phone("84886329"), new Email("franklee@example.com"),
                    new Address("Blk 324 Jurong East Street 10, #13-300"), getReportSet(), getMedHistorySet(),
                    getApptSet(), EMPTY_NRIC, EMPTY_DATE_OF_BIRTH, EMPTY_HEIGHT, EMPTY_WEIGHT, EMPTY_GENDER,
                    EMPTY_BLOODTYPE, EMPTY_OCCUPATION, EMPTY_MARITAL_STATUS, getTagSet("aids")),
            new Person(new Name("Gary Lim"), new Phone("81813212"), new Email("garylim@example.com"),
                    new Address("Blk 192 Punggol Ave 32, #07-122"), getReportSet(), getMedHistorySet(),
                    getApptSet(), EMPTY_NRIC, EMPTY_DATE_OF_BIRTH, EMPTY_HEIGHT, EMPTY_WEIGHT, EMPTY_GENDER,
                    EMPTY_BLOODTYPE, EMPTY_OCCUPATION, EMPTY_MARITAL_STATUS, getTagSet("aids")),
            new Person(new Name("Hansel Chong"), new Phone("85042190"), new Email("hanselchong@example.com"),
                    new Address("Blk 327 Aljunied Road 21, #05-121"), getReportSet(), getMedHistorySet(),
                    getApptSet(), EMPTY_NRIC, EMPTY_DATE_OF_BIRTH, EMPTY_HEIGHT, EMPTY_WEIGHT, EMPTY_GENDER,
                    EMPTY_BLOODTYPE, EMPTY_OCCUPATION, EMPTY_MARITAL_STATUS,
                    getTagSet("dementia", "arthritis")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), getReportSet(), getMedHistorySet(),
                    getApptSet(), EMPTY_NRIC, EMPTY_DATE_OF_BIRTH, EMPTY_HEIGHT, EMPTY_WEIGHT, EMPTY_GENDER,
                    EMPTY_BLOODTYPE, EMPTY_OCCUPATION, EMPTY_MARITAL_STATUS, getTagSet("diabetes")),
            new Person(new Name("John Doe"), new Phone("83453116"), new Email("johndoe@example.com"),
                    new Address("Blk 12 Bedok Street 10, #01-111"), getReportSet(), getMedHistorySet(),
                    getApptSet(), EMPTY_NRIC, EMPTY_DATE_OF_BIRTH, EMPTY_HEIGHT, EMPTY_WEIGHT, EMPTY_GENDER,
                    EMPTY_BLOODTYPE, EMPTY_OCCUPATION, EMPTY_MARITAL_STATUS, getTagSet("diabetes")),
            new Person(new Name("Kasper Wong"), new Phone("98100065"), new Email("kasper@example.com"),
                    new Address("Blk 67 Bukit Batok Street 12, #12-099"), getReportSet(), getMedHistorySet(),
                    getApptSet(), EMPTY_NRIC, EMPTY_DATE_OF_BIRTH, EMPTY_HEIGHT, EMPTY_WEIGHT, EMPTY_GENDER,
                    EMPTY_BLOODTYPE, EMPTY_OCCUPATION, EMPTY_MARITAL_STATUS,
                    getTagSet("eczema", "asthma")),
            new Person(new Name("Luke Wong"), new Phone("98100061"), new Email("luke@example.com"),
                    new Address("Blk 67 Bukit Batok Street 12, #12-099"), getReportSet(), getMedHistorySet(),
                    getApptSet(), EMPTY_NRIC, EMPTY_DATE_OF_BIRTH, EMPTY_HEIGHT, EMPTY_WEIGHT, EMPTY_GENDER,
                    EMPTY_BLOODTYPE, EMPTY_OCCUPATION, EMPTY_MARITAL_STATUS, getTagSet("chickenpox")),
            new Person(new Name("Mandy Poh"), new Phone("98439679"), new Email("mandywong@example.com"),
                    new Address("Blk 524 Sengkang Road 43, #07-099"), getReportSet(), getMedHistorySet(),
                    getApptSet(), EMPTY_NRIC, EMPTY_DATE_OF_BIRTH, EMPTY_HEIGHT, EMPTY_WEIGHT, EMPTY_GENDER,
                    EMPTY_BLOODTYPE, EMPTY_OCCUPATION, EMPTY_MARITAL_STATUS, getTagSet("stroke")),
            new Person(new Name("Nara White"), new Phone("91292667"), new Email("narawhite@example.com"),
                    new Address("Blk 89 Lakeside Street 56, #09-785"), getReportSet(), getMedHistorySet(),
                    getApptSet(), EMPTY_NRIC, EMPTY_DATE_OF_BIRTH, EMPTY_HEIGHT, EMPTY_WEIGHT, EMPTY_GENDER,
                    EMPTY_BLOODTYPE, EMPTY_OCCUPATION, EMPTY_MARITAL_STATUS, getTagSet("aids")),
            new Person(new Name("Oliver Johnson"), new Phone("94565690"), new Email("oliver@example.com"),
                    new Address("Blk 53 Lavender Road 56, #05-235"), getReportSet(), getMedHistorySet(),
                    getApptSet(), EMPTY_NRIC, EMPTY_DATE_OF_BIRTH, EMPTY_HEIGHT, EMPTY_WEIGHT, EMPTY_GENDER,
                    EMPTY_BLOODTYPE, EMPTY_OCCUPATION, EMPTY_MARITAL_STATUS,
                    getTagSet("dengue", "aids")),
            new Person(new Name("Peter Loh"), new Phone("84989292"), new Email("peter@example.com"),
                    new Address("Blk 67 Kallang Street 32, #01-434"), getReportSet(), getMedHistorySet(),
                    getApptSet(), EMPTY_NRIC, EMPTY_DATE_OF_BIRTH, EMPTY_HEIGHT, EMPTY_WEIGHT, EMPTY_GENDER,
                    EMPTY_BLOODTYPE, EMPTY_OCCUPATION, EMPTY_MARITAL_STATUS, getTagSet("dengue")),
            new Person(new Name("Quinn Toh"), new Phone("80740951"), new Email("quinn@example.com"),
                    new Address("Blk 61 Kallang Street 12, #04-122"), getReportSet(), getMedHistorySet(),
                    getApptSet(), EMPTY_NRIC, EMPTY_DATE_OF_BIRTH, EMPTY_HEIGHT, EMPTY_WEIGHT, EMPTY_GENDER,
                    EMPTY_BLOODTYPE, EMPTY_OCCUPATION, EMPTY_MARITAL_STATUS, getTagSet("dengue")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), getReportSet(), getMedHistorySet(),
                    getApptSet(), EMPTY_NRIC, EMPTY_DATE_OF_BIRTH, EMPTY_HEIGHT, EMPTY_WEIGHT, EMPTY_GENDER,
                    EMPTY_BLOODTYPE, EMPTY_OCCUPATION, EMPTY_MARITAL_STATUS,
                    getTagSet("leukaemia", "aids")),
            new Person(new Name("Sammy Tan"), new Phone("85427890"), new Email("sammytan@example.com"),
                    new Address("Blk 12 Boon Lay Street 21, #11-232"), getReportSet(), getMedHistorySet(),
                    getApptSet(), EMPTY_NRIC, EMPTY_DATE_OF_BIRTH, EMPTY_HEIGHT, EMPTY_WEIGHT, EMPTY_GENDER,
                    EMPTY_BLOODTYPE, EMPTY_OCCUPATION, EMPTY_MARITAL_STATUS, getTagSet("arthritis")),
            new Person(new Name("Troy Lim"), new Phone("93233246"), new Email("troylim@example.com"),
                    new Address("Blk 90 Pioneer Street 30, #03-445"), getReportSet(), getMedHistorySet(),
                    getApptSet(), EMPTY_NRIC, EMPTY_DATE_OF_BIRTH, EMPTY_HEIGHT, EMPTY_WEIGHT, EMPTY_GENDER,
                    EMPTY_BLOODTYPE, EMPTY_OCCUPATION, EMPTY_MARITAL_STATUS, getTagSet("chickenpox"))
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

    /**
     * Returns a medical history set containing the list of medical histories.
     */
    public static Set<MedHistory> getMedHistorySet(MedHistory... medHistories) {
        Set<MedHistory> medHistorySet = new TreeSet<>(new MedHistoryComparator());
        for (MedHistory medHistory : medHistories) {
            medHistorySet.add(medHistory);
        }
        return medHistorySet;
    }

    /**
     * Returns a report set containing the list of reports given.
     */
    public static Set<MedicalReport> getReportSet(MedicalReport ... reports) {
        Set<MedicalReport> reportSet = new TreeSet<>(new ReportComparator());
        for (MedicalReport report : reports) {
            reportSet.add(report);
        }
        return reportSet;
    }

    //@@author brandonccm1996
    /**
     * Returns an appt set containing the list of appts given.
     */
    public static Set<Appt> getApptSet(Appt... appts) {
        Set<Appt> apptSet = new TreeSet<>(new ApptComparator());
        for (Appt appt : appts) {
            apptSet.add(appt);
        }
        return apptSet;
    }
}
