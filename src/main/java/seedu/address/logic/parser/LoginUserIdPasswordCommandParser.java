package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LoginUserIdPasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.login.UserIdContainsKeywordsPredicate;
import seedu.address.model.login.UserPasswordContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new LoginUserIdCommand object
 */
public class LoginUserIdPasswordCommandParser implements Parser<LoginCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LoginUserIdPasswordCommand
     * and returns an LoginUserIdPasswordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");
        List<String> keywordsList = new ArrayList<String>(Arrays.asList(keywords));
        return new LoginUserIdPasswordCommand(new UserIdContainsKeywordsPredicate(keywordsList),
                                              new UserPasswordContainsKeywordsPredicate(keywordsList));
    }
}
