package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_COMMENT_ID;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.storage.forum.Context;
import com.t13g2.forum.storage.forum.UnitOfWork;

/**
 * Delete a certain comment. Only admin could delete comments from others,
 * user could only delete comment created by his/her own.
 */
public class DeleteCommentCommand extends Command {
    public static final String COMMAND_WORD = "deleteComment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete a certain comment \n"
            + "Parameters: "
            + PREFIX_COMMENT_ID + "COMMENT ID \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_COMMENT_ID + "1";

    public static final String MESSAGE_SUCCESS = "Comment deleted: %1$s";
    public static final String MESSAGE_NOT_COMMENT_OWNER = "Sorry! You are not the owner of this comment.";

    private final int commentId;

    /**
     * Creates an DeleteCommentCommand to delete the specified {@code Comment}
     */
    public DeleteCommentCommand(int commentId) {
        requireNonNull(commentId);
        this.commentId = commentId;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        String commentContent = "";
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            Comment comment = unitOfWork.getCommentRepository().getComment(commentId);
            if (Context.getInstance().getCurrentUser().getId() != comment.getCreatedByUserId()) {
                throw new CommandException(MESSAGE_NOT_COMMENT_OWNER);
            }
            commentContent = comment.getContent();
            //delete the comment according to the commentId from the memory repository
            unitOfWork.getCommentRepository().deleteComment(commentId);
            //update to local database
            unitOfWork.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String message = "\n"
                + "Comment ID: " + commentId + "\n"
                + "Comment Content: " + commentContent + "\n";

        return new CommandResult(String.format(MESSAGE_SUCCESS, message));
    }
}
