package seedu.planner.storage.xmljaxb;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.record.DirectoryPath;

/**
 * A JAXB-friendly version of the {@code Directory Path}
 */
public class XmlAdaptedDirectoryPath {
    public static final String MISSING_DIRECTORY_PATH = "The Directory Path is missing!";
    public static final String NONEXISTENT_DIRECTORY_PATH = "The Directory Path is non-existent!";
    public static final String DIRECTORY_PATH = "Directory Path ";

    @XmlElement(required = true)
    private DirectoryPath directoryPath;

    public XmlAdaptedDirectoryPath() {
        this.directoryPath = new DirectoryPath();
    }

    public XmlAdaptedDirectoryPath(DirectoryPath directoryPath) {
        this.directoryPath = directoryPath;
    }

    public XmlAdaptedDirectoryPath(String path) {
        this.directoryPath = new DirectoryPath(path);
    }

    public XmlAdaptedDirectoryPath(ReadOnlyFinancialPlanner financialPlanner) {
        this.directoryPath = financialPlanner.getDirectoryPath();
    }

    /**
     * Converts this JAXB-friendly object into the model's {@DirectoryPath} object
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted summary
     */
    public DirectoryPath toModelType() throws IllegalValueException {
        if (directoryPath.getDirectoryPath() == null) {
            throw new IllegalValueException(MISSING_DIRECTORY_PATH);
        }
        if (!DirectoryPath.isValidDirectory(directoryPath.getDirectoryPathValue())) {
            throw new IllegalValueException(DirectoryPath.MESSAGE_DIRECTORYPATH_CONSTRAINTS);
        }
        return new DirectoryPath(directoryPath.getDirectoryPath().getDirectoryPathValue());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedDirectoryPath)) {
            return false;
        }

        XmlAdaptedDirectoryPath otherSummary = (XmlAdaptedDirectoryPath) other;
        return Objects.equals(directoryPath, otherSummary.directoryPath);
    }
}
