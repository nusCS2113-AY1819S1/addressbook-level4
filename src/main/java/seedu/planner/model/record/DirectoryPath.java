package seedu.planner.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.AppUtil.checkArgument;

import java.io.File;
import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.Messages;

/**
 * File path is used to store the preferable location to store the Excel File when user want to export the Data
 * in Excel or the user want to achieve the data.
 */
public class DirectoryPath {
    public static final String DEFAULT_USER_DIRECTORY = System.getProperty("user.dir");

    public static final String DEFAULT_HOME_DIRECTORY = System.getProperty("user.home");
    public static final String DIRECTORY_REGREX = "([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+\\\\?";

    public static final DirectoryPath HOME_DIRECTORY = new DirectoryPath(DEFAULT_HOME_DIRECTORY);

    public static final DirectoryPath WORKING_DIRECTORY = new DirectoryPath(DEFAULT_USER_DIRECTORY);

    //public static final String S = "^(([a-zA-Z]:)|((\\\\|/){2,4}\\w+)\\$?)((\\\\|/)(\\w[\\w ]*.*))+\\.([a-zA-Z0-9]+)$"

    public static final String FILE_SEPERATOR = System.getProperty("file.separator");

    public static final String MESSAGE_DIRECTORYPATH_CONSTRAINTS =
            "Path Directory should exist and only contains "
            + "Alphanumeric(a-z, A-Z, 0-9), Underscore(_), Dot(.), Hyphen(-).\n"
            + "Please take note that inappropriate Directory format or unrealistic directory will result errors.\n"
            + "If the user has not set the Directory Path for the Excel file, "
            + "the Directory will be set to the User's Home Directory, named "
            + DEFAULT_HOME_DIRECTORY + "\n"
            + "For e.g., the appropriate directory is "
            + DEFAULT_HOME_DIRECTORY
            + "  or  "
            + DEFAULT_USER_DIRECTORY;

    private static Logger loggerStatic = LogsCenter.getLogger(DirectoryPath.class);

    private String value;
    private Logger logger = LogsCenter.getLogger(DirectoryPath.class);

    public DirectoryPath(String dirPath) {
        requireNonNull(dirPath);
        //checkArgument(isValidFormat(removeWhiteSpace(dirPath)),
        //String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_DIRECTORYPATH_CONSTRAINTS));
        checkArgument(isExistingDirectory(dirPath),
                String.format(Messages.MESSAGE_UNREALISTIC_DIRECTORY, MESSAGE_DIRECTORYPATH_CONSTRAINTS));
        this.value = dirPath;
    }
    public DirectoryPath() {
        this.value = getDefaultHomeDirectoryValue();
    }
    public DirectoryPath(DirectoryPath path) {
        requireNonNull(path);
        checkArgument(isExistingDirectory(path.getDirectoryPath().getDirectoryPathValue()));
        this.value = path.getDirectoryPath().getDirectoryPathValue();
    }

    /**
     * Remove all the whitespace in the directory path.
     * @param dirPath the given directory path.
     * @return String with no whitespace.
     */
    public static String removeWhiteSpace (String dirPath) {
        requireNonNull(dirPath);
        StringBuilder stringBuilder = new StringBuilder();
        String[] words = dirPath.split(" ");
        for (String word : words) {
            stringBuilder.append(word.trim());
        }
        loggerStatic.info("The String without the whitespace" + stringBuilder);
        return dirPath.replace("\\s", "");
    }
    //TODO 2: create a path file class and make a global change
    //TODO 3: read the excel files
    //TODO 4: delete the period of time records
    //TODO 5: archieve
    //TODO 6: if you have time, please implement to draw graph for the summary

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
                + "hence, we will use the Default User's Home Directory, named %1$s", getDefaultHomeDirectoryValue());
    }

    /**
     * Check if the Directory Path is valid or not by checking Valid format and Existing path in the User's computer.
     * @param dirPath the given Directory path.
     * @return boolean value.
     */
    public static Boolean isValidDirectory(String dirPath) {
        return isExistingDirectoryStatic(dirPath);
    }

    /**
     * Check whether the Directory follows the format.
     * @param dirPath the given Directory path.
     * @return boolean value.
     */
    public Boolean isValidFormat(String dirPath) {
        return dirPath.matches(DIRECTORY_REGREX);
    }

    /**
     * Check whether the given directory is realistic or not.
     * @return boolean value to indicate whether the directory exists.
     */
    public Boolean isExistingDirectory(String dirPath) {
        File file = new File(dirPath);
        return file.isDirectory() && file.exists();
    }

    /**
     * Check whether the given directory is realistic or not.
     * @return boolean value to indicate whether the directory exists.
     */
    public static Boolean isExistingDirectoryStatic(String dirPath) {
        File file = new File(dirPath);
        return file.isDirectory() && file.exists();
    }

    public String getValue() {
        return value;
    }

    public DirectoryPath getDirectoryPath() {
        return (value == null) ? HOME_DIRECTORY : new DirectoryPath(value);
    }

    public String getDirectoryPathValue() {
        return value;
    }

    public static String getDefaultWorkingDirectoryValue() {
        return DEFAULT_USER_DIRECTORY;
    }

    public static String getDefaultHomeDirectoryValue() {
        return DEFAULT_HOME_DIRECTORY;
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
                && this.getDirectoryPath().getDirectoryPathValue()
                == ((DirectoryPath) other).getDirectoryPath().getDirectoryPathValue());
    }

}
