package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;

import java.util.stream.Stream;

import seedu.address.logic.commands.GradebookFindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.adapter.XmlAdaptedGradebook;

/**
 * Parses input arguments and creates a new GradebookFindCommand object
 */
public class GradebookFindCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the GradebookFindCommand
     * and returns a GradebookFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GradebookFindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULECODE, PREFIX_GRADEBOOK_ITEM);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULECODE, PREFIX_GRADEBOOK_ITEM)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GradebookFindCommand.MESSAGE_USAGE));
        }

        String moduleCodeArg = argMultimap.getValue(PREFIX_MODULECODE).get();
        String gradeItemNameArg = argMultimap.getValue(PREFIX_GRADEBOOK_ITEM).get();
        XmlAdaptedGradebook gradebookComponent = new XmlAdaptedGradebook(moduleCodeArg, gradeItemNameArg);
        return new GradebookFindCommand(gradebookComponent);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
