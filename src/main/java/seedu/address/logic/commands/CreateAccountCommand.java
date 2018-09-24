package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERPASSWORD;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.LoginDetails;
import seedu.address.model.Model;
import seedu.address.model.UserId;
import seedu.address.model.UserPassword;
import seedu.address.ui.LoginDialogBoxUserIdPassword;

public class CreateAccountCommand extends Command{

    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates an account for the address book. "
            + "Parameters: "
            + PREFIX_USERID + "USERID "
            + PREFIX_USERPASSWORD + "PASSWORD ";

    public static final String MESSAGE_SUCCESS = "New account created: %1$s";
    private static final String MESSAGE_DUPLICATE_ACCOUNT= "This account already exists in the address book";

    private static LoginDetails toAdd;

    /**
     * Creates a CreateAccountCommand to add the specified {@code LoginDetails}
     */
    public CreateAccountCommand(LoginDetails details) {
        UserId id = new UserId(LoginDialogBoxUserIdPassword.getUserId());
        UserPassword password = new UserPassword(LoginDialogBoxUserIdPassword.getUserPassword());
        details.setUserId(id);
        details.setUserPassword(password);
        requireNonNull(details);
        toAdd = details;
    }
    // add JOption input fields to get user details, then assign it to variables to be assigned to toAdd object
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasAccount(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACCOUNT);
        }

        model.createAccount(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}
