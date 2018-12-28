package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_COUNTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_DISCHARGE_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HISTORY_OLD_DATE;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditHistCommand;
import seedu.address.logic.commands.EditHistCommand.EditHistDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medhistory.MedHistDate;

//@@author xShaivan
/**
 * Parses input arguments and creates a new EditHistCommand object
 */
public class EditHistCommandParser implements Parser<EditHistCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditHistCommand
     * and returns an EditHistCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditHistCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_HISTORY_OLD_DATE, PREFIX_HISTORY_DATE, PREFIX_HISTORY_ALLERGY,
                        PREFIX_HISTORY_COUNTRY, PREFIX_HISTORY_DISCHARGE_STATUS);
        if (!arePrefixesPresent(argMultimap, PREFIX_HISTORY_OLD_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditHistCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditHistCommand.MESSAGE_USAGE), pe);
        }

        MedHistDate medHistDate = ParserUtil.parseMedHistDate(argMultimap.getValue(PREFIX_HISTORY_OLD_DATE).get());
        EditHistDescriptor editHistDescriptor = new EditHistDescriptor();

        if (argMultimap.getValue(PREFIX_HISTORY_DATE).isPresent()) {
            editHistDescriptor.setMedHistDate(
                    ParserUtil.parseMedHistDate(argMultimap.getValue(PREFIX_HISTORY_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_HISTORY_ALLERGY).isPresent()) {
            editHistDescriptor.setAllergy(ParserUtil.parseAllergy(argMultimap.getValue(PREFIX_HISTORY_ALLERGY).get()));
        }
        if (argMultimap.getValue(PREFIX_HISTORY_COUNTRY).isPresent()) {
            editHistDescriptor.setPrevCountry(
                    ParserUtil.parsePrevCountry(argMultimap.getValue(PREFIX_HISTORY_COUNTRY).get()));
        }
        if (argMultimap.getValue(PREFIX_HISTORY_DISCHARGE_STATUS).isPresent()) {
            editHistDescriptor.setDischargeStatus(
                    ParserUtil.parseDischargeStatus(argMultimap.getValue(PREFIX_HISTORY_DISCHARGE_STATUS).get()));
        }
        if (!editHistDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditHistCommand.MESSAGE_MEDHISTORY_NOT_EDITED);
        }
        return new EditHistCommand(index, medHistDate, editHistDescriptor);
    }
}
