package seedu.address.storage.serializable;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.storage.adapter.XmlAdaptedGrades;

/**
 * Represents a list of gradebook components that is serializable to XML format
 */
@XmlRootElement(namespace = "seedu.address.model")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlSerializableGradeList {
    @XmlElementWrapper(name = "grade")
    @XmlElement(name = "gradeInfo")
    private ArrayList<XmlAdaptedGrades> gradeList = new ArrayList<XmlAdaptedGrades>();

    public ArrayList<XmlAdaptedGrades> getGradeList() {
        return this.gradeList;
    }
    public void setGradeList(ArrayList<XmlAdaptedGrades> gradeList) {
        this.gradeList = gradeList;
    }
}
