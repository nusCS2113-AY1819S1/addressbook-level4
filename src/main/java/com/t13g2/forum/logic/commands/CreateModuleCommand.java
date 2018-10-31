package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
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
public class CreateModuleCommand extends Command {
    public static final String COMMAND_WORD = "createModule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a module. "
        + "Parameters: "
        + PREFIX_MODULE_CODE + "MODULE CODE "
        + PREFIX_MODULE_TITLE + "MODULE TITLE "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_MODULE_CODE + "CS2113 "
        + PREFIX_MODULE_TITLE + "Software Engineering and OOP";

    public static final String MESSAGE_SUCCESS = "%1$s now is added to module";
    public static final String MESSAGE_DUPLICATE_MODULE = "%1$s has been added previously";

    private final Module moduleToAdd;

    /**
     * Creates an CreateModuleCommand to create the specified {@code module}.
     */
    public CreateModuleCommand(Module module) {
        requireNonNull(module);
        moduleToAdd = module;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        // if user has not login or is not admin, then throw exception
        try {
            if (!Context.getInstance().isCurrentUserAdmin()) {
                throw new CommandException(User.MESSAGE_NOT_ADMIN);
            }
        } catch (CommandException e) {
            throw e;
        }

        boolean exist = true;
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            unitOfWork.getModuleRepository().getModuleByCode(moduleToAdd.getModuleCode());
            throw new CommandException(String.format(MESSAGE_DUPLICATE_MODULE,
                moduleToAdd.getModuleCode()));
        } catch (CommandException e) {
            throw e;
        } catch (EntityDoesNotExistException e) {
            exist = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!exist) {
            try (UnitOfWork unitOfWork = new UnitOfWork()) {
                moduleToAdd.setCreatedByUserId(Context.getInstance().getCurrentUser().getId());
                unitOfWork.getModuleRepository().addModule(moduleToAdd);
                unitOfWork.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, moduleToAdd.getModuleCode()));
    }
}
