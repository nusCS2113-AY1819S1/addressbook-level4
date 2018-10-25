package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTH_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.user.User;
import seedu.address.model.user.UserManager;
import seedu.address.ui.HtmlTableProcessor;


/**
 * Handles logins within Trajectory.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs you into Trajectory. "
            + "Parameters: "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_AUTH_PASSWORD + "PASSWORD "
            + "\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_EMAIL + "megannicole@nus.edu.sg "
            + PREFIX_AUTH_PASSWORD + "password ";

    public static final String MESSAGE_AUTH_SUCCESS = "You has successfully logged in.";
    public static final String MESSAGE_AUTH_FAILURE = "Your login attempt has failed. Please try again later.";

    private User internalUser;
    /**
     * Creates an StudentAddCommand to add the specified {@code Person}
     */
    public LoginCommand(User user) {
        internalUser = user;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (UserManager.getInstance().authenticate(internalUser)) {
            return new CommandResult(MESSAGE_AUTH_SUCCESS + "\n"
                    + "", HtmlTableProcessor.renderCard(MESSAGE_AUTH_SUCCESS));
        } else {
            return new CommandResult(MESSAGE_AUTH_FAILURE + "\n"
                    + "", HtmlTableProcessor.renderCard(MESSAGE_AUTH_FAILURE));
        }


    }
}
