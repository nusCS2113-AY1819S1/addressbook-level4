package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteUserCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.login.Password;
import seedu.address.model.login.Username;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

public class DeleteUserCommandParser implements Parser<DeleteUserCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteUserCommand
     * and returns an DeleteUserCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteUserCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteUserCommand.MESSAGE_USAGE));
        }

        try {
            Username username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME).get());
            Password password = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get());

            return new DeleteUserCommand(username, password);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
