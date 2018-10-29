package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.commons.core.Messages.MESSAGE_INVALID_COMMENT_ID;
import static com.t13g2.forum.commons.core.Messages.MESSAGE_NOT_COMMENT_OWNER;
import static com.t13g2.forum.commons.core.Messages.MESSAGE_NOT_LOGIN;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_COMMENT_ID;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

//@@author HansKoh
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
    private static String commentContentToDelete;
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
        if (!Context.getInstance().isLoggedIn()) {
            throw new CommandException(MESSAGE_NOT_LOGIN);
        }
        commentContentToDelete = "";
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            Comment comment = unitOfWork.getCommentRepository().getComment(commentId);
            commentContentToDelete = comment.getContent();
            if (Context.getInstance().getCurrentUser().getId() != comment.getCreatedByUserId()) {
                throw new CommandException(MESSAGE_NOT_COMMENT_OWNER);
            }
            //delete the comment according to the commentId from the memory repository
            unitOfWork.getCommentRepository().deleteComment(commentId);
            //update to local database
            unitOfWork.commit();
        } catch (EntityDoesNotExistException e) {
            throw new CommandException(MESSAGE_INVALID_COMMENT_ID);
        } catch (CommandException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        String message = "\n"
                + "Comment ID: " + commentId + "\n"
                + "Comment Content: " + commentContentToDelete + "\n";

        return new CommandResult(String.format(MESSAGE_SUCCESS, message));
    }
}
