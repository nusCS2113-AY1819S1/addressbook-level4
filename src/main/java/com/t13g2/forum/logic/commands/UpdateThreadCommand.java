package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD_ID;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD_TITLE;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.storage.forum.UnitOfWork;

/**
 * Update a existing thread title created by its user in the forum book
 */
public class UpdateThreadCommand extends Command {
    public static final String COMMAND_WORD = "updateThread";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Update a existing thread title created by its user in the forum book.\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_THREAD_ID + "123 "
            + PREFIX_THREAD_TITLE + "This is a new title";

    private static int threadId;
    private static String threadTitle;

    public UpdateThreadCommand(int threadId, String threadTitle) {
        this.threadId = threadId;
        this.threadTitle = threadTitle;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        String messageSuccess = "Updated thread " + threadId + " to a new title: %1$s";
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            ForumThread thread = unitOfWork.getForumThreadRepository().getThread(threadId);
            thread.setTitle(threadTitle);
            unitOfWork.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommandResult(String.format(messageSuccess, threadTitle));
    }
}
