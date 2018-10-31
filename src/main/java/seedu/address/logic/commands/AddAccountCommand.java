package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.account.Account;

/**
 * Adds an account to the account list.
 */
public class AddAccountCommand extends Command {

    public static final String COMMAND_WORD = "addAccount";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an account to the account database. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "admin "
            + PREFIX_PASSWORD + "admin";


    public static final String MESSAGE_SUCCESS = "New account added: %1$s";
    public static final String MESSAGE_DUPLICATE_ACCOUNT = "This account already exists in the database";

    private final Account toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Item}
     */
    public AddAccountCommand(Account account) {
        requireNonNull(account);
        toAdd = account;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasAccount(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACCOUNT);
        }

        model.addAccount(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAccountCommand // instanceof handles nulls
                && toAdd.equals(((AddAccountCommand) other).toAdd));
    }
}
