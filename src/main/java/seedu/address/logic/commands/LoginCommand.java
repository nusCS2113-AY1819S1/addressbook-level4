package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.password.Password;
import seedu.address.model.schedule.Schedule;
import seedu.address.session.SessionManager;

//@@author jylee-git
/**
 * Enables the use of application by logging in using the specified personnel's NRIC and password.
 */
public class LoginCommand extends Command {
    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enables the use of the application by logging in"
            + " using the specified personnel's NRIC and password.\n"
            + "Parameters: " + PREFIX_NRIC + "NRIC " + PREFIX_PASSWORD + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NRIC + "S9724688J "
            + PREFIX_PASSWORD + "NeUeR2018";

    public static final String INVALID_LOGIN_CREDENTIALS = "Login failed. Incorrect NRIC and/or password.";
    public static final String LOGIN_SUCCESS = "Login successful";
    public static final String ALREADY_LOGGED_IN = "You are already logged in. Logout first before logging in again.";

    private Nric loginNric;
    private Password loginPassword;

    public LoginCommand(Nric loginNric, Password loginPassword) {
        requireNonNull(loginNric);
        requireNonNull(loginPassword);

        this.loginNric = loginNric;
        this.loginPassword = loginPassword;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        SessionManager sessionManager = SessionManager.getInstance(model);

        //This method will throw CommandException if user is already logged in or user has typed in invalid credentials.
        sessionManager.loginToSession(loginNric, loginPassword);

        // Retrieves the user schedule once the user is logged in successfully
        String scheduleString;
        try {
            Schedule schedule = sessionManager.getLoggedInPersonDetails().getSchedule();
            scheduleString = schedule.toString();
        } catch (NullPointerException e) {
            scheduleString = "No schedule allocated.";
        }
        String introduction = "\nWelcome " + sessionManager.getLoggedInPersonDetails().getName().toString() + "\n"
                + scheduleString;

        return new CommandResult(String.format(LOGIN_SUCCESS,
                sessionManager.getLoggedInSessionNric()) + introduction);
    }
}
