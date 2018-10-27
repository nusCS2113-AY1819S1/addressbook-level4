package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.commons.core.Messages.MESSAGE_INVALID_THREAD_ID;
import static com.t13g2.forum.commons.core.Messages.MESSAGE_NOT_LOGIN;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD_ID;
import static java.util.Objects.requireNonNull;

import java.util.List;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;
import com.t13g2.forum.ui.DisplayFormatter;

/**
 * List out all the comments under certain thread in the forum book.
 */
public class SelectThreadCommand extends Command {
    public static final String COMMAND_WORD = "selectThread";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List out all the comments under certain thread in the forum book.\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_THREAD_ID + "1";

    private static String message;
    private static int threadId;
    private static String threadTitle;

    public SelectThreadCommand(int threadId) {
        requireNonNull(threadId);
        this.threadId = threadId;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        String messageSuccess = "Listed all comments under Thread ID(%s) with title: %s"
                + "\n****************************************************************************\n"
                + "%s";
        if (!Context.getInstance().isLoggedIn()) {
            throw new CommandException(MESSAGE_NOT_LOGIN);
        }
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            List<Comment> commentList = unitOfWork.getCommentRepository().getCommentsByThread(threadId);
            message = DisplayFormatter.displayCommentList(commentList);
            threadTitle = unitOfWork.getForumThreadRepository().getThread(threadId).getTitle();
        } catch (EntityDoesNotExistException e) {
            throw new CommandException(MESSAGE_INVALID_THREAD_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommandResult(String.format(messageSuccess, threadId, threadTitle, message));
    }
}
