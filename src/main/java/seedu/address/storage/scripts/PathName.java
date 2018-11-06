package seedu.address.storage.scripts;

/**
 * Check if the script path name is in valid format .
 */
public class PathName {
    public static final String MESSAGE_VALIDATION_REGEX = "^[/](?!/)(?!.*//)[^\"<>?:*\\s]+(?<!/)[/]$";

    /**
     * Returns true if a given string is a valid text file name.
     */
    public static boolean isValidPath(String path) {
        return path.matches(MESSAGE_VALIDATION_REGEX);
    }
}
