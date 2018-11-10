package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.commons.events.logic.LoginEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Login Command
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";
    public static final String COMMAND_WORD_ALIAS = "lg";
    public static final String MESSAGE_SUCCESS = "Login has been attempted!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Please enter your username and password."
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "John Doe "
            + PREFIX_PASSWORD + "testpassword ";

    private final String username;
    private final String password;

    public LoginCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        raise(new LoginEvent(username, password));
        return new CommandResult("Login Attempted");
    }
}
