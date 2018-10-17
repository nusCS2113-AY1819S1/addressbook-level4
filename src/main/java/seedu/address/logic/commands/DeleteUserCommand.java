package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.login.Password;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AuthenticatedException;
import seedu.address.model.login.exceptions.UserNotFoundException;

/**
 * Deletes a user identified using it's username and password from the user database.
 */
public class DeleteUserCommand extends Command {

    public static final String COMMAND_WORD = "deregister";

    public static final String MESSAGE_USAGE = "Deregister the user with the parameters: u/USERNAME p/PASSWORD"
            + "\nEXAMPLE: deregister u/user p/123456"
            + "\nNote: You must be logged out before executing this command and username and password must be valid";

    public static final String MESSAGE_SUCCESS = "User successfully deleted: %1$s";
    public static final String MESSAGE_DELETE_FAILURE = "Delete failed. " + "Username or password is incorrect.";
    public static final String MESSAGE_NOT_LOGGED_OUT = "You are not logged out. Please logout to execute this command";

    private final Username username;
    private final Password password;

    /**
     * Creates a DeleteUserCommand to remove the specified {@code User}
     */
    public DeleteUserCommand(Username username, Password password) {
        requireNonNull(username);
        requireNonNull(password);
        this.username = username;
        this.password = password;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            if (model.checkCredentials(this.username, this.password) && !model.hasLoggedIn()) {
                User toDelete = new User(username, password);
                model.deleteUser(toDelete);
                return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete.getUsername().toString()));
            } else {
                return new CommandResult(MESSAGE_DELETE_FAILURE);
            }
        } catch (AuthenticatedException e) {
            throw new CommandException(MESSAGE_NOT_LOGGED_OUT);
        } catch (UserNotFoundException pnfe) {
            throw new CommandException(MESSAGE_DELETE_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteUserCommand
                && this.username.equals(((DeleteUserCommand) other).username)
                && this.password.equals(((DeleteUserCommand) other).password));
    }
}
