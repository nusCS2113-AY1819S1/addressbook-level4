package seedu.recruit.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.commands.EditCompanyCommand;
import seedu.recruit.logic.commands.EditCompanyCommand.EditCompanyDescriptor;
import seedu.recruit.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments and creates a new EditCompanyCommand object
 */
public class EditCompanyCommandParser implements Parser<EditCompanyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCompanyCommand
     * and returns an EditCompanyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCompanyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMPANY_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCompanyCommand.MESSAGE_USAGE),
                    pe);
        }

        EditCompanyDescriptor editCompanyDescriptor = new EditCompanyDescriptor();
        if (argMultimap.getValue(PREFIX_COMPANY_NAME).isPresent()) {
            editCompanyDescriptor.setName(ParserUtil.parseCompanyName(argMultimap.getValue(PREFIX_COMPANY_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editCompanyDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editCompanyDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editCompanyDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }

        if (!editCompanyDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCompanyCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCompanyCommand(index, editCompanyDescriptor);
    }

}
