package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_NAME;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_PASSWORD;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.storage.forum.UnitOfWork;

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
    public static final String MESSAGE_FAIL = "No user named %1$s found or password is wrong";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    private final String userName;
    private final String userPassword;

    public LoginCommand(String userName, String userPassword) {
        requireNonNull(userName);
        requireNonNull(userPassword);
        this.userName = userName;
        this.userPassword = userPassword;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        User exist = new User();
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            try {
                exist = unitOfWork.getUserRepository().authenticate(userName, userPassword);
                if (exist == null) {
                    return new CommandResult(MESSAGE_FAIL);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, exist.getUsername()));
    }
}
