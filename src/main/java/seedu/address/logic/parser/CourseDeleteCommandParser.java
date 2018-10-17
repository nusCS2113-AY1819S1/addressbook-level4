package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;

import java.util.stream.Stream;

import seedu.address.logic.commands.CourseDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new CourseDeleteCommand object
 */
public class CourseDeleteCommandParser implements Parser<CourseDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CourseDeleteCommand
     * and returns an CourseDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CourseDeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COURSE_CODE);

        if (!arePrefixesPresent(argMultimap, PREFIX_COURSE_CODE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CourseDeleteCommand.MESSAGE_USAGE));
        }

        String courseCode = argMultimap.getValue(PREFIX_COURSE_CODE).get();

        return new CourseDeleteCommand(courseCode);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
