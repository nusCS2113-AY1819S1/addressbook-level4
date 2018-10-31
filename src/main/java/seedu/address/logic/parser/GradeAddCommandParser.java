package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_MARKS;

import java.util.stream.Stream;

import seedu.address.logic.commands.GradeAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.gradebook.GradebookManager;
import seedu.address.model.grades.Grades;
import seedu.address.model.grades.GradesManager;
import seedu.address.model.module.ModuleManager;
import seedu.address.model.person.MatricNo;

/**
 * Parses input arguments and creates a new GradeAddCommand object
 */
public class GradeAddCommandParser implements Parser<GradeAddCommand> {
    public static final String MESSAGE_MISSING_PARAMS = "All parameters must be filled";
    public static final String MESSAGE_MARKS_ERROR = "Invalid input. \nMaximum marks should only be an integer";
    public static final String MESSAGE_GRADEBOOK_INVALID = "Gradebook component does not exist";
    public static final String MESSAGE_MODULE_CODE_INVALID = "Module code does not exist";
    private static final String MESSAGE_MARKS_INVALID = "Marks should be within 0-100 range";
    private static final String MESSAGE_DUPLICATE_STUDENT = "Student has already been assigned a grade";
    private static final String MESSAGE_INVALID_STUDENT_ENROL = "Student is not registered to module";
    private static final String MESSAGE_MATRIC_INVALID = "Matriculation numbers are required to start with A, followed"
            + " by a combination of 7 numbers and end with a checksum letter, and it should not be blank";

    /**
     * Parses the given {@code String args} of arguments in the context of the GradeAddCommand
     * and returns a GradeAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GradeAddCommand parse(String args) throws ParseException {
        GradesManager gradesManager = new GradesManager();
        GradebookManager gradebookManager = new GradebookManager();
        ModuleManager moduleManager = ModuleManager.getInstance();
        float studentMarksArg = 0;

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE, PREFIX_GRADEBOOK_ITEM,
                PREFIX_MATRIC, PREFIX_STUDENT_MARKS);

        if (!arePrefixesPresent(argMultimap,
                PREFIX_MODULE_CODE,
                PREFIX_GRADEBOOK_ITEM,
                PREFIX_MATRIC,
                PREFIX_STUDENT_MARKS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT,
                    GradeAddCommand.MESSAGE_USAGE));
        }

        String moduleCodeArg = argMultimap.getValue(PREFIX_MODULE_CODE).get();
        String studentAdminNoArg = argMultimap.getValue(PREFIX_MATRIC).get();

        String gradeComponentNameArg = argMultimap.getValue(PREFIX_GRADEBOOK_ITEM).get();
        boolean isEmpty = gradesManager.isEmpty(moduleCodeArg, gradeComponentNameArg, studentAdminNoArg);
        if (isEmpty) {
            throw new ParseException(MESSAGE_MISSING_PARAMS);
        }
        if (arePrefixesPresent(argMultimap, PREFIX_STUDENT_MARKS) || !argMultimap.getPreamble().isEmpty()) {
            try {
                studentMarksArg = Float.parseFloat(argMultimap.getValue(PREFIX_STUDENT_MARKS).get());
            } catch (NumberFormatException nme) {
                throw new ParseException(MESSAGE_MARKS_ERROR);
            }
        }
        boolean isMatricValid = MatricNo.isValidMatricNo(studentAdminNoArg);
        if (!isMatricValid) {
            throw new ParseException(MESSAGE_MATRIC_INVALID);
        }
        boolean doesModuleExist = moduleManager.doesModuleExist(moduleCodeArg);
        if (!doesModuleExist) {
            throw new ParseException(MESSAGE_MODULE_CODE_INVALID);
        }
        boolean isGradeComponentValid = gradebookManager.isGradeComponentValid(
                moduleCodeArg,
                gradeComponentNameArg);
        if (!isGradeComponentValid) {
            throw new ParseException(MESSAGE_GRADEBOOK_INVALID);
        }
        boolean isStudentEnrolledToModule = gradesManager.isStudentEnrolledToModule(
                moduleCodeArg,
                studentAdminNoArg);
        if (!isStudentEnrolledToModule) {
            throw new ParseException(MESSAGE_INVALID_STUDENT_ENROL);
        }
        boolean isDuplicate = gradesManager.isDuplicate(moduleCodeArg, gradeComponentNameArg, studentAdminNoArg);
        if (isDuplicate) {
            throw new ParseException(MESSAGE_DUPLICATE_STUDENT);
        }
        boolean isMarksValid = gradesManager.isMarksValid(studentMarksArg);
        if (!isMarksValid) {
            throw new ParseException(MESSAGE_MARKS_INVALID);
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
