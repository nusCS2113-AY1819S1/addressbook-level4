package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.account.Account;
import seedu.address.model.item.Item;

/**
 * Logs in account into Stock List.
 */

public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs in account into Stock List. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "jalil "
            + PREFIX_PASSWORD + "dontworrybrother";

    public static final String MESSAGE_SUCCESS = "Signed in: %1$s";
    public static final String MESSAGE_FAIL = "Wrong username/password. Try again";

    private final Account toLogin;

    /**
     * Creates an LoginCommand to add the specified {@code Account}
     */
    public LoginCommand(Account account) {
        requireNonNull(account);
        toLogin = account;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        String username = toLogin.getUsername().toString();
        String password = toLogin.getPassword().toString();

        // NEEDS WORK
        if (!(username.equals("admin")) || !(password.equals("faggot69"))) {
            throw new CommandException(MESSAGE_FAIL);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toLogin));
    }

}
