package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.login.UserRole.MESSAGE_USERROLE_CONSTRAINTS;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.LoginManager;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LoginUserIdPasswordRoleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.login.UserIdContainsKeywordsPredicate;
import seedu.address.model.login.UserPasswordContainsKeywordsPredicate;
import seedu.address.model.login.UserRoleContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new LoginUserIdCommand object
 */
public class LoginUserIdPasswordRoleCommandParser implements Parser<LoginCommand> {

    private static final Logger logger = LogsCenter.getLogger(LoginUserIdPasswordRoleCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the LoginUserIdPasswordRoleCommand
     * and returns an LoginUserIdPasswordRoleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            logger.log(Level.WARNING, "Login input is empty!");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");
        if (keywords.length < 3) {
            logger.log(Level.WARNING, "One of user id/password/role field is missing");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        } else if (keywords.length > 3) {
            logger.log(Level.WARNING, "Additional login input parameters found!");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }
        String encryptedLoginId = null;
        String encryptedLoginPassword = null;
        String encryptedLoginRole = null;
        try {
            encryptedLoginId = Base64.getEncoder().encodeToString(keywords[0].getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.WARNING, "Login input user id characters not supported for encoding!");
            e.printStackTrace();
        }
        try {
            encryptedLoginPassword = Base64.getEncoder().encodeToString(keywords[1].getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.WARNING, "Login input user password characters not supported for encoding!");
            e.printStackTrace();
        }
        try {
            encryptedLoginRole = Base64.getEncoder().encodeToString(keywords[2].getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.WARNING, "Login input user role characters not supported for encoding!");
            e.printStackTrace();
        }
        assert encryptedLoginId != null : "encryptedLoginId should not be null";
        assert encryptedLoginPassword != null : "encryptedLoginPassword should not be null";
        assert encryptedLoginRole != null : "encryptedLoginRole should not be null";
        List<String> keywordsList = new ArrayList<>(Arrays.asList(encryptedLoginId,
                encryptedLoginPassword, encryptedLoginRole));
        return setRoleReturnLoginCommandObject(keywords, keywordsList);
    }

    /**
     * Sets the conditions for certain roles and returns a LoginUserIdPasswordRoleCommand object.
     * @param keywords string array representation of user login input
     * @param keywordsList array list representation of user login input
     * @return a LoginUserIdPasswordRoleCommand object
     */
    public LoginUserIdPasswordRoleCommand setRoleReturnLoginCommandObject(
            String[] keywords, List<String> keywordsList) throws ParseException {
        switch(keywords[2]) {
        case "member":
            LoginManager.setIsMember(true);
            LoginManager.setIsPresident(false);
            LoginManager.setIsTreasurer(false);
            break;
        case "president":
            LoginManager.setIsPresident(true);
            LoginManager.setIsMember(false);
            LoginManager.setIsTreasurer(false);
            break;
        case "treasurer":
            LoginManager.setIsTreasurer(true);
            LoginManager.setIsMember(false);
            LoginManager.setIsPresident(false);
            break;
        default:
            logger.log(Level.WARNING, "Invalid user role!");
            throw new ParseException(MESSAGE_USERROLE_CONSTRAINTS);
        }
        return new LoginUserIdPasswordRoleCommand(new UserIdContainsKeywordsPredicate(keywordsList),
                new UserPasswordContainsKeywordsPredicate(keywordsList),
                new UserRoleContainsKeywordsPredicate(keywordsList));
    }
}
