package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.security.GetAuthenticationEvent;
import seedu.address.commons.events.security.GetAuthenticationReplyEvent;
import seedu.address.logic.commands.FriendCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.security.SecurityAuthenticationException;

/**
 * Parses arguments and creates a new FriendCommand
 */
public class FriendCommandParser extends ParserClass implements Parser<FriendCommand> {

    private boolean isAuthenticated;

    /**
     * Parses the given {@code String} of arguments in the context of the FriendCommand
     * and returns an FriendCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FriendCommand parse(String args) throws ParseException, SecurityAuthenticationException {
        raise(new GetAuthenticationEvent());
        if (!isAuthenticated) {
            throw new SecurityAuthenticationException("User is not authenticated");
        }
        try {
            Index index = ParserUtil.parseIndex(args);
            return new FriendCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FriendCommand.MESSAGE_USAGE), pe);
        }
    }

    @Subscribe
    public void handleGetAuthenticationReplyEvent(GetAuthenticationReplyEvent e) {
        isAuthenticated = e.isAuthenticated();
    }
}
