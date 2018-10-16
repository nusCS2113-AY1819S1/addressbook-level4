package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_MAXMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_WEIGHTAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;

import java.util.stream.Stream;

import seedu.address.logic.commands.GradebookAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.gradebook.XmlAdaptedGradebook;

/**
 * Parses input arguments and creates a new GradebookAddCommand object
 */
public class GradebookAddCommandParser implements Parser<GradebookAddCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the GradebookAddCommand
     * and returns a GradebookAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GradebookAddCommand parse(String args) throws ParseException {
        int gradeComponentMaxMarksArg = 0;
        int gradeComponentWeightageArg = 0;

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULECODE, PREFIX_GRADEBOOK_ITEM,
                PREFIX_GRADEBOOK_MAXMARKS, PREFIX_GRADEBOOK_WEIGHTAGE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULECODE, PREFIX_GRADEBOOK_ITEM)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradebookAddCommand.MESSAGE_USAGE));
        }
        if (arePrefixesPresent(argMultimap, PREFIX_GRADEBOOK_MAXMARKS) || !argMultimap.getPreamble().isEmpty()) {
            try {
                gradeComponentMaxMarksArg = Integer.parseInt(argMultimap.getValue(PREFIX_GRADEBOOK_MAXMARKS).get());
            } catch (NumberFormatException nme) {
                gradeComponentMaxMarksArg = 0;
            }
        }
        if (arePrefixesPresent(argMultimap, PREFIX_GRADEBOOK_WEIGHTAGE) || !argMultimap.getPreamble().isEmpty()) {
            try {
                gradeComponentWeightageArg = Integer.parseInt(argMultimap.getValue(PREFIX_GRADEBOOK_WEIGHTAGE).get());
            } catch (NumberFormatException nme) {
                gradeComponentWeightageArg = 0;
            }
        }
        String moduleCodeArg = argMultimap.getValue(PREFIX_MODULECODE).get();
        String gradeComponentNameArg = argMultimap.getValue(PREFIX_GRADEBOOK_ITEM).get();
        XmlAdaptedGradebook gradebookComponent = new XmlAdaptedGradebook(moduleCodeArg, gradeComponentNameArg,
                gradeComponentMaxMarksArg, gradeComponentWeightageArg);
        return new GradebookAddCommand(gradebookComponent);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
