package seedu.address.storage.serializable;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.storage.adapter.XmlAdaptedClassroom;

/**
 * Represents a list of classrooms that is serializable to XML format
 */
@XmlRootElement(namespace = "seedu.address.storage.serializable")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlSerializableClassroomList {
    @XmlElementWrapper(name = "classrooms")
    @XmlElement(name = "class")
    private ArrayList<XmlAdaptedClassroom> classroomList;

    /**
     * Initializes an empty list of classrooms
     */
    public XmlSerializableClassroomList() {
        classroomList = new ArrayList<>();
    }

    public ArrayList<XmlAdaptedClassroom> getClassroomList() {
        return classroomList;
    }

    public void setClassroomList(ArrayList<XmlAdaptedClassroom> classroomList) {
        this.classroomList = classroomList;
    }
}
