package seedu.planner.model;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.AppUtil.checkArgument;

import java.io.File;
import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.Messages;

//@author nguyenngoclinhchi
/**
 * File path is used to store the preferable location to store the Excel File when user want to export the Data
 * in Excel or the user want to archive the data.
 */
public class DirectoryPath {
    public static final String WORKING_DIRECTORY_STRING = System.getProperty("user.dir");
    public static final String HOME_DIRECTORY_STRING = System.getProperty("user.home");

    public static final String DIRECTORY_REGREX = "([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+\\\\?";

    public static final DirectoryPath HOME_DIRECTORY = new DirectoryPath(HOME_DIRECTORY_STRING);

    public static final DirectoryPath WORKING_DIRECTORY = new DirectoryPath(WORKING_DIRECTORY_STRING);

    public static final String FILE_SEPERATOR = System.getProperty("file.separator");

    public static final String MESSAGE_DIRECTORYPATH_CONSTRAINTS =
            "Path Directory should exist and only contains "
                    + "Alphanumeric(a-z, A-Z, 0-9), Underscore(_), Dot(.), Hyphen(-), "
                    + "and Backslash, only one to separate between directory.\n"
                    + "Please take note that inappropriate Directory format "
                    + "or unrealistic directory will result errors.\n"
                    + "If the user has not set the Directory Path for the Excel file, "
                    + "the Directory will be set to the User's Working Directory, named "
                    + WORKING_DIRECTORY_STRING + "\n"
                    + "For e.g., the appropriate directory is "
                    + HOME_DIRECTORY_STRING
                    + "  or  "
                    + WORKING_DIRECTORY_STRING;

    private static Logger loggerStatic = LogsCenter.getLogger(DirectoryPath.class);

    private String value;
    private Logger logger = LogsCenter.getLogger(DirectoryPath.class);

    public DirectoryPath(String dirPath) {
        requireNonNull(dirPath);
        checkArgument((isExistingDirectory(dirPath) || isExistingFilePath(dirPath)),
                String.format(Messages.MESSAGE_UNREALISTIC_DIRECTORY, MESSAGE_DIRECTORYPATH_CONSTRAINTS));
        this.value = dirPath;
    }
    public DirectoryPath() {
        this.value = getDefaultHomeDirectoryValue();
    }
    public DirectoryPath(DirectoryPath path) {
        requireNonNull(path);
        checkArgument(isValidDirectory(path.getDirectoryPath().getDirectoryPathValue())
                    || isValidFilePath(path.getDirectoryPath().getDirectoryPathValue()));
        this.value = path.getDirectoryPath().getDirectoryPathValue();
    }

    /**
     * Remove all the whitespace in the directory path.
     * @param dirPath the given directory path.
     * @return String with no whitespace.
     */
    public static String removeWhiteSpace(String dirPath) {
        requireNonNull(dirPath);
        StringBuilder stringBuilder = new StringBuilder();
        String[] words = dirPath.split("\\s+");

        for (String word : words) {
            stringBuilder.append(word.trim());
        }
        loggerStatic.info("The String without the whitespace" + stringBuilder.toString());
        return stringBuilder.toString();
    }

    /**
     * Check if the Directory Path is set by checking if the path == null or not.
     * @return boolean value.
     */
    public boolean isDirectorySet() {
        return !(value == null);
    }

    /**
     * Update/set the directory path for the excel file.
     * @param directoryPath the String directory path for the excel file.
     */
    public void updateDirectoryPath(String directoryPath) {
        requireNonNull(directoryPath);
        value = directoryPath;
        logger.info("The new directory is " + directoryPath);
    }

    /**
     * Update/set the directory path for the excel file.
     * @param path the Directory Path for the excel file.
     */
    public void updateDirectoryPath(DirectoryPath path) {
        requireNonNull(path);
        value = path.getDirectoryPathValue();
        logger.info("The new directory is " + path);
    }
    /**
     * Change the user optional Directory path to null.
     * Therefore, when we want to
     */
    public String removeDirectoryPath() {
        value = null;
        return String.format("The User's choose Directory path has been removed, "
                        + "hence, we will use the Default User's Working Directory, named %1$s",
                getDefaultWorkingDirectoryValue());
    }

    /**
     * Check if the Directory Path is valid or not by checking Valid format and Existing path in the User's computer.
     * @param dirPath the given Directory path.
     * @return boolean value.
     */
    public static Boolean isValidDirectory(String dirPath) {
        return isExistingDirectory(dirPath);
    }

    /**
     * Check if the Directory Path is valid or not by checking Valid format and Existing path in the User's computer.
     * @param filePath the given Directory path.
     * @return boolean value.
     */
    public static Boolean isValidFilePath(String filePath) {
        return isExistingFilePath(filePath);
    }

    /**
     * Check whether the Directory follows the format.
     * @param dirPath the given Directory path.
     * @return boolean value.
     */
    public static Boolean isValidFormat(String dirPath) {
        return removeWhiteSpace(dirPath).matches(DIRECTORY_REGREX);
    }

    /**
     * Check whether the given directory is realistic or not.
     * @return boolean value to indicate whether the directory exists.
     */
    public static Boolean isExistingDirectory(String dirPath) {
        File file = new File(dirPath);
        return file.isDirectory();
    }

    /**
     * Check whether the given directory is realistic or not.
     * @return boolean value to indicate whether the directory exists.
     */
    public static Boolean isExistingFilePath(String dirPath) {
        File file = new File(dirPath);
        return file.isFile();
    }

    /**
     * Check whether the given directory is realistic or not.
     * @param filePath the given file path is realistic or not.
     * @return boolean value to indicate whether the directory exists.
     */
    public static Boolean isExistingFile(String filePath) {
        File file = new File(filePath);
        return file.isFile();
    }

    public String getValue() {
        return value;
    }

    public DirectoryPath getDirectoryPath() {
        return (value == null) ? WORKING_DIRECTORY : new DirectoryPath(value);
    }

    public String getDirectoryPathValue() {
        return (value == null) ? WORKING_DIRECTORY_STRING : value;
    }

    public static String getDefaultWorkingDirectoryValue() {
        return WORKING_DIRECTORY_STRING;
    }

    public static String getDefaultHomeDirectoryValue() {
        return HOME_DIRECTORY_STRING;
    }

    public static DirectoryPath getDefaultWorkingDirectory() {
        return WORKING_DIRECTORY;
    }

    public static DirectoryPath getDefaultHomeDirectory() {
        return HOME_DIRECTORY;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DirectoryPath // instanceof handles nulls
                && (this.getDirectoryPath().getDirectoryPathValue())
                .equals(((DirectoryPath) other).getDirectoryPath().getDirectoryPathValue()));
    }
}
