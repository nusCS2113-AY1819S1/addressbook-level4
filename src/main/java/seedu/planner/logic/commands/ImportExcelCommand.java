package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.Messages;
import seedu.planner.commons.util.ExcelUtil;
import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.DirectoryPath;
import seedu.planner.model.Model;
import seedu.planner.model.record.Record;

/**
 * Imports all the records given in the Excel file.
 */
public class ImportExcelCommand extends Command {
    public static final String COMMAND_WORD = "import_excel";
    public static final String MESSAGE_USAGE =
            ": Imports all the records data in Excel file and add them into the current Financial Planner.\n"
            + "If record has already existed in the Financial Planner, it wil be ignored.\n"
            + "Parameters: FILE_PATH.\n"
            + "Example: " + COMMAND_WORD + " " + DirectoryPath.HOME_DIRECTORY_STRING + DirectoryPath.FILE_SEPERATOR
            + "Book1.xlsx\n";
    private DirectoryPath directoryPath;
    private Logger logger = LogsCenter.getLogger(ImportExcelCommand.class);

    public ImportExcelCommand(DirectoryPath directoryPath) {
        this.directoryPath = directoryPath;
    }
    @Override
    public CommandResult execute (Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(this);
        logger.info(directoryPath.getDirectoryPath().getDirectoryPathValue());
        String filePath = directoryPath.getDirectoryPath().getDirectoryPathValue();
        try {
            List<Record> records = ExcelUtil.readExcelSheet(filePath);
            model.addListUniqueRecord(records);
            model.commitFinancialPlanner();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommandResult(String.format(Messages.MESSAGE_RECORD_ADDED_SUCCESSFULLY, filePath));
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportExcelCommand // instanceof handles nulls
                && directoryPath.equals(((ImportExcelCommand) other).directoryPath));
    }
}
