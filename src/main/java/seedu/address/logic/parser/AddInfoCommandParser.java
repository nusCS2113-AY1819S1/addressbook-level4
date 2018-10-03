package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INFO_NRIC;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddInfoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;

/**
 * Parses input arguments and creates a new {@code AddInfoCommand} object
 */
public class AddInfoCommandParser implements Parser<AddInfoCommand> {
    /**
     *
     * Parse the given {@code String} of arguments in the context of {@code AddInfoCommand}
     * and returns a {@code AddInfoCommand} object for execution.
     * @throws ParseException if the user input does not conform to expected format
     */
    public AddInfoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_ADD_INFO_NRIC);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultiMap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInfoCommand.MESSAGE_USAGE), ive);
        }

        String nric = argMultiMap.getValue(PREFIX_ADD_INFO_NRIC).orElse("");

        return new AddInfoCommand(index, new Nric(nric));
    }
}
