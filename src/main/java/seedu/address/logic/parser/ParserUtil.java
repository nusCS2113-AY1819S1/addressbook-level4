package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.ItemName;
import seedu.address.model.item.ItemQuantity;
import seedu.address.model.ledger.Account;
import seedu.address.model.ledger.DateLedger;
import seedu.address.model.member.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.Events.Venue;
import seedu.address.model.Events.Description;
import seedu.address.model.Events.EventName;
import seedu.address.model.Events.EventDate;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

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
     * Parses a {@code String name} into a {@code EventName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static EventName parseEventName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!EventName.CheckValid(trimmedName)) {
            throw new ParseException(EventName.MESSAGE_EVENTNAME_CONSTRAINTS);
        }
        return new EventName(trimmedName);
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static  Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Venue.CheckValid(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_DESCRIPTION_CCONSTRAINT);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String date} into a {@code EventDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code EventDate} is invalid.
     */
    public static EventDate parserEventDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if(!EventDate.CheckValid(trimmedDate)) {
            throw new  ParseException(EventDate.MESSAGE_EVENTDATE_CONSTRAINTS);
        }
        return new EventDate(trimmedDate);
    }
    /**
     * Parses a {@code String venue} into a {@code Venue}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code venue} is invalid.
     */
    public static  Venue parseVenue(String venue) throws ParseException {
        requireNonNull(venue);
        String trimmedVenue = venue.trim();
        if (!Venue.CheckValid(trimmedVenue)) {
            throw new ParseException(Venue.MESSAGE_VENUE_CONSTRAINT);
        }
        return new Venue(trimmedVenue);
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

    public static Postalcode parsePostalcode(String postalcode) throws ParseException {
        requireNonNull(postalcode);
        String trimmedPostalcode = postalcode.trim();
        if (!Postalcode.isValidPostalcode(trimmedPostalcode)) {
            throw new ParseException(Postalcode.MESSAGE_POSTALCODE_CONSTRAINTS);
        }
        return new Postalcode(trimmedPostalcode);
    }

    public static Major parseMajor(String major) throws ParseException {
        requireNonNull(major);
        String trimmedMajor = major.trim();
        if (!Major.isValidMajor(trimmedMajor)) {
            throw new ParseException(Major.MESSAGE_MAJOR_CONSTRAINTS);
        }
        return new Major(trimmedMajor);
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
     * Parses a {@code String ItemName} into a {@code ItemName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code itemName} is invalid.
     */
    public static ItemName parseItemName(String itemName) throws ParseException {
        requireNonNull(itemName);
        String trimmedItemName = itemName.trim();
        if (!ItemName.isValidItemName(trimmedItemName)) {
            throw new ParseException(ItemName.MESSAGE_ITEM_NAME_CONSTRAINTS);
        }
        return new ItemName(trimmedItemName);
    }

    /**
     * Parses a {@code String itemQuantity} into a {@code ItemQuantity}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code itemQuantity} is invalid.
     */
    public static ItemQuantity parseItemQuantity(String itemQuantity) throws ParseException {
        requireNonNull(itemQuantity);
        String trimmedItemQuantity = itemQuantity.trim();
        if (!ItemQuantity.isValidItemQuantity(trimmedItemQuantity)) {
            throw new ParseException(ItemQuantity.MESSAGE_ITEM_QUANTITY_CONSTRAINTS);
        }
        return new ItemQuantity(trimmedItemQuantity);
    }

    /**
     * Parses a {@code Double balance} into a {@code Account}.
     * Leading and trailing decimal places will be trimmed to 2 decimal places.
     *
     * @throws ParseException if the given {@code balance} is invalid.
     */
    public static Double parseBalance(String balance) throws ParseException {
        requireNonNull(balance);
        //DecimalFormat decimalFormat = new DecimalFormat("#.##");
        Double trimmedBalance = Double.parseDouble(balance);
        if (!Account.isValidBalance(balance)) {
            throw new ParseException(Account.MESSAGE_BALANCE_CONSTRAINTS);
        }
        return trimmedBalance;
    }

    /**
     * Parses a {@code String date} into a {@code Leger}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */

    public static DateLedger parseDateLedger(String date) throws ParseException {
        requireNonNull(date);
        //DateFormat formatter = new SimpleDateFormat("DD/MM");
        //String trimmedDate = formatter.format(date);
        String trimmedDate = date.trim();
        if (!DateLedger.isValidDateLedger(trimmedDate)) {
            throw new ParseException(DateLedger.MESSAGE_DATE_CONSTRAINTS);
        }
        return new DateLedger(trimmedDate);
    }
}
