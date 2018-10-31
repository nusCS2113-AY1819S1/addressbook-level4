package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORIGINAL_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.stream.Stream;

import seedu.address.logic.commands.ChangeStatusCommand;
import seedu.address.logic.commands.ChangeStatusCommand.ChangeStatusDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author ChewKinWhye
/**
 * Parses input arguments and creates a new ChangeStatusCommand object
 */
public class ChangeStatusCommandParser implements Parser<ChangeStatusCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ChangeStatusCommand
     * and returns an ChangeStatusCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public ChangeStatusCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME,
                        PREFIX_QUANTITY, PREFIX_ORIGINAL_STATUS, PREFIX_NEW_STATUS);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_QUANTITY, PREFIX_ORIGINAL_STATUS, PREFIX_NEW_STATUS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeStatusCommand.MESSAGE_USAGE));
        }
        String initialStatus = ParserUtil
                .parseStatus(argMultimap.getValue(PREFIX_ORIGINAL_STATUS).get());
        String updatedStatus = ParserUtil
                .parseStatus(argMultimap.getValue(PREFIX_NEW_STATUS).get());
        if (initialStatus.equals("On_Loan") || updatedStatus.equals("On_Loan")) {
            throw new ParseException(String.format(ChangeStatusCommand.MESSAGE_INVALID_STATUS_FIELD));
        }
        ChangeStatusDescriptor changeStatusDescriptor = new ChangeStatusDescriptor();
        changeStatusDescriptor.setName(ParserUtil
                .parseName(argMultimap.getValue(PREFIX_NAME).get()));
        changeStatusDescriptor.setQuantity(ParserUtil
                .parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get()).toInteger());
        changeStatusDescriptor.setInitialStatus(initialStatus);
        changeStatusDescriptor.setUpdatedStatus(updatedStatus);

        return new ChangeStatusCommand(changeStatusDescriptor);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
