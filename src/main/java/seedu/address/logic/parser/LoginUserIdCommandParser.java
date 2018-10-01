package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERPASSWORD;

import java.util.stream.Stream;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LoginUserIdCommand;
import seedu.address.logic.commands.LoginUserPasswordCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.login.LoginDetails;
import seedu.address.model.login.UserId;
import seedu.address.model.login.UserIdContainsKeywordsPredicate;
import seedu.address.model.login.UserPassword;
import seedu.address.model.login.UserPasswordContainsKeywordsPredicate;
import seedu.address.ui.LoginWindow;

/**
 * Parses input arguments and creates a new LoginUserIdCommand object
 */
public class LoginUserIdCommandParser implements Parser<LoginCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateAccountCommand
     * and returns an CreateAccountCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }

        if (LoginWindow.isExistUserId) {
            return new LoginUserPasswordCommand(new UserPasswordContainsKeywordsPredicate());
        } else {
            return new LoginUserIdCommand(new UserIdContainsKeywordsPredicate());
        }
    }
}
