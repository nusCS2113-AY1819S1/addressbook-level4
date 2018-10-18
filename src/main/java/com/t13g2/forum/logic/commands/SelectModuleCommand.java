package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static java.util.Objects.requireNonNull;

import java.util.List;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.model.forum.Module;
import com.t13g2.forum.storage.forum.UnitOfWork;

/**
 * List out all the threads under certain module in the forum book.
 */
public class SelectModuleCommand extends Command {
    public static final String COMMAND_WORD = "selectModule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List all the threads under certain module in the forum book.\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2113";

    private static String message;
    private static String moduleCode;

    public SelectModuleCommand(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        String messageSuccess = "Listed all threads under module " + moduleCode + ":\n %1$s";
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            Module module = unitOfWork.getModuleRepository().getModuleByCode(moduleCode);
            List<ForumThread> threadList = unitOfWork.getForumThreadRepository().getThreadsByModule(module);
            for (ForumThread thread : threadList) {
                message = thread.getId() + ": " + thread.getTitle() + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommandResult(String.format(messageSuccess, message));
    }
}
