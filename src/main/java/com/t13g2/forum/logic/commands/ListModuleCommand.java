package com.t13g2.forum.logic.commands;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.Module;
import com.t13g2.forum.storage.forum.UnitOfWork;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class ListModuleCommand extends Command{
    public static final String COMMAND_WORD = "listModule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all the modules in the forum book.\n"
            + "Example: " + COMMAND_WORD + " ";

    public static final String MESSAGE_SUCCESS = "Listed all modules";
    public static String MESSAGE;

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        try(UnitOfWork unitOfWork = new UnitOfWork()){
            List<Module> moduleList=unitOfWork.getModuleRepository().getAllModule();
            for(Module module : moduleList){
                MESSAGE = module.getModuleCode() + ": " + module.getTitle() + "\n";
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, MESSAGE));
    }

}
