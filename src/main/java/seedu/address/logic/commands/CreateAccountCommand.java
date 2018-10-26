package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.login.LoginDetails;

/**
 * Adds an account to the login book
 */
public class CreateAccountCommand extends Command {

    public static final String COMMAND_WORD = "createaccount";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates an account for the login book. "
            + "Parameters: USERID PASSWORD ROLE\n"
            + "Example: " + COMMAND_WORD + " A1234567M zaq1xsw2cde3 treasurer";


    public static final String MESSAGE_SUCCESS = "New account created: %1$s";
    public static final String MESSAGE_DUPLICATE_ACCOUNT = "This account already exists in the login book";

    private final LoginDetails toAdd;

    /**
     * Creates a CreateAccountCommand to add the specified {@code LoginDetails}
     */
    public CreateAccountCommand(LoginDetails loginDetails) {
        requireNonNull(loginDetails);
        this.toAdd = loginDetails;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasAccount(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACCOUNT);
        }

        model.createAccount(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateAccountCommand // instanceof handles nulls
                && toAdd.equals(((CreateAccountCommand) other).toAdd));
    }
}
