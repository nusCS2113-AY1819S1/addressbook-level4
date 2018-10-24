package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.EmployeeParser;
import seedu.address.logic.parser.ManagerParser;
import seedu.address.logic.parser.ProManageParser;
import seedu.address.model.Model;

/**
 * Login the person into ProManage
 */
public class LoginCommand extends Command {
    private static final String KEY_MANAGER = "manager";
    private static final String KEY_EMPLOYEE = "employee";
    private static final String MESSAGE_INVALID_LOGIN = "Login identity should be either the following:"
            + "\nmanager\nempolyee";
    private static final String MESSAGE_SUCCESS = "Successfully login as %s";

    private final String loginIdentity;

    public LoginCommand(String commandWord) {
        this.loginIdentity = commandWord;
    }

    public ProManageParser getParser() throws CommandException {
        switch (loginIdentity) {
        case KEY_MANAGER:
            return new ManagerParser();
        case KEY_EMPLOYEE:
            return new EmployeeParser();
        default:
            throw new CommandException(MESSAGE_INVALID_LOGIN);
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(String.format(MESSAGE_SUCCESS, loginIdentity));
    }
}
