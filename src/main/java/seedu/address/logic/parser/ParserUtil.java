package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.HYPHEN_DAY;
import static seedu.address.logic.parser.CliSyntax.HYPHEN_MONTH;
import static seedu.address.logic.parser.CliSyntax.HYPHEN_WEEK;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.analysis.AnalysisPeriodType;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.drink.Date;
import seedu.address.model.drink.Price;
import seedu.address.model.drink.Quantity;
import seedu.address.model.tag.Tag;
import seedu.address.model.user.AuthenticationLevel;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

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
     * Parses a {@code String userName} into a {@code UserName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code UserName} is invalid .
     */
    public static UserName parseUserName(String userName) throws ParseException {
        requireNonNull(userName);
        String trimmedUserName = userName.trim();
        if (!UserName.isValidUserName(trimmedUserName)) {
            throw new ParseException(UserName.MESSAGE_USER_NAME_CONSTRAINTS);
        }

        if (UserName.isUserNameTooLong(trimmedUserName)) {
            throw new ParseException(UserName.MESSAGE_USER_NAME_LENGTH_CONSTRAINTS);
        }
        return new UserName(trimmedUserName);
    }

    /**
     * Parses a {@code String password} into a {@code Password}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Password} is invalid.
     */
    public static Password parsePassword(String password) throws ParseException {
        requireNonNull(password);
        String trimmedPassword = password.trim();
        if (!Password.isValidPassword(trimmedPassword)) {
            throw new ParseException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        if (Password.isPasswordTooLong(trimmedPassword)) {
            throw new ParseException(Password.MESSAGE_PASSWORD_LENGTH_CONSTRAINTS);
        }

        return new Password(trimmedPassword);
    }

    /**
     * Parses a {@code String authenticationLevel } into a {@code AuthenticationLevel}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code AuthenticationLevel} is invalid.
     */
    public static AuthenticationLevel parseAuthenticationLevel(String authenticationLevel) throws ParseException {
        requireNonNull(authenticationLevel);
        String trimmedAuthenticationLevel = authenticationLevel.trim().toUpperCase();
        if (!AuthenticationLevel.isAuthenticationLevelValid(trimmedAuthenticationLevel)) {
            throw new ParseException(AuthenticationLevel.MESSAGE_AUTHENTICATIONLEVEL_CONSTRAINTS);
        }
        return new AuthenticationLevel(trimmedAuthenticationLevel);
    }
    // ================== Drink-related parsing ===================

    /**
     * Parses a {@code String itemName} into a {@code String itenName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static seedu.address.model.drink.Name parseDrinkName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!seedu.address.model.drink.Name.isValidName(trimmedName)) {
            throw new ParseException(seedu.address.model.drink.Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new seedu.address.model.drink.Name(trimmedName);
    }

    /**
     * Parses a {@code String price} into a {@code Price}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code price} is invalid.
     */
    public static Price parseDrinkCostPrice(String price) throws ParseException {
        requireNonNull(price);
        String trimmedCostPrice = price.trim();
        if (!Price.isValidPrice(trimmedCostPrice)) {
            throw new ParseException(Price.MESSAGE_PRICE_CONSTRAINTS);
        }
        return new Price(trimmedCostPrice);
    }

    /**
     * Parses a {@code String price} into a {@code Price}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code price} is invalid.
     */
    public static Price parseDrinkDefaultSellingPrice(String defaultSellingPrice) throws ParseException {
        requireNonNull(defaultSellingPrice);
        String trimmedDefaultSellingPrice = defaultSellingPrice.trim();
        if (!Price.isValidPrice(trimmedDefaultSellingPrice)) {
            throw new ParseException(Price.MESSAGE_PRICE_CONSTRAINTS);
        }
        return new Price(trimmedDefaultSellingPrice);
    }

    /**
     * Parses {@code String quantity} into a {@code Quantity}.
     */
    public static Quantity parseQuantity(String quantity) throws ParseException {
        requireNonNull(quantity);
        String trimmedQuantity = quantity.trim();
        if (!Quantity.isValidQuantity(trimmedQuantity)) {
            throw new ParseException(Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
        }
        return new Quantity(trimmedQuantity);
    }


    /**
     * Parses {@code String date} into a {@code Date}.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        if (!Date.isExistingDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_NON_EXISTING_DATE);
        }
        return new Date(trimmedDate);
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
     * Parses {@code string period} into a {@code AnalysisPeriodType}
     */
    public static AnalysisPeriodType parseAnalysisPeriod(String period) throws ParseException {
        requireNonNull(period);
        period = period.replaceAll("\\s+", "");

        switch (period) {
        case HYPHEN_DAY:
            return AnalysisPeriodType.DAY;
        case HYPHEN_WEEK:
            return AnalysisPeriodType.WEEK;
        case HYPHEN_MONTH:
            return AnalysisPeriodType.MONTH;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AnalysisPeriodType.WRONG_PERIOD_MESSAGE));
        }
    }
}
