package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_NAME;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_PASSWORD;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.storage.forum.Context;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;
import com.t13g2.forum.storage.forum.UnitOfWork;

//@@xllx1
/**
 * Updates a certain user's password.
 */
public class AdminUpdatePasswordCommand extends Command {

    public static final String COMMAND_WORD = "updatePass";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates a certain user's password. "
        + "Parameters: "
        + PREFIX_USER_NAME + "USER NAME "
        + PREFIX_USER_PASSWORD + "USER PASSWORD "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_USER_NAME + "john "
        + PREFIX_USER_PASSWORD + "456";

    public static final String MESSAGE_SUCCESS = "Password for %1$s is now %2$s";
    public static final String MESSAGE_INVALID_USER = "No user named %1$s found. Please try again!";


    private final String userNameToUpdate;
    private final String userPassToUpdate;

    /**
     * Creates an AdminUpdatePasswordCommand to update password for the the specified {@code toUpdate}.
     */
    public AdminUpdatePasswordCommand(String uName, String uPass) {
        requireNonNull(uName);
        requireNonNull(uPass);
        this.userNameToUpdate = uName;
        this.userPassToUpdate = uPass;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        // if user has not login or is not admin, then throw exception
        if (Context.getInstance().getCurrentUser() == null) {
            throw new CommandException(User.MESSAGE_NOT_LOGIN);
        }
        if (!Context.getInstance().getCurrentUser().isAdmin()) {
            throw new CommandException(User.MESSAGE_NOT_ADMIN);
        }
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            User user = unitOfWork.getUserRepository().getUserByUsername(userNameToUpdate);
            user.setPassword(userPassToUpdate);
            unitOfWork.getUserRepository().updateUser(user);
            unitOfWork.commit();
        } catch (EntityDoesNotExistException e) {
            throw new CommandException(MESSAGE_INVALID_USER);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, userNameToUpdate, userPassToUpdate));
    }
}
