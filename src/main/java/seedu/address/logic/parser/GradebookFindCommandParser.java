package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.stream.Stream;

import seedu.address.logic.commands.GradebookFindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.gradebook.Gradebook;
import seedu.address.model.gradebook.GradebookManager;

/**
 * Parses input arguments and creates a new GradebookFindCommand object
 */
public class GradebookFindCommandParser {
    public static final String MESSAGE_EMPTY_INPUTS = "Module code and gradebook component name cannot be empty";
    public static final String MESSAGE_EMPTY_MODULE_CODE = "Module code cannot be empty";
    public static final String MESSAGE_EMPTY_COMPONENT_NAME = "Component name cannot be empty";
    /**
     * Parses the given {@code String args} of arguments in the context of the GradebookFindCommand
     * and returns a GradebookFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GradebookFindCommand parse(String args) throws ParseException {
        GradebookManager gradebookManager = new GradebookManager();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE, PREFIX_GRADEBOOK_ITEM);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE, PREFIX_GRADEBOOK_ITEM)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GradebookFindCommand.MESSAGE_USAGE));
        }

        String moduleCodeArg = argMultimap.getValue(PREFIX_MODULE_CODE).get();
        String gradeComponentNameArg = argMultimap.getValue(PREFIX_GRADEBOOK_ITEM).get();
        boolean isModuleCodeAndComponentNameEmpty = gradebookManager.isModuleCodeAndComponentNameEmpty(
                moduleCodeArg,
                gradeComponentNameArg);
        if (isModuleCodeAndComponentNameEmpty) {
            throw new ParseException(MESSAGE_EMPTY_INPUTS);
        }
        boolean isModuleCodeEmpty = gradebookManager.isModuleCodeEmpty(moduleCodeArg);
        if (isModuleCodeEmpty) {
            throw new ParseException(MESSAGE_EMPTY_MODULE_CODE);
        }
        boolean isComponentNameEmpty = gradebookManager.isComponentNameEmpty(gradeComponentNameArg);
        if (isComponentNameEmpty) {
            throw new ParseException(MESSAGE_EMPTY_COMPONENT_NAME);
        }

        Gradebook gradebook = new Gradebook(moduleCodeArg.toLowerCase(), gradeComponentNameArg.toLowerCase());
        return new GradebookFindCommand(gradebook);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
