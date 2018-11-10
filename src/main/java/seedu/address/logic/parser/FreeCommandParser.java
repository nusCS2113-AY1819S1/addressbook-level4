package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.security.GetAuthenticationEvent;
import seedu.address.commons.events.security.SendsAuthenticationStateEvent;
import seedu.address.logic.commands.FreeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.security.SecurityAuthenticationException;

/**
 * Parses input arguments and creates a new {@code FreeCommand} object
 */
public class FreeCommandParser extends ParserClass implements Parser<FreeCommand> {

    private boolean isAuthenticated;

    /**
     * Parses the given {@code String} of arguments in the context of the FreeCommand
     * and returns an {@code FreeCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FreeCommand parse(String args) throws ParseException, SecurityAuthenticationException {
        raise(new GetAuthenticationEvent());
        if (!isAuthenticated) {
            throw new SecurityAuthenticationException("User is not authenticated");
        }

        try {
            Collection <Index> indices = new ArrayList<>();

            for (String string : Arrays.asList(args.trim().split("\\s+"))) {
                indices.add(ParserUtil.parseIndex(string));
            }

            return new FreeCommand(indices);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE), pe);
        }
    }

    @Subscribe
    public void handleAuthenticationStateEvent(SendsAuthenticationStateEvent e) {
        isAuthenticated = e.isAuthenticated();
    }
}
