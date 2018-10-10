package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD_ID;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.storage.forum.UnitOfWork;

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

    private final ForumThread toDelete;

    /**
     * Creates an DeleteThreadCommand to add the specified {@code thread}
     */
    public DeleteThreadCommand(ForumThread thread) {
        requireNonNull(thread);
        toDelete = thread;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try(UnitOfWork unitOfWork = new UnitOfWork()) {
            try {
                ForumThread forumThread =  unitOfWork.getForumThreadRepository().getThread(toDelete.getId());
                unitOfWork.getForumThreadRepository().deleteThread(forumThread);
            } catch (Exception e) {
                e.printStackTrace();
                throw new CommandException(MESSAGE_INVALID_THREAD_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete));
    }

}
