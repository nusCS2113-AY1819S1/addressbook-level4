package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Parameter;


/**
 * Parses input arguments and creates a new {@code SortCommand} object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code SortCommand}
     * and returns a {@code SortCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SORT);
        /*
        try {
        index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
        SortCommand.MESSAGE_USAGE), ive);
        }
        */
        Parameter parameter = new Parameter(argMultimap.getValue(PREFIX_SORT).orElse(""));
        return new SortCommand(parameter);
    }
}
