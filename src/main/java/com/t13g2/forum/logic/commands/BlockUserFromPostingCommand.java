package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_NAME;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.User;


/**
 * Allow admin to block user from posting a new thread.
 */
public class BlockUserFromPostingCommand extends Command {

    public static final String COMMAND_WORD = "block";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Blocks a certain user from posting new thread."
        + "Parameters: "
        + PREFIX_USER_NAME + "UID "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_USER_NAME + "john";

    public static final String MESSAGE_SUCCESS = "Blocked user: %1$s";
    public static final String MESSAGE_DUPLICATE_BLOCK = "This user has already been blocked.";

    private final User toBlock;

    /**
     * Creates an BlockUserFromPostingCommand to block the specified {@code User}
     */
    public BlockUserFromPostingCommand(User userToBeBlocked) {
        requireNonNull(userToBeBlocked);
        toBlock = userToBeBlocked;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.isBlock(toBlock)) {
            throw new CommandException(MESSAGE_DUPLICATE_BLOCK);
        }

        model.blockUser(toBlock);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toBlock));
    }

}
