package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.commons.core.Messages.MESSAGE_BLOCKED_USER;
import static com.t13g2.forum.commons.core.Messages.MESSAGE_INVALID_THREAD_ID;
import static com.t13g2.forum.commons.core.Messages.MESSAGE_NOT_LOGIN;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_COMMENT_CONTENT;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD_ID;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

//@@author HansKoh
/**
 * Create a new comment to the forum book under certain module
 */
public class CreateCommentCommand extends Command {
    public static final String COMMAND_WORD = "createComment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a comment to a thread. "
            + "Parameters: "
            + PREFIX_THREAD_ID + "THREAD ID "
            + PREFIX_COMMENT_CONTENT + "COMMENT...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_THREAD_ID + "1 "
            + PREFIX_COMMENT_CONTENT + "This is a new comment";

    public static final String MESSAGE_SUCCESS = "New comment added: %1$s";
    private final int threadId;
    private final String contentToAdd;
    private Comment comment;
    private int moduleId;
    private int commentId;

    /**
     * Creates a CreateCommentCommand to create the specified {@code Comment}
     */
    public CreateCommentCommand(int threadId, String contentToAdd) {
        requireNonNull(threadId);
        requireNonNull(contentToAdd);
        this.threadId = threadId;
        this.contentToAdd = contentToAdd;
        this.comment = new Comment(); //instantiate a new comment
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!Context.getInstance().isLoggedIn()) {
            throw new CommandException(MESSAGE_NOT_LOGIN);
        }
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            if (Context.getInstance().isCurrentUserBlocked()) {
                throw new CommandException(MESSAGE_BLOCKED_USER);
            }
            //get the respective module by moduleCode
            ForumThread forumThread = unitOfWork.getForumThreadRepository().getThread(threadId);
            comment.setThreadId(forumThread.getId()); //set thread ID to the comment
            comment.setCreatedByUserId(Context.getInstance().getCurrentUser().getId()); //set owner ID to the comment
            comment.setContent(contentToAdd); //pass content to the comment
            commentId = comment.getId();
            moduleId = forumThread.getModuleId();
            //add this comment to unitOfWork meaning save it to the memory repository
            unitOfWork.getCommentRepository().addComment(comment);
            unitOfWork.commit(); //commit meaning add the forum thread update to local database
        } catch (EntityDoesNotExistException e) {
            throw new CommandException(MESSAGE_INVALID_THREAD_ID);
        } catch (CommandException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        String message = "\n"
                + "Module ID: " + moduleId + "\n"
                + "Thread ID: " + threadId + "\n"
                + "Comment ID: " + commentId + "\n"
                + "Comment Content: " + contentToAdd;
        return new CommandResult(String.format(MESSAGE_SUCCESS, message));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateCommentCommand // instanceof handles nulls
                && comment.equals(((CreateCommentCommand) other).comment));
    }
}
