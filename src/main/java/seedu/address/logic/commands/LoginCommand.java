package seedu.address.logic.commands;

import seedu.address.logic.commands.Command;
import seedu.address.model.login.LoginDetails;

/**
 * Queries the login book to see if there is a user ID and password that matches input
 * user ID and password. Used for the login process.
 * Keyword matching is case insensitive for user ID and case sensitive for user Password.
 */
public abstract class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": There must be a user/ prefix before entering user ID" +
            ".Ensure that input user ID is 9 alphanumerical characters long, with the first and last character being " +
            "lower or upper case alpha characters, and there are 7 numerical characters between the 2 alpha " +
            "characters. There must be a pass/ prefix before password. The input user password is case sensitive.";

    public LoginCommand(LoginDetails details) {}

    public LoginCommand() {}
}
