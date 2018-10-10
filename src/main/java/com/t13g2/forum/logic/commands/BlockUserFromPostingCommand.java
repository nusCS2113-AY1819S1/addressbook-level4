package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_NAME;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.storage.forum.UnitOfWork;


/**
 * Allow admin to block user from posting a new thread.
 */
public class BlockUserFromPostingCommand extends Command {

    public static final String COMMAND_WORD = "block";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Blocks a certain user from posting new thread."
        + "Parameters: "
        + PREFIX_USER_NAME + "USER NAME "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_USER_NAME + "john";

    public static final String MESSAGE_SUCCESS = "User blocked successfully: %1$s";
    public static final String MESSAGE_INVALID_USER = "This user does not exist.";
    public static final String MESSAGE_DUPLICATE_BLOCK = "This user has already been blocked.";

    private final String userNameToBlock;

    /**
     * Creates an BlockUserFromPostingCommand to block the specified {@code User}
     */
    public BlockUserFromPostingCommand(String userName) {
        requireNonNull(userName);
        userNameToBlock = userName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        User user = new User();
        try(UnitOfWork unitOfWork = new UnitOfWork()) {
            try {
                user = unitOfWork.getUserRepository().getUserByUsername(userNameToBlock);
                if(user.getIsBlock()) {
                    throw new CommandException(MESSAGE_DUPLICATE_BLOCK);
                }
                else {
                    user.setIsBlock(true);
                    unitOfWork.commit();
                }
            } catch(Exception e){
                e.printStackTrace();
                throw new CommandException(MESSAGE_INVALID_USER);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, user));
    }

}
