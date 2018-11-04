package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditAccountCommand;
import seedu.address.logic.commands.EditAccountCommand.EditAccountDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditAccountCommandParser implements Parser<EditAccountCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditAccountCommand
     * and returns an EditAccountCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditAccountCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAccountCommand.MESSAGE_USAGE),
                    pe);
        }

        EditAccountDescriptor editAccountDescriptor = new EditAccountDescriptor();
        if (argMultimap.getValue(PREFIX_USERNAME).isPresent()) {
            editAccountDescriptor.setUsername(ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PASSWORD).isPresent()) {
            editAccountDescriptor.setPassword(ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get()));
        }

        if (!editAccountDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditAccountCommand.MESSAGE_NOT_EDITED);
        }

        return new EditAccountCommand(index, editAccountDescriptor);
    }

}
