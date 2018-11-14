package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.distributor.DistributorPhone;
import seedu.address.model.distributor.DistributorProduct;
import seedu.address.model.login.Password;
import seedu.address.model.login.Username;
import seedu.address.model.product.Name;
import seedu.address.model.product.ProductInfo;
import seedu.address.model.product.ProductsDistributorName;
import seedu.address.model.product.RemainingItems;
import seedu.address.model.product.SerialNumber;
import seedu.address.model.tag.Tag;
import seedu.address.model.timeidentifiedclass.Reminder;
import seedu.address.model.timeidentifiedclass.TimeIdentifiedClass;

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
     * Returns the time entered by the user for a given reminder.
     * @param reminderTime
     * @return trimmed reminder time (removing trailing whitespaces).
     * @throws ParseException if {@code reminderTime} is in the wrong format or invalid.
     */

    public static String parseReminderTime(String reminderTime) throws ParseException {
        requireNonNull(reminderTime);
        if (!Reminder.isValidReminderTime(reminderTime)) {
            throw new ParseException(Reminder.REMINDER_TIME_CONSTRAINTS);
        }
        return reminderTime.trim();
    }

    /**
     * Returns the date and time entered by the user
     * @param dateAndTime
     * @return trimmed {@code dateAndTime}
     * @throws ParseException if {@code dateAndTime} is in the wrong format or invalid.
     */
    public static String parseDateAndTime(String dateAndTime) throws ParseException {
        requireNonNull(dateAndTime);
        if (!TimeIdentifiedClass.isValidDateAndTime(dateAndTime)) {
            throw new ParseException("Time must be in yyyy/MM/dd HH:mm:ss format and must be a valid time");
        }
        return dateAndTime.trim();
    }

    /**
     * The following method parses the date from user input
     * @param date
     * @return date (removing trailing whitespaces)
     * @throws ParseException if {@code date} is in the wrong format or is invalid.
     */
    public static String parseDate(String date) throws ParseException {
        requireNonNull(date);
        if (!TimeIdentifiedClass.isValidDate(date)) {
            throw new ParseException("Dates must be valid and in yyyy/MM/dd format");
        }
        return date.trim();
    }
    /**
     * Parses a {@code String name} into a {@code DistributorName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static DistributorName parseDistName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!DistributorName.isValidName(trimmedName)) {
            throw new ParseException(DistributorName.MESSAGE_NAME_CONSTRAINTS);
        }
        return new DistributorName(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code SerialNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static SerialNumber parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!SerialNumber.isValidPhone(trimmedPhone)) {
            throw new ParseException(SerialNumber.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new SerialNumber(trimmedPhone);
    }

    /**
     * Parses a {@code String phone} into a {@code DistributorPhone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static DistributorPhone parseDistPhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!DistributorPhone.isValidPhone(trimmedPhone)) {
            throw new ParseException(DistributorPhone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new DistributorPhone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code ProductInfo}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.

     */
    public static ProductInfo parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!ProductInfo.isValidAddress(trimmedAddress)) {
            throw new ParseException(ProductInfo.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new ProductInfo(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code ProductsDistributorName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
    */
    public static ProductsDistributorName parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!ProductsDistributorName.isValidEmail(trimmedEmail)) {
            throw new ParseException(ProductsDistributorName.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new ProductsDistributorName(trimmedEmail);
    }

    /**
     * Parses a {@code String remainingItems} into a {@code RemainingItems}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code remainingItems} is invalid.
     */
    public static RemainingItems parseRemainingItems(String remainingItems) throws ParseException {
        requireNonNull(remainingItems);
        String trimmedRemainingItems = remainingItems.trim();
        if (!RemainingItems.isValidRemainingItems(trimmedRemainingItems)) {
            throw new ParseException(RemainingItems.MESSAGE_REMAINING_ITEMS_CONSTRAINTS);
        }
        return new RemainingItems(trimmedRemainingItems);
    }

    /**
     * Parses a {@code String distributorproduct} into a {@code DistributorProduct}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code distributorproduct} is invalid.
     */

    public static DistributorProduct parseDistProd(String distributorproduct) throws ParseException {
        requireNonNull(distributorproduct);
        String trimmedProd = distributorproduct.trim();
        if (!Name.isValidName(trimmedProd)) {
            throw new ParseException(DistributorProduct.MESSAGE_DISTPROD_CONSTRAINTS);
        }
        return new DistributorProduct(trimmedProd);
    }

    /**
     * Parses {@code Collection<String> distributorproducts} into a {@code Set<DistributorProduct>}.
     */

    public static Set<DistributorProduct> parseDistProds(Collection<String> distributorproducts)
            throws ParseException {
        requireNonNull(distributorproducts);
        final Set<DistributorProduct> prodSet = new HashSet<>();
        for (String prodName : distributorproducts) {
            prodSet.add(parseDistProd(prodName));
        }
        return prodSet;
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
     * Parses a {@code String username} into an {@code Username}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code username} is invalid.
     */
    public static Username parseUsername(String username) throws ParseException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (!Username.isValidUsername(trimmedUsername)) {
            throw new ParseException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }
        return new Username(trimmedUsername);
    }

    /**
     * Parses a {@code String password} into an {@code Password}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code password} is invalid.
     */
    public static Password parsePassword(String password) throws ParseException {
        requireNonNull(password);
        String trimmedPassword = password.trim();
        if (!Password.isValidPassword(trimmedPassword)) {
            throw new ParseException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        return new Password(trimmedPassword);
    }

}
