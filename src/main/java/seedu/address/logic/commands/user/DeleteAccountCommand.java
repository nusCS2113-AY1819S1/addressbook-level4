package seedu.address.logic.commands.user;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHENTICATION_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.authentication.PasswordUtils;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.user.UserName;
import seedu.address.model.user.manager.ManagerModel;

/**
 * Adds a person to the address book.
 */
public class DeleteAccountCommand extends Command {

    public static final String COMMAND_WORD = "deleteAccount";
    public static final String MESSAGE_USERNAME_DO_NOT_EXIST = "This account do not exists";

    public static final String MESSAGE_SUCCESS = "The account has been deleted";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete current user account "
            + "Parameters: "
            + PREFIX_USERNAME + "username: ";
    private final UserName userName;
    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public DeleteAccountCommand (UserName userName) {
        requireNonNull(userName);

        this.userName = userName;
    }


    @Override
    public CommandResult execute (Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        assert model instanceof ManagerModel;

        ManagerModel managerModel = (ManagerModel) model;

        if (managerModel.isUserNameExist (userName)) {
            managerModel.deleteAccount (userName);
        } else {
            throw new CommandException (MESSAGE_USERNAME_DO_NOT_EXIST);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
