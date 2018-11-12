package com.t13g2.forum.logic.commands;

import static java.util.Objects.requireNonNull;

import com.t13g2.forum.commons.core.EventsCenter;
import com.t13g2.forum.commons.events.model.UserLoginEvent;
import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.forum.User;

/**
 *
 */
public class UserDeleteCommand extends Command {
    public static final String COMMAND_WORD = "deleteMe";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Self delete user from the forum book. ";

    public static final String MESSAGE_SUCCESS = "It was nice knowing you! Good bye %1$s :(";
    public static final String MESSAGE_FAIL = "No user logged in for Self Delete";


    private String userName;
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (Context.getInstance().getCurrentUser() != null) {
            try (UnitOfWork unitOfWork = new UnitOfWork()) {
                User userToDelete = Context.getInstance().getCurrentUser();
                userName = userToDelete.getUsername();
                unitOfWork.getUserRepository().deleteUser(userToDelete);
                unitOfWork.commit();
                Context.getInstance().setCurrentUser(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            EventsCenter.getInstance().post(new UserLoginEvent("", false, false));
            return new CommandResult(String.format(MESSAGE_SUCCESS, userName));
        } else {
            return new CommandResult(String.format(MESSAGE_FAIL, userName));
        }
    }
}
