package seedu.address.logic.commands.user;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLD_PASSWORD;

import seedu.address.authentication.PasswordUtils;
import seedu.address.commons.core.CurrentUser;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.user.Password;
import seedu.address.model.user.UserName;

/**
 * Adds a person to the address book.
 */
public class ChangePasswordCommand extends Command {

    public static final String COMMAND_WORD = "changePassword";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": change current user password "
            + "Parameters: "
            + PREFIX_OLD_PASSWORD + "oldPassword "
            + PREFIX_NEW_PASSWORD + "newPassword ";

    public static final String MESSAGE_SUCCESS = "Password has successfully changed to: %1$s";
    public static final String MESSAGE_WRONG_PASSWORD = "THe old password is wrong";
    private final Password newPassword;
    private final Password oldPassword;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public ChangePasswordCommand(Password oldPassword, Password newPassword) {
        requireNonNull(oldPassword);
        requireNonNull(newPassword);
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }

    @Override
    public CommandResult execute (Model model , CommandHistory history) {
        requireNonNull(model);

        UserName username = CurrentUser.getUserName ();
        String hashedOldPassword = model.getLoginInfo (username).getPasswordString ();
        boolean isPasswordCorrect = PasswordUtils.verifyUserPassword (oldPassword.toString (), hashedOldPassword);
        if (isPasswordCorrect) {
            Password newHashedPassword = getHashedPassword();

            model.changePassword (username, newHashedPassword);

        } else {
            return new CommandResult(MESSAGE_WRONG_PASSWORD);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, newPassword));
    }

    private Password getHashedPassword() {
        String hashedPassword = PasswordUtils.generateSecurePassword (newPassword.toString ());
        return new Password (hashedPassword);

    }
}
