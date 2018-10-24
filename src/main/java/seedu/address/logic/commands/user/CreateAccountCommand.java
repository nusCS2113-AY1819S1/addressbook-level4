package seedu.address.logic.commands.user;

import static java.util.Objects.requireNonNull;
import static seedu.address.authentication.AuthenticalLevel.AUTH_ACCOUNTANT;
import static seedu.address.authentication.AuthenticalLevel.AUTH_ADMIN;
import static seedu.address.authentication.AuthenticalLevel.AUTH_MANAGER;
import static seedu.address.authentication.AuthenticalLevel.AUTH_STOCK_TAKER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHENTICATION_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.authentication.PasswordUtils;
import seedu.address.commons.login.authenication.AuthenticationLevel;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.LoginInfoManager;
import seedu.address.model.Model;
/**
 * Adds a person to the address book.
 */
public class CreateAccountCommand extends UserCommand {

    public static final String COMMAND_WORD = "createAccount";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": change current user password "
            + "Parameters: "
            + PREFIX_USERNAME + "username: "
            + PREFIX_PASSWORD + "Password: "
            + PREFIX_AUTHENTICATION_LEVEL + "authentication level: ";

    public static final String MESSAGE_SUCCESS = "New account has been created";
    public static final String MESSAGE_WRONG_AUTHENTICATION_LEVEL = "Wrong authentication level : %1$s";
    public static final String MESSAGE_AUTHENTICATION_LEVEL_FORMAT = " either " + AUTH_ADMIN
            + " or " + AUTH_MANAGER
            + " or " + AUTH_STOCK_TAKER
            + " or " + AUTH_ACCOUNTANT;
    private final String userName;
    private final String password;
    private final String authenticationLevel;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public CreateAccountCommand (String userName, String password, String authenticationLevel) {
        requireNonNull(userName);
        requireNonNull (password);
        requireNonNull (authenticationLevel);
        String hashedPassword = PasswordUtils.generateSecurePassword (password);

        this.userName = userName;
        this.password = hashedPassword;
        this.authenticationLevel = authenticationLevel.toUpperCase ();
    }
    @Override
    public CommandResult execute(LoginInfoManager loginInfoManager, CommandHistory history) {
        requireNonNull(loginInfoManager);
        if (!isAuthenticationLevelValid(authenticationLevel)) {
            return new CommandResult (String.format (MESSAGE_WRONG_AUTHENTICATION_LEVEL,
                    MESSAGE_AUTHENTICATION_LEVEL_FORMAT));
        }
        loginInfoManager.createNewAccount (userName, password, authenticationLevel);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public CommandResult execute (Model model , CommandHistory history) {
        return null;
    }

    /**
     * Returns true if authentication level is enum.
     *
     * @param authenticationLevel User input when create account
     * @return
     */
    private static boolean isAuthenticationLevelValid(String authenticationLevel) {
        if (authenticationLevel.equals (AUTH_ADMIN)
                || authenticationLevel.equals (AUTH_MANAGER)
                || authenticationLevel.equals (AUTH_STOCK_TAKER)
                || authenticationLevel.equals (AUTH_ACCOUNTANT)) {
            return true;
        }
        return false;
    }

}
