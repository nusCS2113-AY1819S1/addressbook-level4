package seedu.address.logic.commands;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.model.prioritylevel.PriorityLevel;
import seedu.address.model.prioritylevel.PriorityLevelEnum;
import seedu.address.session.SessionManager;

//@@author jylee-git
/**
 * Resets the application, deleting data/addressBook.xml,
 *  then exits the program.
 */
public class ResetCommand extends Command {
    public static final String COMMAND_WORD = "reset";

    /**
     * Forcefully deletes data/addressBook.xml and calls exit program.
     * This should return exitCommand's commandResult as the app will exit upon reset.
     */
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        SessionManager sessionManager = SessionManager.getInstance(model);
        if (!sessionManager.isLoggedIn()) {
            throw new CommandException(SessionManager.NOT_LOGGED_IN);
        }
        if (!sessionManager.containsAnyOfThesePriorityLevels(PriorityLevelEnum.IT_UNIT)) {
            throw new CommandException(String.format(PriorityLevel.NOTOF_CERTAIN_PRIORITYLEVEL,
                    PriorityLevelEnum.IT_UNIT));
        }
        Path addressBookPath = new UserPrefs().getAddressBookFilePath();
        Path leaveListPath = new UserPrefs().getLeaveListFilePathFilePath();
        File addressBookFile = new File(addressBookPath.toUri());
        File leaveListFile = new File(leaveListPath.toUri());
        addressBookFile.deleteOnExit();
        leaveListFile.deleteOnExit();

        Logger logger = LogsCenter.getLogger(ResetCommand.class);
        logger.info("AddressBook has been reset. Exiting application now.");

        return new ExitCommand().execute(model, commandHistory);
    }
}
