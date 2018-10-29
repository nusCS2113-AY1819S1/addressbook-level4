package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.commons.core.Messages.MESSAGE_INVALID_THREAD_ID;
import static com.t13g2.forum.commons.core.Messages.MESSAGE_NOT_LOGIN;
import static com.t13g2.forum.commons.core.Messages.MESSAGE_NOT_THREAD_OWNER;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD_ID;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

//@@author HansKoh
/**
 * Delete a certain thread. Only admin could delete threads from others,
 * user could only delete threads created by his/her own.
 */
public class DeleteThreadCommand extends Command {
    public static final String COMMAND_WORD = "deleteThread";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete a certain thread \n"
        + "Parameters: "
        + PREFIX_THREAD_ID + "THREAD ID \n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_THREAD_ID + "1";

    public static final String MESSAGE_SUCCESS = "Thread deleted: %1$s";
    private final int threadId;

    /**
     * Creates an DeleteThreadCommand to delete the specified {@code ForumThread}
     */
    public DeleteThreadCommand(int threadId) {
        requireNonNull(threadId);
        this.threadId = threadId;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!Context.getInstance().isLoggedIn()) {
            throw new CommandException(MESSAGE_NOT_LOGIN);
        }
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            if (Context.getInstance().getCurrentUser().getId()
                    == unitOfWork.getForumThreadRepository().getThread(threadId).getCreatedByUserId()
                    || Context.getInstance().isCurrentUserAdmin()) {
                //delete the thread according to the threadId from the memory repository
                unitOfWork.getForumThreadRepository().deleteThread(threadId);
                //update to local database
                unitOfWork.commit();
            } else {
                throw new CommandException(MESSAGE_NOT_THREAD_OWNER);
            }
        } catch (EntityDoesNotExistException e) {
            throw new CommandException(MESSAGE_INVALID_THREAD_ID);
        } catch (CommandException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        String message = "\n"
            + "Thread ID: " + threadId + "\n";

        return new CommandResult(String.format(MESSAGE_SUCCESS, message));
    }

}
