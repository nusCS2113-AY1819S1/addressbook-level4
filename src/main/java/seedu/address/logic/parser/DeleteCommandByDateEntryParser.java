package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommandByDateEntry;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.record.Date;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class DeleteCommandByDateEntryParser implements Parser<DeleteCommandByDateEntry> {
    public DeleteCommandByDateEntry parse (String args) throws ParseException{
        try{
            Date date = ParserUtil.parseDate(args);
            return new DeleteCommandByDateEntry(date);
        }catch (ParseException pe){
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommandByDateEntry.MESSAGE_USAGE), pe);
        }
    }
}
