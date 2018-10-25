package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.user.User;
import seedu.address.model.user.UserManager;

/**
 * This class is designed for debug use only.
 */
public class DebugCommand extends Command {
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        UserManager um = UserManager.getInstance();
        um.addUser(new User("megannicole@outlook.com", "password" , 1));
        um.saveUserList();
        return new CommandResult("DEBUG COMMAND EXECUTED.");
    }
}
