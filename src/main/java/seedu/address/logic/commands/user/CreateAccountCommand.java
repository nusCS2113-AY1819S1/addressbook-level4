package seedu.address.logic.commands.user;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHENTICATION_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.commons.core.LoginInfo;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.user.manager.ManagerModel;


/**
 * Adds a person to the address book.
 */
public class CreateAccountCommand extends Command {

    public static final String COMMAND_WORD = "createAccount";
    public static final String MESSAGE_DUPLICATE_USERNAME = "This userName already exists";
    public static final String MESSAGE_USAGE = COMMAND_WORD
                                                + "Parameters: "
                                                + PREFIX_USERNAME + "username: "
                                                + PREFIX_PASSWORD + "Password: "
                                                + PREFIX_AUTHENTICATION_LEVEL + "authentication level: ";

    public static final String MESSAGE_SUCCESS = "New account has been created";
    private final LoginInfo newAccount;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */

    public CreateAccountCommand (LoginInfo newAccount) {
        requireAllNonNull (newAccount);

        this.newAccount = newAccount;
    }


    @Override
    public CommandResult execute (Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        assert model instanceof ManagerModel;

        ManagerModel managerModel = (ManagerModel) model;

        if (managerModel.isUserNameExist (newAccount.getUserName ())) {
            throw new CommandException (MESSAGE_DUPLICATE_USERNAME);
        }

        managerModel.createNewAccount (newAccount);
        return new CommandResult(MESSAGE_SUCCESS);
    }


}
