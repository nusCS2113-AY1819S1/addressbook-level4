package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_INDEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteGroupPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteGroupPersonCommand object
 */
public class DeleteGroupPersonCommandParser implements Parser<DeleteGroupPersonCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteGroupPersonCommand
     * and returns an DeleteGroupPersonCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteGroupPersonCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP_INDEX, PREFIX_PERSON_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_GROUP_INDEX, PREFIX_PERSON_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupPersonCommand.MESSAGE_USAGE));
        }

        Index groupIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_GROUP_INDEX).get());
        Index personIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PERSON_INDEX).get());
        return new DeleteGroupPersonCommand(groupIndex, personIndex);
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
