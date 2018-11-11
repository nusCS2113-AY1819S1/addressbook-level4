package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DEADLINE_CONTAINS_ILLEGAL_CHARACTERS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.util.StringUtil.isNonZeroUnsignedInteger;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SelectDeadlineCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.ModuleCode;
import seedu.address.model.task.PriorityLevel;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {
    public static final String MESSAGE_INVALID_DEFERRED_DAYS = "Deferred Days is a not-zero unsigned integer.";
    public static final String MESSAGE_INVALID_INDEX = "Index must be a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_HOURS = "Hour(s) must be an integer greater than 0!";
    public static final String MESSAGE_INVALID_RANK = "Rank must be a non-zero positive integer.";
    public static final String MESSAGE_EMPTY_DESCRIPTION = "Description is empty!";
    public static final String MESSAGE_EMPTY_DEFERRED_DAYS = "Deferred Days is empty!";
    public static final String MESSAGE_EMPTY_TITLE = "Title is empty!";
    public static final String MESSAGE_EMPTY_MODULE_CODE = "Module code is empty!";
    public static final String MESSAGE_EMPTY_HOURS = "Hours is empty!";
    public static final String MESSAGE_EMPTY_MILESTONE = "Milestone description is empty!";
    public static final String MESSAGE_EMPTY_RANK = "Rank is empty!";
    public static final String MESSAGE_EMPTY_INDEX = "Index is empty!";
    public static final String MESSAGE_EMPTY_PRIORITY = "Priority level is empty!";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (trimmedIndex.isEmpty()) {
            throw new ParseException(MESSAGE_EMPTY_INDEX);
        } else if (!isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    //@@author ChanChunCheong
    /**
     * Leading and trailing whitespaces will be trimmed from {@code String hours}
     * If DeferredDays is not a positive integer or is too big to be an integer,
     * @throws ParseException
     */
    public static int parseDefferedDays(String deferredDays) throws ParseException {
        requireNonNull(deferredDays);
        String trimmedIndex = deferredDays.trim();
        if (trimmedIndex.isEmpty()) {
            throw new ParseException(MESSAGE_EMPTY_DEFERRED_DAYS);
        } else if (!isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_DEFERRED_DAYS);
        }
        return Integer.parseInt(deferredDays);
    }
    //@@author

    //@@author chelseyong
    /**
     * Leading and trailing whitespaces will be trimmed from {@code String hours}
     * If hours is not an integer or is too big to be an integer,
     * @throws ParseException
     */
    public static int parseHours(String hours) throws ParseException {
        requireNonNull(hours);
        String trimmedHours = hours.trim();
        if (trimmedHours.isEmpty()) {
            throw new ParseException(MESSAGE_EMPTY_HOURS);
        } else if (!isNonZeroUnsignedInteger(trimmedHours)) {
            throw new ParseException(MESSAGE_INVALID_HOURS);
        }
        return Integer.parseInt(trimmedHours);
    }

    /**
     * Leading and trailing whitespaces will be trimmed from {@code String moduleCode}
     */
    public static ModuleCode parseModuleCode(String moduleCode) throws ParseException {
        requireNonNull(moduleCode);
        String trimmedModuleCode = moduleCode.trim().toUpperCase();
        if (trimmedModuleCode.isEmpty()) {
            throw new ParseException(MESSAGE_EMPTY_MODULE_CODE);
        } else if (!ModuleCode.isValidModuleCode(trimmedModuleCode)) {
            throw new ParseException(ModuleCode.MESSAGE_MODULE_CODE_CONSTRAINTS);
        }
        return new ModuleCode(trimmedModuleCode);
    }

    /**
     * Parses a {@code String deadline} into an {@code Deadline}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code deadline} is invalid.
     */
    public static Deadline parseDeadline(String deadline) throws ParseException {
        requireNonNull(deadline);
        String trimmedDeadline = deadline.trim();
        String[] entries = deadline.split("/");

        if (entries.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SelectDeadlineCommand.MESSAGE_USAGE));
        }
        if (entries.length > 3) {
            throw new ParseException(Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
        }
        // Command Exception is thrown to handle 1/mm/yyyy etc.
        for (String s: entries) {
            if (!isNonZeroUnsignedInteger(s.trim())) {
                throw new ParseException(MESSAGE_DEADLINE_CONTAINS_ILLEGAL_CHARACTERS);
            }
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
        if (trimmedPriority.isEmpty()) {
            throw new ParseException(MESSAGE_EMPTY_PRIORITY);
        }
        if (!PriorityLevel.isValidPriorityLevel(trimmedPriority)) {
            throw new ParseException(PriorityLevel.MESSAGE_PRIORITY_CONSTRAINTS);
        }
        return new PriorityLevel(trimmedPriority);
    }

    /**
     * Leading and trailing whitespaces will be trimmed from {@code String title}
     */
    public static String parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (trimmedTitle.isEmpty()) {
            throw new ParseException(MESSAGE_EMPTY_TITLE);
        }
        return trimmedTitle;
    }

    /**
     * Leading and trailing whitespaces will be trimmed from {@code String description}
     */
    public static String parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (trimmedDescription.isEmpty()) {
            throw new ParseException(MESSAGE_EMPTY_DESCRIPTION);
        }
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
    //@@author JeremyInElysium
    /**
     * Leading and trailing whitespaces will be trimmed from {@code String milestoneDescription}
     */
    public static String parseMilestoneDescription(String milestoneDescription) throws ParseException {
        requireNonNull(milestoneDescription);
        String trimmedMilestoneDescription = milestoneDescription.trim();
        if (trimmedMilestoneDescription.isEmpty()) {
            throw new ParseException(MESSAGE_EMPTY_MILESTONE);
        }
        return trimmedMilestoneDescription;
    }

    /**
     * Leading and trailing whitespaces will be trimmed from {@code String rank}
     */
    public static String parseRank(String rank) throws ParseException {
        requireNonNull(rank);
        String trimmedRank = rank.trim();
        if (trimmedRank.isEmpty()) {
            throw new ParseException(MESSAGE_EMPTY_RANK);
        } else if (!isNonZeroUnsignedInteger(trimmedRank)) {
            throw new ParseException(MESSAGE_INVALID_RANK);
        }


        return trimmedRank;
    }

}
