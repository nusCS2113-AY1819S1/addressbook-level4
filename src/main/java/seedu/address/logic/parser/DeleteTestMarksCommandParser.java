package seedu.address.logic.parser;

import seedu.address.logic.commands.DeleteTestMarksCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEST_NAME;

/**
 * Parses the given {@code String} of arguments in the context of the DeleteTestMarksCommand
 * and returns an DeleteTestMarksCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class DeleteTestMarksCommandParser implements Parser<DeleteTestMarksCommand>{
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTestMarksCommand
     * and returns an DeleteTestMarksCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTestMarksCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTestMarksCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TEST_NAME);
        if (!argMultimap.getValue(PREFIX_TEST_NAME).isPresent()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTestMarksCommand.MESSAGE_USAGE));
        }

        return new DeleteTestMarksCommand(argMultimap.getValue(PREFIX_TEST_NAME).get());
    }
}