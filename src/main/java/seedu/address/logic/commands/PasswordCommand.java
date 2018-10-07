//@@author lws803
package seedu.address.logic.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileEncryptor;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.XmlAddressBookStorage;


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

    private static final Logger logger = LogsCenter.getLogger(PasswordCommand.class);
    private String message;

    /**
     * Executes the FileEncryptor and obtains a message
     * @param credentials will be obtained from parser
     */
    public PasswordCommand (String[] credentials) {
        UserPrefs userPref = new UserPrefs();
        FileEncryptor fe = new FileEncryptor(userPref.getAddressBookFilePath().toString());
        fe.process(credentials[0]);
        String message = fe.getMessage();
        this.message = message;
    }

    @Override
    public CommandResult execute (Model model, CommandHistory history) {
        // TODO: Reset the display locally, do not commit to changes
        Path path = Paths.get("data/addressbook.xml");
        XmlAddressBookStorage storage = new XmlAddressBookStorage(path);
        ReadOnlyAddressBook initialData;
        try {
            initialData = storage.readAddressBook().orElseGet(SampleDataUtil::getSampleAddressBook);
            model.resetData(initialData);
        } catch (IOException ioe) {
            logger.warning(ioe.getMessage());
        } catch (DataConversionException dataE) {
            logger.warning(dataE.getMessage());
        }

        return new CommandResult(this.message);
    }

}
