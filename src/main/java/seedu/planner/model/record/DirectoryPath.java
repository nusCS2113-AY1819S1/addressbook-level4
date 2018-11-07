package seedu.planner.model.record;

import static java.util.Objects.requireNonNull;

//@author nguyenngoclinhchi
/**
 * File path is used to store the preferable location to store the Excel File when user want to export the Data
 * in Excel or the user want to archive the data.
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

    public DirectoryPath(String dirPath) {
        requireNonNull(dirPath);
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
