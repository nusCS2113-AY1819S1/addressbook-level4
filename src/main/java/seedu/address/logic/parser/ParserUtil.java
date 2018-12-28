package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.util.DateTimeUtil.DATE_CONSTRAINTS;
import static seedu.address.model.util.DateTimeUtil.DATE_TIME_CONSTRAINTS;

import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appt.ApptDate;
import seedu.address.model.appt.ApptDateTime;
import seedu.address.model.appt.ApptDrName;
import seedu.address.model.appt.ApptInfo;
import seedu.address.model.appt.ApptVenue;
import seedu.address.model.medhistory.Allergy;
import seedu.address.model.medhistory.DischargeStatus;
import seedu.address.model.medhistory.DischargeStatusEnum;
import seedu.address.model.medhistory.MedHistDate;
import seedu.address.model.medhistory.PrevCountry;
import seedu.address.model.medicalreport.Information;
import seedu.address.model.medicalreport.ReportDate;
import seedu.address.model.medicalreport.Title;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
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
import seedu.address.model.util.DateTimeUtil;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_DISCHARGE_STATUS =
            "Invalid Discharge Status. Please use d or a or e.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }

        return new Email(trimmedEmail);
    }

    //@@author xhxh96
    /**
     * Parses a {@code String nric} into an {@code Nric}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code nric} is invalid.
     */
    public static Nric parseNric(String nric) throws ParseException {
        requireNonNull(nric);
        String trimmedNric = nric.trim();

        if (!Nric.isValidNric(trimmedNric)) {
            throw new ParseException(Nric.MESSAGE_NRIC_CONSTRAINTS);
        }

        if (!Nric.isCorrectNric(trimmedNric)) {
            throw new ParseException(Nric.MESSAGE_NRIC_INVALID);
        }

        return new Nric(trimmedNric);
    }

    /**
     * Parses a {@code String DateOfBirth} into an {@code DateOfBirth}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code DateOfBirth} is invalid.
     */
    public static DateOfBirth parseDateOfBirth(String dateOfBirth) throws ParseException {
        requireNonNull(dateOfBirth);
        String trimmedDateOfBirth = dateOfBirth.trim();

        if (!DateOfBirth.isValidDate(trimmedDateOfBirth)) {
            throw new ParseException(DATE_CONSTRAINTS);
        }

        try {
            DateTimeUtil.isCorrectDate(trimmedDateOfBirth);
        } catch (DateTimeException e) {
            throw new ParseException(e.getMessage());
        }

        return new DateOfBirth(trimmedDateOfBirth);
    }

    /**
     * Parses a {@code String height} into a {@code Height}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if given {@code height} is invalid
     */
    public static Height parseHeight(String height) throws ParseException {
        requireNonNull(height);
        String trimmedHeight = height.trim();

        if (!Height.isValidHeight(trimmedHeight)) {
            throw new ParseException(Height.MESSAGE_HEIGHT_CONSTRAINTS);
        }

        return new Height(trimmedHeight);
    }

    /**
     * Parses a {@code String weight} into a {@code Weight}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if given {@code weight} is invalid.
     */
    public static Weight parseWeight(String weight) throws ParseException {
        requireNonNull(weight);
        String trimmedWeight = weight.trim();

        if (!Weight.isValidWeight(trimmedWeight)) {
            throw new ParseException(Weight.MESSAGE_WEIGHT_CONSTRAINTS);
        }

        return new Weight(trimmedWeight);
    }

    /**
     * Parses a {@code String gender} into a {@code Gender}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if given {@code gender} is invalid.
     */
    public static Gender parseGender(String gender) throws ParseException {
        requireNonNull(gender);
        String trimmedGender = gender.trim();

        if (!Gender.isValidGender(trimmedGender)) {
            throw new ParseException(Gender.MESSAGE_GENDER_CONSTRAINTS);
        }

        return new Gender(trimmedGender);
    }

    /**
     * Parses a {@code String bloodType} into a {@code BloodType}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if given {@code bloodType} is invalid.
     */
    public static BloodType parseBloodType(String bloodType) throws ParseException {
        requireNonNull(bloodType);
        String trimmedBloodType = bloodType.trim();

        if (!BloodType.isValidBloodType(trimmedBloodType)) {
            throw new ParseException(BloodType.MESSAGE_BLOODTYPE_CONSTRAINTS);
        }
        if (BloodType.isIncorrectBloodType(trimmedBloodType)) {
            throw new ParseException(BloodType.MESSAGE_BLOODTYPE_CONSTRAINTS);
        }

        return new BloodType(trimmedBloodType);
    }

    /**
     *
     * Parses a {@code String occupation} into a {@code Occupation}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if given {@code occupation} is invalid.
     */
    public static Occupation parseOccupation(String occupation) throws ParseException {
        requireNonNull(occupation);
        String trimmedOccupation = occupation.trim();

        if (!Occupation.isValidOccupation(trimmedOccupation)) {
            throw new ParseException(Occupation.MESSAGE_OCCUPATION_CONSTRAINTS);
        }

        return new Occupation(trimmedOccupation);
    }

    /**
     * Parses a {@code String maritalStatus} into a {@code MaritalStatus}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if given {@code maritalStatus} is invalid.
     */
    public static MaritalStatus parseMaritalStatus(String maritalStatus) throws ParseException {
        requireNonNull(maritalStatus);
        String trimmedMaritalStatus = maritalStatus.trim();

        if (!MaritalStatus.isValidMaritalStatus(trimmedMaritalStatus)) {
            throw new ParseException(MaritalStatus.MESSAGE_MARITAL_CONSTRAINT);
        }

        return new MaritalStatus(trimmedMaritalStatus);
    }
    //@@author

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    //@@author xShaivan
    /**
     * ==================================================
     * PARSER FOR MEDICAL HISTORY SUBFIELDS
     * ==================================================
     */
    /**
     * Parses a {@code String allergy} into an {@code Allergy}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code allergy} is invalid.
     */
    public static Allergy parseAllergy(String allergy) throws ParseException {
        requireNonNull(allergy);
        String trimmedAllergy = allergy.trim();
        if (!Allergy.isValidAllergy(trimmedAllergy)) {
            throw new ParseException(Allergy.MESSAGE_ALLERGY_CONSTRAINTS);
        }

        return new Allergy(trimmedAllergy);
    }

    /**
     * Parses a {@code String medHistDate} into an {@code MedHistDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code medHistDate} is invalid.
     * ParseException is omitted for now.
     */
    public static MedHistDate parseMedHistDate(String medHistDate) throws ParseException {
        requireNonNull(medHistDate);
        String trimmedMedHistDate = medHistDate.trim();

        if (!MedHistDate.isValidMedHistDate(trimmedMedHistDate)) {
            throw new ParseException(DATE_CONSTRAINTS);
        }

        try {
            DateTimeUtil.isCorrectDate(trimmedMedHistDate);
        } catch (DateTimeException e) {
            throw new ParseException(e.getMessage());
        }

        return new MedHistDate(trimmedMedHistDate);
    }

    /**
     * Parses a {@code String prevCountry} into an {@code PrevCountry}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code prevCountry} is invalid.
     * ParseException is omitted for now.
     */
    public static PrevCountry parsePrevCountry(String prevCountry) throws ParseException {
        requireNonNull(prevCountry);
        String trimmedPrevCountry = prevCountry.trim();
        if (!PrevCountry.isValidPrevCountry(trimmedPrevCountry)) {
            throw new ParseException(PrevCountry.MESSAGE_PREVCOUNTRY_CONSTRAINTS);
        }

        return new PrevCountry(trimmedPrevCountry);
    }

    /**
     * Parses a {@code String dischargeStatus} into an {@code DischargeStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dischargeStatus} is invalid.
     * ParseException is omitted for now.
     */
    public static DischargeStatus parseDischargeStatus(String dischargeStatus) throws ParseException {
        requireNonNull(dischargeStatus);
        boolean flag = false;
        String trimmedDischargeStatus = dischargeStatus.trim();
        String expandedDischargeStatus = "";
        for (DischargeStatusEnum code: DischargeStatusEnum.values()) {
            if (trimmedDischargeStatus.equals(code.name())) {
                expandedDischargeStatus = code.getCode();
                flag = true;
            }
        }
        if (!flag) {
            throw new ParseException(MESSAGE_INVALID_DISCHARGE_STATUS);
        }

        return new DischargeStatus(expandedDischargeStatus);
    }

    //@@author chewkahmeng
    /**
     * ==================================================
     * PARSER FOR MEDICAL REPORT SUBFIELDS
     * ==================================================
     */

    /**
     * Parses a {@code String title} into an {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Title parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (!Title.isValidTitle(trimmedTitle)) {
            throw new ParseException(Title.MESSAGE_TITLE_CONSTRAINTS);
        }
        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code String date} into an {@code ReportDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static ReportDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedReportDate = date.trim();

        if (!ReportDate.isValidDate(trimmedReportDate)) {
            throw new ParseException(DATE_CONSTRAINTS);
        }

        try {
            DateTimeUtil.isCorrectDate(trimmedReportDate);
        } catch (DateTimeException e) {
            throw new ParseException(e.getMessage());
        }

        return new ReportDate(trimmedReportDate);
    }

    /**
     * Parses a {@code String information} into an {@code Information}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Information parseInformation(String information) throws ParseException {
        requireNonNull(information);
        String trimmedInformation = information.trim();
        if (!Information.isValidInformation(trimmedInformation)) {
            throw new ParseException(Information.MESSAGE_INFORMATION_CONSTRAINTS);
        }
        return new Information(trimmedInformation);
    }

    //@@author brandonccm1996
    /**
     * ==================================================
     * PARSER FOR APPT SUBFIELDS
     * ==================================================
     */

    /**
     * Parses a {@code String apptDate} into an {@code ApptDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code apptDate} is invalid.
     */
    public static ApptDate parseApptDate(String apptDate) throws ParseException {
        requireNonNull(apptDate);
        String trimmedApptDate = apptDate.trim();

        if (!ApptDate.isValidDate(trimmedApptDate)) {
            throw new ParseException(DATE_CONSTRAINTS);
        }

        try {
            DateTimeUtil.isCorrectDate(trimmedApptDate);
        } catch (DateTimeException e) {
            throw new ParseException(e.getMessage());
        }

        return new ApptDate(trimmedApptDate);
    }

    /**
     * Parses a {@code String apptDateTime} into an {@code ApptDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code apptDateTime} is invalid.
     */
    public static ApptDateTime parseApptDateTime(String apptDateTime) throws ParseException {
        requireNonNull(apptDateTime);
        String trimmedApptDateTime = apptDateTime.trim();

        if (!ApptDateTime.isValidDateTime(trimmedApptDateTime)) {
            throw new ParseException(DATE_TIME_CONSTRAINTS);
        }

        try {
            DateTimeUtil.isCorrectDateTime(trimmedApptDateTime);
        } catch (DateTimeParseException e) {
            throw new ParseException(e.getMessage());
        }

        return new ApptDateTime(trimmedApptDateTime);
    }

    /**
     * Parses a {@code String apptVenue} into an {@code ApptVenue}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code apptVenue} is invalid.
     */
    public static ApptVenue parseApptVenue(String apptVenue) throws ParseException {
        requireNonNull(apptVenue);
        String trimmedApptVenue = apptVenue.trim();
        if (!ApptVenue.isValidVenue(apptVenue)) {
            throw new ParseException(ApptVenue.MESSAGE_VENUE_CONSTRAINTS);
        }
        return new ApptVenue(trimmedApptVenue);
    }

    /**
     * Parses a {@code String apptInfo} into an {@code ApptInfo}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code apptInfo} is invalid.
     */
    public static ApptInfo parseApptInfo(String apptInfo) throws ParseException {
        requireNonNull(apptInfo);
        String trimmedApptInfo = apptInfo.trim();
        if (!ApptInfo.isValidApptInfo(apptInfo)) {
            throw new ParseException(ApptInfo.MESSAGE_INFO_CONSTRAINTS);
        }
        return new ApptInfo(trimmedApptInfo);
    }

    /**
     * Parses a {@code String apptDrName} into an {@code ApptDrName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code apptDrName} is invalid.
     */
    public static ApptDrName parseApptDrName(String apptDrName) throws ParseException {
        requireNonNull(apptDrName);
        String trimmedApptDrName = apptDrName.trim();
        if (!ApptDrName.isValidDrName(apptDrName)) {
            throw new ParseException(ApptDrName.MESSAGE_DRNAME_CONSTRAINTS);
        }
        return new ApptDrName(trimmedApptDrName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
