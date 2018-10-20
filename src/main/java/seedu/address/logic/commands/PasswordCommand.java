//@@author lws803
package seedu.address.logic.commands;

import seedu.address.commons.exceptions.FileEncryptorException;
import seedu.address.commons.util.FileEncryptor;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;

/**
 * Encrypts the XML data using a password and returns a message
 * Message will be displayed on CommandResult
 */
public class PasswordCommand extends Command {
    public static final String COMMAND_WORD = "password";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Hashes file using password.\n"
            + "Parameters: KEYWORD PASSWORD...\\n"
            + "Example: " + COMMAND_WORD + " myPassword";

    public static final String MESSAGE_ENCRYPT_SUCCESS = "File encrypted!";
    public static final String MESSAGE_DECRYPT_SUCCESS = "File decrypted!";

    private String password;
    private FileEncryptor fe;

    /**
     * Executes the FileEncryptor and obtains a message
     * @param credentials will be obtained from parser
     */
    public PasswordCommand (String[] credentials) {
        UserPrefs userPref = new UserPrefs();
        fe = new FileEncryptor(userPref.getAddressBookFilePath().toString());
        this.password = credentials[0];
    }

    @Override
    public CommandResult execute (Model model, CommandHistory history) throws CommandException {

        String message;
        try {
            message = fe.process(this.password);
        } catch (FileEncryptorException fex) {
            return new CommandResult(fex.getLocalizedMessage());
        }

        model.reinitAddressbook();
        model.getTextPrediction().reinitialise();

        if (message == FileEncryptor.MESSAGE_DECRYPTED) {
            return new CommandResult(MESSAGE_DECRYPT_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_ENCRYPT_SUCCESS);
        }
    }

}
