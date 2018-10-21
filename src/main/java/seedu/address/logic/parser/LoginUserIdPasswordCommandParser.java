package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.LoginManager;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LoginUserIdPasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.exceptions.UserLoginException;
import seedu.address.model.login.UserIdContainsKeywordsPredicate;
import seedu.address.model.login.UserPasswordContainsKeywordsPredicate;
import seedu.address.model.login.UserRoleContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new LoginUserIdCommand object
 */
public class LoginUserIdPasswordCommandParser implements Parser<LoginCommand> {

    private UserLoginException userLoginException = new UserLoginException();

    /**
     * Parses the given {@code String} of arguments in the context of the LoginUserIdPasswordCommand
     * and returns an LoginUserIdPasswordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            userLoginException.showInvalidLoginError();
            userLoginException.showLoginUsage();
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");
        List<String> keywordsList = new ArrayList<>(Arrays.asList(keywords));
        return setRoleReturnLoginCommandObject(keywords, keywordsList);
    }

    /**
     * Sets the conditions for certain roles and returns a LoginUserIdPasswordCommand object.
     * @param keywords string array representation of user login input
     * @param keywordsList array list representation of user login input
     * @return a LoginUserIdPasswordCommand object
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginUserIdPasswordCommand setRoleReturnLoginCommandObject(String[] keywords,
                                                                      List<String> keywordsList) throws ParseException {
        if (keywords.length < 3) {
            userLoginException.showMissingLoginError();
            userLoginException.showLoginUsage();
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        } else if (keywords.length > 3) {
            userLoginException.showInvalidLoginError();
            userLoginException.showLoginUsage();
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        } else {
            switch(keywords[2]) {
            case "member":
                LoginManager.setIsMember(true);
                break;
            case "president":
                LoginManager.setIsPresident(true);
                break;
            case "treasurer":
                LoginManager.setIsTreasurer(true);
                break;
            default:
                break;
            }
        }
        return new LoginUserIdPasswordCommand(new UserIdContainsKeywordsPredicate(keywordsList),
                new UserPasswordContainsKeywordsPredicate(keywordsList),
                new UserRoleContainsKeywordsPredicate(keywordsList));
    }
}
