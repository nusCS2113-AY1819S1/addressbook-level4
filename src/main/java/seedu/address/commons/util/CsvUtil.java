package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;


/**
 * Handles writing to CSV files.
 */
public class CsvUtil {

    public static final String MESSAGE_ERROR = "Error! could not write to %s.csv";
    public static final String BASE_DIRECTORY = "data/CSVexport/";

    private static final Logger logger = LogsCenter.getLogger(CsvUtil.class);

    private static final String COMMA_DELIM = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String MESSAGE_CREATE_DIRECTORY_ERROR =
            "Error: directory cannot be created.";
    private static final String MESSAGE_FLUSH_CLOSE_ERROR =
            "An error has occurred while flushing/closing the FileWriter.";


    /**
     * Builds the string containing the headers separated by commas.
     */
    private static String parseHeaders(ArrayList<String> headerList) {
        requireNonNull(headerList);

        StringBuilder headers = new StringBuilder();

        int i = 1;
        int size = headerList.size();
        for (String header : headerList) {
            headers.append(header);
            if (i++ < size) {
                headers.append(COMMA_DELIM);
            }
        }

        return headers.toString();
    }

    /**
     * This method creates a CSV file from a list of CSV-friendly String.
     *
     * @return true if writing to file is successful, false otherwise
     */
    public static boolean writeToCsv(
            String fileNameParam,
            ArrayList<String> headersParam,
            ArrayList<String> dataParam) {

        requireNonNull(fileNameParam);
        requireNonNull(headersParam);
        requireNonNull(dataParam);

        String fileName = BASE_DIRECTORY + fileNameParam + ".csv";
        String headers = parseHeaders(headersParam);
        ArrayList<String> data = new ArrayList<>(dataParam);

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.getParentFile().mkdir();
            }
        } catch (SecurityException e) {
            logger.info(MESSAGE_CREATE_DIRECTORY_ERROR);
            e.printStackTrace();
            return false;
        }

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName);

            fileWriter.append(headers);
            fileWriter.append(NEW_LINE_SEPARATOR);

            for (String obj : data) {
                fileWriter.append(obj);
                fileWriter.append(NEW_LINE_SEPARATOR);
            }

        } catch (IOException e) {
            logger.info(String.format(MESSAGE_ERROR, fileNameParam));
            e.printStackTrace();
            return false;
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException | NullPointerException e) {
                logger.info(MESSAGE_FLUSH_CLOSE_ERROR);
                e.printStackTrace();
            }
        }

        return true;
    }
}
