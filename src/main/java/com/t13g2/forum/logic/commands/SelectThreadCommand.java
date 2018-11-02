package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.commons.core.Messages.MESSAGE_INVALID_THREAD_ID;
import static com.t13g2.forum.commons.core.Messages.MESSAGE_NOT_LOGIN;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD_ID;
import static java.util.Objects.requireNonNull;

import java.util.List;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.logic.util.DisplayFormatter;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

//@@author HansKoh
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
    private static int moduleId;
    private static String threadTitle;
    private static String moduleCode;

    public SelectThreadCommand(int threadId) {
        requireNonNull(threadId);
        this.threadId = threadId;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        String messageSuccess = "Listed all comments under \n"
                + "Module Code: %s\n"
                + "Thread ID      : %s\n"
                + "Thread Title  : %s\n"
                + "****************************************************************************\n"
                + "****************************************************************************\n"
                + "%s";
        if (!Context.getInstance().isLoggedIn()) {
            throw new CommandException(MESSAGE_NOT_LOGIN);
        }
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            moduleId = unitOfWork.getForumThreadRepository().getThread(threadId).getModuleId();
            moduleCode = unitOfWork.getModuleRepository().getModule(moduleId).getModuleCode();
            List<Comment> commentList = unitOfWork.getCommentRepository().getCommentsByThread(threadId);
            message = DisplayFormatter.displayCommentList(commentList);
            threadTitle = unitOfWork.getForumThreadRepository().getThread(threadId).getTitle();
            Context.getInstance().setCurrentThreadId(unitOfWork.getForumThreadRepository().getThread(threadId).getId());
        } catch (EntityDoesNotExistException e) {
            throw new CommandException(MESSAGE_INVALID_THREAD_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommandResult(String.format(messageSuccess, moduleCode, threadId, threadTitle, message));
    }
}
