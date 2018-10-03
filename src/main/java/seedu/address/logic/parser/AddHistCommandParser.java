package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddHistCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.MedHistory;

/**
 * Parses input arguments and create a {@code AddHistCommand} object
 */

public class AddHistCommandParser implements Parser<AddHistCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddHistCommand}
     * and returns a {@code AddHistCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public AddHistCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_HISTORY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());

        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHistCommand.MESSAGE_USAGE), ive);
        }

        String medhistory = argMultimap.getValue(PREFIX_HISTORY).orElse("");
        return new AddHistCommand(index, new MedHistory (medhistory));
    }
}
