package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.events.security.GetAuthenticationEvent;
import seedu.address.commons.events.security.GetAuthenticationReplyEvent;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.security.SecurityAuthenticationException;


/**
 * Parses the arguments of tag command and returns as a Tag object
 */
public class LoginCommandParser extends ParserClass implements Parser<LoginCommand> {

    private boolean isAuthenticated;

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns an TagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String args) throws ParseException, SecurityAuthenticationException {
        raise(new GetAuthenticationEvent());
        if (isAuthenticated) {
            throw new SecurityAuthenticationException("Please logout if you would like login to another account");
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }

        String username = argMultimap.getValue(PREFIX_USERNAME).get();
        String password = argMultimap.getValue(PREFIX_PASSWORD).get();

        return new LoginCommand(username, password);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Subscribe
    public void handleGetAuthenticationReplyEvent(GetAuthenticationReplyEvent e) {
        isAuthenticated = e.isAuthenticated();
    }

}
