package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEST_MARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEST_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.AddTestMarksCommand;
import seedu.address.logic.commands.EditTestMarksCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
/**
 * Parses the given {@code String} of arguments in the context of the FindCommand
 * and returns an FindCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class EditTestMarksCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTestMarksCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTestMarksCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        List<String> nameKeywordsList =
                new ArrayList<String>(Arrays.asList(nameKeywords));

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TEST_NAME, PREFIX_TEST_MARK);

        nameKeywordsList.remove(PREFIX_TEST_NAME + argMultimap.getValue(PREFIX_TEST_NAME).get());
        nameKeywordsList.remove(PREFIX_TEST_MARK + argMultimap.getValue(PREFIX_TEST_MARK).get());

        return new EditTestMarksCommand(new NameContainsKeywordsPredicate(nameKeywordsList),
                argMultimap.getValue(PREFIX_TEST_NAME).get(), argMultimap.getValue(PREFIX_TEST_MARK).get());
    }
}
