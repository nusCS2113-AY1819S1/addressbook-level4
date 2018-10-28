package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ADMIN_NO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_MARKS;

import java.util.stream.Stream;

import seedu.address.logic.commands.GradeAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.gradebook.GradebookManager;
import seedu.address.model.grades.Grades;
import seedu.address.model.grades.GradesManager;

/**
 * Parses input arguments and creates a new GradeAddCommand object
 */
public class GradeAddCommandParser implements Parser<GradeAddCommand> {
    public static final String MESSAGE_MISSING_PARAMS = "All parameters must be filled";
    public static final String MESSAGE_MARKS_ERROR = "Invalid input. \nMaximum marks should only be an integer";
    public static final String MESSAGE_GRADEBOOK_INVALID = "Gradebook component does not exist";
    private static final String MESSAGE_MARKS_INVALID = "Marks should be within 0-100 range";
    private static final String MESSAGE_MARKS_EXCEED = "Marks assigned is above maximum marks.";
    private static final String MESSAGE_DUPLICATE_STUDENT = "Student has already been assigned a grade";
    /**
     * Parses the given {@code String args} of arguments in the context of the GradeAddCommand
     * and returns a GradeAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GradeAddCommand parse(String args) throws ParseException {
        GradesManager gradesManager = new GradesManager();
        GradebookManager gradebookManager = new GradebookManager();
        float studentMarksArg = 0;

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE, PREFIX_GRADEBOOK_ITEM,
                PREFIX_STUDENT_ADMIN_NO, PREFIX_STUDENT_MARKS);

        if (!arePrefixesPresent(argMultimap,
                PREFIX_MODULE_CODE,
                PREFIX_GRADEBOOK_ITEM,
                PREFIX_STUDENT_ADMIN_NO,
                PREFIX_STUDENT_MARKS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT,
                    GradeAddCommand.MESSAGE_USAGE));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_STUDENT_MARKS) || !argMultimap.getPreamble().isEmpty()) {
            try {
                studentMarksArg = Float.parseFloat(argMultimap.getValue(PREFIX_STUDENT_MARKS).get());
            } catch (NumberFormatException nme) {
                throw new ParseException(MESSAGE_MARKS_ERROR);
            }
        }

        String moduleCodeArg = argMultimap.getValue(PREFIX_MODULE_CODE).get();
        String studentAdminNoArg = argMultimap.getValue(PREFIX_STUDENT_ADMIN_NO).get();
        String gradeComponentNameArg = argMultimap.getValue(PREFIX_GRADEBOOK_ITEM).get();
        boolean isEmpty = gradesManager.isEmpty(moduleCodeArg, gradeComponentNameArg, studentAdminNoArg);
        if (isEmpty) {
            throw new ParseException(MESSAGE_MISSING_PARAMS);
        }
        boolean isGradeComponentValid = gradebookManager.isGradeComponentValid(moduleCodeArg, gradeComponentNameArg);
        if (!isGradeComponentValid) {
            throw new ParseException(MESSAGE_GRADEBOOK_INVALID);
        }
        boolean isDuplicate = gradesManager.isDuplicate(moduleCodeArg, gradeComponentNameArg, studentAdminNoArg);
        if (isDuplicate) {
            throw new ParseException(MESSAGE_DUPLICATE_STUDENT);
        }
        boolean isMarksValid = gradesManager.isMarksValid(studentMarksArg);
        if (!isMarksValid) {
            throw new ParseException(MESSAGE_MARKS_INVALID);
        }
        boolean hasMarksExceed = gradebookManager.hasMarksExceed(moduleCodeArg, gradeComponentNameArg, studentMarksArg);
        if (!hasMarksExceed) {
            throw new ParseException(MESSAGE_MARKS_EXCEED);
        }
        Grades grade = new Grades(
                moduleCodeArg,
                gradeComponentNameArg,
                studentAdminNoArg,
                studentMarksArg);
        return new GradeAddCommand(grade);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
