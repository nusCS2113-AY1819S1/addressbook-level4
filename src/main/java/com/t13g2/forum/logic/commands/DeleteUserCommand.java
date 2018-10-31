package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_NAME;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.commons.core.EventsCenter;
import com.t13g2.forum.commons.events.model.UserLoginEvent;
import com.t13g2.forum.commons.exceptions.NotLoggedInException;
import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

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
        try {
            if (!Context.getInstance().isCurrentUserAdmin()) {
                throw new CommandException(User.MESSAGE_NOT_ADMIN);
            }
        } catch (CommandException e) {
            throw e;
        } catch (NotLoggedInException e) {
            throw new CommandException(User.MESSAGE_NOT_LOGIN);
        }

        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            User userToDelete = unitOfWork.getUserRepository().getUserByUsername(userNameToDelete);
            unitOfWork.getUserRepository().deleteUser(userToDelete);
            unitOfWork.commit();
        } catch (EntityDoesNotExistException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_USER, userNameToDelete));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String currentUserName = Context.getInstance().getCurrentUser().getUsername();
        if (userNameToDelete.equals(currentUserName)) {
            EventsCenter.getInstance().post(new UserLoginEvent("", false, false));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, userNameToDelete));
    }
}
