package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_NAME;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.User;

/**
 * Deletes a specific user by admin
 */
public class DeleteUserCommand extends Command {
    public static final String COMMAND_WORD = "deleteUser";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a certain user. "
        + "Parameters: "
        + PREFIX_USER_NAME + "USER NAME "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_USER_NAME + "john ";

    public static final String MESSAGE_SUCCESS = "%1$s successfully deleted.";
    public static final String MESSAGE_INVALID_USER = "No user named %1$s found. Please try again!";

    private final String userNameToDelete;

    /**
     * Creates an DeleteUserCommand to delete the specified {@code userName}.
     */
    public DeleteUserCommand(String uName) {
        requireNonNull(uName);
        this.userNameToDelete = uName;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        // if user has not login or is not admin, then throw exception
        if (!model.checkIsLogin()) {
            throw new CommandException(User.MESSAGE_NOT_LOGIN);
        }
        if (!model.checkIsAdmin()) {
            throw new CommandException(User.MESSAGE_NOT_ADMIN);
        }
        User userToDelete = model.doesUserExist(userNameToDelete);
        if (userToDelete == null) {
            throw new CommandException(String.format(MESSAGE_INVALID_USER, userNameToDelete));
        }
        model.deleteUser(userToDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, userNameToDelete));
    }
}
