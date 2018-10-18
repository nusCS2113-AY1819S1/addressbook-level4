package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_USERID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERPASSWORD;

import seedu.address.logic.commands.CreateAccountCommand;
import seedu.address.model.login.LoginDetails;

/**
 * A utility class for LoginDetails.
 */
public class AccountUtil {

    /**
     * Returns an add command string for adding the {@code account}.
     */
    public static String getCreateAccountCommand(LoginDetails loginDetails) {
        return CreateAccountCommand.COMMAND_WORD + " " + getAccountDetails(loginDetails);
    }

    /**
     * Returns the part of command string for the given {@code account}'s details.
     */
    public static String getAccountDetails(LoginDetails loginDetails) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_USERID + loginDetails.getUserId().fullUserId + " ");
        sb.append(PREFIX_USERPASSWORD + loginDetails.getUserPassword().fullUserPassword + " ");
        return sb.toString();
    }

}
