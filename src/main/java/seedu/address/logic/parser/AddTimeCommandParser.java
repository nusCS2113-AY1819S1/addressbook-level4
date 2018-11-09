package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.events.security.GetAuthenticationEvent;
import seedu.address.commons.events.security.GetAuthenticationReplyEvent;
import seedu.address.logic.commands.AddTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TimeSlot;
import seedu.address.security.SecurityAuthenticationException;

/**
 * Parses input arguments and creates a new AddTimeCommand object
 */
public class AddTimeCommandParser extends ParserClass implements Parser<AddTimeCommand> {

    private boolean isAuthenticated;

    /**
     * Parses the given {@code String} of arguments in the context of the AddTimeCommand
     * and returns an AddTimeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTimeCommand parse (String args) throws ParseException, SecurityAuthenticationException {
        raise(new GetAuthenticationEvent());
        if (!isAuthenticated) {
            throw new SecurityAuthenticationException("User is not authenticated");
        }
        try {
            TimeSlot timeSlot = ParserUtil.parseTimeSlot(args.trim());
            return new AddTimeCommand(timeSlot);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTimeCommand.MESSAGE_USAGE));
        }
    }

    @Subscribe
    public void handleGetAuthenticationReplyEvent(GetAuthenticationReplyEvent e) {
        isAuthenticated = e.isAuthenticated();
    }
}
