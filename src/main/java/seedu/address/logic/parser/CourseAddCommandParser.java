package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_FACULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.CourseAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.course.Course;



/**
 * Parses input arguments and creates a new CourseAddCommand object
 */
public class CourseAddCommandParser implements Parser<CourseAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CourseAddCommand
     * and returns an CourseAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CourseAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COURSE_CODE, PREFIX_COURSE_FACULTY, PREFIX_COURSE_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_COURSE_CODE, PREFIX_COURSE_FACULTY, PREFIX_COURSE_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CourseAddCommand.MESSAGE_USAGE));
        }

        String courseCode = argMultimap.getValue(PREFIX_COURSE_CODE).get();
        String courseName = argMultimap.getValue(PREFIX_COURSE_NAME).get();
        String originFaculty = argMultimap.getValue(PREFIX_COURSE_FACULTY).get();



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
