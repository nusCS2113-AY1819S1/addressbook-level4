package seedu.address.logic.commands;

/**
 * Queries the login book to see if there is a user ID and password that matches input
 * user ID and password. Used for the login process.
 * Keyword matching is case insensitive for user ID and case sensitive for user Password.
 */
public abstract class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Login into addressbook with input user ID and password."
            + "There must be a user/ prefix before user ID and pass/ prefix before user password.\n"
            + "Parameters: user/USERID pass/PASSWORD\n"
            + "Example: " + COMMAND_WORD + " user/A3583758X pass/passphrase";
}
