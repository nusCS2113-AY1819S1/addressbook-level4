package seedu.address.testutil;

import static seedu.address.testutil.AccountBuilder.DEFAULT_ROLE;
import static seedu.address.testutil.AccountBuilder.DEFAULT_USERID;
import static seedu.address.testutil.AccountBuilder.DEFAULT_USERPASSWORD;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import seedu.address.logic.commands.CreateAccountCommand;
import seedu.address.logic.commands.LoginUserIdPasswordRoleCommand;
import seedu.address.model.login.LoginDetails;
import seedu.address.model.login.UserIdContainsKeywordsPredicate;
import seedu.address.model.login.UserPasswordContainsKeywordsPredicate;
import seedu.address.model.login.UserRoleContainsKeywordsPredicate;

/**
 * A utility class for LoginDetails.
 */
public class AccountUtil {

    /**
     * Returns a login command string for logging in using the {@code account} login details.
     */
    public static String getLoginCommand(LoginDetails loginDetails) {
        return LoginUserIdPasswordRoleCommand.COMMAND_WORD + " " + getAccountDetails(loginDetails);
    }

    /**
     * Returns a create account command string for adding the {@code account}.
     */
    public static String getCreateAccountCommand(LoginDetails loginDetails) {
        return CreateAccountCommand.COMMAND_WORD + " " + getAccountDetails(loginDetails);
    }

    /**
     * Returns the part of command string for the given {@code account}'s details.
     */
    public static String getAccountDetails(LoginDetails loginDetails) {
        StringBuilder sb = new StringBuilder();
        sb.append(loginDetails.getUserId().fullUserId + " ");
        sb.append(loginDetails.getUserPassword().fullUserPassword + " ");
        sb.append(loginDetails.getUserRole().fullUserRole + " ");
        return sb.toString();
    }

    /**
     * @return a LoginUserIdPasswordRoleCommand object that contains default login credentials
     */
    public static LoginUserIdPasswordRoleCommand buildLogin() {
        String encryptedLoginId = null;
        String encryptedLoginPassword = null;
        String encryptedLoginRole = null;
        try {
            encryptedLoginId = Base64.getEncoder().encodeToString(DEFAULT_USERID.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            encryptedLoginPassword = Base64.getEncoder().encodeToString(DEFAULT_USERPASSWORD.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            encryptedLoginRole = Base64.getEncoder().encodeToString(DEFAULT_ROLE.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<String> keywordsList = new ArrayList<>(Arrays.asList(encryptedLoginId,
                encryptedLoginPassword, encryptedLoginRole));
        return new LoginUserIdPasswordRoleCommand(new UserIdContainsKeywordsPredicate(keywordsList),
                new UserPasswordContainsKeywordsPredicate(keywordsList),
                new UserRoleContainsKeywordsPredicate(keywordsList));
    }
}
