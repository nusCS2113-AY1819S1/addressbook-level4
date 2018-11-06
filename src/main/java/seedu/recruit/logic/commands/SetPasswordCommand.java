package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.storage.UserPrefsChangedEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.LogicManager;
import seedu.recruit.logic.commands.exceptions.CommandException;
import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;

/**
 *  Sets a new password to access RecruitBook
 */

public class SetPasswordCommand extends Command {

    public static final String COMMAND_WORD = "Set Password Interface";

    public static final String MESSAGE_USAGE = "Enter your new password.\n"
                                               + "Enter 'cancel' to exit this interface.";

    public static final String MESSAGE_CONFIRM_PASSWORD = "Renter your new password to confirm change.";

    public static final String MESSAGE_PASSWORD_NOT_SAME = "Passwords entered not the same. Please try again\n";

    public static final String MESSAGE_SUCCESS = "Successfully changed password.";

    private static String newPassword;

    private static int numTries = 0;

    private final String enteredPassword;

    public SetPasswordCommand (String password) {
        enteredPassword = password;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs) throws CommandException {
        requireNonNull(model);
        numTries++;
        if (numTries % 2 == 1) {
            newPassword = enteredPassword;
            return new CommandResult(MESSAGE_CONFIRM_PASSWORD);
        } else if (enteredPassword.equals(newPassword)) {
            userPrefs.setHashedPassword(newPassword);
            LogicManager.setLogicState("primary");
            EventsCenter.getInstance().post(new UserPrefsChangedEvent(userPrefs));

            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_PASSWORD_NOT_SAME + MESSAGE_USAGE);
        }
    }

}
