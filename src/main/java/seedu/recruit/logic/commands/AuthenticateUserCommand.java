package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.logic.ChangeLogicStateEvent;
import seedu.recruit.commons.events.logic.UserAuthenticatedEvent;
import seedu.recruit.logic.CommandHistory;

import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;

/**
 * Verifies the identity of owner with password hash saved in preferences.json
 */


public class AuthenticateUserCommand extends Command {
    public static final String MESSAGE_USAGE = "RecruitBook is password-protected.\n"
                                                + "Enter administrator password to continue.";

    public static final String MESSAGE_SUCCESS = "Password verified. Welcome to RecruitBook!";
    public static final String MESSAGE_WRONG_PASSWORD = "You've entered an invalid password.\n"
                                                        + "Please try again.";
    private static int numTries = 0;

    private final String enteredPassword;

    public AuthenticateUserCommand(String userInput) {
        enteredPassword = userInput;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs) throws CommandException {
        requireNonNull(model);
        numTries++;
        if (enteredPassword.equals(userPrefs.getHashedPassword())) {
            EventsCenter.getInstance().post(new UserAuthenticatedEvent());
            EventsCenter.getInstance().post(new ChangeLogicStateEvent("primary"));

            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_WRONG_PASSWORD);
        }
    }
}
