package seedu.planner.commons.util;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Chart;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.charts.AxisCrosses;
import org.apache.poi.ss.usermodel.charts.AxisPosition;
import org.apache.poi.ss.usermodel.charts.ChartAxis;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.ChartLegend;
import org.apache.poi.ss.usermodel.charts.DataSources;
import org.apache.poi.ss.usermodel.charts.LegendPosition;
import org.apache.poi.ss.usermodel.charts.LineChartData;
import org.apache.poi.ss.usermodel.charts.ValueAxis;
import org.apache.poi.ss.util.CellRangeAddress;
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
import seedu.planner.ui.SummaryEntry;

/**
 * Transfer data into Excel file utilities.
 */
public class ExcelUtil {
    private static final int FIRST_COLUMN = 0;
    private static final int SECOND_COLUMN = 1;
    private static final int THIRD_COLUMN = 2;
    private static final int FOURTH_COLUMN = 3;
    private static final int FIRST_ROW = 0;
    private static final int SECOND_ROW = 1;
    private static final int THIRD_ROW = 2;
    private static final int FOURTH_ROW = 3;
    private static final int MAXIMUM_GAP_BETWEEN_COLUMN = 4;
    private static final int LEFT_OUT_CHARACTER = 4;
    private static final int STARTING_INDEX = 0;
    private static final int STARTING_SHEET = 0;
    private static final int RECORD_EMPTY = 0;
    private static final int STARTING_CURRENCY = 2;
    private static final char MINUS_SIGN_CHAR = '-';
    private static final char PLUS_SIGN_CHAR = '+';
    private static final char CURRENCY_CHAR = '$';
    private static final Double CHANGE_TO_DOUBLE = 1.0;
    private static final String PLUS_SIGN_STRING = "+";
    private static final String MINUS_SIGN_STRING = "-";
    private static final String CURRENCY_STRING = "$";
    private static final String WHITE_SPACE = " ";
    private static final String NAME_TITLE = "NAME";
    private static final String DATE_TITLE = "DATE";
    private static final String MONEY_TITLE = "MONEY";
    private static final String TAG_TITLE = "TAGS";
    private static final String INCOME_TITLE = "INCOME";
    private static final String OUTCOME_TITLE = "EXPENSE";
    private static final String TOTAL_MONEY = "TOTAL";
    private static final String TAG_SEPARATOR = " ... ";

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
            FileInputStream targetFile = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(targetFile);
            File filexssf = new File(filePath);
            File filexssfCopy = new File(filePath);
            if (filexssf.renameTo(filexssfCopy)) {
                logger.info("READ EXCEL: FILE CLOSED");
            } else {
                logger.info("READ EXCEL: FILE OPENED");
                throw new ParseException(Messages.MESSAGE_FILE_OPENED);
            }

            boolean isRightSheet = true;

            List<Record> records = new ArrayList<>();

            workbook.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

            for (int sn = STARTING_SHEET; sn < workbook.getNumberOfSheets(); sn++) {
                XSSFSheet sheet = workbook.getSheetAt(sn);
                isRightSheet = true;
                for (int rn = sheet.getFirstRowNum(); rn <= sheet.getLastRowNum() && isRightSheet; rn++) {
                    Row row = sheet.getRow(rn);
                    if (row == null) {
                        continue;
                    }
                    if (row.getLastCellNum() - row.getFirstCellNum() > MAXIMUM_GAP_BETWEEN_COLUMN) {
                        throw new ParseException(Messages.MESSAGE_INVALID_ENTRY_EXCEL_FILE);
                    }
                    String nameString = null;
                    String dateString = null;
                    String tagsString = null;
                    String moneyString = null;

                    for (int cn = row.getFirstCellNum(); cn < row.getLastCellNum(); cn++) {
                        Cell cell = row.getCell(cn);
                        if (cn == row.getFirstCellNum() + FIRST_COLUMN) {
                            nameString = retrieveDataFromOneRow(row, cell, FIRST_COLUMN);
                        } else if (cn == row.getFirstCellNum() + SECOND_COLUMN) {
                            dateString = retrieveDataFromOneRow(row, cell, SECOND_COLUMN);
                        } else if (cn == row.getFirstCellNum() + THIRD_COLUMN) {
                            moneyString = retrieveDataFromOneRow(row, cell, THIRD_COLUMN);
                        } else if (cn == row.getFirstCellNum() + FOURTH_COLUMN) {
                            tagsString = retrieveDataFromOneRow(row, cell, FOURTH_COLUMN);
                        }
                    }
                    if (nameString == null || dateString == null || moneyString == null) {
                        throw new ParseException(Messages.MESSAGE_INVALID_ENTRY_EXCEL_FILE);
                    }
                    if (rn == sheet.getFirstRowNum()) {
                        if (!isFirstRowTitleExist(nameString, dateString, moneyString, tagsString)) {
                            isRightSheet = false;
                        }
                        continue;
                    }
                    moneyString = checkMoneyString(moneyString);
                    records.add(createRecord(nameString, dateString, moneyString, tagsString));
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
    public static void writeExcelSheetIntoDirectory (List<Record> recordList,
                                                     List<SummaryEntry> daySummaryEntryList,
                                                     XSSFSheet recordDataSheet, XSSFSheet summaryDataSheet,
                                                     XSSFWorkbook workbook, String filePath) {
        try {
            writeDataIntoExcelSheetRecord(recordList, recordDataSheet);
            writeDataIntoExcelSheetSummary(daySummaryEntryList, summaryDataSheet);
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(filePath, false);
            workbook.write(out);
            out.close();
            drawChart(summaryDataSheet, filePath, workbook);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Draw the line chart.
     */
    public static void drawChart (XSSFSheet sheet, String filePath, XSSFWorkbook workbook)
                                                                                        throws FileNotFoundException {
        try {
            final int firstRowSheet = sheet.getFirstRowNum() + SECOND_ROW;
            final int lastRowSheet = sheet.getLastRowNum();
            final int firstColumnSheet = sheet.getRow(firstRowSheet).getFirstCellNum();
            final int lastColumnSheet = firstColumnSheet;
            final int firstColumnIncome = firstColumnSheet + FIRST_COLUMN;
            final int lastColumnIncome = firstColumnIncome;
            final int firstColumnOutcome = firstColumnSheet + SECOND_COLUMN;
            final int lastColumnOutcome = firstColumnOutcome;
            final int firstColumnNet = firstColumnSheet + THIRD_COLUMN;
            final int lastColumnNet = firstColumnNet;
            final int widthChart = 12;
            final int heightChart = 18;

            if (!DirectoryPath.isValidFilePath(filePath)) {
                throw new ParseException(Messages.MESSAGE_UNREALISTIC_DIRECTORY);
            }

            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0,
                    firstColumnSheet + FOURTH_COLUMN + THIRD_COLUMN, firstRowSheet,
                    lastColumnSheet + widthChart, firstRowSheet + heightChart);

            Chart chart = drawing.createChart(anchor);
            ChartLegend legend = chart.getOrCreateLegend();
            legend.setPosition(LegendPosition.TOP_RIGHT);

            LineChartData data = chart.getChartDataFactory().createLineChartData();

            // Use a category axis for the bottom axis.
            ChartAxis bottomAxis = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
            ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
            leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

            ChartDataSource<String> xDate = DataSources.fromStringCellRange(
                    sheet, new CellRangeAddress(firstRowSheet, lastRowSheet, firstColumnSheet, lastColumnSheet));
            ChartDataSource<Number> yIncome = DataSources.fromNumericCellRange(
                    sheet, new CellRangeAddress(firstRowSheet, lastRowSheet, firstColumnIncome, lastColumnIncome));
            ChartDataSource<Number> yOutcome = DataSources.fromNumericCellRange(
                    sheet, new CellRangeAddress(firstRowSheet, lastRowSheet, firstColumnOutcome, lastColumnOutcome));
            ChartDataSource<Number> yNet = DataSources.fromNumericCellRange(
                    sheet, new CellRangeAddress(firstRowSheet, lastRowSheet, firstColumnNet, lastColumnNet));

            data.addSeries(xDate, yIncome);
            data.addSeries(xDate, yOutcome);
            data.addSeries(xDate, yNet);

            chart.plot(data, bottomAxis, leftAxis);

            FileOutputStream fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //==========================================SUB METHOD FOR READ EXCEL===============================================
    //TODO: coloring the over-limit records.

    /**
     * Check is the file is being used, if yes, then we cannot read/write the Excel file.
     */
    public static Boolean checkIfFileOpen (String filePath) {
        try {
            File filexssf = new File(filePath);
            File filexssfCopy = new File(filePath);
            if (filexssf.renameTo(filexssfCopy)) {
                logger.info("READ EXCEL: FILE CLOSED");
                return false;
            } else {
                logger.info("READ EXCEL: FILE OPENED");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    /**
     * Check if the first row has the appropriate title.
     */
    private static Boolean isFirstRowTitleExist(String nameString,
                                                String dateString,
                                                String moneyString,
                                                String tagsString) {
        return (nameString.equalsIgnoreCase(NAME_TITLE)
                && dateString.equalsIgnoreCase(DATE_TITLE)
                && moneyString.equalsIgnoreCase(MONEY_TITLE)
                && tagsString.equalsIgnoreCase(TAG_TITLE));
    }

    /**
     * Change the String of Money into appropriate format, as positive number won't have + sign, so we have to add it.
     * For positive number, the "+" will be discarded when you try to add money into Financial Planner --> error.
     */
    public static String checkMoneyString (String moneyString) {
        return (moneyString.charAt(STARTING_INDEX) == MINUS_SIGN_CHAR)
                || moneyString.charAt(STARTING_INDEX) == PLUS_SIGN_CHAR
                ? (moneyString) : (PLUS_SIGN_STRING + moneyString);
    }

    /**
     * Return string for each specific cell, as different method for different column of 1 row.
     */
    public static String retrieveDataFromOneRow (Row row, Cell cell, int columnIndex) {
        if (isStringCellType(cell) && cell != null) {
            return cell.getStringCellValue().trim();
        }
        if (isNumericCellType(cell) && cell != null && columnIndex == THIRD_COLUMN) {
            return Double.toString(cell.getNumericCellValue() * CHANGE_TO_DOUBLE);
        }
        return null;
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
     * Check if the Cell type is String.
     */
    private static Boolean isStringCellType(Cell cell) {
        return (cell.getCellTypeEnum() == CellType.STRING);
    }

    /**
     * Check if the Cell type is Numeric.
     */
    private static Boolean isNumericCellType(Cell cell) {
        return (cell.getCellTypeEnum() == CellType.NUMERIC);
    }

    //==========================================SUB METHOD FOR EXPORT EXCEL=============================================

    /**
     * Write Record data into Excel Sheet.
     */
    private static void writeDataIntoExcelSheetRecord (List<Record> records, XSSFSheet sheet) {
        int rowNum = STARTING_INDEX;
        Row startingRow = sheet.createRow(rowNum);
        writeDataIntoCell(startingRow, FIRST_COLUMN, NAME_TITLE);
        writeDataIntoCell(startingRow, SECOND_COLUMN, DATE_TITLE);
        writeDataIntoCell(startingRow, THIRD_COLUMN, MONEY_TITLE);
        writeDataIntoCell(startingRow, FOURTH_COLUMN, TAG_TITLE);

        for (Record record : records) {
            Row row = sheet.createRow(++rowNum);
            StringBuilder stringBuilder = new StringBuilder();
            writeDataIntoCell(row, FIRST_COLUMN, record.getName().fullName);
            writeDataIntoCell(row, SECOND_COLUMN, record.getDate().value);
            writeDataIntoCell(row, THIRD_COLUMN, record.getMoneyFlow().valueDouble);
            if (record.getTags().size() > RECORD_EMPTY) {
                for (Tag tag : record.getTags()) {
                    stringBuilder.append(tag.tagName + TAG_SEPARATOR);
                }
                writeDataIntoCell(row, FOURTH_COLUMN, stringBuilder.toString()
                        .substring(STARTING_INDEX, stringBuilder.toString().length() - LEFT_OUT_CHARACTER));
            }
        }
    }

    /**
     * Write Summary data into Excel Sheet.
     */
    private static void writeDataIntoExcelSheetSummary (List<SummaryEntry> daySummaryEntryList, XSSFSheet sheet) {
        int rowNum = STARTING_INDEX;
        Row startingRow = sheet.createRow(rowNum);
        writeDataIntoCell(startingRow, FIRST_COLUMN, DATE_TITLE);
        writeDataIntoCell(startingRow, SECOND_COLUMN, INCOME_TITLE);
        writeDataIntoCell(startingRow, THIRD_COLUMN, OUTCOME_TITLE);
        writeDataIntoCell(startingRow, FOURTH_COLUMN, TOTAL_MONEY);
        daySummaryEntryList.sort(SortUtil.compareTimeStampAttribute());
        for (SummaryEntry summaryEntry : daySummaryEntryList) {
            Row row = sheet.createRow(++rowNum);
            writeDataIntoCell(row, FIRST_COLUMN,
                    summaryEntry.getTimeStamp());
            writeDataIntoCell(row, SECOND_COLUMN,
                    Double.parseDouble(removeCurrencySign(summaryEntry.getTotalIncome())));
            writeDataIntoCell(row, THIRD_COLUMN,
                    Double.parseDouble(removeCurrencySign((summaryEntry.getTotalExpense()))));
            writeDataIntoCell(row, FOURTH_COLUMN,
                    Double.parseDouble(removeCurrencySign(summaryEntry.getTotal())));
        }
    }

    /**
     * Remove the character of $ in the String money retrieved.
     */
    private static String removeCurrencySign (String money) {
        String moneyString = null;
        if (money.contains(CURRENCY_STRING)) {
            for (int i = STARTING_INDEX; i < money.length(); i++) {
                if (money.charAt(i) == CURRENCY_CHAR) {
                    moneyString = (money.charAt(STARTING_INDEX) == MINUS_SIGN_CHAR)
                                ? (MINUS_SIGN_STRING + money.substring(++i))
                                : (PLUS_SIGN_STRING + money.substring(++i));
                }
            }
        } else {
            moneyString = (money.charAt(STARTING_INDEX) == MINUS_SIGN_CHAR)
                        ? (PLUS_SIGN_STRING + money)
                        : money;
        }
        return moneyString;
    }

    /**
     * Write data into cell.
     */
    private static void writeDataIntoCell (Row row, int colNum, Object object) {
        if (object instanceof String) {
            row.createCell(colNum).setCellValue((String) object);
        } else {
            row.createCell(colNum).setCellValue((Double) object);
        }
    }

    /**
     * Create the fileName path.
     */
    public static String setPathFile (String nameFile, String directoryPath) {
        return directoryPath + (System.getProperty("file.separator") + nameFile);
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
