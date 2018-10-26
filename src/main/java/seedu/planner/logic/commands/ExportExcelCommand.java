package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.planner.model.Model.PREDICATE_SHOW_ALL_RECORDS;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.Messages;
import seedu.planner.commons.util.ExcelUtil;
import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.DirectoryPath;
import seedu.planner.model.Model;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;
import seedu.planner.model.record.Record;

/**
 * Export the data of the records within specific period.
 */
public class ExportExcelCommand extends Command {
    public static final String COMMAND_WORD = "export_excel";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the records within specific period or all records in the Financial Planner into Excel file .\n"
            + "Parameters: START_DATE END_DATE DIRECTORY_PATH,START_DATE should be equal to or smaller than END_DATE.\n"
            + "You can specifically type what you want to conform. Date/period start with d/ "
            + "and Directory path start with dir/.\n"
            + "Example 1: You want to set Directory: "
            + COMMAND_WORD + " dir/" + DirectoryPath.HOME_DIRECTORY_STRING + "\n"
            + "Example 2: You want to set records have only 1 date: " + COMMAND_WORD + " d/31-3-1999\n"
            + "Example 3: You want to set records whose date lies within the period: "
            + COMMAND_WORD + "d/31-3-1999 31-3-2019\n"
            + "Example 4: You want to export all the records in the Financial Planner: " + COMMAND_WORD + "\n"
            + "Example 5: You want to export records lies within the period and set specific Directory: "
            + COMMAND_WORD + "d/31-3-1999 31-3-2019" + " dir/" + DirectoryPath.HOME_DIRECTORY_STRING + "\n";
    private final Date startDate;
    private final Date endDate;
    private final String directoryPath;
    private final Predicate<Record> predicate;
    private Logger logger = LogsCenter.getLogger(ExportExcelCommand.class);

    public ExportExcelCommand() {
        this.startDate = null;
        this.endDate = null;
        this.directoryPath = DirectoryPath.HOME_DIRECTORY_STRING;
        this.predicate = PREDICATE_SHOW_ALL_RECORDS;
    }

    public ExportExcelCommand(String directoryPath) {
        this.startDate = null;
        this.endDate = null;
        this.directoryPath = directoryPath;
        this.predicate = PREDICATE_SHOW_ALL_RECORDS;
    }

    public ExportExcelCommand(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.directoryPath = DirectoryPath.HOME_DIRECTORY_STRING;
        this.predicate = new DateIsWithinIntervalPredicate(startDate, endDate);
    }

    public ExportExcelCommand(Date startDate, Date endDate, String directoryPath) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.directoryPath = directoryPath;
        this.predicate = new DateIsWithinIntervalPredicate(startDate, endDate);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        String messageResult;
        requireNonNull(this);
        model.updateFilteredRecordList(predicate);

        List<Record> recordList = model.getFilteredRecordList();
        String nameFile = ExcelUtil.setNameExcelFile(startDate, endDate);
        logger.info(nameFile);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(nameFile);

        logger.info("START DATE : " + startDate + " END DATE: " + endDate);
        if (recordList.size() > 0) {
            logger.info("NUMBER OF RECORDS: " + recordList.size());
            logger.info("directory path: " + directoryPath);
            logger.info("NAME FILE: " + nameFile);
            ExcelUtil.writeExcelSheetIntoDirectory(
                    recordList, sheet, workbook, directoryPath, nameFile);
            messageResult = String.format(Messages.MESSAGE_EXCEL_FILE_WRITTEN_SUCCESSFULLY,
                    nameFile, directoryPath);
        } else {
            messageResult = Messages.MESSAGE_NO_RECORDS_TO_EXPORT;
        }
        return new CommandResult(messageResult);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportExcelCommand // instanceof handles nulls
                && predicate.equals(((ExportExcelCommand) other).predicate)); // state check
    }
}
