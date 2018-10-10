package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.login.Password;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AuthenticatedException;


public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = "Login with the parameters: u/USERNAME p/PASSWORD"
            + "\nEXAMPLE: login u/user p/123456"
            + "\nDefault user login credentials are as follows: USERNAME: user , PASSWORD: pass";

    public static final String MESSAGE_AUTHENTICATION_SUCCESS = "Login successful!";
    public static final String MESSAGE_AUTHENTICATION_FAILURE = "Username or password is incorrect. Please try again.";
    public static final String MESSAGE_AUTHENTICATED = "You are logged in.";

    private final Username username;
    private final Password password;

    public LoginCommand(Username username, Password password) {
        requireNonNull(username);
        requireNonNull(password);

        this.username = username;
        this.password = password;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(username);
        requireNonNull(password);
        requireNonNull(model);

        try {
            if (model.checkLoginCredentials(this.username, this.password)) {
                return new CommandResult(MESSAGE_AUTHENTICATION_SUCCESS);
            } else {
                return new CommandResult(MESSAGE_AUTHENTICATION_FAILURE);
            }
        } catch (AuthenticatedException e) {
            throw new CommandException(MESSAGE_AUTHENTICATED);
        }
    }


}
