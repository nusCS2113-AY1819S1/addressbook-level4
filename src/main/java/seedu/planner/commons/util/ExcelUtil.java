package seedu.planner.commons.util;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.Messages;
import seedu.planner.logic.parser.ArgumentMultimap;
import seedu.planner.logic.parser.ArgumentTokenizer;
import seedu.planner.logic.parser.ParserUtil;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.DirectoryPath;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.MoneyFlow;
import seedu.planner.model.record.Name;
import seedu.planner.model.record.Record;
import seedu.planner.model.tag.Tag;

/**
 * Transfer data into Excel file utilities.
 */
public class ExcelUtil {
    private static final int FIRST_COLUMN = 0;
    private static final int SECOND_COLUMN = 1;
    private static final int THIRD_COLUMN = 2;
    private static final int FOURTH_COLUMN = 3;
    private static final int MAXIMUM_GAP_BETWEEN_COLUMN = 4;
    private static final int LEFT_OUT_CHARACTER = 4;
    private static final int STARTING_INDEX = 0;
    private static final int STARTING_ROW_DATA = 1;
    private static final int STARTING_SHEET = 0;
    private static final int RECORD_EMPTY = 0;
    private static final Double CHANGE_TO_DOUBLE = 1.0;
    private static final char MINUS_SIGN = '-';
    private static final String PLUS_SIGN = "+";
    private static final String WHITE_SPACE = " ";
    private static final String NAME_TITLE = "NAME";
    private static final String DATE_TITLE = "DATE";
    private static final String MONEY_TITLE = "MONEY SPENT/RECEIVED";
    private static final String TAG_TITLE = "TAGS";
    public static final String TAG_SEPARATOR = " ... ";

    private static Logger logger = LogsCenter.getLogger(ExcelUtil.class);

    //==========================================MAIN METHOD============================================================

    /**
     * Read the Excel file.
     */
    public static List<Record> readExcelSheet (String filePath) throws ParseException {
        try {
            if (!DirectoryPath.isValidFilePath(filePath)) {
                throw new ParseException(Messages.MESSAGE_UNREALISTIC_DIRECTORY);
            }
            FileInputStream file = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            List<Record> records = new ArrayList<>();
            workbook.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            for (int sn = STARTING_SHEET; sn < workbook.getNumberOfSheets(); sn++) {
                XSSFSheet sheet = workbook.getSheetAt(sn);
                for (int rn = sheet.getFirstRowNum() + STARTING_ROW_DATA; rn <= sheet.getLastRowNum(); rn++) {
                    Row row = sheet.getRow(rn);
                    if (row != null) {
                        if (row.getLastCellNum() - row.getFirstCellNum() > MAXIMUM_GAP_BETWEEN_COLUMN) {
                            throw new ParseException(Messages.MESSAGE_INVALID_ENTRY_EXCEL_FILE);
                        }
                        records.add(retrieveDataForEachRow(row));
                    }
                }
            }
            return records;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParseException(Messages.MESSAGE_INVALID_ENTRY_EXCEL_FILE);
        }
    }

    /**
     * Write the excel sheet into Directory.
     */
    public static void writeExcelSheetIntoDirectory (List<Record> recordList, XSSFSheet sheet,
                                                       XSSFWorkbook workbook, String path, String nameFile) {
        writeDataIntoExcelSheet(recordList, sheet);
        try {
            //Write the workbook in file system
            path += (System.getProperty("file.separator") + nameFile);
            FileOutputStream out = new FileOutputStream(path);
            workbook.write(out);
            out.close();
            readExcelSheet(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //==========================================SUB METHOD=============================================================
    //TODO TOMORROW: check whether the first row detected is name, date, money, tags or not
    //TODO: coloring the over-limit records.
    //TODO: create a summary for the records on the excel file in a different sheet.
    //TODO: fix all the Check style.
    //TODO: Achieve all the wanted records.

    //private static Boolean isFirstRowTitleExist();

    /**
     * Retrieve Date on 1 row and change them into String.
     */
    private static Record retrieveDataForEachRow (Row row) throws ParseException {
        String nameString = null;
        String dateString = null;
        String tagsString = null;
        String moneyString = null;
        for (int cn = row.getFirstCellNum(); cn < row.getLastCellNum(); cn++) {
            Cell cell = row.getCell(cn);
            if (cell.getColumnIndex() == row.getFirstCellNum() + FIRST_COLUMN) {
                nameString = cell.getStringCellValue().trim();
            } else if (cell.getColumnIndex() == row.getFirstCellNum() + SECOND_COLUMN) {
                dateString = cell.getStringCellValue().trim();
            } else if (cell.getColumnIndex() == row.getFirstCellNum() + THIRD_COLUMN) {
                if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                    moneyString = Double.toString(cell.getNumericCellValue() * CHANGE_TO_DOUBLE);
                } else {
                    moneyString = cell.getStringCellValue();
                }
            } else if (cell != null && cell.getColumnIndex() == row.getFirstCellNum() + FOURTH_COLUMN) {
                tagsString = cell.getStringCellValue().trim();
            }
        }
        if (nameString == null || dateString == null || moneyString == null) {
            throw new ParseException(Messages.MESSAGE_INVALID_ENTRY_EXCEL_FILE);
        }
        // For positive number, the "+" will be discarded when you try to add money into Financial Planner --> error.
        moneyString = (moneyString.charAt(STARTING_INDEX) == MINUS_SIGN) ? (moneyString) : (PLUS_SIGN + moneyString);
        logger.info(nameString + WHITE_SPACE + dateString + WHITE_SPACE + moneyString + WHITE_SPACE + tagsString);
        return createRecord(nameString, dateString, moneyString, tagsString);
    }

    /**
     * Write the map of data into Excel sheet.
     */
    public static void writeDataIntoExcelSheet (List<Record> recordList, XSSFSheet sheet) {
        Map<String, Object[]> data = exportData(recordList);
        Set<String> keySet = data.keySet();
        for (String key : keySet) {
            Row row = sheet.createRow(Integer.parseInt(key));
            Object[] objects = data.get(key);
            int col = STARTING_INDEX;
            for (Object object : objects) {
                Cell cell = row.createCell(col++);
                if (object instanceof String) {
                    cell.setCellValue((String) object);
                } else {
                    cell.setCellValue((Double) object);
                }
            }
        }
    }

    /**
     * Export the records into map of data.
     */
    public static final Map<String, Object[]> exportData (List<Record> recordList) {
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        int id = STARTING_INDEX;
        data.put(String.valueOf(id), new Object[]{NAME_TITLE, DATE_TITLE, MONEY_TITLE, TAG_TITLE});
        for (Record record : recordList) {
            StringBuilder stringBuilder = new StringBuilder();
            if (record.getTags().size() > RECORD_EMPTY) {
                for (Tag tag : record.getTags()) {
                    stringBuilder.append(tag.tagName + TAG_SEPARATOR);
                }
                data.put(String.valueOf(++id), new Object[]{
                            record.getName().fullName,
                            record.getDate().value,
                            record.getMoneyFlow().valueDouble,
                            stringBuilder.toString()
                                .substring(STARTING_INDEX, stringBuilder.toString().length() - LEFT_OUT_CHARACTER)});
            } else {
                data.put(String.valueOf(++id), new Object[]{
                        record.getName().fullName,
                        record.getDate().value,
                        record.getMoneyFlow().valueDouble});
            }
        }
        return data;
    }

    /**
     * Create a records with data given.
     */
    private static Record createRecord(String nameString, String dateString, String moneyString, String tagsString)
            throws ParseException {
        Name nameParse = ParserUtil.parseName(nameString);
        Date dateParse = ParserUtil.parseDate(dateString);
        MoneyFlow moneyFlow = ParserUtil.parseMoneyFlow(moneyString);
        Set<Tag> tagList = new HashSet<>();
        if (tagsString != null) {
            String processedTags = tagsString.replace(TAG_SEPARATOR, WHITE_SPACE + PREFIX_TAG);
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(processedTags, PREFIX_TAG);
            tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        }
        return new Record(nameParse, dateParse, moneyFlow, tagList);
    }

    /**
     * Set the name for the Excel file based on type of inputs.
     */
    public static String setNameExcelFile (Date startDate, Date endDate) {
        return (startDate == null && endDate == null)
                ? "Financial_Planner_ALL.xlsx"
                : ((startDate.equals(endDate))
                ? String.format("Financial_Planner_%1$s.xlsx", startDate.getValue())
                : String.format("Financial_Planner_%1$s_%2$s.xlsx", startDate.getValue(), endDate.getValue()));
    }
}
