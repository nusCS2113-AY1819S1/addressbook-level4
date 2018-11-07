package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_NAME;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_PASSWORD;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.commons.core.EventsCenter;
import com.t13g2.forum.commons.events.model.UserLoginEvent;
import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

/**
 *
 */
public class LoginCommand extends Command {
    public static final String COMMAND_WORD = "login";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": login to forum book. "
            + "Parameters: "
            + PREFIX_USER_NAME + "USER NAME "
            + PREFIX_USER_PASSWORD + "PASSWORD \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USER_NAME + "John Doe "
            + PREFIX_USER_PASSWORD + "1234 ";


    public static final String MESSAGE_SUCCESS = "WELCOME : %1$s";
    public static final String MESSAGE_FAIL = "No user named %s found or password is wrong";
    public static final String MESSAGE_USER_ACTIVE = "Hi %s, Please logout before trying to login. Thank you";
    private final String userName;
    private final String userPassword;
    private boolean empty;

    public LoginCommand(String userName, String userPassword) {
        requireNonNull(userName);
        requireNonNull(userPassword);
        this.userName = userName;
        this.userPassword = userPassword;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        User exist = null;
        if (Context.getInstance().getCurrentUser() == null) {

            try (UnitOfWork unitOfWork = new UnitOfWork()) {
                exist = unitOfWork.getUserRepository().authenticate(userName, userPassword);
            } catch (EntityDoesNotExistException e) {
                exist = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (exist != null) {
                Context.getInstance().setCurrentUser(exist);
                EventsCenter.getInstance().post(new UserLoginEvent(userName, exist.isAdmin(), exist.isBlock()));
                return new CommandResult(String.format(MESSAGE_SUCCESS, userName));
            } else {
                EventsCenter.getInstance().post(new UserLoginEvent("", false, false));
                throw new CommandException(String.format(MESSAGE_FAIL, userName));
            }
        } else {
            return new CommandResult(String.format(MESSAGE_USER_ACTIVE, userName));
        }
    }
}
