package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.commons.core.Messages.MESSAGE_INVALID_THREAD_ID;
import static com.t13g2.forum.commons.core.Messages.MESSAGE_NOT_LOGIN;
import static com.t13g2.forum.commons.core.Messages.MESSAGE_NOT_THREAD_OWNER;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD_ID;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD_TITLE;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

/**
 * Update a existing thread title in the forum book
 * User could only update thread title created by his/her own.
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
    private static String threadTitleToUpdate;

    public UpdateThreadCommand(int threadId, String threadTitleToUpdate) {
        requireNonNull(threadId);
        requireNonNull(threadTitleToUpdate);
        this.threadId = threadId;
        this.threadTitleToUpdate = threadTitleToUpdate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!Context.getInstance().isLoggedIn()) {
            throw new CommandException(MESSAGE_NOT_LOGIN);
        }
        String messageSuccess = "Updated thread " + threadId + " to a new title: %1$s";
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            ForumThread forumThread = unitOfWork.getForumThreadRepository().getThread(threadId);
            if (Context.getInstance().getCurrentUser().getId() != forumThread.getCreatedByUserId()) {
                throw new CommandException(MESSAGE_NOT_THREAD_OWNER);
            }
            forumThread.setTitle(threadTitleToUpdate);
            unitOfWork.getForumThreadRepository().updateThread(forumThread);
            unitOfWork.commit();
        } catch (EntityDoesNotExistException e) {
            throw new CommandException(MESSAGE_INVALID_THREAD_ID);
        } catch (CommandException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommandResult(String.format(messageSuccess, threadTitleToUpdate));
    }
}
