package seedu.planner.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.planner.model.tag.Tag.MESSAGE_TAG_NUM_CONSTRAINTS;
import static seedu.planner.model.tag.Tag.NUM_MAX_TAGS;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.commons.util.DateUtil;
import seedu.planner.commons.util.StringUtil;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.DirectoryPath;
import seedu.planner.model.Month;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.record.Name;
import seedu.planner.model.tag.Tag;


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
     * Parses a {@code String date} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDateFormat(trimmedDate)) {
            if (trimmedDate.equals(Date.DATE_INPUT_TODAY)) {
                return new Date(DateUtil.getDateTodayForInput());
            } else if (trimmedDate.equals(Date.DATE_INPUT_YESTERDAY)) {
                return new Date(DateUtil.getDateYesterdayForInput());
            } else {
                throw new ParseException(Date.MESSAGE_DATE_CONSTRAINTS);
            }
        }
        Date result;
        try {
            result = new Date(trimmedDate);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
        return result;
    }

    /**
     * Parses a string into a {@code Month}.
     * Leading and trailing whitespaces will be trimmed.
     * @param month month given as a string
     * @throws ParseException if the given {@code month} is invalid.
     */
    public static Month parseMonth(String month) throws ParseException {
        requireNonNull(month);
        String trimmedMonth = month.trim();
        if (!Month.isValidMonth(trimmedMonth)) {
            throw new ParseException(Month.MESSAGE_MONTH_CONSTRAINTS);
        }
        Month result;
        try {
            result = new Month(trimmedMonth);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
        return result;
    }

    /**
     * Parses a {@code String moneyFlow} into an {@code MoneyFlow}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code MoneyFlow} is invalid.
     */
    public static MoneyFlow parseMoneyFlow(String moneyFlow) throws ParseException {
        requireNonNull(moneyFlow);
        String trimmedMoneyFlow = moneyFlow.trim();
        if (!MoneyFlow.isValidMoneyFlow(trimmedMoneyFlow)) {
            throw new ParseException(MoneyFlow.MESSAGE_MONEY_FLOW_CONSTRAINTS);
        }
        return new MoneyFlow(trimmedMoneyFlow);
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
        if (tagSet.size() > NUM_MAX_TAGS) {
            throw new ParseException(String.format(MESSAGE_TAG_NUM_CONSTRAINTS, NUM_MAX_TAGS));
        }
        return tagSet;
    }

    /**
     * Parses {@code Directory Path} into a {@code DirectoryPath}
     */
    public static String parseDirectoryString(String dirPath) throws ParseException {
        requireNonNull(dirPath);
        if (!DirectoryPath.isValidDirectory(dirPath)) {
            throw new ParseException(String.format(
                    Messages.MESSAGE_INVALID_COMMAND_FORMAT, Messages.MESSAGE_UNREALISTIC_DIRECTORY));
        }
        return dirPath;
    }

    /**
     * Parses {@code Directory Path} into a {@code DirectoryPath}
     */
    public static String parseFilePathString(String dirPath) throws ParseException {
        requireNonNull(dirPath);
        System.out.println(dirPath);
        if (!DirectoryPath.isValidFilePath(dirPath)) {
            throw new ParseException(String.format(
                    Messages.MESSAGE_INVALID_COMMAND_FORMAT, Messages.MESSAGE_UNREALISTIC_DIRECTORY));
        }
        return dirPath;
    }
}
