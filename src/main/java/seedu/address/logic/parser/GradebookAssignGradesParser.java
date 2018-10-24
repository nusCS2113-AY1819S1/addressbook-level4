package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_STUDENT_MARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ADMIN;

import java.util.stream.Stream;

import seedu.address.logic.commands.GradebookAssignGradesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.gradebook.Gradebook;
import seedu.address.model.gradebook.GradebookManager;

/**
 * Parses input arguments and creates a new GradebookAssignGradesCommand object
 */
public class GradebookAssignGradesParser implements Parser<GradebookAssignGradesCommand> {
    public static final String MESSAGE_MARKS_INVALID_TYPE = "Invalid input. \nMarks should only be an integer";
    public static final String MESSAGE_EMPTY_INPUTS = "All parameters cannot be empty";
    public static final String MESSAGE_MARKS_INVALID_RANGE = "Marks should be within 0-100 range";

    /**
     * Parses the given {@code String args} of arguments in the context of the GradebookAddCommand
     * and returns a GradebookAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GradebookAssignGradesCommand parse(String args) throws ParseException {
        int studentMarksArg = 0;
        GradebookManager gradebookManager = new GradebookManager();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_STUDENT_ADMIN,
                PREFIX_MODULE_CODE,
                PREFIX_GRADEBOOK_ITEM,
                PREFIX_GRADEBOOK_STUDENT_MARKS);
        if (!arePrefixesPresent(argMultimap,
                PREFIX_STUDENT_ADMIN,
                PREFIX_MODULE_CODE,
                PREFIX_GRADEBOOK_ITEM,
                PREFIX_GRADEBOOK_STUDENT_MARKS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT,
                    GradebookAssignGradesCommand.MESSAGE_USAGE));
        }
        if (arePrefixesPresent(argMultimap, PREFIX_GRADEBOOK_STUDENT_MARKS) || !argMultimap.getPreamble().isEmpty()) {
            try {
                studentMarksArg = Integer.parseInt(argMultimap.getValue(PREFIX_GRADEBOOK_STUDENT_MARKS).get());
            } catch (NumberFormatException nme) {
                throw new ParseException(MESSAGE_MARKS_INVALID_TYPE);
            }
        }
        String studentAdminNo = argMultimap.getValue(PREFIX_STUDENT_ADMIN).get();
        String moduleCodeArg = argMultimap.getValue(PREFIX_MODULE_CODE).get();
        String gradeComponentNameArg = argMultimap.getValue(PREFIX_GRADEBOOK_ITEM).get();
        boolean isEmpty = gradebookManager.isEmpty(
                studentAdminNo,
                moduleCodeArg,
                gradeComponentNameArg,
                studentMarksArg);
        if (isEmpty) {
            throw new ParseException(MESSAGE_EMPTY_INPUTS);
        }
        boolean isMaxMarksValid = gradebookManager.isMaxMarksValid(studentMarksArg);
        if (!isMaxMarksValid) {
            throw new ParseException(MESSAGE_MARKS_INVALID_RANGE);
        }

        Gradebook gradebook = new Gradebook(
                studentAdminNo,
                moduleCodeArg,
                gradeComponentNameArg,
                studentMarksArg);
        return new GradebookAssignGradesCommand(gradebook);
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
