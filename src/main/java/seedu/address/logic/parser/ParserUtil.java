package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medhistory.Allergy;
import seedu.address.model.medhistory.MedHistDate;
import seedu.address.model.medhistory.PrevCountry;
import seedu.address.model.medicalreport.Date;
import seedu.address.model.medicalreport.Information;
import seedu.address.model.medicalreport.Title;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.timetable.ApptDateTime;
import seedu.address.model.timetable.ApptDrName;
import seedu.address.model.timetable.ApptInfo;
import seedu.address.model.timetable.ApptVenue;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

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
        return new Nric(trimmedNric);
    }

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

    /**
     * Parses a {@code String allergy} into an {@code Allergy}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code allergy} is invalid.
     * ParseException is omitted for now.
     */
    public static Allergy parseAllergy(String allergy) {
        requireNonNull(allergy);
        String trimmedAllergy = allergy.trim();

        return new Allergy(trimmedAllergy);
    }

    /**
     * Parses a {@code String prevCountry} into an {@code PrevCountry}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code prevCountry} is invalid.
     * ParseException is omitted for now.
     */
    public static MedHistDate parseMedHistDate(String medHistDate) {
        requireNonNull(medHistDate);
        String trimmedMedHistDate = medHistDate.trim();

        return new MedHistDate(trimmedMedHistDate);
    }

    /**
     * Parses a {@code String prevCountry} into an {@code PrevCountry}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code prevCountry} is invalid.
     * ParseException is omitted for now.
     */
    public static PrevCountry parsePrevCountry(String prevCountry) {
        requireNonNull(prevCountry);
        String trimmedPrevCountry = prevCountry.trim();

        return new PrevCountry(trimmedPrevCountry);
    }

    /**
     * ==================================================
     * PARSER FOR MEDICAL REPORT SUBFIELDS
     * ==================================================
     */

    /**
     * Parses a {@code String title} into an {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Title parseTitle(String title) {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code String date} into an {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     * ParseException is omitted for now.

     */
    public static Date parseDate(String date) {
        requireNonNull(date);
        String trimmedDate = date.trim();
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String information} into an {@code Information}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Information parseInformation(String information) {
        requireNonNull(information);
        String trimmedInformation = information.trim();
        return new Information(trimmedInformation);
    }

    /**
     * ==================================================
     * PARSER FOR APPT SUBFIELDS
     * ==================================================
     */

    /**
     * Parses a {@code String apptDateTime} into an {@code ApptDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static ApptDateTime parseApptTime(String apptDateTime) {
        requireNonNull(apptDateTime);
        String trimmedApptDateTime = apptDateTime.trim();
        return new ApptDateTime(trimmedApptDateTime);
    }

    /**
     * Parses a {@code String apptVenue} into an {@code ApptVenue}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static ApptVenue parseApptVenue(String apptVenue) {
        requireNonNull(apptVenue);
        String trimmedApptVenue = apptVenue.trim();
        return new ApptVenue(trimmedApptVenue);
    }

    /**
     * Parses a {@code String apptInfo} into an {@code ApptInfo}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static ApptInfo parseApptInfo(String apptInfo) {
        requireNonNull(apptInfo);
        String trimmedApptInfo = apptInfo.trim();
        return new ApptInfo(trimmedApptInfo);
    }

    /**
     * Parses a {@code String apptDrName} into an {@code ApptDrName}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static ApptDrName parseApptDrName(String apptDrName) {
        requireNonNull(apptDrName);
        String trimmedApptDrName = apptDrName.trim();
        return new ApptDrName(trimmedApptDrName);
    }
}
