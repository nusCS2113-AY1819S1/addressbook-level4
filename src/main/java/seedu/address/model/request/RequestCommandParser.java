package seedu.address.model.request;

import seedu.address.logic.parser.*;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Quantity;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.model.request.CliSyntax.PREFIX_EMAIL;
import static seedu.address.model.request.CliSyntax.PREFIX_QUANTITY;

/**
 * Parses input arguments and creates a new RequestCommand object
 */
public class RequestCommandParser implements RequestParser<RequestCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RequestCommand
     * and returns an RequestCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RequestCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_QUANTITY, PREFIX_EMAIL);

        if (!arePrefixesPresent(argMultimap, PREFIX_ID, PREFIX_QUANTITY, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RequestCommand.MESSAGE_USAGE));
        }

        Isbn isbn = RequestParserUtil.parseIsbn(argMultimap.getValue(PREFIX_ID).get());
        Quantity quantity = RequestParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());
        Email email = RequestParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());

        Request request = new Request(isbn, email, quantity);

        return new RequestCommand(request);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
