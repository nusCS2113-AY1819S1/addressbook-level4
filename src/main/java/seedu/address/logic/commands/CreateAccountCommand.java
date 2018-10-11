package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERPASSWORD;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.login.LoginDetails;

/**
 * Adds an account to the login book
 */
public class CreateAccountCommand extends Command {

    public static final String COMMAND_WORD = "createaccount";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates an account for the address book. "
            + "Parameters: "
            + PREFIX_USERID + "USERID "
            + PREFIX_USERPASSWORD + "PASSWORD "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERID + "A1234567M "
            + PREFIX_USERPASSWORD + "zaq1xsw2cde3";

    public static final String MESSAGE_SUCCESS = "New account created: %1$s";
    private static final String MESSAGE_DUPLICATE_ACCOUNT = "This account already exists in the address book";

    private final LoginDetails toAdd;

    /**
     * Creates a CreateAccountCommand to add the specified {@code LoginDetails}
     */
    public CreateAccountCommand(LoginDetails details) {
        requireNonNull(details);
        toAdd = details;
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
