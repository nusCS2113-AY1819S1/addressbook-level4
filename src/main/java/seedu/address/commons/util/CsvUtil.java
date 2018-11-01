package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Handles writing to CSV files.
 */
public class CsvUtil {

    // private static final String BASE_DIRECTORY = "data/CSVexport/";
    private static final String BASE_DIRECTORY = System.getProperty("user.home") + "/";
    private static final String COMMA_DELIM = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String MESSAGE_ERROR = "Error! could not write to %s.";


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
     * This method creates a CSV file from a CSV-adapted Java object.
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
            e.printStackTrace();
            return false;
        }

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName);

            fileWriter.append(headers.toString());
            fileWriter.append(NEW_LINE_SEPARATOR);

            for (String obj : data) {
                fileWriter.append(obj);
                fileWriter.append(NEW_LINE_SEPARATOR);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException | NullPointerException e) {
                System.out.println("An error has occurred while flushing/closing the FileWriter.");
                e.printStackTrace();
            }
        }

        return true;
    }
}
