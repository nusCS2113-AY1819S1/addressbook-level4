package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.planner.logic.commands.DeleteByDateCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.record.Date;

//@author nguyenngoclinhchi
/**
 * Parses input arguments and creates a new DeleteByDateCommand object
 */
public class DeleteCommandByDateEntryParser implements Parser<DeleteByDateCommand> {
    /**
     * Parses the date and create a new command DeleteByDateCommand
     * @param args
     * @return new command DeleteByDateCommand
     * @throws ParseException if the Date is invalid
     */
    public DeleteByDateCommand parse (String args) throws ParseException {
        try {
            Date date = ParserUtil.parseDate(args);
            return new DeleteByDateCommand(date);
        } catch (ParseException pe) {
            throw new ParseException (
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByDateCommand.MESSAGE_USAGE), pe);
        }
    }
}
