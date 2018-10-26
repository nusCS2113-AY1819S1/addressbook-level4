package seedu.planner.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.AppUtil.checkArgument;
import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.Messages;
import seedu.planner.commons.util.FileUtil;

/**
 * File path is used to store the preferable location to store the Excel File when user want to export the Data
 * in Excel or the user want to achieve the data.
 */
public class DirectoryPath {
    public static final String DEFAULT_USER_DIRECTORY = System.getProperty("user.dir");
    public static final String DEFAULT_HOME_DIRECTORY = System.getProperty("user.home");
    public static final String DIRECTORY_REGREX = "([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+\\\\?";
    public static final String FILE_SEPERATOR = System.getProperty("file.separator");

    public static final String MESSAGE_DIRECTORYPATH_CONSTRAINTS =
            "Path Directory should exist and only contains "
            + "Alphanumeric(a-z, A-Z, 0-9), Underscore(_), Dot(.), Hyphen(-).\n"
            + "Please take note that inappropriate Directory format or unrealistic directory will result errors.\n"
            + "For e.g., the appropriate directory is C:"
            + DEFAULT_HOME_DIRECTORY
            + "or "
            + DEFAULT_USER_DIRECTORY;

    private String dirPath;
    private Logger logger = LogsCenter.getLogger(DirectoryPath.class);

    public DirectoryPath(String dirPath) {
        requireNonNull(dirPath);
        checkArgument(isValidFormat(removeWhiteSpace(dirPath)),
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_DIRECTORYPATH_CONSTRAINTS));
        checkArgument(FileUtil.isValidDirectory(dirPath),
                String.format(Messages.MESSAGE_UNREALISTIC_DIRECTORY, MESSAGE_DIRECTORYPATH_CONSTRAINTS));
        this.dirPath = dirPath;
    }

    /**
     * Remove all the whitespace in the directory path.
     * @param dirPath the given directory path.
     * @return String with no whitespace.
     */
    public static String removeWhiteSpace (String dirPath) {
        requireNonNull(dirPath);
        return dirPath.replace("\\s", "");
    }
    //TODO: CLAIM YOUR EFFORT FIRST (diagram, github)

    //TODO 1: remove all whitespace for the path file before checking the regrex
    //TODO 2: check whether directories exist or not, if not then dont have to check regrex.
    //TODO 2: create a path file class and make a global change
    //TODO 3: read the excel files
    //TODO 4: delete the period of time records
    //TODO 5: archieve
    //TODO 6: if you have time, please implement to draw graph for the summary

    public static Boolean isValidFormat (String dirPath) {
        return dirPath.matches(DIRECTORY_REGREX);
    }

    public String getDirPath() {
        return dirPath;
    }

    public static String getDefaultWorkingDirectory() {
        return DEFAULT_USER_DIRECTORY;
    }

    public static String getDefaultHomeDirectory() {
        return DEFAULT_HOME_DIRECTORY;
    }
}
