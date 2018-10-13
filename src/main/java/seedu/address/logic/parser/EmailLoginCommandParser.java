package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.util.stream.Stream;

import seedu.address.logic.commands.EmailLoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;


/**
 * Parses input arguments and creates a new EmailLoginCommand object
 */
public class EmailLoginCommandParser implements Parser<EmailLoginCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EmailLoginCommand
     * and returns an EmailLoginCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailLoginCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EMAIL, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_EMAIL, PREFIX_PASSWORD)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailLoginCommand.MESSAGE_USAGE));
        }

        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        String password = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get());

        return new EmailLoginCommand(email, password);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
