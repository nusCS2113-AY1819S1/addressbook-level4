package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.nio.file.Path;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.events.security.GetAuthenticationEvent;
import seedu.address.commons.events.security.GetAuthenticationReplyEvent;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.security.SecurityAuthenticationException;

/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser extends ParserClass implements Parser<ExportCommand> {

    private boolean isAuthenticated;

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportCommand parse(String args) throws ParseException, SecurityAuthenticationException {
        raise(new GetAuthenticationEvent());
        if (!isAuthenticated) {
            throw new SecurityAuthenticationException("User is not authenticated");
        }
        if (args.trim().length() == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        } else {
            Path path = ParserUtil.parseImportExportFileName(args);
            return new ExportCommand(path);
        }
    }

    @Subscribe
    public void handleGetAuthenticationReplyEvent(GetAuthenticationReplyEvent e) {
        isAuthenticated = e.isAuthenticated();
    }
}
