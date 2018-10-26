package seedu.planner.commons.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import seedu.planner.model.record.Record;

/**
 * Transfer data into Excel file utilities.
 */
public class ExcelUtil {
    /**
     * Export data of records into Map.
     * @param recordList list of records.
     * @return a map containing the ID for each record, name, date, money spent/received.
     */
    public static Map<String, Object[]> exportData (List<Record> recordList) {
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        int id = 0;
        data.put(String.valueOf(id++), new Object[] {"NAME", "DATE", "MONEY SPENT/RECEIVED"});
        for (Record record : recordList) {
            data.put(String.valueOf(id++), new Object[] {
                    record.getName().fullName,
                    record.getDate().value,
                    record.getMoneyFlow().valueDouble});
        }
        return data;
    }
    /**
     * Write the data into Excel sheet
     * @param data
     * @param sheet the Excel sheet which have
     */
    public static void writeDataIntoExcelSheet (Map<String, Object[]> data, XSSFSheet sheet) {
        Set<String> keySet = data.keySet();
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
        }
    }
}
