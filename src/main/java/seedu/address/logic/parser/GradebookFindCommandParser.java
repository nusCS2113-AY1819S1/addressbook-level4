package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADEBOOK_MODULE;

import java.util.stream.Stream;

import seedu.address.logic.commands.GradebookFindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.gradebook.GradebookComponent;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class GradebookFindCommandParser implements Parser<GradebookFindCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GradebookFindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GRADEBOOK_MODULE);

        if (!arePrefixesPresent(argMultimap, PREFIX_GRADEBOOK_MODULE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradebookFindCommand.MESSAGE_USAGE));
        }

        String moduleCodeArg = argMultimap.getValue(PREFIX_GRADEBOOK_MODULE).get();

        GradebookComponent gradebookComponent = new GradebookComponent(moduleCodeArg);
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
