package com.t13g2.forum.logic.commands;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.storage.forum.UnitOfWork;

import java.util.List;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD_ID;
import static java.util.Objects.requireNonNull;

/**
 * List out all the comments under certain thread in the forum book.
 */
public class SelectThreadCommand extends Command{
    public static final String COMMAND_WORD = "selectThread";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List out all the comments under certain thread in the forum book.\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_THREAD_ID + "1";

    public static String MESSAGE;
    public static int threadId;

    public SelectThreadCommand(int threadId) {
        this.threadId = threadId;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        String MESSAGE_SUCCESS = "Listed all comments under Thread " + threadId + ":\n %1$s";
        try(UnitOfWork unitOfWork = new UnitOfWork()){
            ForumThread thread = unitOfWork.getForumThreadRepository().getThread(threadId);
            List<Comment> commentList=unitOfWork.getCommentRepository().getCommentsByThread(threadId);
            for(Comment comment : commentList){
                MESSAGE = comment.getId() + ": " + comment.getContent() + "\n";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, MESSAGE));
    }
}
