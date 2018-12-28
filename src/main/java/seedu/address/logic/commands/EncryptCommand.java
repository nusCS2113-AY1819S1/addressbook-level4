package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Toggles the encryption function of Health Book
 */
public class EncryptCommand extends Command {

    public static final String COMMAND_WORD = "encrypt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggles data-encryption.\n";

    public static final String SHOWING_ENCRYPTION_TOGGLED = "Encryption has been toggled on.";

    public static final String SHOWING_ENCRYPTION_UNTOGGLED = "Encryption has been toggled off.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        model.getUserPrefs().toggleEncryption();

        if (model.getUserPrefs().getEncryption()) {
            return new CommandResult(SHOWING_ENCRYPTION_TOGGLED);
        } else {
            return new CommandResult(SHOWING_ENCRYPTION_UNTOGGLED);
        }
    }
}
