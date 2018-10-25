package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_COMMENT_CONTENT;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD_ID;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.storage.forum.Context;
import com.t13g2.forum.storage.forum.UnitOfWork;

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
    public static final String MESSAGE_BLOCKED_USER = "You have been blocked for creating new threads and comments!";
    public static final String MESSAGE_NOT_LOGIN = "Sorry! You have not login yet.";
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
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            if (!model.checkIsLogin()) {
                throw new CommandException(MESSAGE_NOT_LOGIN);
            } else if (Context.getInstance().getCurrentUser().isBlock()) {
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
