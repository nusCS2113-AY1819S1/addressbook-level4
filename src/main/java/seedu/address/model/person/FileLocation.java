package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.util.FileUtil;

/**
 * Represents a fileLocation.
 * Guarantees: immutable; is valid as declared in {@link #isValidFileLocation(String test)}
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
    public boolean isValidFileLocation(String test) {
        return FileUtil.isValidPath(test);
    }

    /**
     * Returns true if the file path of current object is valid.
     */
    public boolean isValidFileLocation() {
        return FileUtil.isValidPath(fileLocation);
    }

    /**
     * Returns the equivalent Path object.
     */
    public Path toPath() {
        return Paths.get(fileLocation.toString());
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
