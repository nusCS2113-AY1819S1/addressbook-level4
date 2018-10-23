package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;
import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.Messages;
import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.Model;
import seedu.planner.model.record.DirectoryPath;

/**
 * Set the directory path for the Excel file.
 */
public class SetPathCommand extends Command {
    public static final String COMMAND_WORD = "set_path";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Set the directory path for Excel file.\n"
            + "Parameter: DIRECTORY_PATH\n"
            + "E.g: " + COMMAND_WORD + " " + DirectoryPath.DEFAULT_HOME_DIRECTORY;
    private DirectoryPath directoryPath;
    private Logger logger = LogsCenter.getLogger(SetPathCommand.class);

    public SetPathCommand(DirectoryPath directoryPath) {
        if (DirectoryPath.isValidDirectory(directoryPath.getDirectoryPath().getDirectoryPathValue())) {
            this.directoryPath = directoryPath;
        } else {
            this.directoryPath = DirectoryPath.HOME_DIRECTORY;
        }
    }
    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(this);
        model.setDirectoryPath(directoryPath.getDirectoryPath().getDirectoryPathValue());
        return new CommandResult(
                String.format(Messages.MESSAGE_SET_DIRECTORY_SUCCESSFULLY,
                        directoryPath.getDirectoryPath().getDirectoryPathValue()));
    }
}
