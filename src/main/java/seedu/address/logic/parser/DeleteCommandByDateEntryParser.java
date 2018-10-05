package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteCommandByDateEntry;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.record.Date;


/**
 * Parses input arguments and creates a new DeleteCommandByDateEntry object
 */
public class DeleteCommandByDateEntryParser implements Parser<DeleteCommandByDateEntry> {
    /**
     * Parses the date and create a new command DeleteCommandByDateEntry
     * @param args
     * @return new command DeleteCommandByDateEntry
     * @throws ParseException if the Date is invalid
     */
    public DeleteCommandByDateEntry parse (String args) throws ParseException {
        try {
            Date date = ParserUtil.parseDate(args);
            return new DeleteCommandByDateEntry(date);
        } catch (ParseException pe) {
            throw new ParseException (
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommandByDateEntry.MESSAGE_USAGE), pe);
        }
    }
}
