package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.charts.LineChartData;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.Messages;
import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.Model;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;
import seedu.planner.model.record.Record;

/**
 * Export the data of the records within specific period.
 */
public class ExportExcelCommand extends Command {
    public static final String COMMAND_WORD = "export_excel";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Export the records data into Excel file within specific period.\n"
            + "The file will be named in format: Financial_Planner_STARTDATE_ENDDATE.\n"
            + "Parameters: START_DATE END_DATE, START_DATE should be equal to or smaller than END_DATE.\n"
            + "Example: " + COMMAND_WORD + " 31-03-1999 31-3-2018\n";

    private final DateIsWithinIntervalPredicate predicate;
    private Logger logger = LogsCenter.getLogger(ExportExcelCommand.class);

    public ExportExcelCommand(DateIsWithinIntervalPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(this);
        model.updateFilteredRecordList(predicate);
        List<Record> recordList = model.getFilteredRecordList();
        String nameFile = String.format("Financial_Planner_%1$s_%2$s.xlsx",
                predicate.getStartDate().getValue(), predicate.getEndDate().getValue());
        logger.info(nameFile);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(nameFile);
        writeDataIntoExcelSheet(exportData(recordList), sheet);
        writeWorkBookInFileSystem(nameFile, workbook);
        return new CommandResult(String.format(Messages.MESSAGE_EXCEL_FILE_WRITTEN_SUCCESSFULLY, nameFile));
    }

    /**
     * Export data of records into Map.
     * @param recordList list of records.
     * @return a map containing the ID for each record, name, date, money spent/received.
     */
    public Map<String, Object[]> exportData (List<Record> recordList) {
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        int id = 0;
        data.put(String.valueOf(id++), new Object[] {"NAME", "DATE", "MONEY SPENT/RECEIVED"});
        for (Record record : recordList) {
            data.put(String.valueOf(id++), new Object[] {
                    record.getName().fullName,
                    record.getDate().value,
                    record.getMoneyFlow().valueDouble});
            logger.info(String.format("The record is: %1$s %2$s %3$s, ID = %4$d",
                    record.getName().fullName, record.getDate().value, record.getMoneyFlow().value, id));
        }
        logger.info(String.format("The records have: %1$d records.\n", recordList.size()));
        logger.info(String.format("The data map have: %1$d\n", data.size()));
        return data;
    }

    /**
     * Write the data into Excel sheet
     * @param data
     * @param sheet the Excel sheet which have
     */
    public void writeDataIntoExcelSheet (Map<String, Object[]> data, XSSFSheet sheet) {
        Set<String> keySet = data.keySet();
        logger.info(String.format("The key set have: %1$d\n", keySet.size()));
        int rowNum = 0;
        for (String key : keySet) {
            Row row = sheet.createRow(rowNum++);
            Object[] objects = data.get(key);
            int cellNum = 0;
            for (Object object : objects) {
                Cell cell = row.createCell(cellNum++);
                if (object instanceof String) {
                    cell.setCellValue((String) object);
                } else {
                    cell.setCellValue((Double) object);
                }
            }
            logger.info(String.format("Row num = %1$d, cell num = %1$d\n", rowNum, cellNum));
        }
    }

    /**
     * Write the Excel workbook in the file system, or you can customise the location of the Excel workbook.
     * @param fileName name of the file
     * @param workbook the Excel Workbook we want to produce.
     */
    public void writeWorkBookInFileSystem (String fileName, XSSFWorkbook workbook) {
        try {
            FileOutputStream fileOut = new FileOutputStream(new File(fileName));
            workbook.write(fileOut);
            fileOut.close();
            System.out.println(String.format("%1$s is written successfully.\n", fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportExcelCommand // instanceof handles nulls
                && predicate.equals(((ExportExcelCommand) other).predicate)); // state check
    }
}
