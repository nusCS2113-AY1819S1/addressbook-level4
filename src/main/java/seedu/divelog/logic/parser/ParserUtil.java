package seedu.divelog.logic.parser;

import seedu.divelog.commons.core.Messages;
import seedu.divelog.commons.core.index.Index;
import seedu.divelog.commons.util.StringUtil;
import seedu.divelog.logic.commands.AddCommand;
import seedu.divelog.logic.parser.exceptions.ParseException;
import seedu.divelog.model.dive.DepthProfile;


/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_DEPTH = "Depth must be a number.";
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
     * @author arjo
     * Parses depth profile
     * @param depth
     * @return a Depth Profile object.
     */
    public static DepthProfile parseDepth(String depth) throws ParseException {
        try {
            return new DepthProfile(Float.valueOf(depth));
        } catch (NumberFormatException n) {
            throw new ParseException(MESSAGE_INVALID_DEPTH);
        }
    }


    /**
     *  @author Cjunx
     *  Returns true if string given is DATE FORMATTED
     * {@code ArgumentMultimap}.
     */
    public static void checkDateformat(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(CliSyntax.PREFIX_DATE_START).get().length() != 8
                || argMultimap.getValue(CliSyntax.PREFIX_DATE_END).get().length() != 8) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_DATE_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            Integer.parseInt(argMultimap.getValue(CliSyntax.PREFIX_DATE_START).get());
            Integer.parseInt(argMultimap.getValue(CliSyntax.PREFIX_DATE_END).get());
        } catch (NumberFormatException nfe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_DATE_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    /**
     *  @author Cjunx
     *  Returns true if string given is TIMEZONE FORMATTED
     * {@code ArgumentMultimap}.
     */
    public static void checkTimeZoneformat(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(CliSyntax.PREFIX_TIMEZONE).get().length() != 2
                && argMultimap.getValue(CliSyntax.PREFIX_TIMEZONE).get().length() != 3) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_TIMEZONE_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        if (!argMultimap.getValue(CliSyntax.PREFIX_TIMEZONE).get().startsWith("+")
                && !argMultimap.getValue(CliSyntax.PREFIX_TIMEZONE).get().startsWith("-")) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_TIMEZONE_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    /**
     *  @author Cjunx
     * Returns true if string given is TIME FORMATTED
     * {@code ArgumentMultimap}.
     */
    public static void checkTimeformat(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(CliSyntax.PREFIX_TIME_START).get().length() != 4
                || argMultimap.getValue(CliSyntax.PREFIX_TIME_END).get().length() != 4
                || argMultimap.getValue(CliSyntax.PREFIX_SAFETY_STOP).get().length() != 4) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_TIME_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Integer.parseInt(argMultimap.getValue(CliSyntax.PREFIX_TIME_END).get());
            Integer.parseInt(argMultimap.getValue(CliSyntax.PREFIX_SAFETY_STOP).get());
            Integer.parseInt(argMultimap.getValue(CliSyntax.PREFIX_TIME_START).get());
        } catch (NumberFormatException nfe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_TIME_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }
}
