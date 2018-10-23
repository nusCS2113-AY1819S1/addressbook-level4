package seedu.planner.storage.xmljaxb;

import static seedu.planner.storage.xmljaxb.XmlAdaptedDirectoryPath.MISSING_DIRECTORY_PATH;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.record.DirectoryPath;

/**
 * An Immutable DirectoryPath that is serializable to XML format
 */
@XmlRootElement(name = "directorypath")
public class XmlSerializableDirectoryPath extends XmlSerializableClass<DirectoryPath> {

    @XmlElement
    private XmlAdaptedDirectoryPath xmlAdaptedDirectoryPath;

    public XmlSerializableDirectoryPath() {
        this.xmlAdaptedDirectoryPath = new XmlAdaptedDirectoryPath();
    }

    public XmlSerializableDirectoryPath(XmlAdaptedDirectoryPath directoryPath) {
        this.xmlAdaptedDirectoryPath = directoryPath;
    }

    public XmlSerializableDirectoryPath(ReadOnlyFinancialPlanner financialPlanner) {
        this();
        xmlAdaptedDirectoryPath = new XmlAdaptedDirectoryPath(financialPlanner.getDirectoryPath());
    }

    /**
     * Converts this RecordList into the model's {@code RecordList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedRecord}.
     */
    public DirectoryPath toModelType() throws IllegalValueException {
        XmlAdaptedDirectoryPath xmlAdaptedDirectoryPath = new XmlAdaptedDirectoryPath();
        xmlAdaptedDirectoryPath = this.xmlAdaptedDirectoryPath;
        DirectoryPath directoryPath = xmlAdaptedDirectoryPath.toModelType();
        if (directoryPath.getDirectoryPath() == null) {
            throw new IllegalValueException(MISSING_DIRECTORY_PATH);
        }
        if (!DirectoryPath.isValidDirectory(directoryPath.getDirectoryPathValue())) {
            throw new IllegalValueException(DirectoryPath.MESSAGE_DIRECTORYPATH_CONSTRAINTS);
        }
        if (directoryPath.isExistingDirectory(directoryPath.getDirectoryPathValue())) {
            throw new IllegalValueException(XmlAdaptedDirectoryPath.NONEXISTENT_DIRECTORY_PATH);
        }
        return directoryPath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof XmlSerializableDirectoryPath)) {
            return false;
        }
        return xmlAdaptedDirectoryPath.equals(((XmlSerializableDirectoryPath) other).xmlAdaptedDirectoryPath);
    }

}
