package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD_ID;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.model.forum.Module;
import com.t13g2.forum.storage.forum.UnitOfWork;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.storage.forum.UnitOfWork;

/**
 * Delete a certain thread. Only admin could delete threads from others,
 * user could only delete threads created by himself/herself.
 */

/**
 * Example: deleteThread tId/1
 */
public class DeleteThreadCommand extends Command {
    public static final String COMMAND_WORD = "deleteThread";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete a certain thread "
        + "Parameters: "
        + PREFIX_THREAD_ID + "THREAD ID "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_THREAD_ID + "1";

    public static final String MESSAGE_SUCCESS = "Thread deleted: %1$s";
    public static final String MESSAGE_INVALID_THREAD_ID = "Invalid Thread id";

    private final ForumThread forumThread;
    private final int ThreadId;
    private final String threadTitle;

    /**
     * Creates an DeleteThreadCommand to delete the specified {@code ForumThread}
     */
    public DeleteThreadCommand(ForumThread forumThread) {
        requireNonNull(forumThread);
        this.forumThread = forumThread;
        this.ThreadId = forumThread.getId();
        this.threadTitle = forumThread.getTitle();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            try {
                unitOfWork.getForumThreadRepository().deleteThread(ThreadId);//delete the thread according to the ThreadId from the memory repository
                unitOfWork.commit();//update to local database
            } catch (Exception e) {
                e.printStackTrace();
                throw new CommandException(MESSAGE_INVALID_THREAD_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String MESSAGE = "\n"
                + "Thread ID: " + ThreadId + "\n"
                + "Thread Title: " + threadTitle + "\n";
        return new CommandResult(String.format(MESSAGE_SUCCESS, MESSAGE));
    }

}
