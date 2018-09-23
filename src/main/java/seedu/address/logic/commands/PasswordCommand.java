package seedu.address.logic.commands;

import seedu.address.commons.util.FileEncryptor;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

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

    // public static final String MESSAGE_SUCCESS = "Command executed";
    private String message;

    /**
     * Executes the FileEncryptor and obtains a message
     */
    public PasswordCommand (String[] credentials) {
        FileEncryptor fe = new FileEncryptor(credentials[0], "data/addressbook.xml");
        String message = fe.getMessage();

        this.message = message;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {

        return new CommandResult(this.message);
    }

}
