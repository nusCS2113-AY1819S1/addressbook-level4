package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditLeaveCommand;
import seedu.address.logic.commands.EditLeaveCommand.EditLeaveDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author Hafizuddin-NUS
/**
 * Parses input arguments and creates a new EditLeaveCommand object
 */
public class ApproveLeaveCommandParser implements Parser<EditLeaveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditLeaveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditLeaveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditLeaveCommand.MESSAGE_USAGE), pe);
        }

        EditLeaveDescriptor editLeaveDescriptor = new EditLeaveDescriptor();

        editLeaveDescriptor.setApproval(ParserUtil.parseApproval("APPROVED"));


        return new EditLeaveCommand(index, editLeaveDescriptor);
    }
}
