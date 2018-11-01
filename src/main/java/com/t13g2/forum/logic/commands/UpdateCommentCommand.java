package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.commons.core.Messages.MESSAGE_INVALID_COMMENT;
import static com.t13g2.forum.commons.core.Messages.MESSAGE_INVALID_COMMENT_ID;
import static com.t13g2.forum.commons.core.Messages.MESSAGE_NOT_COMMENT_OWNER;
import static com.t13g2.forum.commons.core.Messages.MESSAGE_NOT_LOGIN;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_COMMENT_CONTENT;
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
            + PREFIX_COMMENT_CONTENT + "This is a new comment";

    public static final String MESSAGE_SUCCESS = "Comment content updated successfully! %1$s";
    private static int commentIdToUpdate;
    private static String contentToUpdate;
    private int currentModuleId;
    private int currentThreadId;
    private String currentModuleCode;

    public UpdateCommentCommand(int commentIdToUpdate, String contentToUpdate) {
        requireNonNull(commentIdToUpdate);
        requireNonNull(contentToUpdate);
        this.commentIdToUpdate = commentIdToUpdate;
        this.contentToUpdate = contentToUpdate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!Context.getInstance().isLoggedIn()) {
            throw new CommandException(MESSAGE_NOT_LOGIN);
        }
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            currentThreadId = Context.getInstance().getCurrentThreadId();
            currentModuleId = Context.getInstance().getCurrentModuleId();
            currentModuleCode = unitOfWork.getModuleRepository().getModule(currentModuleId).getModuleCode();
            Comment comment = unitOfWork.getCommentRepository().getComment(commentIdToUpdate);
            if (currentThreadId != comment.getThreadId()) {
                throw new CommandException(MESSAGE_INVALID_COMMENT);
            }
            if (Context.getInstance().getCurrentUser().getId() == comment.getCreatedByUserId()
                    || Context.getInstance().isCurrentUserAdmin()) {
                comment.setContent(contentToUpdate);
                unitOfWork.commit();
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
        String updateMessage = "\n\n"
                + "Under Module Code: " + currentModuleCode + "\n"
                + "Under Thread ID: " + currentThreadId + "\n"
                + "Updated Comment ID: " + commentIdToUpdate + "\n"
                + "Updated Comment Content: " + contentToUpdate + "\n";
        return new CommandResult(String.format(MESSAGE_SUCCESS, updateMessage));
    }
}
