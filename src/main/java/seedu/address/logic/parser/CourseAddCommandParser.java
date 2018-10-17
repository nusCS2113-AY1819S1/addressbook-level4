package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSENAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACULTY;

import java.util.stream.Stream;

import seedu.address.logic.commands.CourseAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.course.Course;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class CourseAddCommandParser implements Parser<CourseAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CourseAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COURSECODE, PREFIX_FACULTY, PREFIX_COURSENAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_COURSECODE, PREFIX_FACULTY, PREFIX_COURSENAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CourseAddCommand.MESSAGE_USAGE));
        }

        String courseCode = argMultimap.getValue(PREFIX_COURSECODE).get();
        String courseName = argMultimap.getValue(PREFIX_COURSENAME).get();
        String originFaculty = argMultimap.getValue(PREFIX_FACULTY).get();



        Course course = new Course(courseCode, courseName, originFaculty);
        return new CourseAddCommand(course);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
