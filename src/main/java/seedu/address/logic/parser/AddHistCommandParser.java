package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_COUNTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_DATE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddHistCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medhistory.Allergy;
import seedu.address.model.medhistory.MedHistDate;
import seedu.address.model.medhistory.MedHistory;
import seedu.address.model.medhistory.PrevCountry;


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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_HISTORY_DATE, PREFIX_HISTORY_ALLERGY,
                PREFIX_HISTORY_COUNTRY);

        if (!arePrefixesPresent(argMultimap, PREFIX_HISTORY_DATE, PREFIX_HISTORY_ALLERGY,
                PREFIX_HISTORY_COUNTRY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHistCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHistCommand.MESSAGE_USAGE), ive);
        }

        Allergy allergy = ParserUtil.parseAllergy(argMultimap.getValue(PREFIX_HISTORY_ALLERGY).get());
        PrevCountry prevCountry = ParserUtil.parsePrevCountry(argMultimap.getValue(PREFIX_HISTORY_COUNTRY).get());
        MedHistDate medHistDate = ParserUtil.parseMedHistDate(argMultimap.getValue(PREFIX_HISTORY_DATE).get());
        return new AddHistCommand(index, new MedHistory (medHistDate, allergy, prevCountry));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
