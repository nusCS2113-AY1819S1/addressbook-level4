//@@author lekoook
package seedu.address.logic.parser;

import seedu.address.logic.commands.MailCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to be use by MailCommand.
 */
public class MailCommandParser implements Parser<MailCommand> {
    public MailCommand parse(String args) throws ParseException {
        return new MailCommand();
    }
}