package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.budgetelements.ClubName;
import seedu.address.model.budgetelements.ExpectedTurnout;
import seedu.address.model.budgetelements.NumberOfEvents;
import seedu.address.model.clubbudget.TotalBudget;
import seedu.address.model.login.UserId;
import seedu.address.model.login.UserPassword;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses a {@code String userId} into a {@code UserId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code userId} is invalid.
     */
    public static UserId parseUserId(String userId) throws ParseException {
        requireNonNull(userId);
        String trimmeduserId = userId.trim();
        if (!UserId.isValidUserId(trimmeduserId)) {
            throw new ParseException(UserId.MESSAGE_USERID_CONSTRAINTS);
        }
        return new UserId(trimmeduserId);
    }

    /**
     * Parses a {@code String userPassword} into a {@code UserPassword}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code userPassword} is invalid.
     */
    public static UserPassword parseUserPassword(String userPassword) throws ParseException {
        requireNonNull(userPassword);
        String trimmeduserPassword = userPassword.trim();
        if (!UserPassword.isValidUserPassword(trimmeduserPassword)) {
            throw new ParseException(UserPassword.MESSAGE_USERPASSWORD_CONSTRAINTS);
        }
        return new UserPassword(trimmeduserPassword);
    }


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
     * Parses a {@code String clubname} into a {@code ClubName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code clubname} is invalid.
     */
    public static ClubName parseClubName(String clubname) throws ParseException {
        requireNonNull(clubname);
        String trimmedClubName = clubname.trim();
        if (!ClubName.isValidClubName(trimmedClubName)) {
            throw new ParseException(ClubName.MESSAGE_CLUB_NAME_CONSTRAINTS);
        }
        return new ClubName(trimmedClubName);
    }

    /**
     * Parses a {@code String expectedturnout} into a {@code ExpectedTurnout}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code expectedturnout} is invalid.
     */
    public static ExpectedTurnout parseExpectedTurnout(String expectedturnout) throws ParseException {
        requireNonNull(expectedturnout);
        String trimmedExpectedTurnout = expectedturnout.trim();
        if (!ExpectedTurnout.isValidExpectedTurnout(trimmedExpectedTurnout)) {
            throw new ParseException(ExpectedTurnout.MESSAGE_EXPECTED_TURNOUT_CONSTRAINTS);
        }
        return new ExpectedTurnout(trimmedExpectedTurnout);
    }

    /**
     * Parses a {@code String numberofevents} into a {@code NumberOfEvents}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code numberofevents} is invalid.
     */
    public static NumberOfEvents parseNumberOfEvents(String numberofevents) throws ParseException {
        requireNonNull(numberofevents);
        String trimmedNumberOfEvents = numberofevents.trim();
        if (!NumberOfEvents.isValidNumberOfEvents(trimmedNumberOfEvents)) {
            throw new ParseException(NumberOfEvents.MESSAGE_NUMBER_OF_EVENTS_CONSTRAINTS);
        }
        return new NumberOfEvents(trimmedNumberOfEvents);
    }

    /**
     * Parses a {@code int totalBudget} into a {@code int}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code totalBudget} is invalid.
     */
    public static TotalBudget parseTotalBudget(String totalBudget) throws ParseException {
        requireNonNull(totalBudget);
        String trimmedTotalBudget = totalBudget.trim();
        if (!TotalBudget.isValidTotalBudget(trimmedTotalBudget)) {
            throw new ParseException(TotalBudget.MESSAGE_TOTAL_BUDGET_CONSTRAINTS);
        }
        return new TotalBudget(trimmedTotalBudget);
    }
}
