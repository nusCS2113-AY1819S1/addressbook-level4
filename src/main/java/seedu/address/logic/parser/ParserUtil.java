package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.model.task.Deadline;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.PriorityLevel;

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

    //    /**
    //     * Parses a {@code String name} into a {@code Name}.
    //     * Leading and trailing whitespaces will be trimmed.
    //     *
    //     * @throws ParseException if the given {@code name} is invalid.
    //     */
    //    public static Name parseName(String name) throws ParseException {
    //        requireNonNull(name);
    //        String trimmedName = name.trim();
    //        if (!Name.isValidName(trimmedName)) {
    //            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
    //        }
    //        return new Name(trimmedName);
    //    }

    /**
     * Parses a {@code String deadline} into an {@code Deadline}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code deadline} is invalid.
     */
    public static Deadline parseDeadline(String deadline) throws ParseException {
        requireNonNull(deadline);
        String trimmedDeadline = deadline.trim();
        if (!Deadline.isValidDeadline(trimmedDeadline)) {
            throw new ParseException(Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
        }
        return new Deadline(trimmedDeadline);
    }

    /**
     * Parses a {@code String priority} into an {@code Priority}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code priority} is invalid.
     */
    public static PriorityLevel parsePriorityLevel(String priority) throws ParseException {
        requireNonNull(priority);
        String trimmedPriority = priority.trim();
        if (!PriorityLevel.isValidPriorityLevel(trimmedPriority)) {
            throw new ParseException(PriorityLevel.MESSAGE_PRIORITY_CONSTRAINTS);
        }
        //        return new PriorityLevel(trimmedPriority);
        return new PriorityLevel(trimmedPriority);
    }

    /**
     * Leading and trailing whitespaces will be trimmed from {@code String title}
     */
    public static String parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        return trimmedTitle;
    }

    /**
     * Leading and trailing whitespaces will be trimmed from {@code String description}
     */
    public static String parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        return trimmedDescription;
    }

    //@@author emobeany
    /**
     * Leading and trailing whitespaces will be trimmed from {@code String day}
     */
    public static String parseDay(String day) throws ParseException {
        requireNonNull(day);
        String trimmedDay = day.trim();
        return trimmedDay;
    }

    /**
     * Leading and trailing whitespaces will be trimmed from {@code String month}
     */
    public static String parseMonth(String month) throws ParseException {
        requireNonNull(month);
        String trimmedMonth = month.trim();
        return trimmedMonth;
    }

    /**
     * Leading and trailing whitespaces will be trimmed from {@code String year}
     */
    public static String parseYear(String year) throws ParseException {
        requireNonNull(year);
        String trimmedYear = year.trim();
        return trimmedYear;
    }

}
