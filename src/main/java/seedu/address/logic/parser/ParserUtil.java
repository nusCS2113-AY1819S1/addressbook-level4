package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leave.Approval;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.EmployeeId;
import seedu.address.model.person.Address;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.Mode;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.WorkingRate;
import seedu.address.model.person.password.Password;
import seedu.address.model.prioritylevel.PriorityLevel;
import seedu.address.model.prioritylevel.PriorityLevelEnum;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.TimeEnd;
import seedu.address.model.schedule.TimeStart;
import seedu.address.model.schedule.Venue;
import seedu.address.model.tag.Tag;

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
     * Parses a {@code String nric} into a {@Code Nric}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if given {@code nric} does not conform to the requirements.
     */
    public static Nric parseNric(String nric) throws ParseException {
        requireNonNull(nric);
        String trimmedNric = nric.trim();
        if (!Nric.isValidNric(nric)) {
            throw new ParseException(Nric.MESSAGE_NRIC_CONSTRAINTS);
        }
        return new Nric(trimmedNric);
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

    //@@author pinjuen
    /**
     * Parses a {@code String mode} into an {@code Mode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code mode} is invalid.
     */
    public static Mode parseMode(String mode) throws ParseException {
        requireNonNull(mode);
        String trimmedMode = mode.trim();
        if (!Mode.isValidMode(trimmedMode)) {
            throw new ParseException(Mode.MESSAGE_MODE_CONSTRAINTS);
        }
        return new Mode(trimmedMode);
    }
    //@@author

    /**
     * Parses a {@code String department} into a {@code Department}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code department} is invalid.
     */
    public static Department parseDepartment(String department) throws ParseException {
        requireNonNull(department);
        String trimmedDepartment = department.trim();
        if (!Department.isValidDepartment(trimmedDepartment)) {
            throw new ParseException(Department.MESSAGE_DEPARTMENT_CONSTRAINTS);
        }
        return new Department(trimmedDepartment);
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
     * Parses a {@code String nric} into a {@code EmployeeId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code nric} is invalid.
     */
    public static EmployeeId parseEmployeeId(String nric) throws ParseException {
        requireNonNull(nric);
        String trimmedNric = nric.trim();
        if (!EmployeeId.isValidEmployeeId(trimmedNric)) {
            throw new ParseException(EmployeeId.MESSAGE_NRIC_CONSTRAINTS);
        }
        return new EmployeeId(trimmedNric);
    }

    /**
     * Parses a {@code String date} into a {@code date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String status} into a {@code status}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code status} is invalid.
     */
    public static Approval parseApproval(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim();
        if (!Approval.isValidApproval(trimmedStatus)) {
            throw new ParseException(Approval.MESSAGE_APPROVAL_CONSTRAINTS);
        }
        return new Approval(trimmedStatus);
    }
    /**
     * Parses a {@code String password} into a {@code Password}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the required format for password is invalid.
     */
    public static Password parsePassword(String password) throws ParseException {
        requireNonNull(password);
        String trimmedPassword = password.trim();
        if (!Password.isValidPassword(password)) {
            throw new ParseException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        return new Password(trimmedPassword);
    }

    /**
     * Parses a {@code String timeStart}, {@code String timeEnd}, {@code String venue} into a {@code Schedule}.
     * Leading and trailing whitespaces will be trimmed
     */
    public static Schedule parseSchedule(String timeStart, String timeEnd, String venue) throws ParseException {
        requireAllNonNull(timeStart, timeEnd, venue);

        String trimmedTimeStart = timeStart.trim();
        if (!TimeStart.isValidTimeStart(timeStart)) {
            throw new ParseException(TimeStart.MESSAGE_TIME_START_CONSTRAINTS);
        }
        TimeStart start = new TimeStart(trimmedTimeStart);

        String trimmedTimeEnd = timeEnd.trim();
        if (!TimeEnd.isValidTimeEnd(timeEnd)) {
            throw new ParseException(TimeEnd.MESSAGE_TIME_END_CONSTRAINTS);
        }
        TimeEnd end = new TimeEnd(trimmedTimeEnd);

        String trimmedVenue = venue.trim();
        if (!Venue.isValidVenue(venue)) {
            throw new ParseException(Venue.MESSAGE_VENUE_CONSTRAINTS);
        }
        Venue place = new Venue(trimmedVenue);

        Schedule schedule;
        try {
            schedule = new Schedule(start, end, place);
        } catch (Exception e) {
            throw new ParseException(Schedule.MESSAGE_TIME_CONSTRAINTS);
        }

        return schedule;
    }

    /**
     * Parses {@code Collection<String> schedules} into a {@code Set<Schedule>}.
     */
    public static Set<Schedule> parseSchedules(Collection<String> schedules) throws ParseException {
        requireNonNull(schedules);
        final Set<Schedule> scheduleSet = new HashSet<>();
        for (String schedule : schedules) {
            scheduleSet.add(parseSchedule(schedule, schedule, schedule));
        }
        return scheduleSet;
    }

    /** Parses a {@code String priorityLevel} into a {@code PriorityLevel}.
     * @throws ParseException is the priorityLevel does not fall between the valid levels.
     */
    public static PriorityLevel parsePriorityLevel(String priorityLevel) throws ParseException {
        requireNonNull(priorityLevel);

        int priorityLevelCode;
        try {
            priorityLevelCode = Integer.valueOf(priorityLevel.trim());
        } catch (NumberFormatException e) {
            throw new ParseException(PriorityLevel.MESSAGE_PRIORITY_CONSTRAINTS);
        }

        if (!PriorityLevelEnum.isValidPriorityLevel(priorityLevelCode)) {
            throw new ParseException(PriorityLevel.MESSAGE_PRIORITY_CONSTRAINTS);
        }
        return new PriorityLevel(priorityLevelCode);
    }

    /**
     * Parses a {@code String workingRate} into a {@code WorkingRate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code workingRate} is invalid.
     */
    public static WorkingRate parseWorkingRate(String workingRate) throws ParseException {
        requireNonNull(workingRate);
        String trimmedWorkingRate = workingRate.trim();
        if (!WorkingRate.isValidWorkingRate(workingRate)) {
            throw new ParseException(WorkingRate.MESSAGE_WORKINGRATE_CONSTRAINTS);
        }
        return new WorkingRate(trimmedWorkingRate);
    }

    //@@author jylee-git
    //=========================== CONDITION CHECKERS ================================================

    /**
     * Checks and returns true if there is only one prefix of each type.
     *
     * WARNING: This method WILL NOT WORK for AddCommand and DeleteCommand due to address regex accepting
     *  input which contains string prefix of other prefixes, which can crash the whole system.
     */
    public static boolean hasOnlyOnePrefixOfSpecifiedType(String userInput, Prefix... prefixes) throws ParseException {
        for (Prefix prefix : prefixes) {
            if (prefix.equals(CliSyntax.PREFIX_ADDRESS)) {
                throw new ParseException("Exception thrown because address prefix is entered.");
            }
            if (userInput.indexOf(prefix.getPrefix()) != userInput.lastIndexOf(prefix.getPrefix())) {
                return false;
            }
        }
        //return argument.indexOf(prefix) == argument.lastIndexOf(prefix);
        return true;
    }
}
