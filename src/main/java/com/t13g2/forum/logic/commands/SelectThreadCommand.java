package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD_ID;
import static java.util.Objects.requireNonNull;

import java.util.List;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.storage.forum.UnitOfWork;

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

    public SelectThreadCommand(int threadId) {
        this.threadId = threadId;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        String messageSuccess = "Listed all comments under Thread " + threadId + ":\n %1$s";
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            List<Comment> commentList = unitOfWork.getCommentRepository().getCommentsByThread(threadId);
            for (Comment comment : commentList) {
                message = comment.getId() + ": " + comment.getContent() + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommandResult(String.format(messageSuccess, message));
    }
}
