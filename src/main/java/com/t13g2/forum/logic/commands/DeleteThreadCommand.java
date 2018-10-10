package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD_ID;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;

/**
 * Delete a certain thread. Only admin could delete threads from others,
 * user could only delete threads created by his/her own.
 */
public class DeleteThreadCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete a certain thread "
        + "Parameters: "
        + PREFIX_THREAD_ID + "THREAD ID "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_THREAD_ID + "1";

    public static final String MESSAGE_SUCCESS = "Thread deleted: %1$s";
    public static final String MESSAGE_INVALID_THREAD_ID = "Invalid Thread id";

    private final Thread toDelete;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public DeleteThreadCommand(Thread thread) {
        requireNonNull(thread);
        toDelete = thread;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // if (model.hasThread(toDelete)) {
        //     throw new CommandException(MESSAGE_INVALID_THREAD_ID);
        // }
        // model.deleteThread(toDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete));
    }

}
