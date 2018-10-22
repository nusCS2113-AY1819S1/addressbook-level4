package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.planner.model.Model.PREDICATE_SHOW_ALL_RECORDS;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Logger;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.Messages;
import seedu.planner.commons.util.ExcelUtil;
import seedu.planner.commons.util.FileUtil;
import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
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
            + "The file will be named in format: Financial_Planner_STARTDATE_ENDDATE.\n"
            + "Parameters: START_DATE END_DATE, START_DATE should be equal to or smaller than END_DATE.\n"
            + "Example 1: " + COMMAND_WORD + " 31-03-1999 31-3-2019\n"
            + "Example 2: " + COMMAND_WORD;

    private final Date startDate;
    private final Date endDate;

    private final Predicate<Record> predicate;
    private Logger logger = LogsCenter.getLogger(ExportExcelCommand.class);

    public ExportExcelCommand() {
        startDate = null;
        endDate = null;
        predicate = PREDICATE_SHOW_ALL_RECORDS;
    }

    public ExportExcelCommand(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.predicate = new DateIsWithinIntervalPredicate(startDate, endDate);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(this);
        model.updateFilteredRecordList(predicate);

        List<Record> recordList = model.getFilteredRecordList();
        String nameFile = ExcelUtil.setNameExcelFile(startDate, endDate);
        logger.info(nameFile);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(nameFile);

        ExcelUtil.writeDataIntoExcelSheet(ExcelUtil.exportData(recordList), sheet);

        String path = "D:\\Important things\\NUS\\MODULES\\CS2113T"
                + System.getProperty("file.separator")
                + String.format(nameFile);
        path.replace("\\", System.getProperty("file.separator"));
        FileUtil.writeWorkBookInFileSystem(nameFile, workbook, path);
        return new CommandResult(String.format(Messages.MESSAGE_EXCEL_FILE_WRITTEN_SUCCESSFULLY, nameFile, path));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportExcelCommand // instanceof handles nulls
                && predicate.equals(((ExportExcelCommand) other).predicate)); // state check
    }
}
