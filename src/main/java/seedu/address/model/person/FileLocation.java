package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.util.FileUtil;

/**
 * Represents a fileLocation. Notice that Java has its own "Path" class already...
 * Guarantees: immutable; is valid as declared in {@link #isValidFileLocation(String)}
 */
public class FileLocation {

    public static final String MESSAGE_CONSTRAINTS =
            "FileLocation should be a valid file path.";

    public final String fileLocation;

    /**
     * Constructs a {@code FileLocation}.
     *
     * @param fileLocation A valid fileLocation.
     */
    public FileLocation(String fileLocation) {
        requireNonNull(fileLocation);
        checkArgument(isValidFileLocation(fileLocation), MESSAGE_CONSTRAINTS);
        this.fileLocation = fileLocation;
    }

    /**
     * Returns true if a given string is a valid file path.
     */
    public static boolean isValidFileLocation(String test) {
        return FileUtil.isValidPath(test);
    }


    @Override
    public String toString() {
        return fileLocation;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FileLocation // instanceof handles nulls
                && fileLocation.equals(((FileLocation) other).fileLocation)); // state check
    }

    @Override
    public int hashCode() {
        return fileLocation.hashCode();
    }

}
