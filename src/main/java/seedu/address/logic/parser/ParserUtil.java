package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.person.Gender.inputTransform;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.distribute.DistributeAlgorithm;
import seedu.address.model.email.Message;
import seedu.address.model.email.Subject;
import seedu.address.model.group.GroupLocation;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nationality;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    private static String groupName;

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
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
     * Parses a {@code String gender} into a {@code Gender}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code gender} is invalid.
     */
    public static Gender parseGender(String gender) throws ParseException {
        requireNonNull(gender);
        String trimmedGender = gender.trim();
        if (!Gender.isInputAccepted(trimmedGender)) {
            throw new ParseException(Gender.MESSAGE_NAME_CONSTRAINTS);
        }
        trimmedGender = inputTransform(trimmedGender);
        requireNonNull(trimmedGender);
        return new Gender(trimmedGender);
    }

    /**
     * Parses a {@code String nationality} into a {@code Nationality}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code nationality} is invalid.
     */
    public static Nationality parseNationality(String countryCode) throws ParseException {
        requireNonNull(countryCode);
        String trimmedCountryCode = countryCode.trim();
        if (!Nationality.isValidCountryCode(trimmedCountryCode)) {
            throw new ParseException(Nationality.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Nationality(trimmedCountryCode);
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
     * Parses a {@code String groupName} into a {@code GroupName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code GroupName} is invalid.
     */
    public static GroupName parseGroupName(String groupName) throws ParseException {
        requireNonNull(groupName);
        String trimmedGroupName = groupName.trim();
        if (!GroupName.isValidGroupName(trimmedGroupName)) {
            throw new ParseException(GroupName.MESSAGE_GROUP_NAME_CONSTRAINTS);
        }
        return new GroupName(trimmedGroupName);
    }

    /**
     * Parses a {@code String groupLocation} into a {@code GroupLocation}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code GroupLocation} is invalid.
     */
    public static GroupLocation parseGroupLocation(String groupLocation) throws ParseException {
        requireNonNull(groupLocation);
        String trimmedGroupLocation = groupLocation.trim();
        if (!GroupLocation.isValidGroupLocation(trimmedGroupLocation)) {
            throw new ParseException(GroupLocation.MESSAGE_GROUP_LOCATION_CONSTRAINTS);
        }
        return new GroupLocation(trimmedGroupLocation);
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
     * Parses a {@code String Grade} into a {@code Grade}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code grade} is invalid.
     */
    public static Grade parseGrade(String grade) throws ParseException {
        requireNonNull(grade);
        String trimmedGrade = grade.trim();
        if (!Grade.isValidGrade(trimmedGrade)) {
            throw new ParseException(Grade.MESSAGE_GRADE_CONSTRAINTS);
        }
        return new Grade(trimmedGrade);
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
     * Parses {@code String subject} into a {@code Subject}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code subject} is invalid.
     */
    public static Subject parseSubject(String subject) throws ParseException {
        requireNonNull(subject);
        String trimmedSubject = subject.trim();
        if (!Subject.isValidSubject(trimmedSubject)) {
            throw new ParseException(Subject.MESSAGE_SUBJECT_CONSTRAINTS);
        }
        return new Subject(trimmedSubject);
    }

    /**
     * Parses {@code String message} into a {@code Message}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code message} is invalid.
     */
    public static Message parseMessage(String message) throws ParseException {
        requireNonNull(message);
        String trimmedMessage = message.trim();
        if (!Message.isValidMessage(trimmedMessage)) {
            throw new ParseException(Message.MESSAGE_MESSAGE_CONSTRAINTS);
        }
        return new Message(trimmedMessage);
    }

    /** Checks if input value by the user is not Null or not 0
     * @param value
     * @return
     * @throws ParseException
     */
    public static int parseInteger(String value) throws ParseException {
        String trimmedInt = value.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedInt)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Integer.parseInt(trimmedInt);
    }


    /**
     * Conducts the check of flags during user command input
     * Accepts "true" or '1' to assert true
     * Accepts "false" or '0' to assert false
     * @param isFlagged
     * @return
     * @throws ParseException
     */
    public static Boolean parseIsFlagged(String isFlagged) throws ParseException {
        requireNonNull(isFlagged);
        String trimmedFlaggedValue = isFlagged.trim().toLowerCase();
        switch(trimmedFlaggedValue) {
        case "false":
        case "0":
            trimmedFlaggedValue = "false";
            break;
        case "true":
        case "1":
            trimmedFlaggedValue = "true";
            break;
        default:
            throw new ParseException(DistributeAlgorithm.MESSAGE_FLAG_ERROR);
        }
        Boolean flag = Boolean.valueOf(trimmedFlaggedValue);
        if (flag) {
            return true;
        } else {
            return false;
        }
    }

}

