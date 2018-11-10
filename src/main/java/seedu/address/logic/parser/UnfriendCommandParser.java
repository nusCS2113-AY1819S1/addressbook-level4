package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.security.GetAuthenticationEvent;
import seedu.address.commons.events.security.SendsAuthenticationStateEvent;
import seedu.address.logic.commands.UnfriendCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.security.SecurityAuthenticationException;

/**
 * Parses input and creates a new UnfriendCommand
 */
public class UnfriendCommandParser extends ParserClass implements Parser<UnfriendCommand> {

    private boolean isAuthenticated;

    /**
     * Parses the given {@code String} of arguments in the context of the UnfriendCommand
     * and returns an UnfriendCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public UnfriendCommand parse(String args) throws ParseException, SecurityAuthenticationException {
        raise(new GetAuthenticationEvent());
        if (!isAuthenticated) {
            throw new SecurityAuthenticationException("User is not authenticated");
        }
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnfriendCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnfriendCommand.MESSAGE_USAGE), pe);
        }
    }

    @Subscribe
    public void handleAuthenticationStateEvent(SendsAuthenticationStateEvent e) {
        isAuthenticated = e.isAuthenticated();
    }
}
