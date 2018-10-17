package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_COMMENT_CONTENT;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD_TITLE;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.model.forum.Module;
import com.t13g2.forum.storage.forum.UnitOfWork;


/**
 * Create a new thread to the forum book under certain module
 */
public class CreateThreadCommand extends Command {

    public static final String COMMAND_WORD = "createThread";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a thread to the forum book. "
            + "Parameters: "
            + PREFIX_MODULE_CODE + "MODULE CODE "
            + PREFIX_THREAD_TITLE + "THREAD TITILE "
            + PREFIX_COMMENT_CONTENT + "COMMENT...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2113 "
            + PREFIX_THREAD_TITLE + "EXAM DISCUSSION "
            + PREFIX_COMMENT_CONTENT + "Hi fellows, what is the topic coverage for the final?";

    public static final String MESSAGE_SUCCESS = "New thread added: %1$s";
    private String moduleCode;
    private ForumThread forumThread;
    private Comment comment;

    /**
     * Creates a CreateThreadCommand to create the specified {@code ForumThread}
     */
    public CreateThreadCommand(String moduleCode, ForumThread forumThread, Comment comment) {
        requireNonNull(moduleCode);
        this.moduleCode = moduleCode;
        this.forumThread = forumThread;
        this.comment = comment;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            //get the respective module by moduleCode
            Module module = unitOfWork.getModuleRepository().getModuleByCode(moduleCode);
            forumThread.setModuleId(module.getId()); //pass the module ID to this forum thread
            comment.setThreadId(forumThread.getId()); //pass the thread ID to this comment
            //add this forum thread to unitOfWork meaning save it to the memory repository
            unitOfWork.getForumThreadRepository().addThread(forumThread);
            //add this comment to unitOfWork meaning save it to the memory repository
            unitOfWork.getCommentRepository().addComment(comment);
            unitOfWork.commit(); //commit meaning add the forum thread update to local database
        } catch (Exception e) {
            e.printStackTrace();
        }
        String message = "\n"
                + "Module: " + moduleCode + "\n"
                + "Thread Title: " + forumThread.getTitle() + "\n"
                + "Comment: " + comment.getContent();
        return new CommandResult(String.format(MESSAGE_SUCCESS, message));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateThreadCommand // instanceof handles nulls
                && forumThread.equals(((CreateThreadCommand) other).forumThread));
    }
}
