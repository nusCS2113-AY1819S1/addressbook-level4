package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    private final Username username = new Username(" ");
    private final Password password = new Password(" ");
    private User currentUser = new User(username, password);
    public static final String MESSAGE_LOGIN = "Please login first!";

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model, CommandHistory history) throws CommandException;

    /**
     * Sets current logged in user.
     * @param user
     */
    public void setCurrentUser(User user) {
        currentUser = user;
        currentUser.setLoginStatus(true);
    }

    /**
     * Clears current logged in user.
     */
    public void clearCurrentUser() {
        currentUser.setLoginStatus(false);
    }

    /**
     * Checks whether or not a user is logged in.
     * @throws CommandException
     */
    public void authenticate() throws CommandException {
        if (!currentUser.getLoginStatus()) {
            throw new CommandException(MESSAGE_LOGIN);
        }
    }
}
