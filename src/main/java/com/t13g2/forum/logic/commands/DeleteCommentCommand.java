package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.commons.core.Messages.MESSAGE_INVALID_COMMENT;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Delete a certain comment under corresponding module and thread\n"
            + "Parameters: "
            + PREFIX_COMMENT_ID + "COMMENT ID \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_COMMENT_ID + "1";

    public static final String MESSAGE_SUCCESS = "Comment deleted successfully! %1$s";
    private final int commentIdToDelete;
    private String currentModuleCode;
    private int currentModuleId;
    private int threadId;
    /**
     * Creates an DeleteCommentCommand to delete the specified {@code Comment}
     */
    public DeleteCommentCommand(int commentIdToDelete) {
        requireNonNull(commentIdToDelete);
        this.commentIdToDelete = commentIdToDelete;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!Context.getInstance().isLoggedIn()) {
            throw new CommandException(MESSAGE_NOT_LOGIN);
        }
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            threadId = unitOfWork.getCommentRepository().getComment(commentIdToDelete).getThreadId();
            currentModuleId = Context.getInstance().getCurrentModuleId();
            if (Context.getInstance().getCurrentThreadId() != threadId) {
                throw new CommandException(MESSAGE_INVALID_COMMENT);
            }
            Comment commentToDelete = unitOfWork.getCommentRepository().getComment(commentIdToDelete);
            if (Context.getInstance().getCurrentUser().getId() == commentToDelete.getCreatedByUserId()
                    || Context.getInstance().isCurrentUserAdmin()) {
                //delete the comment according to the commentId from the memory repository
                unitOfWork.getCommentRepository().deleteComment(commentIdToDelete);
                //update to local database
                unitOfWork.commit();
                currentModuleCode = unitOfWork.getModuleRepository().getModule(currentModuleId).getModuleCode();
            } else {
                throw new CommandException(MESSAGE_NOT_COMMENT_OWNER);
            }
        } catch (EntityDoesNotExistException e) {
            throw new CommandException(MESSAGE_INVALID_COMMENT_ID);
        } catch (CommandException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        String deleteMessage = "\n\n"
                + "Under Module Code: " + currentModuleCode + "\n"
                + "Under Thread ID: " + Context.getInstance().getCurrentThreadId() + "\n"
                + "Deleted Comment ID: " + commentIdToDelete + "\n";

        return new CommandResult(String.format(MESSAGE_SUCCESS, deleteMessage));
    }
}
