package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ADMIN_NO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_MARKS;

import java.util.stream.Stream;

import seedu.address.logic.commands.StudentGradeAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.grades.Grades;
import seedu.address.model.grades.GradesManager;

/**
 * Parses input arguments and creates a new StudentGradeAddCommand object
 */
public class StudentGradeAddCommandParser implements Parser<StudentGradeAddCommand> {
    public static final String MESSAGE_MARKS_ERROR = "Invalid input. \nMaximum marks should only be an integer";
    public static final String MESSAGE_EMPTY_INPUTS = "Module code and gradebook component name cannot be empty";
    /**
     * Parses the given {@code String args} of arguments in the context of the GradebookAddCommand
     * and returns a GradebookAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StudentGradeAddCommand parse(String args) throws ParseException {
        GradesManager gradesManager = new GradesManager();
        int studentMarks = 0;

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
                    StudentGradeAddCommand.MESSAGE_USAGE));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_STUDENT_MARKS) || !argMultimap.getPreamble().isEmpty()) {
            try {
                studentMarks = Integer.parseInt(argMultimap.getValue(PREFIX_STUDENT_MARKS).get());
            } catch (NumberFormatException nme) {
                throw new ParseException(MESSAGE_MARKS_ERROR);
            }
        }

        String moduleCodeArg = argMultimap.getValue(PREFIX_MODULE_CODE).get();
        String studentAdminNoArg = argMultimap.getValue(PREFIX_STUDENT_ADMIN_NO).get();
        String gradeComponentNameArg = argMultimap.getValue(PREFIX_GRADEBOOK_ITEM).get();
        boolean isEmpty = gradesManager.isEmpty(moduleCodeArg, gradeComponentNameArg, studentAdminNoArg);
        if (isEmpty) {
            throw new ParseException(MESSAGE_EMPTY_INPUTS);
        }
        Grades grade = new Grades(
                moduleCodeArg,
                gradeComponentNameArg,
                studentAdminNoArg,
                studentMarks);
        return new StudentGradeAddCommand(grade);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
