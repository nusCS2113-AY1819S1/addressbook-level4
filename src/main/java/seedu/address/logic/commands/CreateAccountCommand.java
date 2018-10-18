package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.login.LoginDetails;
import seedu.address.model.login.UserIdContainsKeywordsPredicate;

/**
 * Adds an account to the login book
 */
public class CreateAccountCommand extends Command {

    public static final String COMMAND_WORD = "createaccount";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates an account for the login book. "
            + "Parameters: USERID PASSWORD\n"
            + "Example: " + COMMAND_WORD + " A1234567M zaq1xsw2cde3";

    public static final String MESSAGE_SUCCESS = "New account created: %1$s";
    public static final String MESSAGE_DUPLICATE_ACCOUNT = "This account already exists in the login book";

    private final LoginDetails toAdd;
    private final UserIdContainsKeywordsPredicate idPredicate;

    /**
     * Creates a CreateAccountCommand to add the specified {@code LoginDetails}
     */
    public CreateAccountCommand(UserIdContainsKeywordsPredicate idPredicate, LoginDetails loginDetails) {
        requireNonNull(loginDetails);
        this.idPredicate = idPredicate;
        this.toAdd = loginDetails;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.updateFilteredLoginDetailsList(idPredicate);

        if (model.getFilteredLoginDetailsList().size() != 0) {
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
