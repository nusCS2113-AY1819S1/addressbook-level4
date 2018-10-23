package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.login.Password;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AuthenticationFailedException;
import seedu.address.model.login.exceptions.UserNotFoundException;

/**
 *  Changes registered user password
 */
public class ChangeUserPasswordCommand extends Command {

    public static final String COMMAND_WORD = "change-pw";

    public static final String MESSAGE_USAGE = "Changes the user password."
            + "with the parameters: u/USERNAME p/PASSWORD newp/NEWPASSWORD"
            + "\nEXAMPLE: change-pw u/inventarie p/123 newp/000";

    public static final String MESSAGE_SUCCESS = "Password updated for %1$s";
    public static final String MESSAGE_UPDATE_FAILURE = "Password update failed. Username or password is incorrect.";
    public static final String MESSAGE_NOT_LOGGED_OUT = "You are not logged out. "
            + "Please logout to execute this command.";

    private final Username username;
    private final Password password;
    private final Password newPassword;

    /**
     * Creates a ChangeUserPasswordCommand to modify the specified {@code User} password.
     */
    public ChangeUserPasswordCommand(Username username, Password password, Password newPassword) {
        requireNonNull(username);
        requireNonNull(password);
        requireNonNull(newPassword);
        this.username = username;
        this.password = password;
        this.newPassword = newPassword;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            if (model.checkCredentials(username, password)) {
                User target = new User(username, password);
                User userWithNewPassword = new User(username, newPassword);
                model.updateUserPassword(target, userWithNewPassword);
                return new CommandResult(String.format(MESSAGE_SUCCESS, username));
            } else {
                return new CommandResult(MESSAGE_UPDATE_FAILURE);
            }
        } catch (AuthenticationFailedException e) {
            throw new CommandException(MESSAGE_NOT_LOGGED_OUT);
        } catch (UserNotFoundException e) {
            throw new CommandException(MESSAGE_UPDATE_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ChangeUserPasswordCommand
                && this.username.equals(((ChangeUserPasswordCommand) other).username)
                && this.password.equals(((ChangeUserPasswordCommand) other).password))
                && this.newPassword.equals(((ChangeUserPasswordCommand) other).newPassword);
    }

}
