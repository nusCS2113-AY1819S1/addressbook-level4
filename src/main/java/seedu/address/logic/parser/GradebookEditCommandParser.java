package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM_EDIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_MAXMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_WEIGHTAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.stream.Stream;

import seedu.address.logic.commands.GradebookEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.gradebook.Gradebook;
import seedu.address.model.gradebook.GradebookManager;

/**
 * Parses input arguments and creates a new GradebookEditCommand object
 */
public class GradebookEditCommandParser {
    private static final String MESSAGE_MAX_MARKS_ERROR = "Invalid input. \nMaximum marks should only be an integer";
    private static final String MESSAGE_WEIGHTAGE_ERROR = "Invalid input. \nWeightage should only be an integer";
    private static final String MESSAGE_MAX_MARKS_INVALID = "Max marks should be within 0-100 range";
    private static final String MESSAGE_WEIGHTAGE_INVALID = "Weightage should be within 0-100 range";
    private static final String MESSAGE_ERROR_EMPTY = "Module code and gradebook component name cannot be empty";
    private static final String MESSAGE_WEIGHTAGE_EXCEED = "The accumulated weightage for module stated has exceeded!";

    /**
     * Parses the given {@code String args} of arguments in the context of the GradebookFindCommand
     * and returns a GradebookFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GradebookEditCommand parse(String args) throws ParseException {
        int gradeComponentMaxMarksArg = 0;
        int gradeComponentWeightageArg = 0;
        String newGradeComponentNameArg = "";
        GradebookManager gradebookManager = new GradebookManager();

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE, PREFIX_GRADEBOOK_ITEM,
                PREFIX_GRADEBOOK_ITEM_EDIT, PREFIX_GRADEBOOK_MAXMARKS, PREFIX_GRADEBOOK_WEIGHTAGE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE, PREFIX_GRADEBOOK_ITEM)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GradebookEditCommand.MESSAGE_USAGE));
        }
        if (arePrefixesPresent(argMultimap, PREFIX_GRADEBOOK_MAXMARKS) || !argMultimap.getPreamble().isEmpty()) {
            try {
                gradeComponentMaxMarksArg = Integer.parseInt(argMultimap.getValue(PREFIX_GRADEBOOK_MAXMARKS).get());
            } catch (NumberFormatException nme) {
                throw new ParseException(MESSAGE_MAX_MARKS_ERROR);
            }
        }
        if (arePrefixesPresent(argMultimap, PREFIX_GRADEBOOK_WEIGHTAGE) || !argMultimap.getPreamble().isEmpty()) {
            try {
                gradeComponentWeightageArg = Integer.parseInt(argMultimap.getValue(PREFIX_GRADEBOOK_WEIGHTAGE).get());
            } catch (NumberFormatException nme) {
                throw new ParseException(MESSAGE_WEIGHTAGE_ERROR);
            }
        }
        if (arePrefixesPresent(argMultimap, PREFIX_GRADEBOOK_ITEM_EDIT) || !argMultimap.getPreamble().isEmpty()) {
            newGradeComponentNameArg = argMultimap.getValue(PREFIX_GRADEBOOK_ITEM_EDIT).get();
        }

        String moduleCodeArg = argMultimap.getValue(PREFIX_MODULE_CODE).get();
        String gradeComponentNameArg = argMultimap.getValue(PREFIX_GRADEBOOK_ITEM).get();
        boolean isEmpty = gradebookManager.isEmpty(moduleCodeArg, gradeComponentNameArg);
        if (isEmpty) {
            throw new ParseException(MESSAGE_ERROR_EMPTY);
        }
        boolean isMaxMarksValid = gradebookManager.isMaxMarksValid(gradeComponentMaxMarksArg);
        if (!isMaxMarksValid) {
            throw new ParseException(MESSAGE_MAX_MARKS_INVALID);
        }
        boolean isWeightageValid = gradebookManager.isWeightageValid(gradeComponentWeightageArg);
        if (!isWeightageValid) {
            throw new ParseException(MESSAGE_WEIGHTAGE_INVALID);
        }
        boolean hasWeightageExceed = gradebookManager.hasWeightageExceed(moduleCodeArg, gradeComponentWeightageArg);
        if (hasWeightageExceed) {
            throw new ParseException(MESSAGE_WEIGHTAGE_EXCEED);
        }

        Gradebook gradebook = new Gradebook(
                moduleCodeArg,
                gradeComponentNameArg,
                newGradeComponentNameArg,
                gradeComponentMaxMarksArg,
                gradeComponentWeightageArg);
        return new GradebookEditCommand(gradebook);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
