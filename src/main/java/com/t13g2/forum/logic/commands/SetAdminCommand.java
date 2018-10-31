package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_ADMIN_SET;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_NAME;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

//@@author xllx1
/**
 * Set a certain user as admin or set the admin as user
 */
public class SetAdminCommand extends Command {
    public static final String COMMAND_WORD = "setAdmin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set/Revert (true/false) a user as admin. "
        + "Parameters: "
        + PREFIX_USER_NAME + "USER NAME "
        + PREFIX_ADMIN_SET + "SET/REVERT "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_USER_NAME + "john "
        + PREFIX_ADMIN_SET + "true  ";

    public static final String MESSAGE_SUCCESS = "%1$s now is %2$s.";
    public static final String MESSAGE_FAILED = "You cannot set/revert yourself.";
    public static final String MESSAGE_INVALID_USER = "The user \"%1$s\" does not exist.";
    public static final String MESSAGE_DUPLICATE_SET = "The user \"%1$s\" is already an admin.";
    public static final String MESSAGE_DUPLICATE_REVERT = "%1$s is not an admin, unable to revert.";

    private final String userNametoSetAdmin;
    private final boolean setAdmin;

    /**
     * Creates an SetAdminCommand to set the specified {@code userName}.
     */
    public SetAdminCommand(String userName, boolean set) {
        requireNonNull(userName);
        userNametoSetAdmin = userName;
        setAdmin = set;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        String isAdmin = "an admin";
        User userToSet = null;

        // if user has not login or is not admin, then throw exception
        if (!Context.getInstance().isLoggedIn()) {
            throw new CommandException(User.MESSAGE_NOT_LOGIN);
        } else if (!Context.getInstance().isCurrentUserAdmin()) {
            throw new CommandException(User.MESSAGE_NOT_ADMIN);
        }

        if (Context.getInstance().getCurrentUser().getUsername().equals(userNametoSetAdmin)) {
            return new CommandResult(MESSAGE_FAILED);
        }
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            userToSet = unitOfWork.getUserRepository().getUserByUsername(userNametoSetAdmin);
            if (setAdmin && userToSet.isAdmin()) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_SET, userNametoSetAdmin));
            } else if (!setAdmin && !userToSet.isAdmin()) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_REVERT, userNametoSetAdmin));
            } else {
                userToSet.setAdmin(!userToSet.isAdmin());
                unitOfWork.getUserRepository().updateUser(userToSet);
                unitOfWork.commit();
            }

            if (!setAdmin) {
                isAdmin = "a user";
            }
        } catch (CommandException e) {
            throw e;
        } catch (EntityDoesNotExistException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_USER, userNametoSetAdmin));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, userNametoSetAdmin, isAdmin));
    }
}
