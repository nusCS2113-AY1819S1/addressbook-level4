package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_NAME;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_PASSWORD;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

/**
 *
 */
public class AddUserCommand extends Command {
    /**
     *
     */
    public static final String COMMAND_WORD = "addUser";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add user to forum book. "
        + "Parameters: "
        + PREFIX_USER_NAME + "USER NAME "
        + PREFIX_USER_PASSWORD + "PASSWORD \n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_USER_NAME + "John Doe "
        + PREFIX_USER_PASSWORD + "1234 ";


    public static final String MESSAGE_SUCCESS = "WELCOME : %1$s. You had been successfully been registered.";
    //public static final String MESSAGE_FAIL = "No user named %1$s found or password is wrong";
    public static final String MESSAGE_DUPLICATE_PERSON = "This user name already exists, Please use another name.";
    private final User userToAdd;

    public AddUserCommand(User newUser) {
        requireNonNull(newUser);
        this.userToAdd = newUser;

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            boolean exist = false;
            try {
                if (userToAdd == unitOfWork.getUserRepository().getUserByUsername(userToAdd.getUsername())) {
                    exist = true;
                    throw new CommandException(String.format(MESSAGE_DUPLICATE_PERSON));
                }

            } catch (EntityDoesNotExistException ex) {
                if (!exist) {
                    unitOfWork.getUserRepository().addUser(userToAdd);
                    unitOfWork.commit();
                }
                ex.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, userToAdd.getUsername()));
    }
}
