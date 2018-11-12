package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_FACULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.CourseEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.course.CourseCode;



/**
 * Parses input arguments and creates a new CourseEditCommand object
 */
public class CourseEditCommandParser implements Parser<CourseEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CourseEditCommand
     * and returns an CourseEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CourseEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COURSE_CODE, PREFIX_COURSE_NAME, PREFIX_COURSE_FACULTY);

        if (!arePrefixesPresent(argMultimap, PREFIX_COURSE_CODE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CourseEditCommand.MESSAGE_USAGE));
        }

        CourseEditCommand.EditCourseDescriptor editCourseDescriptor = new CourseEditCommand.EditCourseDescriptor();

        if (argMultimap.getValue(PREFIX_COURSE_NAME).isPresent()) {
            editCourseDescriptor
                    .setCourseName(ParserUtil.parseCourseName(argMultimap.getValue(PREFIX_COURSE_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_COURSE_FACULTY).isPresent()) {
            editCourseDescriptor
                    .setFacultyName(ParserUtil.parseFacultyName(argMultimap.getValue(PREFIX_COURSE_FACULTY).get()));
        }

        if (!editCourseDescriptor.isAnyFieldEdited()) {
            throw new ParseException(CourseEditCommand.MESSAGE_NOT_EDITED);
        }

        return new CourseEditCommand(new CourseCode(argMultimap.getValue(PREFIX_COURSE_CODE)
                .get()), editCourseDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}
