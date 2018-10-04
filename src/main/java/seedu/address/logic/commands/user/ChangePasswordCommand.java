package seedu.address.logic.commands.user;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.authentication.PasswordUtils;
import seedu.address.commons.core.CurrentUser;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.LoginInfoList;
import seedu.address.model.Model;
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

    private final String newPassword;
    private final String oldPassword;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public ChangePasswordCommand(String oldPassword, String newPassword) {
        requireNonNull(oldPassword);
        requireNonNull(newPassword);
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }

    public CommandResult execute(LoginInfoList loginInfoList, CommandHistory history) {
        requireNonNull(loginInfoList);

//        if (model.hasPerson(toAdd)) {
//                    throw new CommandException(MESSAGE_DUPLICATE_PERSON);
//        }
//
//        model.addPerson(toAdd);
//        model.commitAddressBook();
        String username = CurrentUser.getUserName ();
        String hashedOldPassword  = loginInfoList.getLoginInfo (username).getPassword ();
        boolean isPasswordCorrect = PasswordUtils.verifyUserPassword (oldPassword, hashedOldPassword);
        if (isPasswordCorrect){
            String newHashedPassword = PasswordUtils.generateSecurePassword (newPassword);
            loginInfoList.changePassword (username, newHashedPassword);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, newPassword));
    }

    @Override
    public CommandResult execute (Model model , CommandHistory history) throws CommandException {
        return null;
    }
}
