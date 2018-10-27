package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DIR;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;

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
            + "You MUST include your directory path, starting with prefix: " + PREFIX_DIR + "\n"
            + "If record has already existed in the Financial Planner, it wil be ignored.\n"
            + Messages.MESSAGE_INVALID_ENTRY_EXCEL_FILE
            + "Parameters: (FILE_PATH) or (DIRECTORY PATH and FILE NAME)\n"
            + "Example 1: " + COMMAND_WORD + " " + PREFIX_DIR
            + DirectoryPath.HOME_DIRECTORY_STRING + DirectoryPath.FILE_SEPERATOR + "Book1.xlsx (MUST end with .xlsx)\n"
            + "Example 2: " + COMMAND_WORD + " " + PREFIX_DIR
            + DirectoryPath.HOME_DIRECTORY_STRING + " " + PREFIX_NAME + " Book1\n";
    private String directoryPath;
    private Logger logger = LogsCenter.getLogger(ImportExcelCommand.class);

    public ImportExcelCommand(String directoryPath) {
        this.directoryPath = directoryPath;
    }
    @Override
    public CommandResult execute (Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(this);
        logger.info(directoryPath);
        String filePath = directoryPath;
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
