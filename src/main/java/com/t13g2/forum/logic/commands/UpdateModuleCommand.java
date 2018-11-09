package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_MODULE_ID;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_MODULE_TITLE;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.forum.Module;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

//@@author xllx1

/**
 * Creates a module to the forum book by admin
 */
public class UpdateModuleCommand extends Command {
    public static final String COMMAND_WORD = "updateModule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates a module. "
        + "Parameters: "
        + PREFIX_MODULE_ID + "MODULE ID "
        + PREFIX_MODULE_CODE + "MODULE CODE "
        + PREFIX_MODULE_TITLE + "MODULE TITLE "
        + "\nExample: " + COMMAND_WORD + " "
        + PREFIX_MODULE_ID + "1 "
        + PREFIX_MODULE_CODE + "CS2113 "
        + PREFIX_MODULE_TITLE + "Software Engineering and OOP";

    public static final String MESSAGE_SUCCESS = "The module is now updated to\n"
        + "Module Code: %s\nModule Title: %s";
    public static final String MESSAGE_INVALID_MODULE_ID = "Invalid module id: %1$s.";

    private final int moduleIdToUpdate;
    private final String moduleCodeToUpdate;
    private final String moduleTitleToUpdate;

    /**
     * Creates an UpdateModuleCommand to create the specified {@code module}.
     */
    public UpdateModuleCommand(int moduleId, String moduleCode, String moduleTitle) {
        requireNonNull(moduleId);
        moduleIdToUpdate = moduleId;
        moduleCodeToUpdate = moduleCode;
        moduleTitleToUpdate = moduleTitle;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        // if user has not login or is not admin, then throw exception
        if (!Context.getInstance().isLoggedIn()) {
            throw new CommandException(User.MESSAGE_NOT_LOGIN);
        } else if (!Context.getInstance().isCurrentUserAdmin()) {
            throw new CommandException(User.MESSAGE_NOT_ADMIN);
        }

        Module moduleToUpdate = null;
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            moduleToUpdate = unitOfWork.getModuleRepository().getModule(moduleIdToUpdate);
            if (!moduleCodeToUpdate.equals("")) {
                moduleToUpdate.setModuleCode(moduleCodeToUpdate);
            }
            if (!moduleTitleToUpdate.equals("")) {
                moduleToUpdate.setTitle(moduleTitleToUpdate);
            }
            unitOfWork.getModuleRepository().updateModule(moduleToUpdate);
            unitOfWork.commit();
        } catch (CommandException e) {
            throw e;
        } catch (EntityDoesNotExistException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_MODULE_ID, moduleIdToUpdate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, moduleToUpdate.getModuleCode(),
            moduleToUpdate.getTitle()));
    }
}
