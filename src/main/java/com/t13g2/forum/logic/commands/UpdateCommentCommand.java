package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_COMMENT_CONTENT;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_COMMENT_ID;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.storage.forum.Context;
import com.t13g2.forum.storage.forum.UnitOfWork;

/**
 * Update a existing comment content in the forum book
 * User could only update comment content created by his/her own.
 */
public class UpdateCommentCommand extends Command {
    public static final String COMMAND_WORD = "updateComment";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Update a existing comment title created by its user in the forum book.\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_COMMENT_ID + "123 "
            + PREFIX_COMMENT_CONTENT + "This is a new title";

    public static final String MESSAGE_INVALID_COMMENT_ID = "Invalid Comment ID";
    public static final String MESSAGE_NOT_COMMENT_OWNER = "Sorry! You are not the owner of this comment.";

    private static int commentId;
    private static String contentToUpdate;

    public UpdateCommentCommand(int commentId, String contentToUpdate) {
        requireNonNull(commentId);
        requireNonNull(contentToUpdate);
        this.commentId = commentId;
        this.contentToUpdate = contentToUpdate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        String messageSuccess = "Updated comment " + commentId + " to a new title: %1$s";
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            Comment comment = unitOfWork.getCommentRepository().getComment(commentId);
            if (Context.getInstance().getCurrentUser().getId() == comment.getCreatedByUserId()) {
                throw new CommandException(MESSAGE_NOT_COMMENT_OWNER);
            }
            comment.setContent(contentToUpdate); //            unitOfWork.getCommentRepository().updateComment(comment);
            unitOfWork.commit();
        } catch (Exception e) {
            e.printStackTrace(); //            throw new CommandException(MESSAGE_INVALID_COMMENT_ID);
        }
        return new CommandResult(String.format(messageSuccess, contentToUpdate));
    }
}
