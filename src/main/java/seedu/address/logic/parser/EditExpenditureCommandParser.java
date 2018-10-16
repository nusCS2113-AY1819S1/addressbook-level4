package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;


import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditExpenditureCommand;
import seedu.address.logic.commands.EditExpenditureCommand.EditExpenditureDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditExpenditureCommand object
 */
public class EditExpenditureCommandParser implements  Parser<EditExpenditureCommand> {

    public EditExpenditureCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_MONEY, PREFIX_CATEGORY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format
                    (MESSAGE_INVALID_COMMAND_FORMAT, EditExpenditureCommand.MESSAGE_USAGE), pe);
        }

        EditExpenditureDescriptor editExpenditureDescriptor = new EditExpenditureDescriptor();
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editExpenditureDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_MONEY).isPresent()) {
            editExpenditureDescriptor.setMoney(ParserUtil.parseMoney(argMultimap.getValue(PREFIX_MONEY).get()));
        }
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            editExpenditureDescriptor.setCategory(ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get()));
        }

        if (!editExpenditureDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditExpenditureCommand.MESSAGE_NOT_EDITED);
        }

        return new EditExpenditureCommand(index, editExpenditureDescriptor);
    }

}


