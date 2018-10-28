package seedu.address.storage.serializable;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.storage.adapter.XmlAdaptedStudentModule;

/**
 * Represents a list of student-module associations that is serializable to XML format.
 */
@XmlRootElement(namespace = "seedu.address.storage.serializable")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlSerializableStudentModuleList {

    @XmlElementWrapper(name = "studentModules")
    @XmlElement(name = "studentModule")
    private ArrayList<XmlAdaptedStudentModule> studentModules;

    /**
     * Initializes an empty list of student-module associations
     */
    public XmlSerializableStudentModuleList() {
        this.studentModules = new ArrayList<>();
    }

    public ArrayList<XmlAdaptedStudentModule> getStudentModuleList() {
        return studentModules;
    }

    public void setStudentModuleList(ArrayList<XmlAdaptedStudentModule> studentModules) {
        this.studentModules = studentModules;
    }
}
